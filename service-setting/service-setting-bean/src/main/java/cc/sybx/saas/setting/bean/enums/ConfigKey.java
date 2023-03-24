package cc.sybx.saas.setting.bean.enums;

import cc.sybx.saas.common.annotation.ApiEnum;
import cc.sybx.saas.common.annotation.ApiEnumProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@ApiEnum(dataType = "java.lang.String")
public enum ConfigKey {

    @ApiEnumProperty("S2B审核管理")
    S2BAUDIT("s2b_audit");

    private final String value;

    ConfigKey(String value) {
        this.value = value;
    }

    @JsonValue
    public String toValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
