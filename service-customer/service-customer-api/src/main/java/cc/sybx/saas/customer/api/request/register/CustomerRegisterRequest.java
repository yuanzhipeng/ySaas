package cc.sybx.saas.customer.api.request.register;

import cc.sybx.saas.common.base.BaseRequest;
import cc.sybx.saas.common.enums.DefaultFlag;
import cc.sybx.saas.customer.bean.dto.CustomerDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisterRequest extends BaseRequest {

    @ApiModelProperty(value = "负责业务员")
    private String employeeId;

    @ApiModelProperty(value = "会员信息-共用DTO")
    @NotNull
    private CustomerDTO customerDTO;

    @ApiModelProperty(value = "会员id")
    private String customerId;

    @ApiModelProperty(value = "统一社会信用代码")
    private String socialCreditCode;

    @ApiModelProperty(value = "公司性质")
    private Integer businessNatureType;

    @ApiModelProperty(value = "公司行业")
    private Integer businessIndustryType;

    @ApiModelProperty(value = "营业执照地址")
    private String businessLicenseUrl;

    @ApiModelProperty(value = "渠道码")
    private String channelCode;
}
