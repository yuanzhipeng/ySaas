package cc.sybx.saas.setting.api.provider.baseconfig;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.setting.api.request.baseconfig.BaseConfigByStoreIdRequest;
import cc.sybx.saas.setting.api.response.baseconfig.BaseConfigResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * <p>基本设置查询服务Provider</p>
 */
@FeignClient(value = "${application.setting.name}", contextId = "BaseConfigQueryProvider")
public interface BaseConfigQueryProvider {

    @PostMapping("/setting/${application.setting.version}/baseconfig/init")
    BaseResponse initBaseConfig(@RequestBody @Valid BaseConfigByStoreIdRequest baseConfigByStoreIdRequest) ;

    @PostMapping("/setting/${application.setting.version}/baseconfig/get-base-config")
    BaseResponse<BaseConfigResponse> getBaseConfig(@RequestBody @Valid BaseConfigByStoreIdRequest request);

}
