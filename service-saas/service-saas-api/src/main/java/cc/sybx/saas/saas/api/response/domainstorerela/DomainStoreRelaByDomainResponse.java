package cc.sybx.saas.saas.api.response.domainstorerela;

import cc.sybx.saas.saas.bean.vo.DomainStoreRelaVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class DomainStoreRelaByDomainResponse implements Serializable {

    /**
     * 域名映射
     */
    @ApiModelProperty(value = "域名映射关系信息")
    private DomainStoreRelaVO domainStoreRelaVO;
}
