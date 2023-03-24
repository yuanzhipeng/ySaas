package cc.sybx.saas.customer.api.reponse.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class CustomerValidateSendMobileCodeResponse implements Serializable {

    @ApiModelProperty(value = "是否可以发送验证码")
    private Boolean result;
}
