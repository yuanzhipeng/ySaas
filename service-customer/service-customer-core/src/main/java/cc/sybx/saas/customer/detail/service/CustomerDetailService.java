package cc.sybx.saas.customer.detail.service;

import cc.sybx.saas.customer.detail.model.root.CustomerDetail;
import cc.sybx.saas.customer.detail.repository.CustomerDetailRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomerDetailService {
    @Resource
    private CustomerDetailRepository customerDetailRepository;

    /**
     * 通过会员详情ID查询会员详情
     *
     * @param customerDetailId
     * @return
     */
    public CustomerDetail findByCustomerId(String customerDetailId) {
        return customerDetailRepository.findByCustomerId(customerDetailId);
    }
}
