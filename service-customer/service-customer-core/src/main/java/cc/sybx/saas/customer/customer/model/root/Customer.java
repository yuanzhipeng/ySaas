package cc.sybx.saas.customer.customer.model.root;

import cc.sybx.ms.util.jap.LocalDateTimeConverter;
import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.common.util.CustomLocalDateTimeDeserializer;
import cc.sybx.saas.common.util.CustomLocalDateTimeSerializer;
import cc.sybx.saas.customer.bean.enums.CheckState;
import cc.sybx.saas.customer.detail.model.root.CustomerDetail;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customer")
@EqualsAndHashCode(exclude = "customerDetail")
public class Customer implements Serializable {

    /**
     * 客户id
     */
    @Id
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 客户账号
     */
    @Column(name = "customer_account")
    private String customerAccount;

    /**
     * 客户密码
     */
    @Column(name = "customer_password")
    private String customerPassword;

    /**
     * 密码安全等级
     */
    @Column(name = "safe_level")
    private Integer safeLevel;

    /**
     * 密码盐值
     */
    @Column(name = "customer_salt_val")
    private String customerSaltVal;

    /**
     * 店铺 id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 公司 id
     */
    @Column(name = "company_info_id")
    private Long companyInfoId;

    /**
     * 客户详情
     */
    @JsonManagedReference
    @OneToOne(mappedBy = "customer")
    private CustomerDetail customerDetail;

    /**
     * 审核状态 0:待审核 1:已审核 2:审核未通过
     */
    @Enumerated
    @Column(name = "check_state")
    private CheckState checkState;

    /**
     * 锁定时间
     */
    @Column(name = "login_lock_time", insertable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime loginLockTime;

    /**
     * 审核时间
     */
    @Column(name = "check_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime checkTime;

    @Column(name = "login_ip")
    private String loginIp;

    /**
     * 登陆时间
     */
    @Column(name = "login_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime loginTime;

    /**
     * 密码错误次数
     */
    @Column(name = "login_error_time")
    private Integer loginErrorCount = 0;

    /**
     * 创建时间/注册时间
     */
    @Column(name = "create_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Column(name = "create_person")
    private String createPerson;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Column(name = "update_person")
    private String updatePerson;

    /**
     * 删除时间
     */
    @Column(name = "delete_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
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
