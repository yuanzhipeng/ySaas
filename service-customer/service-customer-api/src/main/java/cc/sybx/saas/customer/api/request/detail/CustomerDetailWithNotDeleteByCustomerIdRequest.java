package cc.sybx.saas.customer.api.request.detail;

import cc.sybx.saas.common.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * <p>根据会员id查询单个会员明细request</p>
 */
@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerDetailWithNotDeleteByCustomerIdRequest extends BaseRequest {

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    @NotBlank
    private String customerId;

}
