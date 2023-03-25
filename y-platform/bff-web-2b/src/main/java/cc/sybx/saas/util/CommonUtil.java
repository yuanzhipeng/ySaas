package cc.sybx.saas.util;

import cc.sybx.saas.common.exception.SaasRuntimeException;
import cc.sybx.saas.common.redis.CacheKeyConstant;
import cc.sybx.saas.common.util.CommonErrorCode;
import cc.sybx.saas.common.util.Constants;
import cc.sybx.saas.common.util.HttpUtil;
import cc.sybx.saas.common.util.MD5Util;
import cc.sybx.saas.customer.bean.vo.CustomerVO;
import cc.sybx.saas.customer.response.LoginResponse;
import cc.sybx.saas.saas.api.provider.DomainStoreRelaQueryProvider;
import cc.sybx.saas.saas.api.request.domainstorerela.DomainStoreRelaByDomainRequest;
import cc.sybx.saas.saas.api.response.domainstorerela.DomainStoreRelaByDomainResponse;
import cc.sybx.saas.saas.bean.vo.DomainStoreRelaVO;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Component
public class CommonUtil {
    @Resource
    private RedisService redisService;
    @Value("jwt.secret-key")
    private String jwtSecretKey;
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
     *
     * @return
     */
    public DomainStoreRelaVO getDomainInfo() {
        return getDomainInfo("");
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


    /**
     * 拼接登录后返回值
     *
     * @param customer
     * @return
     */
    public LoginResponse getLoginResponse(CustomerVO customer) {
        Date date = new Date();
        String token = Jwts.builder().setSubject(customer.getCustomerAccount())
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .setIssuedAt(date)
                .claim("customerId", customer.getCustomerId())
                .claim("customerAccount", customer.getCustomerAccount())
                .claim("customerName", customer.getCustomerDetail().getCustomerName())
                .claim("customerDetailId", customer.getCustomerDetail().getCustomerDetailId())
                .claim("ip", customer.getLoginIp())
                .claim("storeId", customer.getStoreId())
                .claim("terminalToken", MD5Util.md5Hex(customer.getCustomerId() + date.getTime() + RandomStringUtils.randomNumeric(4)))
                .claim("firstLogin", Objects.isNull(customer.getLoginTime()))//是否首次登陆
                .setExpiration(DateUtils.addMonths(date, 1))
                .compact();
        //redis保存token,并且7天后过期
        redisService.setString(CacheKeyConstant.JSON_WEB_TOKEN.concat(token),token,60L*60*60*24*7);

        return LoginResponse.builder()
                .accountName(customer.getCustomerAccount())
                .customerId(customer.getCustomerId())
                .token(token)
                .checkState(customer.getCheckState().toValue())
                .customerDetail(customer.getCustomerDetail())
                .build();
    }

    /**
     * 获取jwtToken
     *
     * @return
     */
    public String getToken(HttpServletRequest request) {

        String jwtHeaderKey = "Authorization";
        String jwtHeaderPrefix = "Bearer ";

        String authHeader = request.getHeader(jwtHeaderKey);

        //当token失效,直接返回失败
        if (authHeader != null && authHeader.length() > 16) {
            return authHeader.substring(jwtHeaderPrefix.length());
        }
        return null;
    }
}
