package cc.sybx.saas.common.enums;

import cc.sybx.saas.common.annotation.ApiEnum;
import cc.sybx.saas.common.annotation.ApiEnumProperty;
import cc.sybx.saas.common.exception.SaasRuntimeException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 终端: 0 pc 1 h5 2 app 3 mini
 */
@ApiEnum
public enum TerminalType {

    @ApiEnumProperty("PC")
    PC,
    @ApiEnumProperty("H5")
    H5,
    @ApiEnumProperty("APP")
    APP,
    //    @ApiEnumProperty("小程序")
//    MINIPROGRAM,
    @ApiEnumProperty("MINI")
    MINI;

    @JsonCreator
    public static TerminalType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }


    public static TerminalType getTerminalType(String name) {
        switch (name) {
            case "PC":
                return TerminalType.PC;
            case "H5":
                return TerminalType.H5;
            case "APP":
                return TerminalType.APP;
            case "MINIPROGRAM":
                return TerminalType.MINI;
            default:
                throw new SaasRuntimeException("参数错误！请检查TerminalType是否正确！！");
        }
    }
}
