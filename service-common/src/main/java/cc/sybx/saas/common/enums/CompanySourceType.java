package cc.sybx.saas.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 商家来源类型
 */
public enum CompanySourceType {

    SETTLE_IN;

    @JsonCreator
    public static CompanySourceType fromValue(int value){
        return values()[value];
    }

    @JsonValue
    public int toValue(){
        return this.ordinal();
    }
}
