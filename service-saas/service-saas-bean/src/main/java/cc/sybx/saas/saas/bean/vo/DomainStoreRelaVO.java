package cc.sybx.saas.saas.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class DomainStoreRelaVO implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * PC端域名
     */
    @ApiModelProperty(value = "PC端域名")
    private String pcDomain;

    /**
     * H5端域名
     */
    @ApiModelProperty(value = "H5端域名")
    private String h5Domain;

    /**
     * 店铺Id
     */
    @ApiModelProperty(value = "店铺Id")
    private Long storeId;

    /**
     * 公司信息ID
     */
    @ApiModelProperty(value = "公司信息ID")
    private Long companyInfoId;

    /**
     * 初始化PC端域名
     */
    @ApiModelProperty(value = "初始化PC端域名")
    private String initPcDomain;

    /**
     * 初始化H5端域名
     */
    @ApiModelProperty(value = "初始化H5端域名")
    private String initH5Domain;

    /**
     * 完整PC端域名(老业务使用，不修改)
     */
    @ApiModelProperty(value = "完整PC端域名")
    private String fullPcDomain;

    /**
     * 完整H5端域名(老业务使用，不修改)
     */
    @ApiModelProperty(value = "完整H5端域名")
    private String fullH5Domain;

    /**
     * pem证书路径
     */
    @ApiModelProperty(value = "pem证书路径")
    private String pemFile;

    /**
     * key证书路径
     */
    @ApiModelProperty(value = "key证书路径")
    private String keyFile;

    /**
     * pc是否使用自定义全域名 0：否，1：是
     */
    @ApiModelProperty(value = "pc是否使用自定义全域名 0：否，1：是")
    private Integer isPcFullDomain;

    /**
     * h5是否使用自定义全域名 0：否，1：是
     */
    @ApiModelProperty(value = "h5是否使用自定义全域名 0：否，1：是")
    private Integer isH5FullDomain;
}
