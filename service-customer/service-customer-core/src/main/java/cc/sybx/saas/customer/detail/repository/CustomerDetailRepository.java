package cc.sybx.saas.customer.detail.repository;

import cc.sybx.saas.customer.bean.vo.CustomerDetailInitEsVO;
import cc.sybx.saas.customer.customer.model.root.CustomerDetailInitEs;
import cc.sybx.saas.customer.detail.model.root.CustomerDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDetailRepository extends JpaRepository<CustomerDetail, String>, JpaSpecificationExecutor<CustomerDetail> {

    /**
     * 根据会员详情ID查询会员详情信息
     * @param customerId
     * @return
     */
    @Query("from CustomerDetail c where c.delFlag = 0 and c.customerId = :customerId")
    CustomerDetail findByCustomerId(@Param("customerId") String customerId);

    /**
     * 分页查询会员详情信息
     * @return
     */
    @Query("select new cc.sybx.saas.customer.customer.model.root.CustomerDetailInitEs(c.customerId,c.customerName," +
            " c.provinceId, c.cityId, c.areaId, c.streetId,\n" +
            "  c.customerAddress, c.customerStatus, c.contactName, c.contactPhone, \n" +
            "                                c.employeeId, c.rejectReason, c.forbidReason, c.createTime, c.storeId) " +
            " from CustomerDetail c where c.delFlag = 0  ")
    List<CustomerDetailInitEs>  page(Pageable pageable);

    @Query("select new cc.sybx.saas.customer.customer.model.root.CustomerDetailInitEs(c.customerId,c.customerName," +
            " c.provinceId, c.cityId, c.areaId, c.streetId,\n" +
            "  c.customerAddress, c.customerStatus, c.contactName, c.contactPhone, \n" +
            "                                c.employeeId, c.rejectReason, c.forbidReason, c.createTime, c.storeId) " +
            " from CustomerDetail c where c.delFlag = 0 and c.customerId in :idList ")
    List<CustomerDetailInitEs> queryByIdList(@Param("idList") List<String> idList);
}
