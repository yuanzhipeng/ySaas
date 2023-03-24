package cc.sybx.saas.customer.detail.repository;

import cc.sybx.saas.customer.detail.model.root.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDetailRepository extends JpaRepository<CustomerDetail, String>, JpaSpecificationExecutor<CustomerDetail> {

    /**
     * 根据会员详情ID查询会员详情信息
     *
     * @param customerDetailId
     * @return
     */
    @Query("from CustomerDetail c where c.delFlag = 0 and c.customerDetailId = :customerDetailId")
    CustomerDetail findByCustomerDetailId(@Param("customerDetailId") String customerDetailId);
}
