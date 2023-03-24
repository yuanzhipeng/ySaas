package cc.sybx.saas.saas.api.request.domainstorerela;

import cc.sybx.saas.common.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainStoreRelaByDomainRequest extends BaseRequest {

    @ApiModelProperty(value = "域名", hidden = true)
    private String domain;
}
