package cc.sybx.saas.customer.customer.model.root;

import cc.sybx.saas.customer.bean.enums.CheckState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBase implements Serializable {
    /**
     * 会员ID
     */
    private String customerId;

    /**
     * 账户
     */
    private String customerAccount;

    /**
     * 会员名称
     */
    private String customerName;

    /**
     * 会员等级ID
     */
    private Long customerLevelId;

    /**
     * 会员等级名称
     */
    private String customerLevelName;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 客户成长值
     */
    private Long growthValue;

    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @Enumerated
    private CheckState checkState;

    public CustomerBase(Long storeId, String customerId, String customerAccount, CheckState checkState){
        this.storeId = storeId;
        this.customerId = customerId;
        this.customerAccount = customerAccount;
        this.checkState = checkState;

    }
}
