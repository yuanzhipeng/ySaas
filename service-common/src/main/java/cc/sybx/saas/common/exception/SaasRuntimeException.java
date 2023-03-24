package cc.sybx.saas.common.exception;

import cc.sybx.saas.common.util.CommonErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaasRuntimeException extends RuntimeException{
    private String errorCode = "";

    private Object data;

    private String result = "fail";

    private Object[] params;

    /**
     * 默认构造，展示系统异常
     */
    public SaasRuntimeException() {
        super();
        this.errorCode = CommonErrorCode.SUCCESSFUL;
    }

    /**
     * 默认errorCode为 空字符串
     *
     * @param cause
     */
    public SaasRuntimeException(Throwable cause) {
        this("", cause);
    }

    /**
     * 只有出错信息
     * 多用于系统自身发生的异常，此时没有上级异常
     *
     * @param errorCode 错误码
     * @param result    出错信息
     */
    public SaasRuntimeException(String errorCode, String result) {
        super();
        this.result = result;
        this.errorCode = errorCode;
    }


    /**
     * 只有出错信息
     * 多用于系统自身发生的异常，此时没有上级异常
     *
     * @param errorCode 异常码 异常码的错误信息会被messageSource读取
     */
    public SaasRuntimeException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }


    /**
     * 只有出错信息
     * 多用于系统自身发生的异常，此时没有上级异常
     *
     * @param errorCode
     * @param params
     */
    public SaasRuntimeException(String errorCode, Object[] params) {
        super();
        this.errorCode = errorCode;
        this.params = params;
    }

    /**
     * 错误码 + 上级异常
     *
     * @param errorCode
     * @param cause
     */
    public SaasRuntimeException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    /**
     * 返回数据 + 异常码
     *
     * @param data
     * @param errorCode
     */
    public SaasRuntimeException(Object data, String errorCode) {
        this.data = data;
        this.errorCode = errorCode;
    }

    /**
     * 返回数据 + 异常码 + 异常信息
     *
     * @param data
     * @param errorCode
     */
    public SaasRuntimeException(Object data, String errorCode, String errMsg) {
        super(errMsg);
        this.data = data;
        this.errorCode = errorCode;
        this.result = errMsg;
    }

    /**
     * 返回数据 + 异常码 + 异常信息所用参数
     *
     * @param data
     * @param errorCode
     */
    public SaasRuntimeException(String errorCode, Object[] params, Object data) {
        this.errorCode = errorCode;
        this.params = params;
        this.data = data;
    }

    /**
     * 返回值 + 异常链
     *
     * @param data
     * @param cause
     */
    public SaasRuntimeException(Object data, Throwable cause) {
        super(cause);
        this.data = data;
    }
}
