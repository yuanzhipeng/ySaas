package cc.sybx.saas.customer.api.request.register;

import cc.sybx.saas.common.base.BaseRequest;
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
public class CustomerByAccountRequest extends BaseRequest implements Serializable {

    @ApiModelProperty(value = "账号")
    private String customerAccount;
}
