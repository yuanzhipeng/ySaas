package cc.sybx.saas.customer.api.provider.register;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.customer.api.reponse.register.CustomerByAccountResponse;
import cc.sybx.saas.customer.api.request.register.CustomerByAccountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "${application.customer.name}", contextId = "CustomerSiteQueryProvider")
public interface CustomerSiteQueryProvider {

    /**
     * 根据账号查询用户信息
     * @param customerByAccountRequest
     * @return
     */
    @PostMapping(value = "/customer/${application.customer.version}/get-customer-by-customer-account")
    BaseResponse<CustomerByAccountResponse> getCustomerByCustomerAccount(@RequestBody @Valid CustomerByAccountRequest customerByAccountRequest);
}
