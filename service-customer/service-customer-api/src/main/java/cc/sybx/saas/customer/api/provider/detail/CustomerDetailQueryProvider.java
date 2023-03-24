package cc.sybx.saas.customer.api.provider.detail;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.customer.api.reponse.detail.CustomerDetailGetWithNotDeleteByCustomerIdResponse;
import cc.sybx.saas.customer.api.request.detail.CustomerDetailWithNotDeleteByCustomerIdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 客户明细查询
 */
@FeignClient(value = "${application.customer.name}", contextId = "CustomerDetailQueryProvider")
public interface CustomerDetailQueryProvider {

    /**
     * 根据会员id查询未删除的会员明细
     * @param request 包含会员id的查询参数 {@link CustomerDetailWithNotDeleteByCustomerIdRequest}
     * @return 会员明细 {@link CustomerDetailGetWithNotDeleteByCustomerIdResponse}
     */
    @PostMapping("/customer/${application.customer.version}/detail/get-customer-detail-with-not-delete-by-customer-id")
    BaseResponse<CustomerDetailGetWithNotDeleteByCustomerIdResponse> getCustomerDetailWithNotDeleteByCustomerId(@RequestBody @Valid CustomerDetailWithNotDeleteByCustomerIdRequest request);

}
