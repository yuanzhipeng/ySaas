package cc.sybx.saas.init;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.elasticsearch.api.provider.customer.EsCustomerDetailProvider;
import cc.sybx.saas.elasticsearch.api.request.customer.EsCustomerDetailInitRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/init")
@Api(value = "初始化ES服务", tags = "InitEsDataController")
public class InitEsDataController {
    @Resource
    private EsCustomerDetailProvider esCustomerDetailProvider;


    /**
     * 将mysql 会员信息 同步到-> ES中
     */
    @ApiOperation(value = "将mysql 会员信息 同步到-> ES中")
    @RequestMapping(value = "/customerES", method = RequestMethod.POST)
    public BaseResponse initAllCustomerES(@RequestBody EsCustomerDetailInitRequest queryRequest) {
        esCustomerDetailProvider.init(queryRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
