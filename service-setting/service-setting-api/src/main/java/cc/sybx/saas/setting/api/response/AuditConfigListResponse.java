package cc.sybx.saas.setting.api.response;

import cc.sybx.saas.setting.bean.vo.ConfigVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel
public class AuditConfigListResponse implements Serializable {

    @ApiModelProperty(value = "审核开关列表")
    private List<ConfigVO> configVOList = new ArrayList<>();
}
