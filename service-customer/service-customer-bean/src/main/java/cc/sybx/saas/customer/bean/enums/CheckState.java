package cc.sybx.saas.customer.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CheckState {

    WAIT_CHECK, CHECKED, NOT_PASS;

    @JsonCreator
    public CheckState fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
