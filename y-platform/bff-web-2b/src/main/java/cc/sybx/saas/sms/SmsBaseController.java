package cc.sybx.saas.sms;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.common.exception.SaasRuntimeException;
import cc.sybx.saas.common.redis.CacheKeyConstant;
import cc.sybx.saas.common.util.CommonErrorCode;
import cc.sybx.saas.common.util.Constants;
import cc.sybx.saas.common.util.SiteResultCode;
import cc.sybx.saas.common.util.ValidateUtil;
import cc.sybx.saas.customer.api.provider.detail.CustomerDetailQueryProvider;
import cc.sybx.saas.customer.api.provider.register.CustomerSiteProvider;
import cc.sybx.saas.customer.api.provider.register.CustomerSiteQueryProvider;
import cc.sybx.saas.customer.api.reponse.register.CustomerByAccountResponse;
import cc.sybx.saas.customer.api.request.detail.CustomerDetailWithNotDeleteByCustomerIdRequest;
import cc.sybx.saas.customer.api.request.register.CustomerByAccountRequest;
import cc.sybx.saas.customer.api.request.register.CustomerSendMobileCodeRequest;
import cc.sybx.saas.customer.api.request.register.CustomerValidateSendMobileCodeRequest;
import cc.sybx.saas.customer.bean.enums.CustomerStatus;
import cc.sybx.saas.customer.bean.enums.SmsTemplate;
import cc.sybx.saas.customer.bean.vo.CustomerDetailVO;
import cc.sybx.saas.util.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@RestController
@RequestMapping
@Api(tags = "SmsBaseController [短信控制器]")
public class SmsBaseController {
    @Resource
    private CommonUtil commonUtil;
    @Resource
    private CustomerSiteProvider customerSiteProvider;
    @Resource
    private CustomerSiteQueryProvider customerSiteQueryProvider;
    @Resource
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @ApiOperation(value = "发送验证码[用户登陆]")
    @RequestMapping(value = "/verification/customeraccount/send/sms/login", method = RequestMethod.POST)
    public BaseResponse verificationCustomerAccountSendSmsLogin(@RequestBody String customerAccount){
        Long storeId = commonUtil.getStoreIdWithDefault();

        if(!ValidateUtil.isPhone(customerAccount)){
            throw new SaasRuntimeException(CommonErrorCode.PARAMETER_ERROR);
        }

        //是否可以发送
        CustomerValidateSendMobileCodeRequest customerValidateSendMobileCodeRequest =
                new CustomerValidateSendMobileCodeRequest();
        customerValidateSendMobileCodeRequest.setMobile(customerAccount);
        customerValidateSendMobileCodeRequest.setStoreIdAtSaaS(storeId);
        if (!customerSiteProvider.validateSendMobileCode(customerValidateSendMobileCodeRequest).getContext().getResult()) {
            throw new SaasRuntimeException(SiteResultCode.ERROR_000016);
        }

        //账号是否注册
        CustomerByAccountRequest request = new CustomerByAccountRequest();
        request.setCustomerAccount(customerAccount);
        request.setStoreIdAtSaaS(storeId);
        BaseResponse<CustomerByAccountResponse> responseBaseResponse = customerSiteQueryProvider.getCustomerByCustomerAccount(request);
        CustomerByAccountResponse response = responseBaseResponse.getContext();
        if (Objects.isNull(response)) {
            throw new SaasRuntimeException(SiteResultCode.ERROR_010005);
        }
        validateAccountStatus(response);
        CustomerDetailVO customerDetail = this.findCustomerDetailByCustomerId(response.getCustomerId());
        if (customerDetail == null) {
            throw new SaasRuntimeException(SiteResultCode.ERROR_010005);
        }

        //是否禁用
        if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
            throw new SaasRuntimeException(SiteResultCode.ERROR_010002, new Object[]{"，原因为：" + customerDetail
                    .getForbidReason()});
        }

        //发送验证码
        CustomerSendMobileCodeRequest customerSendMobileCodeRequest = new CustomerSendMobileCodeRequest();
        customerSendMobileCodeRequest.setMobile(customerAccount);
        customerSendMobileCodeRequest.setRedisKey(CacheKeyConstant.YZM_CUSTOMER_LOGIN);
        customerSendMobileCodeRequest.setSmsTemplate(SmsTemplate.VERIFICATION_CODE);
        customerSendMobileCodeRequest.setStoreIdAtSaaS(storeId);
        if (Constants.yes.equals(customerSiteProvider.sendMobileCode(customerSendMobileCodeRequest).getContext().getResult())) {
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.FAILED();
    }
    
    /**
     * 根据会员获取
     *
     * @param customerId
     * @return
     */
    private CustomerDetailVO findCustomerDetailByCustomerId(String customerId) {
        return customerDetailQueryProvider.getCustomerDetailWithNotDeleteByCustomerId(
                CustomerDetailWithNotDeleteByCustomerIdRequest.builder().customerId(customerId).build()).getContext();
    }
    /**
     *校验用户是否锁定
     * @param
     */
    public void validateAccountStatus(CustomerByAccountResponse customer){
        //锁定时间
        LocalDateTime now = LocalDateTime.now();
        if (customer.getLoginLockTime() != null) {
            if (now.isBefore(customer.getLoginLockTime().plus(30, ChronoUnit.MINUTES))) {
                long minutes = ChronoUnit.MINUTES.between(customer.getLoginLockTime().toLocalTime(), now.toLocalTime());
                minutes = 30 - minutes;
                if (minutes < 1) {
                    minutes = 1;
                }
                throw new SaasRuntimeException("K-010004", new Object[]{minutes});
            }
        }
    }
}
