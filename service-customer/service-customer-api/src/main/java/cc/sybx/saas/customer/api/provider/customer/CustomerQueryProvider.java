package cc.sybx.saas.customer.api.provider.customer;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.customer.api.reponse.customer.CustomerDetailInitEsResponse;
import cc.sybx.saas.customer.api.request.customer.CustomerDetailInitEsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "${application.customer.name}", contextId = "CustomerQueryProvider")
public interface CustomerQueryProvider {

    /**
     * 初始化会员ES
     *
     * @param request {@link CustomerDetailInitEsRequest}
     * @return {@link CustomerDetailInitEsResponse}
     */
    @PostMapping("/customer/${application.customer.version}/customer/list-by-page")
    BaseResponse<CustomerDetailInitEsResponse> listByPage(@RequestBody @Valid CustomerDetailInitEsRequest request);
}
