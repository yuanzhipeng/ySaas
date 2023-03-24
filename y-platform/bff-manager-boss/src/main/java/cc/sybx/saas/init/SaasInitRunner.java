package cc.sybx.saas.init;

import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.common.redis.CacheKeyConstant;
import cc.sybx.saas.common.util.Constants;
import cc.sybx.saas.redis.RedisHsetBean;
import cc.sybx.saas.redis.RedisService;
import cc.sybx.saas.saas.api.provider.DomainStoreRelaQueryProvider;
import cc.sybx.saas.saas.api.request.DomainStoreRelaListRequest;
import cc.sybx.saas.saas.api.response.domainstorerela.DomainStoreRelaListResponse;
import cc.sybx.saas.saas.bean.vo.DomainStoreRelaVO;
import cc.sybx.saas.setting.api.provider.AuditQueryProvider;
import cc.sybx.saas.setting.api.request.ConfigQueryRequest;
import cc.sybx.saas.setting.bean.enums.ConfigKey;
import cc.sybx.saas.setting.bean.enums.ConfigType;
import cc.sybx.saas.setting.bean.vo.ConfigVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Saas 系统初始化
 */
@Slf4j
@Order(7)
@Component
public class SaasInitRunner implements CommandLineRunner {
    @Resource
    private RedisService redisService;
    @Resource
    private AuditQueryProvider auditQueryProvider;
    @Resource
    private DomainStoreRelaQueryProvider domainStoreRelaQueryProvider;

    @Override
    public void run(String... args) throws Exception {
        ConfigQueryRequest config = new ConfigQueryRequest();
        config.setConfigKey(ConfigKey.S2BAUDIT.toString());
        config.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        List<ConfigVO> configVOList = auditQueryProvider.getConfigKey(config).getContext().getConfigVOList();
        if(CollectionUtils.isNotEmpty(configVOList)){
            Optional<ConfigVO> domainOptional = configVOList.stream().filter(conf -> StringUtils.equals(conf.getConfigType(),
                    ConfigType.SAAS_DOMAIN.toValue())).findFirst();
            ConfigVO domain = domainOptional.get();
            redisService.setString(CacheKeyConstant.SAAS_DOMAIN, domain.getContext());
            this.cacheForRedis(domain.getContext());
            log.info("Saas化系统启动");
        } else {
            log.error("Saas化系统初始化失败!");
        }
    }

    /**
     * 缓存信息到redis
     */
    private void cacheForRedis(String mainHost) {
        DomainStoreRelaListResponse domainStoreRelaListResponse =
                domainStoreRelaQueryProvider.list(DomainStoreRelaListRequest.builder().delFlag(DeleteFlag.NO).build()).getContext();
        cacheDomain(domainStoreRelaListResponse, mainHost);
        cacheDomainForNginx(domainStoreRelaListResponse, mainHost);
    }

