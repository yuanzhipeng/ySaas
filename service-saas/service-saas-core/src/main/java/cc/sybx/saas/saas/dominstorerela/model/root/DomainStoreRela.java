package cc.sybx.saas.saas.dominstorerela.model.root;

import cc.sybx.saas.common.base.BaseEntity;
import cc.sybx.saas.common.enums.DeleteFlag;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>域名映射关系实体类</p>
 * @author 宋汉林
 * @date 2020-01-15 14:27:41
 */
@Data
@Entity
@Table(name = "domain_store_rela")
public class DomainStoreRela extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * PC端域名
     */
    @Column(name = "pc_domain")
    private String pcDomain;

    /**
     * H5端域名
     */
    @Column(name = "h5_domain")
    private String h5Domain;

    /**
     * 店铺Id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 公司信息ID
     */
    @Column(name = "company_info_id")
    private Long companyInfoId;

    /**
     * 是否删除标志 0：否，1：是；默认0
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 初始化PC端域名
     */
    @Column(name = "init_pc_domain")
    private String initPcDomain;

    /**
     * 初始化H5端域名
     */
    @Column(name = "init_h5_domain")
    private String initH5Domain;

    /**
     * pem证书路径
     */
    @Column(name = "pem_file")
    private String pemFile;

    /**
     * key证书路径
     */
    @Column(name = "key_file")
    private String keyFile;

    /**
     * pc是否使用自定义全域名 0：否，1：是
     */
    @Column(name = "is_pc_full_domain")
    private Integer isPcFullDomain;

    /**
     * h5是否使用自定义全域名 0：否，1：是
     */
    @Column(name = "is_h5_full_domain")
    private Integer isH5FullDomain;

}
