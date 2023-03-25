package cc.sybx.saas.common.enums;

import cc.sybx.saas.common.annotation.ApiEnum;
import cc.sybx.saas.common.annotation.ApiEnumProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@ApiEnum
public enum SortType {
    /**
     * asc:升序
     */
    @ApiEnumProperty("asc:升序")
    ASC("asc"),

    /**
     * desc:倒序
     */
    @ApiEnumProperty("desc:倒序")
    DESC("desc");

    private final String value;

    SortType(String value) {
        this.value = value;
    }

    @JsonValue
    public String toValue() {
        return value;
    }
}
