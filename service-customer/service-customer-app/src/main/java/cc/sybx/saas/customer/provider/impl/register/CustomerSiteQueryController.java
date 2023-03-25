package cc.sybx.saas.customer.provider.impl.register;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.common.util.KsBeanUtil;
import cc.sybx.saas.customer.api.provider.register.CustomerSiteQueryProvider;
import cc.sybx.saas.customer.api.reponse.login.CustomerLoginResponse;
import cc.sybx.saas.customer.api.reponse.register.CustomerByAccountResponse;
import cc.sybx.saas.customer.api.request.login.CustomerLoginRequest;
import cc.sybx.saas.customer.api.request.register.CustomerByAccountRequest;
import cc.sybx.saas.customer.bean.vo.CustomerDetailVO;
import cc.sybx.saas.customer.customer.model.root.Customer;
import cc.sybx.saas.customer.customer.service.CustomerSiteService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

@RestController
public class CustomerSiteQueryController implements CustomerSiteQueryProvider {
    @Resource
    private CustomerSiteService customerSiteService;

    @Override
    public BaseResponse<CustomerLoginResponse> login(@RequestBody @Valid CustomerLoginRequest customerLoginRequest) {
        Customer customer = customerSiteService.login(customerLoginRequest.getCustomerAccount(),customerLoginRequest.getPassword(), customerLoginRequest.getStoreIdAtSaaS());
        if (Objects.isNull(customer)){
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.success(KsBeanUtil.convert(customer,CustomerLoginResponse.class));
    }

    /**
     * 根据账号查询用户信息
     * @param customerByAccountRequest
     * @return
     */
    @Override
    public BaseResponse<CustomerByAccountResponse> getCustomerByCustomerAccount(CustomerByAccountRequest customerByAccountRequest) {
        Customer customer = customerSiteService.getCustomerByAccount(customerByAccountRequest.getCustomerAccount(), customerByAccountRequest.getStoreIdAtSaaS());

        CustomerByAccountResponse response = new CustomerByAccountResponse();
        if(Objects.isNull(customer)){
            return BaseResponse.SUCCESSFUL();
        }

        KsBeanUtil.copyPropertiesThird(customer, response);
        CustomerDetailVO customerDetailVO = new CustomerDetailVO();
        KsBeanUtil.copyPropertiesThird(customer.getCustomerDetail(), customerDetailVO);
        response.setCustomerDetail(customerDetailVO);
        return BaseResponse.success(response);
    }
}
