package cc.sybx.saas.system;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.common.util.Constants;
import cc.sybx.saas.saas.bean.vo.DomainStoreRelaVO;
import cc.sybx.saas.setting.api.provider.baseconfig.BaseConfigQueryProvider;
import cc.sybx.saas.setting.api.request.baseconfig.BaseConfigByStoreIdRequest;
import cc.sybx.saas.setting.api.response.baseconfig.BaseConfigResponse;
import cc.sybx.saas.util.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system")
@Api(tags = "SystemController [基本设置Api]")
public class SystemController {
    @Resource
    private CommonUtil commonUtil;
    @Resource
    private BaseConfigQueryProvider baseConfigQueryProvider;

    @ApiOperation(value = "基本配置查询")
    @RequestMapping(value = "/baseConfig", method = RequestMethod.GET)
    public BaseResponse<BaseConfigResponse> systemBaseConfig(){
        BaseConfigByStoreIdRequest req = BaseConfigByStoreIdRequest.builder()
                .storeId(commonUtil.getStoreIdWithDefault())
                .build();

        BaseConfigResponse context = baseConfigQueryProvider.getBaseConfig(req).getContext();
        //App 分享填充h5域名
        DomainStoreRelaVO domainInfo = commonUtil.getDomainInfo();
        context.setPcWebsite(domainInfo.getFullPcDomain());
        context.setMobileWebsite(domainInfo.getFullH5Domain());
        BaseConfigResponse boosContext = baseConfigQueryProvider.getBaseConfig(
                BaseConfigByStoreIdRequest.builder().storeId(Constants.BOSS_DEFAULT_STORE_ID).build()).getContext();
        context.setSupplierWebsite(boosContext.getSupplierWebsite());
        return BaseResponse.success(context);
    }
}
