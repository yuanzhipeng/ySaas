package cc.sybx.saas.elasticsearch.customer.model.root;

import lombok.Data;

import java.io.Serializable;

@Data
public class EsStoreCustomerRela implements Serializable {

    private String id;

    /**
     * 用户标识
     */
    private String customerId;

    /**
     * 店铺标识
     */
    private Long storeId;

    /**
     * 商家标识
     */
    private Long companyInfoId;

    /**
     * 店铺等级标识
     */
    private Long storeLevelId;

    /**
     * 负责的业务员标识
     */
    private String employeeId;
}
