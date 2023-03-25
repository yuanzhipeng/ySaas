package cc.sybx.saas.customer.request;

import cc.sybx.saas.common.enums.GenderType;
import cc.sybx.saas.common.enums.TerminalType;
import cc.sybx.saas.common.util.CustomLocalDateDeserializer;
import cc.sybx.saas.common.util.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@ApiModel
public class RegisterRequest {

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号")
    private String customerId;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String customerAccount;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String customerPassword;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String verifyCode;

    /**
     * 会员名称
     */
    @ApiModelProperty(value = "会员名称")
    private String customerName;

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
    @ApiModelProperty(value = "区")
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
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String contactPhone;

    /**
     * 是否是忘记密码 true：忘记密码 | false：
     */
    @ApiModelProperty(value = "是否是忘记密码")
    private Boolean isForgetPassword;

    /**
     * 图片验证码
     */
    @ApiModelProperty(value = "图片验证码")
    private String patchca;

    /**
     * 图片验证码的key
     */
    @ApiModelProperty(value = "图片验证码的key")
    private String uuid;

    /**
     * 业务员id
     */
    @ApiModelProperty(value = "业务员id")
    private String employeeId;

    /**
     * 邀请人id
     */
    @ApiModelProperty(value = "邀请人id")
    private String inviteeId;

    /**
     * 分享人id
     */
    @ApiModelProperty(value = "分享人id")
    private String shareUserId;

    /**
     * 邀请码
     */
    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    /**
     * 公司性质
     */
    @ApiModelProperty(value = "公司性质")
    private Integer businessNatureType;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    /**
     * 统一社会信用代码
     */
    @ApiModelProperty(value = "统一社会信用代码")
    private String socialCreditCode;

    /**
     * 营业执照地址
     */
    @ApiModelProperty(value = "营业执照地址")
    private String businessLicenseUrl;

    /**
     * 企业会员是否第一次点击注册
     */
    @ApiModelProperty(value = "企业会员是否第一次点击注册")
    private Boolean firstRegisterFlag;

    /**
     * 终端类型，用于区分三端不同逻辑处理  PC:0 H5:1 APP:2
     */
    @ApiModelProperty(value = "渠道终端")
    private TerminalType terminalType;


    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate birthDay;

    /**
     * 性别，0女，1男
     */
    @ApiModelProperty(value = "性别，0女，1男")
    private GenderType gender;

    @ApiModelProperty(value = "渠道码")
    private String channelCode;
}
