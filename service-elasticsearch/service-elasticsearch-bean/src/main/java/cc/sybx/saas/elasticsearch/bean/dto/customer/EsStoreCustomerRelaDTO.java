package cc.sybx.saas.elasticsearch.bean.dto.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 店铺-会员(包含会员等级)关联实体类
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsStoreCustomerRelaDTO implements Serializable {

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
