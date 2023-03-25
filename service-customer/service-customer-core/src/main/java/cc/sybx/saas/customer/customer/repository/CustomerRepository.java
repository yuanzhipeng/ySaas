package cc.sybx.saas.customer.customer.repository;

import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.customer.customer.model.root.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {

    /**
     * 账户 店铺id 获取用户信息
     * @param customerAccount
     * @param storeId
     * @param delFlag
     * @return
     */
    Customer findByCustomerAccountAndStoreIdAndDelFlag(String customerAccount, Long storeId, DeleteFlag delFlag);

    /**
     * 解锁
     * @param customerId
     * @return
     */
    @Modifying
    @Query("update Customer e set e.loginLockTime = null, e.loginErrorCount = 0 where e.customerId = :customerId")
    int unlockCustomer(@Param("customerId") String customerId);

    /**
     * 修改登录次数
     * @param customerId
     */
    @Modifying
    @Query("update Customer e set e.loginErrorCount = IFNULL(e.loginErrorCount,0) + 1 where e.customerId = :customerId")
    int updateloginErrorCount(@Param("customerId") String customerId);

    /**
     * 修改锁时间
     * @param customerId customerId
     * @return
     */
    @Modifying
    @Query("update Customer e set e.loginLockTime = ?2 where e.customerId =?1")
    int updateLoginLockTime(String customerId, LocalDateTime localDateTime);

    /**
     * 修改客户登录时间
     * @param customerId customerId
     * @param loginTime  loginTime
     * @param loginIp    loginIp
     * @return rows
     */
    @Modifying
    @Query("update Customer e set e.loginTime = ?2, e.loginErrorCount = 0, e.loginLockTime = null, e.loginIp = ?3 " +
            "where e.customerId =?1")
    int updateLoginTime(String customerId, LocalDateTime loginTime, String loginIp);
}
