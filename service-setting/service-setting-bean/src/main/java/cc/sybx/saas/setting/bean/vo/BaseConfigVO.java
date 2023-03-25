package cc.sybx.saas.setting.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class BaseConfigVO implements Serializable {

    /**
     * 基本设置ID
     */
    @ApiModelProperty(value = "基本设置ID")
    private Integer baseConfigId;

    /**
     * PC端商城网址
     */
    @ApiModelProperty(value = "PC端网址")
    private String pcWebsite;

    /**
     * 移动端商城网址
     */
    @ApiModelProperty(value = "移动端网址")
    private String mobileWebsite;

    /**
     * PClogo
     */
    @ApiModelProperty(value = "PClogo")
    private String pcLogo;

    /**
     * PCbanner,最多可添加5个,多个图片间以'|'隔开
     */
    @ApiModelProperty(value = "PCbanner,最多可添加5个,多个图片间以'|'隔开")
    private String pcBanner;

    /**
     * 移动banner,最多可添加5个,多个图片间以'|'隔开
     */
    @ApiModelProperty(value = "移动banner,最多可添加5个,多个图片间以'|'隔开")
    private String mobileBanner;

    /**
     * PC首页banner,最多可添加5个,多个图片间以'|'隔开
     */
    @ApiModelProperty(value = "PC首页banner,最多可添加5个,多个图片间以'|'隔开")
    private String pcMainBanner;

    /**
     * 网页ico
     */
    @ApiModelProperty(value = "网页ico")
    private String pcIco;

    /**
     * pc标题
     */
    @ApiModelProperty(value = "pc标题")
    private String pcTitle;

    /**
     * 后台登录网址
     */
    @ApiModelProperty(value = "后台登录网址")
    private String supplierWebsite;

    /**
     * 会员注册协议
     */
    @ApiModelProperty(value = "会员注册协议")
    private String registerContent;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺Id")
    private Long storeId;
}
