package cc.sybx.saas.setting.api.response.baseconfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseConfigResponse implements Serializable {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Integer baseConfigId;

    /**
     * PC端网址
     */
    @ApiModelProperty(value = "PC端网址")
    private String pcWebsite;

    /**
     * 移动端网址
     */
    @ApiModelProperty(value = "移动端网址")
    private String mobileWebsite;

    /**
     * PClogo
     */
    @ApiModelProperty(value = "PClogo")
    private String pcLogo;

    /**
     * PC登录页banner,最多可添加5个,多个图片间以"|"隔开
     */
    @ApiModelProperty(value = "PC登录页banner,最多可添加5个,多个图片间以\"|\"隔开")
    private String pcBanner;

    /**
     * PC首页banner,最多可添加5个,多个图片间以"|"隔开
     */
    @ApiModelProperty(value = "PC首页banner,最多可添加5个,多个图片间以\"|\"隔开")
    private String pcMainBanner;

    /**
     * 移动banner,最多可添加5个,多个图片间以"|"隔开
     */
    @ApiModelProperty(value = "移动banner,最多可添加5个,多个图片间以\"|\"隔开")
    private String mobileBanner;

    /**
     * 图标，最多添加一个
     */
    @ApiModelProperty(value = "图标，最多添加一个")
    private String pcIco;

    /**
     * 首页标题
     */
    @ApiModelProperty(value = "首页标题")
    private String pcTitle;

    /**
     * 商家网址
     */
    @ApiModelProperty(value = "商家网址")
    private String supplierWebsite;

    /**
     * 会员注册协议
     */
    @ApiModelProperty(value = "会员注册协议")
    private String registerContent;

    @ApiModelProperty(value = "品牌商城Id")
    private Long storeId;
}
