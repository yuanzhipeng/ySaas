package cc.sybx.saas.customer.employee.model.root;

import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.common.enums.GenderType;
import cc.sybx.saas.common.util.CustomLocalDateDeserializer;
import cc.sybx.saas.common.util.CustomLocalDateSerializer;
import cc.sybx.saas.common.util.CustomLocalDateTimeDeserializer;
import cc.sybx.saas.common.util.CustomLocalDateTimeSerializer;
import cc.sybx.saas.customer.bean.enums.AccountState;
import cc.sybx.saas.customer.company.model.root.CompanyInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    /**
     * 会员名称
     */
    @Column(name = "employee_name")
    private String employeeName;

    /**
     * 会员电话
     */
    @Column(name = "employee_mobile")
    private String employeeMobile;

    /**
     * 角色id
     */
    @Column(name = "role_ids")
    private String roleIds;

    /**
     * 0 是 1否
     */
    @Column(name = "is_employee")
    private Integer isEmployee;

    /**
     * 账户名
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 密码
     */
    @Column(name = "account_password")
    private String accountPassword;

    /**
     * salt
     */
    @Column(name = "employee_salt_val")
    private String employeeSaltVal;

    /**
     * 账号状态
     */
    @Column(name = "account_state")
    @Enumerated
    private AccountState accountState;

    /**
     * 账号禁用原因
     */
    @Column(name = "account_disable_reason")
    private String accountDisableReason;

    /**
     * 第三方店铺id
     */
    @Column(name = "third_id")
    private String thirdId;

    /**
     * 会员id
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 删除标志
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag = DeleteFlag.NO;

    /**
     * 创建时间
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

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "update_person")
    private String updatePerson;

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "delete_time")
    private LocalDateTime deleteTime;

    @Column(name = "delete_person")
    private String deletePerson;

    /**
     * 登录失败次数
     */
    @Column(name = "login_error_time", insertable = false)
    private Integer loginErrorTime = 0;

    /**
     * 锁定时间
     */
    @Column(name = "login_lock_time", insertable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime loginLockTime;

    /**
     * 会有登录时间
     */
    @Column(name = "login_time", insertable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime loginTime;

    /**
     * 商家Id
     */
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "company_info_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private CompanyInfo companyInfo;

    /**
     * 商家Id
     */
    @Column(name = "company_info_id", insertable = false, updatable = false)
    private Long companyInfoId;

    /**
     * 是否是主账号
     */
    @Column(name = "is_master_account")
    private Integer isMasterAccount;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 工号
     */
    @Column(name = "job_no")
    private String jobNo;

    /**
     * 职位
     */
    @Column(name = "position")
    private String position;

    /**
     * 生日
     */
    @Column(name = "birthday")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate birthday;

    /**
     * 性别，0：保密，1：男，2：女
     */
    @Column(name = "sex")
    private GenderType sex;

    /**
     * 是否激活会员账号，0：否，1：是
     */
    @Column(name = "become_member")
    private Integer becomeMember;

    /**
     * 交接人员工ID
     */
    @Column(name = "heir_employee_id")
    private String heirEmployeeId;

    /**
     * 所属部门集合
     */
    @Column(name = "department_ids")
    private String departmentIds;

    /**
     * 管理部门集合
     */
    @Column(name = "manage_department_ids")
    private String manageDepartmentIds;
}
