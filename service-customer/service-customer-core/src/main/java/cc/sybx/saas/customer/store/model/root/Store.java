package cc.sybx.saas.customer.store.model.root;

import cc.sybx.saas.common.enums.BoolFlag;
import cc.sybx.saas.common.enums.CompanySourceType;
import cc.sybx.saas.common.enums.DefaultFlag;
import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.common.util.CustomLocalDateTimeDeserializer;
import cc.sybx.saas.common.util.CustomLocalDateTimeSerializer;
import cc.sybx.saas.customer.bean.enums.CheckState;
import cc.sybx.saas.customer.bean.enums.StoreState;
import cc.sybx.saas.customer.company.model.root.CompanyInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.boot.autoconfigure.session.StoreType;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "store")
public class Store implements Serializable {

    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    /**
     * 公司信息
     */
    @ManyToOne
    @JoinColumn(name = "company_info_id")
    @JsonBackReference
    private CompanyInfo companyInfo;

    /**
     * 签约开始日期
     */
    @Column(name = "contract_start_date")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractStartDate;

    /**
     * 签约结束日期
     */
    @Column(name = "contract_end_date")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;

    /**
     * 店铺名称
     */
    @Column(name = "store_name")
    private String storeName;

    /**
     * 店铺logo
     */
    @Column(name = "store_logo")
    private String storeLogo;

    /**
     * 店铺店招
     */
    @Column(name = "store_sign")
    private String storeSign;

    /**
     * 联系人名字
     */
    @Column(name = "contact_person")
    private String contactPerson;

    /**
     * 联系方式
     */
    @Column(name = "contact_mobile")
    private String contactMobile;

    /**
     * 联系邮箱
     */
    @Column(name = "contact_email")
    private String contactEmail;

    /**
     * 省
     */
    @Column(name = "province_id")
    private Long provinceId;

    /**
     * 市
     */
    @Column(name = "city_id")
    private Long cityId;

    /**
     * 区
     */
    @Column(name = "area_id")
    private Long areaId;

    /**
     * 街道
     */
    @Column(name = "street_id")
    private Long streetId;

    /**
     * 详细地址
     */
    @Column(name = "address_detail")
    private String addressDetail;

    /**
     * 结算日 多个结算日期用逗号分割 eg：5,15,25
     */
    @Column(name = "account_day")
    private String accountDay;

    /**
     * 审核状态 0、待审核 1、已审核 2、审核未通过
     */
    @Column(name = "audit_state")
    private CheckState auditState;

    /**
     * 审核未通过原因
     */
    @Column(name = "audit_reason")
    private String auditReason;

    /**
     * 店铺状态 0、开启 1、关店
     */
    @Column(name = "store_state")
    private StoreState storeState;

    /**
     * 店铺关店原因
     */
    @Column(name = "store_closed_reason")
    private String storeClosedReason;

    /**
     * 删除标志 0未删除 1已删除
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Column(name = "company_type")
    @Enumerated
    private BoolFlag companyType;

    /**
     * 商家名称
     */
    @Column(name = "supplier_name")
    private String supplierName;

    /**
     * 申请入驻时间
     */
    @Column(name = "apply_enter_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime applyEnterTime;

    /**
     * 店铺小程序码
     */
    @Column(name = "small_program_code")
    private String smallProgramCode;

    /**
     * 使用的运费模板类别(0:店铺运费,1:单品运费)
     */
    @Column(name = "freight_template_type")
    private DefaultFlag freightTemplateType;

    /**
     * 一对多关系，多个SPU编号
     */
    @Transient
    private List<String> goodsIds = new ArrayList<>();

    /**
     * 店铺拼音名称
     */
    @Column(name = "store_pinyin_name")
    private String storePinyinName;

    /**
     * 供应商拼音名称
     */
    @Column(name = "supplier_pinyin_name")
    private String supplierPinyinName;

    /**
     * 商家来源类型 0:商城入驻
     */
    @Enumerated
    @Column(name = "company_source_type")
    private CompanySourceType companySourceType;
}
