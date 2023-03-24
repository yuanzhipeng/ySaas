package cc.sybx.saas.customer.api.request.register;

import cc.sybx.saas.common.base.BaseRequest;
import cc.sybx.saas.customer.bean.enums.SmsTemplate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 会员登录注册-发送手机验证码Request
 */
@Data
@ApiModel
public class CustomerSendMobileCodeRequest extends BaseRequest implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "存入redis的验证码key")
    private String redisKey;

    @NotBlank
    @ApiModelProperty(value = "要发送短信的手机号码")
    private String mobile;

    @NotNull
    @ApiModelProperty(value = "短信内容模版")
    private SmsTemplate smsTemplate;
}
