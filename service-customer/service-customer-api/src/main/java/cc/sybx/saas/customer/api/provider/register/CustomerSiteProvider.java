package cc.sybx.saas.customer.api.provider.register;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.customer.api.reponse.register.CustomerSendMobileCodeResponse;
import cc.sybx.saas.customer.api.reponse.register.CustomerValidateSendMobileCodeResponse;
import cc.sybx.saas.customer.api.request.register.CustomerSendMobileCodeRequest;
import cc.sybx.saas.customer.api.request.register.CustomerValidateSendMobileCodeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 会员-注册/修改/发送验证码/绑定第三方账号API
 */
@FeignClient(value = "${application.customer.name}", contextId = "CustomerSiteProvider")
public interface CustomerSiteProvider {

    /**
     * 是否可以发送验证码
     * @param customerValidateSendMobileCodeRequest {@link CustomerValidateSendMobileCodeRequest}
     * @return 是否可以发送验证码 {@link CustomerValidateSendMobileCodeResponse}
     */
    @PostMapping("/customer/${application.customer.version}/validate-send-mobile-code")
    BaseResponse<CustomerValidateSendMobileCodeResponse> validateSendMobileCode(@RequestBody @Valid CustomerValidateSendMobileCodeRequest
                                                                                        customerValidateSendMobileCodeRequest);


    /**
     * 发送手机验证码
     *
     * @param customerSendMobileCodeRequest {@link CustomerSendMobileCodeRequest}
     * @return 发送手机验证码结果 {@link CustomerSendMobileCodeResponse}
     */
    @PostMapping("/customer/${application.customer.version}/send-mobile-code")
    BaseResponse<CustomerSendMobileCodeResponse> sendMobileCode(@RequestBody @Valid CustomerSendMobileCodeRequest customerSendMobileCodeRequest);
}
