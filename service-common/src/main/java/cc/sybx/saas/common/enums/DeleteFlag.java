package cc.sybx.saas.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DeleteFlag {

    NO, YES;

    @JsonCreator
    public static DeleteFlag fromValue(int value){
        return values()[value];
    }

    @JsonValue
    public int toValue(){
        return this.ordinal();
    }
}
