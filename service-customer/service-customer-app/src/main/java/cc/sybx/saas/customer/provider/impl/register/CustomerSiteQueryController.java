package cc.sybx.saas.customer.provider.impl.register;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.common.util.KsBeanUtil;
import cc.sybx.saas.customer.api.provider.register.CustomerSiteQueryProvider;
import cc.sybx.saas.customer.api.reponse.register.CustomerByAccountResponse;
import cc.sybx.saas.customer.api.request.register.CustomerByAccountRequest;
import cc.sybx.saas.customer.bean.vo.CustomerDetailVO;
import cc.sybx.saas.customer.customer.model.root.Customer;
import cc.sybx.saas.customer.customer.service.CustomerSiteService;

import javax.annotation.Resource;
import java.util.Objects;

public class CustomerSiteQueryController implements CustomerSiteQueryProvider {
    @Resource
    private CustomerSiteService customerSiteService;

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
