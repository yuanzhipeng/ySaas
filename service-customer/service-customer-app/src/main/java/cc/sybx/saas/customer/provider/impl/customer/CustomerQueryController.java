package cc.sybx.saas.customer.provider.impl.customer;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.customer.api.provider.customer.CustomerQueryProvider;
import cc.sybx.saas.customer.api.reponse.customer.CustomerDetailInitEsResponse;
import cc.sybx.saas.customer.api.request.customer.CustomerDetailInitEsRequest;
import cc.sybx.saas.customer.bean.vo.CustomerDetailInitEsVO;
import cc.sybx.saas.customer.customer.service.CustomerService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CustomerQueryController implements CustomerQueryProvider {
    @Resource
    private CustomerService customerService;

    @Override
    public BaseResponse<CustomerDetailInitEsResponse> listByPage(CustomerDetailInitEsRequest request) {
        List<CustomerDetailInitEsVO> customerDetailInitEsVOS = customerService.listByPage(request);
        return BaseResponse.success(new CustomerDetailInitEsResponse(customerDetailInitEsVOS));
    }
}
