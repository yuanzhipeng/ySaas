package cc.sybx.saas.setting.baseconfig.model.root;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "base_config")
public class BaseConfig {

    /**
     * 基本设置ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "base_config_id")
    private Long baseConfigId;

    /**
     * PC端网址
     */
    @Column(name = "pc_website")
    private String pcWebsite;

    /**
     * 移动端网址
     */
    @Column(name = "mobile_website")
    private String mobileWebsite;

    /**
     * PClogo
     */
    @Column(name = "pc_logo")
    private String pcLogo;

    /**
     * PCbanner,最多可添加5个,多个图片间以'|'隔开
     */
    @Column(name = "pc_banner")
    private String pcBanner;

    /**
     * 移动banner,最多可添加5个,多个图片间以'|'隔开
     */
    @Column(name = "mobile_banner")
    private String mobileBanner;

    /**
     * PC首页banner,最多可添加5个,多个图片间以'|'隔开
     */
    @Column(name = "pc_main_banner")
    private String pcMainBanner;

    /**
     * 网页ico
     */
    @Column(name = "pc_ico")
    private String pcIco;

    /**
     * pc标题
     */
    @Column(name = "pc_title")
    private String pcTitle;

    /**
     * 后台登录网址
     */
    @Column(name = "supplier_website")
    private String supplierWebsite;

    /**
     * 会员注册协议
     */
    @Column(name = "register_content")
    private String registerContent;

    /**
     * 店铺Id
     */
    @Column(name = "store_id")
    private Long storeId;
}
