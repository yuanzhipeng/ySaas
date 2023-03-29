package cc.sybx.saas.elasticsearch.api.provider.customer;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.elasticsearch.api.request.customer.EsCustomerDetailInitRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "${application.elasticsearch.name}", contextId = "EsCustomerDetailProvider")
public interface EsCustomerDetailProvider {

    /**
     * 初始化会员ES
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elasticsearch.version}/customer-detail/init")
    BaseResponse init(@RequestBody @Valid EsCustomerDetailInitRequest request);
}