    /**
     * 缓存域名映射
     */
    private void cacheDomain(DomainStoreRelaListResponse domainStoreRelaListResponse, String mainHost) {
        redisService.delete(CacheKeyConstant.PC_SAAS_KEY);
        redisService.delete(CacheKeyConstant.MOBILE_SAAS_KEY);
        redisService.delete(CacheKeyConstant.APP_OR_MINI_SAAS_KEY);
        if (Objects.nonNull(domainStoreRelaListResponse)) {
            List<DomainStoreRelaVO> list = domainStoreRelaListResponse.getDomainStoreRelaVOList();
            if (CollectionUtils.isNotEmpty(list)) {
                List<RedisHsetBean> pcList = new ArrayList<>();
                List<RedisHsetBean> mobileList = new ArrayList<>();
                List<RedisHsetBean> appList = new ArrayList<>();
                list.forEach(domain -> {
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(domain.getH5Domain())) {
                        RedisHsetBean h5Domain = new RedisHsetBean();
                        if(domain.getIsH5FullDomain() == 0){
                            // 非自定义全域名需拼主域名
                            h5Domain.setField((domain.getH5Domain() + mainHost).toLowerCase());
                        }else{
                            h5Domain.setField(domain.getH5Domain().toLowerCase());
                        }
                        h5Domain.setValue(JSON.toJSONString(domain));
                        mobileList.add(h5Domain);
                    }
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(domain.getPcDomain())) {
                        RedisHsetBean pcDomain = new RedisHsetBean();
                        pcDomain.setValue(JSON.toJSONString(domain));
                        if(domain.getIsPcFullDomain() == 0){
                            // 非自定义全域名需拼主域名
                            pcDomain.setField((domain.getPcDomain() + mainHost).toLowerCase());
                        }else{
                            pcDomain.setField(domain.getPcDomain().toLowerCase());
                        }
                        pcList.add(pcDomain);
                    }
                    RedisHsetBean storeIdDomain = new RedisHsetBean();
                    storeIdDomain.setValue(JSON.toJSONString(domain));
                    storeIdDomain.setField(domain.getStoreId().toString());
                    appList.add(storeIdDomain);
                });
                if (CollectionUtils.isNotEmpty(pcList)) {
                    redisService.hsetPipeline(CacheKeyConstant.PC_SAAS_KEY, pcList);
                }
                if (CollectionUtils.isNotEmpty(mobileList)) {
                    redisService.hsetPipeline(CacheKeyConstant.MOBILE_SAAS_KEY, mobileList);
                }
                if (CollectionUtils.isNotEmpty(appList)) {
                    redisService.hsetPipeline(CacheKeyConstant.APP_OR_MINI_SAAS_KEY, appList);
                }
            }
        }
    }

    /**
     * 缓存域名映射提供给nginx
     */
    private void cacheDomainForNginx(DomainStoreRelaListResponse domainStoreRelaListResponse, String mainHost) {
        redisService.delete(CacheKeyConstant.CACHE_PC_KEY_FOR_NGINX);
        redisService.delete(CacheKeyConstant.CACHE_MOBILE_KEY_FOR_NGINX);
        redisService.delete(CacheKeyConstant.CACHE_PC_FULL_KEY_FOR_NGINX);
        redisService.delete(CacheKeyConstant.CACHE_MOBILE_FULL_KEY_FOR_NGINX);

        if (Objects.nonNull(domainStoreRelaListResponse)) {
            List<DomainStoreRelaVO> list = domainStoreRelaListResponse.getDomainStoreRelaVOList();
            if (CollectionUtils.isNotEmpty(list)) {
                // pc非自定义域名
                redisService.setString(CacheKeyConstant.CACHE_PC_KEY_FOR_NGINX,
                        list.stream()
                                .filter(d -> org.apache.commons.lang3.StringUtils.isNotBlank(d.getPcDomain()) && d.getIsPcFullDomain() == 0)
                                .map(d -> (d.getPcDomain() + mainHost).toLowerCase())
                                .distinct()
                                .collect(Collectors.joining(" ")));
                // h5非自定义域名
                redisService.setString(CacheKeyConstant.CACHE_MOBILE_KEY_FOR_NGINX,
                        list.stream()
                                .filter(d -> org.apache.commons.lang3.StringUtils.isNotBlank(d.getH5Domain()) && d.getIsH5FullDomain() == 0)
                                .map(d -> (d.getH5Domain() + mainHost).toLowerCase())
                                .distinct()
                                .collect(Collectors.joining(" ")));
                // pc自定义域名 不拼mainhost
                redisService.setString(CacheKeyConstant.CACHE_PC_FULL_KEY_FOR_NGINX,
                        list.stream()
                                .filter(d -> org.apache.commons.lang3.StringUtils.isNotBlank(d.getPcDomain()) && d.getIsPcFullDomain() == 1)
                                .map(d -> d.getPcDomain().toLowerCase())
                                .distinct()
                                .collect(Collectors.joining(" ")));
                // h5自定义域名 不拼mainhost
                redisService.setString(CacheKeyConstant.CACHE_MOBILE_FULL_KEY_FOR_NGINX,
                        list.stream()
                                .filter(d -> org.apache.commons.lang3.StringUtils.isNotBlank(d.getH5Domain()) && d.getIsH5FullDomain() == 1)
                                .map(d -> d.getH5Domain().toLowerCase())
                                .distinct()
                                .collect(Collectors.joining(" ")));
            }
        }
    }
}
