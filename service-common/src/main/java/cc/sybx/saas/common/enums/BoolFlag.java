package cc.sybx.saas.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.StringUtils;

public enum BoolFlag {

    NO, YES;

    @JsonCreator
    public static BoolFlag fromValue(int value) {
        return values()[value];
    }

    @JsonCreator
    public static BoolFlag fromValue(String value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return values()[Integer.parseInt(value)];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
