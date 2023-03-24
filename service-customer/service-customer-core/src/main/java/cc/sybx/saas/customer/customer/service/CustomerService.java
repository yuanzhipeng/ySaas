package cc.sybx.saas.customer.customer.service;

import cc.sybx.saas.customer.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomerService {
    @Resource
    private CustomerRepository customerRepository;


}
