package cc.sybx.saas.util;

import cc.sybx.saas.common.exception.SaasRuntimeException;
import cc.sybx.saas.common.redis.CacheKeyConstant;
import cc.sybx.saas.common.util.CommonErrorCode;
import cc.sybx.saas.common.util.Constants;
import cc.sybx.saas.common.util.HttpUtil;
import cc.sybx.saas.saas.api.provider.DomainStoreRelaQueryProvider;
import cc.sybx.saas.saas.api.request.domainstorerela.DomainStoreRelaByDomainRequest;
import cc.sybx.saas.saas.api.response.domainstorerela.DomainStoreRelaByDomainResponse;
import cc.sybx.saas.saas.bean.vo.DomainStoreRelaVO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Component
public class CommonUtil {
    @Resource
    private RedisService redisService;
    @Resource
    private DomainStoreRelaQueryProvider domainStoreRelaQueryProvider;


    /**
     * 获取当前登录的店铺信息ID
     * 若没有设置默认值为boss对应的店铺ID
     *
     * @return
     */
    public Long getStoreIdWithDefault() {
        return getStoreIdWithDefault("");
    }

    /**
     * 获取当前登录的店铺信息ID
     * 若没有设置默认值为boss对应的店铺ID
     *
     * @return
     */
    public Long getStoreIdWithDefault(String url) {
        DomainStoreRelaVO domainStoreRelaVO = getDomainInfo(url);
        return getStoreIdWithDefault(domainStoreRelaVO);
    }

    /**
     * 获取当前登录的店铺信息ID
     * 若没有设置默认值为boss对应的店铺ID
     *
     * @return
     */
    public Long getStoreIdWithDefault(DomainStoreRelaVO domainStoreRelaVO) {
        if (Objects.nonNull(domainStoreRelaVO)) {
            return domainStoreRelaVO.getStoreId();
        }
        return Constants.BOSS_DEFAULT_STORE_ID;
    }

    /**
     * 获取当前品牌商城Id
     * 该方法非特殊情况下, 不要调用
     * <p>
     * 支付的时候会跳转页面打开第三方支付的页面, 此时无法获取到origin,
     * 因此我们手动给他添加一个url.
     * 参考示例: h5端支付宝支付
     *
     * @param url
     * @return
     */
    public DomainStoreRelaVO getDomainInfo(String url) {
        // 优先判断App SAAS鉴权
        String saasAuth = HttpUtil.getRequest().getHeader("saas-auth");
        if (StringUtils.isNoneBlank(saasAuth)) {
            return wrapperVO(this.getStoreInfoBySaasAuth(saasAuth));
        }
        String referer = HttpUtil.getServerName();
//        if (StringUtils.isBlank(referer)) {
//            referer = HttpUtil.getRequest().getHeader("Origin");
//        }
//        if (StringUtils.isBlank(referer)) {
//            referer = HttpUtil.getRequest().getHeader("Referer");
//        }
//        if (StringUtils.isBlank(referer)) {
//            return null;
//        }
//        referer = referer.substring(referer.indexOf("://") + 3);
        log.info("request origin is: " + referer);
        // lv_test_del测试环境,设置测试数据
//        if("127.0.0.1".equals(referer)){
//            referer = "site-mall-test.saas.com";
//        }
        DomainStoreRelaVO domainStore = this.findDomainStore(referer);
        //获取域名对应品牌商城信息
        if (Objects.isNull(domainStore)) {
            String domain = this.getSaasDomain();
            int index = referer.indexOf(domain);
            referer = referer.substring(0, index);
            DomainStoreRelaByDomainResponse domainStoreRelaByDomainResponse =
                    domainStoreRelaQueryProvider.findByDomain(DomainStoreRelaByDomainRequest.builder()
                            .domain(referer).build()).getContext();
            //获取域名对应品牌商城信息
            if (Objects.nonNull(domainStoreRelaByDomainResponse.getDomainStoreRelaVO())) {
                return wrapperVO(domainStoreRelaByDomainResponse.getDomainStoreRelaVO(), domain);
            } else {
                throw new SaasRuntimeException(CommonErrorCode.METHOD_NOT_ALLOWED);
            }
        }
        return wrapperVO(domainStore);
    }

    public DomainStoreRelaVO findDomainStore(String domain) {
        Map<String, String> pcList = redisService.hgetall(CacheKeyConstant.PC_SAAS_KEY);
        Map<String, String> mobileList = redisService.hgetall(CacheKeyConstant.MOBILE_SAAS_KEY);
        Map<String, String> domains = new HashMap<>();
        domains.putAll(pcList);
        domains.putAll(mobileList);
        Optional<String> optional = domains.keySet().stream()
                .filter(s -> StringUtils.isNotBlank(s) && s.equalsIgnoreCase(domain)).findFirst();
        if (optional.isPresent()) {
            String domainInfo = domains.get(optional.get());

            return JSONObject.parseObject(domainInfo, DomainStoreRelaVO.class);
        }
        return null;
    }

    /**
     * 封装成完整域名
     *
     * @param domainStore
     * @return
     */
    private DomainStoreRelaVO wrapperVO(DomainStoreRelaVO domainStore) {
        return wrapperVO(domainStore, this.getSaasDomain());
    }

    /**
     * 封装成完整域名
     *
     * @param domainStore
     * @param saasDomain
     * @return
     */
    private DomainStoreRelaVO wrapperVO(DomainStoreRelaVO domainStore, String saasDomain) {
        if(domainStore.getIsH5FullDomain() == 1){
            domainStore.setFullH5Domain("https://" + domainStore.getH5Domain().toLowerCase());
        }else{
            domainStore.setFullH5Domain(("https://" + domainStore.getH5Domain() + saasDomain).toLowerCase());
        }
        if(domainStore.getIsPcFullDomain() == 1){
            domainStore.setFullPcDomain("https://" + domainStore.getPcDomain().toLowerCase());
        }else{
            domainStore.setFullPcDomain(("https://" + domainStore.getPcDomain() + saasDomain).toLowerCase());
        }
        return domainStore;
    }

    /**
     * 获得主域名
     *
     * @return
     */
    private String getSaasDomain() {
        return redisService.getString(CacheKeyConstant.SAAS_DOMAIN);
    }

    /**
     * App鉴权 获取门店信息
     *
     * @return
     */
    public DomainStoreRelaVO getStoreInfoBySaasAuth(String saasAuth) {
        //Base64解密获取门店id
        String storeId = new String(Base64.getUrlDecoder().decode(saasAuth.getBytes()));
        String info = redisService.hget(CacheKeyConstant.APP_OR_MINI_SAAS_KEY, storeId);
        if (StringUtils.isNotBlank(info)) {
            return JSONObject.parseObject(info, DomainStoreRelaVO.class);
        }
        throw new SaasRuntimeException(CommonErrorCode.METHOD_NOT_ALLOWED);
    }
}
