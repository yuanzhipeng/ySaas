package cc.sybx.saas.customer.customer.service;

import cc.sybx.saas.common.base.MessageMQRequest;
import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.common.enums.NodeType;
import cc.sybx.saas.common.enums.node.AccountSecurityType;
import cc.sybx.saas.common.exception.SaasRuntimeException;
import cc.sybx.saas.common.redis.CacheKeyConstant;
import cc.sybx.saas.common.util.*;
import cc.sybx.saas.customer.api.constant.EmployeeErrorCode;
import cc.sybx.saas.customer.bean.enums.CheckState;
import cc.sybx.saas.customer.bean.enums.CustomerStatus;
import cc.sybx.saas.customer.bean.enums.SmsTemplate;
import cc.sybx.saas.customer.customer.model.entity.CustomerDetailQueryRequest;
import cc.sybx.saas.customer.customer.model.root.Customer;
import cc.sybx.saas.customer.customer.repository.CustomerRepository;
import cc.sybx.saas.customer.detail.model.root.CustomerDetail;
import cc.sybx.saas.customer.detail.repository.CustomerDetailRepository;
import cc.sybx.saas.customer.employee.model.root.Employee;
import cc.sybx.saas.customer.mq.ProducerService;
import cc.sybx.saas.customer.redis.RedisService;
import cc.sybx.saas.customer.sms.SmsSendUtil;
import cc.sybx.saas.customer.store.model.root.Store;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.boot.autoconfigure.session.StoreType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class CustomerSiteService {
    @Resource
    private SmsSendUtil smsSendUtil;
    @Resource
    private RedisService redisService;
    @Resource
    private ProducerService producerService;
    @Resource
    private CustomerRepository customerRepository;
    @Resource
    private CustomerDetailRepository customerDetailRepository;

    /**
     * 密码错误超过6次后锁定的时间，单位：分钟
     */
    private static final int LOCK_MINUTES = 30;

    /**
     * 允许密码错误最大次数
     */
    private static final int PASS_WRONG_MAX_COUNTS = 5;

    /**
     * 注册时每个客户默认负责业务员是系统默认system
     */
    private static final String DEFAULT_EMPLOYEE = "system";

    /***
     * 昵称前缀
     */
    private static final String CUSTOMER_NAME_PREFIX = "YRyx";

    @Transactional(noRollbackFor = SaasRuntimeException.class)
    public Customer login(String customerAccount, String password, Long storeId) throws SaasRuntimeException {
        CustomerDetail customerDetail = this.getCustomerDetailByAccount(customerAccount, storeId);
        if (Objects.isNull(customerDetail) || Objects.isNull(customerDetail.getCustomer())) {
            String errKey = CacheKeyConstant.LOGIN_ERR + storeId + ":" + customerAccount;
            String lockTimeKey = CacheKeyConstant.SAAS_BOSS_LOCK_TIME + storeId + ":" + customerAccount;
            //登录错误次数
            String errCountStr = redisService.getString(errKey);
            //锁定时间
            String lockTimeStr = redisService.getString(lockTimeKey);
            int error = NumberUtils.toInt(errCountStr);

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime lockTime = LocalDateTime.now();
            if (StringUtils.isNotBlank(lockTimeStr)) {
                lockTime = LocalDateTime.parse(lockTimeStr, df);
                if (LocalDateTime.now().isAfter(lockTime.plus(LOCK_MINUTES, ChronoUnit.MINUTES))) {
                    //如果锁定时间结束，则清空账户错误次数和锁定时间
                    error = 0;
                    redisService.delete(errKey);
                    redisService.delete(lockTimeKey);
                }
            }

            error = error + 1;
            redisService.setString(errKey, String.valueOf(error));
            if (error < PASS_WRONG_MAX_COUNTS) {
                //用户名或密码错误，还有几次机会
                throw new SaasRuntimeException(EmployeeErrorCode.ACCOUNT_PASSWORD_ERROR, new Object[]{PASS_WRONG_MAX_COUNTS - error});
            } else if (error == PASS_WRONG_MAX_COUNTS) {
                //连续输错密码5次，请30分钟后重试
                redisService.setString(lockTimeKey, df.format(LocalDateTime.now()));
                throw new SaasRuntimeException(EmployeeErrorCode.LOCKED_MINUTES, new Object[]{LOCK_MINUTES});
            } else {
                //连续输错密码5次，请{0}分钟后重试
                throw new SaasRuntimeException(EmployeeErrorCode.LOCKED_MINUTES, new Object[]{LocalDateTime.now().until
                        (lockTime.plus(LOCK_MINUTES, ChronoUnit.MINUTES), ChronoUnit.MINUTES)});
            }

        }

        Customer customer = customerDetail.getCustomer();

        if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
            throw new SaasRuntimeException(EmployeeErrorCode.ACCOUNT_DISABLED, new Object[]{"，原因为：" + customerDetail
                    .getForbidReason()});
        }

        Integer currentErrCount = Objects.isNull(customer.getLoginErrorCount()) ? Integer.valueOf(0) :
                customer.getLoginErrorCount();
        LocalDateTime now = LocalDateTime.now();

        //已被锁定
        if (customer.getLoginLockTime() != null) {
            if (now.isBefore(customer.getLoginLockTime().plus(LOCK_MINUTES, ChronoUnit.MINUTES))) {
                long minutes = ChronoUnit.MINUTES.between(customer.getLoginLockTime().toLocalTime(), now.toLocalTime());
                minutes = LOCK_MINUTES - minutes;
                if (minutes < 1) {
                    minutes = 1;
                }
                throw new SaasRuntimeException(EmployeeErrorCode.LOCKED_MINUTES, new Object[]{minutes});
            } else {
                // 30分钟后解锁用户
                customerRepository.unlockCustomer(customer.getCustomerId());
                currentErrCount = 0;
            }
        }

        //生成加密后的登陆密码
        String encryptPwd = SecurityUtil.getStoreLogpwd(String.valueOf(customer.getCustomerId()), password, customer
                .getCustomerSaltVal());
        if (!customer.getCustomerPassword().equals(encryptPwd)) {
            if (currentErrCount + 1 == 3) {
                this.sendMessage(NodeType.ACCOUNT_SECURITY, AccountSecurityType.LOGIN_PASSWORD_SUM_OUT.getType(), customer.getCustomerId(), customer.getCustomerAccount());
            }
            //累积一次错误
            // 记录失败次数
            customerRepository.updateloginErrorCount(customer.getCustomerId());
            // 超过最大错误次数，锁定用户; 否则错误次数+1
            if (currentErrCount + 1 >= PASS_WRONG_MAX_COUNTS) {
                customerRepository.updateLoginLockTime(customer.getCustomerId(), now);
                throw new SaasRuntimeException(EmployeeErrorCode.LOCKED_MINUTES, new Object[]{LOCK_MINUTES});
            } else {
                throw new SaasRuntimeException(EmployeeErrorCode.ACCOUNT_PASSWORD_ERROR, new Object[]{PASS_WRONG_MAX_COUNTS -
                        (currentErrCount + 1)});
            }
        }

        customer.setLoginIp(HttpUtil.getIpAddr());
        customerRepository.updateLoginTime(customer.getCustomerId(), now, customer.getLoginIp());
        customer.setCustomerDetail(customerDetail);
        return customer;
    }

    /**
     * 是否可以发送验证码
     *
     * @param mobile 要发送短信的手机号码
     * @return true:可以发送，false:不可以
     */
    public boolean isSendSms(String mobile, Long storeId) {
        String timeStr = redisService.hget(CacheKeyConstant.YZM_MOBILE_LAST_TIME, storeId + ":" + mobile);
        if (StringUtils.isBlank(timeStr)) {
            return true;
        }
        //如果当前时间 > 上一次发送时间+1分钟
        return LocalDateTime.now().isAfter(DateUtil.parse(timeStr, DateUtil.FMT_TIME_1).plusMinutes(1));
    }

    /**
     * 获取有效用户
     * @param account
     * @param storeId
     * @return
     */
    public Customer getCustomerByAccount(String account, Long storeId){
        return customerRepository.findByCustomerAccountAndStoreIdAndDelFlag(account, storeId, DeleteFlag.NO);
    }

    /**
     * 发送手机验证码
     *
     * @param redisKey    存入redis的验证码key
     * @param mobile      要发送短信的手机号码
     * @param smsTemplate 短信内容模版
     * @return
     */
    public Integer doMobileSms(String redisKey, String mobile, SmsTemplate smsTemplate, Long storeId) {
        //记录发送时间
        redisService.hset(CacheKeyConstant.YZM_MOBILE_LAST_TIME, storeId + ":" + mobile, DateUtil.nowTime());
        String verifyCode = RandomStringUtils.randomNumeric(6);
        smsSendUtil.send(storeId, smsTemplate, new String[]{mobile}, verifyCode);
        redisService.setString(redisKey + ":" + storeId + ":" + mobile, verifyCode, Constants.SMS_TIME);
        return Constants.yes;
    }

    @Transactional
    public Customer register(Customer customer, String employeeId, String channelCode) {
        this.beforeRegister(customer);

        customer.setDelFlag(DeleteFlag.NO);
        customer.setCreateTime(LocalDateTime.now());
        customer.setSafeLevel(SafeLevelUtil.getSafeLevel(customer.getCustomerPassword()));
        //生成盐值
        String saltVal = SecurityUtil.getNewPsw();
        //生成加密后的登陆密码
        String encryptPwd = SecurityUtil.getStoreLogpwd(String.valueOf(customer.getCustomerId()), customer
                .getCustomerPassword(), saltVal);
        customer.setCustomerSaltVal(saltVal);
        customer.setCustomerPassword(encryptPwd);
        customer = customerRepository.save(customer);

        CustomerDetail customerDetail =
                Objects.isNull(customer.getCustomerDetail()) ? new CustomerDetail() : customer.getCustomerDetail();
        customerDetail.setCustomerId(customer.getCustomerId());
        customerDetail.setDelFlag(DeleteFlag.NO);
        customerDetail.setCustomerStatus(CustomerStatus.ENABLE);
        customerDetail.setCreateTime(LocalDateTime.now());
        customerDetail.setCompanyInfoId(customer.getCompanyInfoId());
        customerDetail.setStoreId(customer.getStoreId());
        customerDetailRepository.save(customerDetail);

        customer.setCustomerDetail(customerDetail);
        return customer;
    }

    /**
     * 注册客户前判断客户状态，是否存在
     *
     * @param customer
     */
    private void beforeRegister(Customer customer) {
        this.beforeRegister(customer.getCustomerAccount(), customer.getStoreId());
    }

    /**
     * 注册客户前判断客户状态，是否存在
     *
     * @param customerAccount
     */
    private void beforeRegister(String customerAccount, Long storeId) {
        Customer oldCustomer = this.getCustomerByAccount(customerAccount, storeId);
        if (oldCustomer != null) {
            CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(oldCustomer.getCustomerId());
            if (Objects.isNull(customerDetail)) {
                throw new SaasRuntimeException(SiteResultCode.ERROR_010101);
            }

            if (CustomerStatus.DISABLE.toValue() == customerDetail.getCustomerStatus().toValue()) {
                throw new SaasRuntimeException(SiteResultCode.ERROR_010006);
            }

            throw new SaasRuntimeException(SiteResultCode.ERROR_010101);
        }
    }


    /**
     * 根据账号获取有效的CustomerDetail
     *
     * @param account
     * @return
     */
    public CustomerDetail getCustomerDetailByAccount(String account, Long storeId) {
        List<CustomerDetail> customerList = customerDetailRepository.findAll(CustomerDetailQueryRequest.builder()
                .equalCustomerAccount(account)
                .storeId(storeId)
                .delFlag(DeleteFlag.NO.toValue())
                .build().getWhereCriteria());
        return CollectionUtils.isNotEmpty(customerList) ? customerList.get(0) : null;
    }

    /**
     * 消息中心通知节点发送消息
     *
     * @param nodeType
     * @param nodeCode
     * @param customerId
     */
    private void sendMessage(NodeType nodeType, String nodeCode, String customerId, String mobile) {
        MessageMQRequest request = new MessageMQRequest();
        request.setNodeType(nodeType.toValue());
        request.setNodeCode(nodeCode);
        request.setCustomerId(customerId);
        request.setMobile(mobile);
        producerService.sendMessage(request);
    }
}
