package cc.sybx.saas.common.util;

public final class CommonErrorCode {

    /**
     * 操作成功
     */
    public final static String SUCCESSFUL = "K-000000";

    /**
     * 操作失败
     */
    public final static String FAILED = "K-000001";

    /**
     * 参数错误
     */
    public final static String PARAMETER_ERROR = "K-000009";

    /**
     * 非法字符
     */
    public final static String ILLEGAL_CHARACTER = "K-000017";

    /**
     * 针对我们的业务权限
     */
    public final static String METHOD_NOT_ALLOWED = "K-999998";

    /**
     * 指定异常，不走国际化，异常信息由B2bRuntimeException字段result设定
     */
    public final static String
            SPECIFIED = "K-999999";

}
