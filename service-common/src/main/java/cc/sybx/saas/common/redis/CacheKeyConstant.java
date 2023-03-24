package cc.sybx.saas.common.redis;

/**
 * 缓存常量
 */
public interface CacheKeyConstant {

    /**
     * saas主域名
     */
    String SAAS_DOMAIN = "SAAS_DOMAIN";

    /**
     * redis 缓存saas域名配置信息
     */
    String PC_SAAS_KEY = "PC_SAAS_KEY";
    String APP_OR_MINI_SAAS_KEY = "APP_OR_MINI_SAAS_KEY";
    String MOBILE_SAAS_KEY = "MOBILE_SAAS_KEY";

    /**
     * C 端用户登录发送验证码
     */
    String YZM_CUSTOMER_LOGIN = "YZM_CUSTOMER_LOGIN:";

    /**
     * 短信上一次发送时间的key
     */
    String YZM_MOBILE_LAST_TIME = "YZM_MOBILE_LAST_TIME:";

    /**
     * redis 缓存saas域名配置信息, 提供给Nginx配置使用
     */
    String CACHE_PC_KEY_FOR_NGINX = "SAAS_PC_KEY";

    /**
     * redis 缓存自定义全域名配置信息, 提供给Nginx配置使用
     */
    String CACHE_PC_FULL_KEY_FOR_NGINX = "SAAS_PC_KEY_FULL";

    /**
     * redis 缓存saas域名配置信息, 提供给Nginx配置使用
     */
    String CACHE_MOBILE_KEY_FOR_NGINX = "SAAS_MOBILE_KEY";

    /**
     * redis 缓存自定义全域名配置信息, 提供给Nginx配置使用
     */
    String CACHE_MOBILE_FULL_KEY_FOR_NGINX = "SAAS_MOBILE_KEY_FULL";
}
