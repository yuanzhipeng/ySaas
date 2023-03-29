package cc.sybx.saas.customer.storecustomer.model.root;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "store_customer_rela")
public class StoreCustomerRela implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /**
     * 用户标识
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 店铺标识
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 商家标识
     */
    @Column(name = "company_info_id")
    private Long companyInfoId;

    /**
     * 店铺等级标识
     */
    @Column(name = "store_level_id")
    private Long storeLevelId;

    /**
     * 负责的业务员标识
     */
    @Column(name = "employee_id")
    private String employeeId;
}
