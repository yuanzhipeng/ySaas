package cc.sybx.saas.customer.detail.model.root;

import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.common.enums.GenderType;
import cc.sybx.saas.common.util.CustomLocalDateDeserializer;
import cc.sybx.saas.common.util.CustomLocalDateSerializer;
import cc.sybx.saas.common.util.CustomLocalDateTimeDeserializer;
import cc.sybx.saas.common.util.CustomLocalDateTimeSerializer;
import cc.sybx.saas.customer.bean.enums.CustomerStatus;
import cc.sybx.saas.customer.customer.model.root.Customer;
import cc.sybx.saas.customer.store.model.root.Store;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customer_detail")
@NamedEntityGraphs(@NamedEntityGraph(name = "customerDetail.all", attributeNodes = {@NamedAttributeNode("customer")}))
public class CustomerDetail implements Serializable {

    /**
     * 会员详情 id
     */
    @Id
    @Column(name = "customer_detail_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String customerDetailId;

    /**
     * 会员Id
     */
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    /**
     * 会员id
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 会员名称
     */
    @Column(name = "customer_name")
    private String customerName;

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
    @Column(name = "customer_address")
    private String customerAddress;

    @Enumerated
    @Column(name = "customer_status")
    private CustomerStatus customerStatus;

    /**
     * 联系人名字
     */
    @Column(name = "contact_name")
    private String contactName;

    /**
     * 联系方式
     */
    @Column(name = "contact_phone")
    private String contactPhone;

    /**
     * 负责业务员
     */
    @Column(name = "employee_id")
    private String employeeId;

    /**
     * 审核驳回理由
     */
    @Column(name = "reject_reason")
    private String rejectReason;

    /**
     * 禁用原因
     */
    @Column(name = "forbid_reason")
    private String forbidReason;


    /**
     * 生日
     */
    @Column(name = "birth_day")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate birthDay;

    /**
     * 性别
     */
    @Column(name = "gender")
    private GenderType gender;

    /**
     * 是否为员工 0：否 1：是
     */
    @Column(name = "is_employee")
    private Integer isEmployee;

    /**
     * 店铺Id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 会员ID
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    @JsonBackReference
    private Store store;

    /**
     * 公司Id
     */
    @Column(name = "company_info_id")
    private Long companyInfoId;

    /**
     * 创建时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Column(name = "create_person")
    private String createPerson;

    /**
     * 修改时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Column(name = "update_person")
    private String updatePerson;

    /**
     * 删除时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "delete_time")
    private LocalDateTime deleteTime;


    /**
     * 删除人
     */
    @Column(name = "delete_person")
    private String deletePerson;

    /**
     * 删除标志 0:未删除 1:已删除
     */
    @Column(name = "delete_flag")
    private DeleteFlag delFlag;
}
