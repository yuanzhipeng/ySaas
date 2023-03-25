package cc.sybx.saas.customer.provider.impl.register;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.common.util.KsBeanUtil;
import cc.sybx.saas.customer.api.provider.register.CustomerSiteProvider;
import cc.sybx.saas.customer.api.reponse.register.CustomerRegisterResponse;
import cc.sybx.saas.customer.api.reponse.register.CustomerSendMobileCodeResponse;
import cc.sybx.saas.customer.api.reponse.register.CustomerValidateSendMobileCodeResponse;
import cc.sybx.saas.customer.api.request.register.CustomerRegisterRequest;
import cc.sybx.saas.customer.api.request.register.CustomerSendMobileCodeRequest;
import cc.sybx.saas.customer.api.request.register.CustomerValidateSendMobileCodeRequest;
import cc.sybx.saas.customer.bean.dto.CustomerDTO;
import cc.sybx.saas.customer.customer.model.root.Customer;
import cc.sybx.saas.customer.customer.service.CustomerSiteService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

@RestController
public class CustomerSiteController implements CustomerSiteProvider {
    @Resource
    private CustomerSiteService customerSiteService;

    /**
     * 是否可以发送验证码
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CustomerValidateSendMobileCodeResponse> validateSendMobileCode(@RequestBody @Valid CustomerValidateSendMobileCodeRequest request) {
        Boolean result = customerSiteService.isSendSms(request.getMobile(), request.getStoreIdAtSaaS());
        return BaseResponse.success(new CustomerValidateSendMobileCodeResponse(result));
    }

    /**
     * 发送手机验证码
     * @param customerSendMobileCodeRequest {@link CustomerSendMobileCodeRequest}
     * @return
     */
    @Override
    public BaseResponse<CustomerSendMobileCodeResponse> sendMobileCode(CustomerSendMobileCodeRequest customerSendMobileCodeRequest) {
        Integer result = customerSiteService.doMobileSms(customerSendMobileCodeRequest.getRedisKey(),
                customerSendMobileCodeRequest.getMobile(),
                customerSendMobileCodeRequest.getSmsTemplate(),
                customerSendMobileCodeRequest.getStoreIdAtSaaS()
        );
        return BaseResponse.success(new CustomerSendMobileCodeResponse(result));
    }

    @Override
    public BaseResponse<CustomerRegisterResponse> register(@RequestBody @Valid CustomerRegisterRequest customerRegisterRequest) {
        CustomerDTO customerDTO = customerRegisterRequest.getCustomerDTO();
        Customer customer = KsBeanUtil.convert(customerDTO, Customer.class);
        customer.setStoreId(customerRegisterRequest.getStoreIdAtSaaS());
        customer.setCompanyInfoId(customerRegisterRequest.getCompanyInfoIdAtSaaS());
        Customer customerRegister = customerSiteService.register(customer,customerRegisterRequest.getEmployeeId(),customerRegisterRequest.getChannelCode());
        if (Objects.isNull(customerRegister)){
            return BaseResponse.SUCCESSFUL();
        }
        CustomerRegisterResponse customerRegisterResponse =  KsBeanUtil.convert(customerRegister,CustomerRegisterResponse.class);
        return BaseResponse.success(customerRegisterResponse);
    }
}
