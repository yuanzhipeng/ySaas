package cc.sybx.saas.customer.bean.enums;

import cc.sybx.saas.common.annotation.ApiEnum;
import cc.sybx.saas.common.annotation.ApiEnumProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

@ApiEnum
public enum StoreState {

    @ApiEnumProperty("0、开启")
    OPENING,
    @ApiEnumProperty("1、关店")
    CLOSED;

    @JsonCreator
    public StoreState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
