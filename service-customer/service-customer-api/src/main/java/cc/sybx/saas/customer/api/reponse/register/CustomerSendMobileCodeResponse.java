package cc.sybx.saas.customer.api.reponse.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * C端用户登陆 短信发送request
 */
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSendMobileCodeResponse implements Serializable {

    @ApiModelProperty(value = "验证码")
    private Integer result;
}
