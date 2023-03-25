package cc.sybx.saas.common.enums;

import cc.sybx.saas.common.annotation.ApiEnum;
import cc.sybx.saas.common.annotation.ApiEnumProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 通知节点类型
 */
@ApiEnum
public enum NodeType {
    @ApiEnumProperty(" 0：账号安全")
    ACCOUNT_SECURITY,

    @ApiEnumProperty(" 1：账户资产")
    ACCOUNT_ASSETS,

    @ApiEnumProperty(" 2：订单进度")
    ORDER_PROGRESS_RATE,

    @ApiEnumProperty(" 3：退单进度")
    RETURN_ORDER_PROGRESS_RATE,

    @ApiEnumProperty(" 4：分销业务")
    DISTRIBUTION;

    @JsonCreator
    public static NodeType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
