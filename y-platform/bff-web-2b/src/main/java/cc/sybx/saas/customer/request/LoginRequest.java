package cc.sybx.saas.customer.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    @NotBlank
    private String customerAccount;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotBlank
    private String customerPassword;
}
