package cc.sybx.saas.common.enums;

import cc.sybx.saas.common.annotation.ApiEnum;
import cc.sybx.saas.common.annotation.ApiEnumProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

@ApiEnum
public enum DeleteFlag {
    @ApiEnumProperty("0:否")
    NO,
    @ApiEnumProperty("1:是")
    YES;

    @JsonCreator
    public static DeleteFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}