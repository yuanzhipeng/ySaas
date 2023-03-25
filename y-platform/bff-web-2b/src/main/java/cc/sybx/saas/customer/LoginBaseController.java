package cc.sybx.saas.customer;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.common.exception.SaasRuntimeException;
import cc.sybx.saas.common.redis.CacheKeyConstant;
import cc.sybx.saas.common.util.KsBeanUtil;
import cc.sybx.saas.common.util.SiteResultCode;
import cc.sybx.saas.customer.api.provider.register.CustomerSiteProvider;
import cc.sybx.saas.customer.api.provider.register.CustomerSiteQueryProvider;
import cc.sybx.saas.customer.api.reponse.login.CustomerLoginResponse;
import cc.sybx.saas.customer.api.reponse.register.CustomerRegisterResponse;
import cc.sybx.saas.customer.api.request.login.CustomerLoginRequest;
import cc.sybx.saas.customer.api.request.register.CustomerRegisterRequest;
import cc.sybx.saas.customer.bean.dto.CustomerDTO;
import cc.sybx.saas.customer.bean.vo.CustomerVO;
import cc.sybx.saas.customer.request.LoginRequest;
import cc.sybx.saas.customer.request.RegisterRequest;
import cc.sybx.saas.customer.response.LoginResponse;
import cc.sybx.saas.mq.producer.WebBaseProducerService;
import cc.sybx.saas.saas.bean.vo.DomainStoreRelaVO;
import cc.sybx.saas.util.CommonUtil;
import cc.sybx.saas.util.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping
@Api(tags = "LoginBaseController")
public class LoginBaseController {
    @Resource
    private CommonUtil commonUtil;
    @Resource
    private RedisService redisService;
    @Resource
    private CustomerSiteProvider customerSiteProvider;
    @Resource
    private WebBaseProducerService webBaseProducerService;
    @Resource
    private CustomerSiteQueryProvider customerSiteQueryProvider;

    /**
     * 登陆
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        CustomerLoginRequest request = new CustomerLoginRequest();
        request.setCustomerAccount(new String(Base64.getUrlDecoder().decode(loginRequest.getCustomerAccount().getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        request.setPassword(new String(Base64.getUrlDecoder().decode(loginRequest.getCustomerPassword().getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        request.setStoreIdAtSaaS(commonUtil.getStoreIdWithDefault());
        BaseResponse<CustomerLoginResponse> customerLoginResponseBaseResponse = customerSiteQueryProvider.login(request);

        CustomerLoginResponse customerLoginResponse = customerLoginResponseBaseResponse.getContext();
        LoginResponse loginResponse = LoginResponse.builder().build();
        if (Objects.nonNull(customerLoginResponse)) {
            CustomerVO customerVO = new CustomerVO();
            KsBeanUtil.copyPropertiesThird(customerLoginResponse, customerVO);
            //返回值
            loginResponse = commonUtil.getLoginResponse(customerVO);
            loginResponse.setNewFlag(Boolean.FALSE);
            if(Objects.isNull(customerLoginResponse.getLoginTime())){
                loginResponse.setNewFlag(Boolean.TRUE);
            }
        }
        log.info("login success ......");
        return BaseResponse.success(loginResponse);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public BaseResponse logout(HttpServletRequest request){
        String token = commonUtil.getToken(request);
        token = CacheKeyConstant.JSON_WEB_TOKEN.concat(token);
        if(redisService.hasKey(token)){
            redisService.delete(token);
        }
        return BaseResponse.SUCCESSFUL();
    }



    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponse<LoginResponse> register(@RequestBody RegisterRequest registerRequest){
        DomainStoreRelaVO domainInfo = commonUtil.getDomainInfo();
        Long storeId = domainInfo.getStoreId();
        Long companyInfoId = domainInfo.getCompanyInfoId();

        // 校验及封装校验后请求参数
        String smsKey = CacheKeyConstant.VERIFY_CODE_KEY + ":" + storeId + ":" + registerRequest.getCustomerAccount();
        // 校验短信验证码
        this.verifySmsCode(registerRequest, storeId);

        CustomerDTO customer = new CustomerDTO();
        customer.setCustomerAccount(registerRequest.getCustomerAccount());
        customer.setCustomerPassword(registerRequest.getCustomerPassword());

        CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest();
        customerRegisterRequest.setEmployeeId(registerRequest.getEmployeeId());
        customerRegisterRequest.setCustomerDTO(customer);
        customerRegisterRequest.setStoreIdAtSaaS(storeId);
        customerRegisterRequest.setCompanyInfoIdAtSaaS(companyInfoId);
        customerRegisterRequest.setChannelCode(registerRequest.getChannelCode());
        BaseResponse<CustomerRegisterResponse> customerRegisterResponseBaseResponse = customerSiteProvider.register(customerRegisterRequest);
        CustomerVO customerVO = customerRegisterResponseBaseResponse.getContext();

        if (customerVO != null) {
            //删除验证码缓存
            redisService.delete(smsKey);
            LoginResponse loginResponse = commonUtil.getLoginResponse(customerVO);

            webBaseProducerService.sendMQForCustomerRegister(customerVO);
            return BaseResponse.success(loginResponse);
        }
        log.info("register customer failed ......");
        return BaseResponse.FAILED();
    }

    /**
     * 校验请求短信验证码参数
     */
    private void verifySmsCode(RegisterRequest registerRequest, Long storeId){

        //累计验证错误次数
        String errKey = CacheKeyConstant.REGISTER_ERR + ":" + storeId + ":" + registerRequest.getCustomerAccount();
        String smsKey = CacheKeyConstant.VERIFY_CODE_KEY + ":" + storeId + ":" + registerRequest.getCustomerAccount();
        if (NumberUtils.toInt(redisService.getString(errKey)) >= 3) {
            redisService.delete(smsKey);
            throw new SaasRuntimeException(SiteResultCode.ERROR_000016);
        }

        //验证验证码
        String t_verifyCode = redisService.getString(smsKey);
        if (t_verifyCode == null || (!t_verifyCode.equalsIgnoreCase(registerRequest.getVerifyCode()))) {
            redisService.incrKey(errKey);
            redisService.expireBySeconds(errKey, 60L);
            throw new SaasRuntimeException(SiteResultCode.ERROR_000010);
        }
    }
}
