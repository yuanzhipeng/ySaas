package cc.sybx.saas.customer.bean.enums;

import cc.sybx.saas.common.annotation.ApiEnum;
import cc.sybx.saas.common.annotation.ApiEnumProperty;

/**
 * 短信模板
 *
 * @author aqlu
 * @date 15/12/4
 */
@ApiEnum(dataType = "java.lang.String")
public enum SmsTemplate {
    @ApiEnumProperty("验证码短信模板")
    VERIFICATION_CODE("验证码：%s，有效期5分钟，请勿泄露给他人。"),

    @ApiEnumProperty("用户密码短信模板")
    CUSTOMER_PASSWORD("您本次账号是：%1s，密码是: %2s"),

    @ApiEnumProperty("企业会员密码短信模板")
    ENTERPRISE_CUSTOMER_PASSWORD("恭喜您成为企业会员，您可享受企业会员专享价，您的账号是：%1s，密码是:  %2s，快去商城采购吧~"),

    @ApiEnumProperty("员工密码短信模板")
    EMPLOYEE_PASSWORD("您本次账号是：%1s，密码是: %2s"),

    @ApiEnumProperty("会员导入成功信息模板")
    CUSTOMER_IMPORT_SUCCESS("欢迎加入ySaas系统，您的账号创建成功，您可使用短信验证码进行快捷登录，默认密码为手机号后6位，登陆后请及时更改！");

    private String content;

    SmsTemplate(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }
}
