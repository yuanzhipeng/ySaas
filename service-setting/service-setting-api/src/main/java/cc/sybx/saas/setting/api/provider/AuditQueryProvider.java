package cc.sybx.saas.setting.api.provider;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.setting.api.request.ConfigQueryRequest;
import cc.sybx.saas.setting.api.response.AuditConfigListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "${application.setting.name}", contextId = "AuditQueryProvider")
public interface AuditQueryProvider {

    /**
     * 根据设置的键 查询配置
     * @param configQueryRequest
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/audit/get-system-config")
    BaseResponse<AuditConfigListResponse> getConfigKey(@RequestBody @Valid ConfigQueryRequest configQueryRequest);
}
