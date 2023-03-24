package cc.sybx.saas.customer.api.request.register;

import cc.sybx.saas.common.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 会员登录注册-验证手机号是否可发送验证码Request
 */
@ApiModel
@Data
public class CustomerValidateSendMobileCodeRequest extends BaseRequest implements Serializable {

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
}
