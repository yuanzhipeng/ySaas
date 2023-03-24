package cc.sybx.saas.setting.api.request;

import cc.sybx.saas.common.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = true)
public class ConfigQueryRequest extends BaseRequest {

    /**
     * 键
     */
    @ApiModelProperty(value = "键")
    private String configKey;

    /**
     * 键
     */
    @ApiModelProperty(value = "键集合")
    private List<String> configKeyList;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String configType;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", dataType = "cc.sybx.saas.common.enums.DefaultFlag")
    private Integer status;

    /**
     * 删除标记
     */
    @ApiModelProperty(value = "删除标记", dataType = "cc.sybx.saas.common.enums.DeleteFlag")
    private Integer delFlag;

    /**
     * 商户id
     */
    @ApiModelProperty(value = "商户id")
    private Long storeId;
}
