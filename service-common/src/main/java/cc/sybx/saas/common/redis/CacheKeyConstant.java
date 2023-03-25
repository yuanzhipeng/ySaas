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
     * 短信验证码的key 注册
     */
    String VERIFY_CODE_KEY = "YZM_SMS_KEY:";

    /**
     * 注册错误次数KEY
     */
    String REGISTER_ERR = "REGISTER_ERR:";

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

    String JSON_WEB_TOKEN = "JSON_WEB_TOKEN:";

    /**
     * 登录错误次数KEY
     */
    String LOGIN_ERR = "LOGIN_ERR:";

    /**
     * 平台 登录错误5次，账号锁定时间KEY
     */
    String SAAS_BOSS_LOCK_TIME = "SAAS_BOSS_LOCK_TIME:";
}
