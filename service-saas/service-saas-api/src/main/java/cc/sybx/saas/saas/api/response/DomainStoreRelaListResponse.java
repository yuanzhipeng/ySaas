package cc.sybx.saas.saas.api.response;

import cc.sybx.saas.saas.bean.vo.DomainStoreRelaVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class DomainStoreRelaListResponse implements Serializable {

    /**
     * 域名映射关系列表结果
     */
    @ApiModelProperty(value = "域名映射关系列表结果")
    private List<DomainStoreRelaVO> domainStoreRelaVOList;
}
