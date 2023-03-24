package cc.sybx.saas.customer.customer.service;

import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.common.redis.CacheKeyConstant;
import cc.sybx.saas.common.util.Constants;
import cc.sybx.saas.common.util.DateUtil;
import cc.sybx.saas.customer.bean.enums.SmsTemplate;
import cc.sybx.saas.customer.customer.model.root.Customer;
import cc.sybx.saas.customer.customer.repository.CustomerRepository;
import cc.sybx.saas.customer.redis.RedisService;
import cc.sybx.saas.customer.sms.SmsSendUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@Service
public class CustomerSiteService {
    @Resource
    private SmsSendUtil smsSendUtil;
    @Resource
    private RedisService redisService;
    @Resource
    private CustomerRepository customerRepository;

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
}
