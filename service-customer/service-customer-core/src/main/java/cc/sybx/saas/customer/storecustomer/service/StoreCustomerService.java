package cc.sybx.saas.customer.storecustomer.service;

import cc.sybx.saas.customer.storecustomer.model.root.StoreCustomerRela;
import cc.sybx.saas.customer.storecustomer.repository.StoreCustomerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StoreCustomerService {
    @Resource
    private StoreCustomerRepository storeCustomerRepository;

    /**
     * 根据会员ID查询店铺-会员关系
     * @param customerIds
     * @return
     */
    public List<StoreCustomerRela> findByCustomerIdIn(List<String> customerIds){
        return storeCustomerRepository.findByCustomerIdIn(customerIds);
    }
}
