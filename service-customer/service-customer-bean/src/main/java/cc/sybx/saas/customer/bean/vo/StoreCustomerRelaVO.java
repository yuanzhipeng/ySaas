package cc.sybx.saas.customer.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class StoreCustomerRelaVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 用户标识
     */
    @ApiModelProperty(value = "用户标识")
    private String customerId;

    /**
     * 店铺标识
     */
    @ApiModelProperty(value = "店铺标识")
    private Long storeId;

    /**
     * 商家标识
     */
    @ApiModelProperty(value = "商家标识")
    private Long companyInfoId;

    /**
     * 店铺等级标识
     */
    @ApiModelProperty(value = "店铺等级标识")
    private Long storeLevelId;

    /**
     * 负责的业务员标识
     */
    @ApiModelProperty(value = "负责的业务员标识")
    private String employeeId;
}
