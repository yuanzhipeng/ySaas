package cc.sybx.saas.customer.bean.vo;

import cc.sybx.saas.common.enums.DefaultFlag;
import cc.sybx.saas.common.util.CustomLocalDateTimeDeserializer;
import cc.sybx.saas.common.util.CustomLocalDateTimeSerializer;
import cc.sybx.saas.customer.bean.enums.CheckState;
import cc.sybx.saas.customer.bean.enums.CustomerStatus;
import cc.sybx.saas.customer.bean.vo.StoreCustomerRelaVO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerDetailInitEsVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    @ApiModelProperty(value = "客户ID")
    private String customerId;

    /**
     * 会员名称
     */
    @ApiModelProperty(value = "会员名称")
    private String customerName;

    /**
     * 账户
     */
    @ApiModelProperty(value = "账号")
    private String customerAccount;

    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    private Long provinceId;

    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private Long cityId;

    /**
     * 区
     */
    @ApiModelProperty(value = "区")
    private Long areaId;

    /**
     * 街道
     */
    @ApiModelProperty(value = "街道")
    private Long streetId;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String customerAddress;

    /**
     * 联系人名字
     */
    @ApiModelProperty(value = "联系人名字")
    private String contactName;

    /**
     * 客户等级ID
     */
    @ApiModelProperty(value = "客户等级ID")
    private Long customerLevelId;


    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String contactPhone;


    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @ApiModelProperty(value = "审核状态")
    private CheckState checkState;


    /**
     * 账号状态 0：启用中  1：禁用中
     */
    @ApiModelProperty(value = "账号状态")
    private CustomerStatus customerStatus;

    /**
     * 负责业务员
     */
    @ApiModelProperty(value = "业务员ID")
    private String employeeId;


    /**
     * 是否为分销员 0：否  1：是
     */
    @ApiModelProperty(value = "是否分销员")
    private DefaultFlag isDistributor;


    /**
     * 审核驳回理由
     */
    @ApiModelProperty(value = "审核驳回原因")
    private String rejectReason;

    /**
     * 禁用原因
     */
    @ApiModelProperty(value = "禁用原因")
    private String forbidReason;

    @ApiModelProperty(value = "店铺关联客户关系ID集合")
    private List<StoreCustomerRelaVO> esStoreCustomerRelaList;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 品牌商城id
     */
    @ApiModelProperty(value = "品牌商城id")
    private Long storeId;
}
