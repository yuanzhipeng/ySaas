package cc.sybx.saas.elasticsearch.provider.impl;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.elasticsearch.customer.service.EsCustomerDetailService;
import cc.sybx.saas.elasticsearch.api.provider.customer.EsCustomerDetailProvider;
import cc.sybx.saas.elasticsearch.api.request.customer.EsCustomerDetailInitRequest;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class EsCustomerDetailController implements EsCustomerDetailProvider {
    @Resource
    private EsCustomerDetailService esCustomerDetailService;

    @Override
    public BaseResponse init(EsCustomerDetailInitRequest request) {
        esCustomerDetailService.init(request);
        return BaseResponse.SUCCESSFUL();
    }
}
