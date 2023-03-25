package cc.sybx.saas.setting.provider.impl.baseconfig;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.common.util.KsBeanUtil;
import cc.sybx.saas.setting.api.provider.baseconfig.BaseConfigQueryProvider;
import cc.sybx.saas.setting.api.request.baseconfig.BaseConfigByStoreIdRequest;
import cc.sybx.saas.setting.api.response.baseconfig.BaseConfigResponse;
import cc.sybx.saas.setting.baseconfig.model.root.BaseConfig;
import cc.sybx.saas.setting.baseconfig.service.BaseConfigService;
import cc.sybx.saas.setting.bean.vo.BaseConfigVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

@RestController
public class BaseConfigQueryController implements BaseConfigQueryProvider {
    @Resource
    private BaseConfigService baseConfigService;

    @Override
    public BaseResponse initBaseConfig(BaseConfigByStoreIdRequest baseConfigByStoreIdRequest) {
        return null;
    }

    @Override
    public BaseResponse<BaseConfigResponse> getBaseConfig(@RequestBody @Valid BaseConfigByStoreIdRequest request){
        BaseConfigResponse response = new BaseConfigResponse();
        BaseConfig baseConfig = baseConfigService.findByStoreId(request.getStoreId());
        if(Objects.isNull(baseConfig)) {
            baseConfigService.init(request.getStoreId());
        }
        BaseConfigVO baseConfigVO = baseConfigService.wrapperVo(baseConfig);
        if (Objects.nonNull(baseConfigVO)) {
            KsBeanUtil.copyPropertiesThird(baseConfigVO, response);
        }
        return BaseResponse.success(response);
    }
}
