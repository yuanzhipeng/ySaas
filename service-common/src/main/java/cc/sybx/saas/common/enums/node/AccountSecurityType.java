package cc.sybx.saas.common.enums.node;

import cc.sybx.saas.common.annotation.ApiEnum;
import cc.sybx.saas.common.annotation.ApiEnumProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 账户安全通知枚举类
 */
@ApiEnum
public enum  AccountSecurityType {

    @ApiEnumProperty(" 0：登陆密码试错通知")
    LOGIN_PASSWORD_SUM_OUT("LOGIN_PASSWORD_SUM_OUT","登陆密码试错通知"),

    @ApiEnumProperty("1：支付密码试错通知")
    PAY_PASSWORD_SUM_OUT("PAY_PASSWORD_SUM_OUT","支付密码试错通知");

    private String type;

    private String description;

    AccountSecurityType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @JsonCreator
    public AccountSecurityType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
