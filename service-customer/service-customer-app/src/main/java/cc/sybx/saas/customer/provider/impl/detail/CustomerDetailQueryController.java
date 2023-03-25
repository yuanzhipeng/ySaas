package cc.sybx.saas.customer.provider.impl.detail;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.common.util.KsBeanUtil;
import cc.sybx.saas.customer.api.provider.detail.CustomerDetailQueryProvider;
import cc.sybx.saas.customer.api.reponse.detail.CustomerDetailGetWithNotDeleteByCustomerIdResponse;
import cc.sybx.saas.customer.api.request.detail.CustomerDetailWithNotDeleteByCustomerIdRequest;
import cc.sybx.saas.customer.bean.vo.CustomerDetailVO;
import cc.sybx.saas.customer.bean.vo.CustomerVO;
import cc.sybx.saas.customer.customer.model.root.Customer;
import cc.sybx.saas.customer.detail.model.root.CustomerDetail;
import cc.sybx.saas.customer.detail.service.CustomerDetailService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

@RestController
public class CustomerDetailQueryController implements CustomerDetailQueryProvider {
    @Resource
    private CustomerDetailService customerDetailService;

    @Override
    public BaseResponse<CustomerDetailGetWithNotDeleteByCustomerIdResponse> getCustomerDetailWithNotDeleteByCustomerId(CustomerDetailWithNotDeleteByCustomerIdRequest request) {
        CustomerDetailGetWithNotDeleteByCustomerIdResponse response = new CustomerDetailGetWithNotDeleteByCustomerIdResponse();
        wrapperVO(customerDetailService.findByCustomerId(request.getCustomerId()), response);
        return BaseResponse.success(response);
    }

    private void wrapperVO(CustomerDetail customerDetail, CustomerDetailVO customerDetailVO){
        if(Objects.nonNull(customerDetail)){
            KsBeanUtil.copyPropertiesThird(customerDetail, customerDetailVO);
            Customer customer = customerDetail.getCustomer();
            if(Objects.nonNull(customer)){
                CustomerVO customerVO = new CustomerVO();
                KsBeanUtil.copyPropertiesThird(customer, customerVO);
                customerDetailVO.setCustomerVO(customerVO);
            }
        }
    }
}
