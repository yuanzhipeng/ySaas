package cc.sybx.saas.customer.response;

import cc.sybx.saas.customer.bean.vo.CustomerDetailVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    /**
     * jwt验证token
     */
    @ApiModelProperty(value = "jwt验证token")
    private String token;

    /**
     * 账号名称
     */
    @ApiModelProperty(value = "账号名称")
    private String accountName;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerId;

    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @ApiModelProperty(value = "审核状态", dataType = "cc.sybx.saas.customer.bean.enums.CheckState")
    private Integer checkState;

    /**
     * 客户明细
     */
    @ApiModelProperty(value = "客户明细信息")
    private CustomerDetailVO customerDetail;

    /**
     * 是否直接可以登录 0 否 1 是
     */
    @ApiModelProperty(value = "是否直接可以登录")
    private Boolean isLoginFlag;

    /**
     * 被邀请会员邀请码
     */
    @ApiModelProperty(value = "被邀请会员邀请码")
    private String inviteCode;

    /**
     * 新用户初始登录标志
     */
    @ApiModelProperty(value = "新用户初始登录标志")
    private Boolean newFlag;

    /**
     * 第三方用户授权token
     */
    @ApiModelProperty(value = "第三方用户授权token")
    private String channelToken;
}
