package cc.sybx.saas.setting.bean.enums;

import cc.sybx.saas.common.annotation.ApiEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

@ApiEnum(dataType = "java.lang.String")
public enum ConfigType {

    @ApiModelProperty("Saas化主域名")
    SAAS_DOMAIN("saas_domain");

    private final String value;

    ConfigType(String value){
        this.value = value;
    }

    @JsonValue
    public String toValue(){
        return value;
    }
}
