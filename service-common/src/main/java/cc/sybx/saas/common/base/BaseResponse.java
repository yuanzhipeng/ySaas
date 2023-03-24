package cc.sybx.saas.common.base;

import cc.sybx.saas.common.util.CommonErrorCode;
import cc.sybx.saas.common.util.MessageSourceUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;

@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> implements Serializable {

    public static BaseResponse SUCCESSFUL() {
        return new BaseResponse(CommonErrorCode.SUCCESSFUL);
    }

    public static BaseResponse FAILED() {
        return new BaseResponse(CommonErrorCode.FAILED);
    }

    public BaseResponse(String code) {
        this.code = code;
        this.message = MessageSourceUtil.getMessage(code, null, LocaleContextHolder
                .getLocale());
    }

    public BaseResponse(String code, Object[] args) {
        this.code = code;
        this.message = MessageSourceUtil.getMessage(code, args, LocaleContextHolder
                .getLocale());
    }

    /**
     * 结果码
     */
    @ApiModelProperty(value = "结果码", required = true)
    private String code;

    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    private String message;

    /**
     * 错误内容
     */
    @ApiModelProperty(value = "错误内容")
    private Object errorData;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private T context;

    /**
     * 特殊提示
     *
     * @param errorCode 异常码
     * @param message   消息
     * @param obj       业务错误的时候，但是依旧要返回数据
     * @return
     */
    public static BaseResponse info(String errorCode, String message, Object obj) {
        return new BaseResponse<>(errorCode, message, obj, null);
    }

    /**
     * 特殊提示
     *
     * @param errorCode 异常码
     * @param message   消息
     * @return
     */
    public static <T> BaseResponse<T> info(String errorCode, String message) {
        return new BaseResponse<>(errorCode, message, null, null);
    }

    /**
     * 失败
     *
     * @param message 消息
     * @return
     */
    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(CommonErrorCode.FAILED, message, null, null);
    }

    /**
     * 成功
     *
     * @param context 内容
     * @return
     */
    public static <T> BaseResponse<T> success(T context) {
        return new BaseResponse<>(CommonErrorCode.SUCCESSFUL, null, null, context);
    }

}

