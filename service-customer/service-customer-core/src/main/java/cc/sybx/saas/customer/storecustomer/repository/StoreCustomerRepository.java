package cc.sybx.saas.customer.storecustomer.repository;

import cc.sybx.saas.customer.storecustomer.model.root.StoreCustomerRela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreCustomerRepository extends JpaRepository<StoreCustomerRela, String>, JpaSpecificationExecutor<StoreCustomerRela> {

    /**
     * 根据会员ID查询店铺-会员关系
     * @param customerIds
     * @return
     */
    List<StoreCustomerRela> findByCustomerIdIn(List<String> customerIds);
}
