package cc.sybx.saas.setting.api.request.baseconfig;

import cc.sybx.saas.common.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseConfigByStoreIdRequest extends BaseRequest {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long storeId;
}
