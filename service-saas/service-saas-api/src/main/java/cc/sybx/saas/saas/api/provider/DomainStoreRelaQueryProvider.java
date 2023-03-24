package cc.sybx.saas.saas.api.provider;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.saas.api.request.DomainStoreRelaListRequest;
import cc.sybx.saas.saas.api.response.DomainStoreRelaListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "${application.saas.name}", contextId = "DomainStoreRelaQueryProvider")
public interface DomainStoreRelaQueryProvider {

    /**
     * 列表查询域名映射关系API
     *
     * @param domainStoreRelaListReq 列表请求参数和筛选对象 {@link DomainStoreRelaListRequest}
     * @return 域名映射关系的列表信息 {@link DomainStoreRelaListResponse}
     * @author 宋汉林
     */
    @PostMapping("/saas/${application.saas.version}/domainstorerela/list")
    BaseResponse<DomainStoreRelaListResponse> list(@RequestBody @Valid DomainStoreRelaListRequest domainStoreRelaListReq);
}
