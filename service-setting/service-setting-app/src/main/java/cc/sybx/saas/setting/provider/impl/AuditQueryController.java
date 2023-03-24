package cc.sybx.saas.setting.provider.impl;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.common.util.Constants;
import cc.sybx.saas.common.util.KsBeanUtil;
import cc.sybx.saas.setting.api.provider.AuditQueryProvider;
import cc.sybx.saas.setting.api.request.ConfigQueryRequest;
import cc.sybx.saas.setting.api.response.AuditConfigListResponse;
import cc.sybx.saas.setting.audit.AuditService;
import cc.sybx.saas.setting.config.model.root.Config;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuditQueryController implements AuditQueryProvider {
    @Resource
    private AuditService auditService;

    /**
     * 根据配置键查询配置
     * @param configQueryRequest
     * @return
     */
    @Override
    public BaseResponse<AuditConfigListResponse> getConfigKey(ConfigQueryRequest configQueryRequest) {
        AuditConfigListResponse response = new AuditConfigListResponse();
        List<Config> configList = auditService.findByConfigKey(configQueryRequest.getConfigKey(), configQueryRequest.getStoreId());
        if(CollectionUtils.isEmpty(configList)){
            configList = auditService.findByConfigKey(configQueryRequest.getConfigKey(), Constants.BOSS_DEFAULT_STORE_ID);
            List<Config> configs = KsBeanUtil.convert(configList, Config.class);
            configs = configs.stream().peek(config -> {
                config.setId(null);
                config.setStoreId(configQueryRequest.getStoreId());
                config.setStatus(NumberUtils.INTEGER_ZERO);
                config.setContext(null);
                config.setCreateTime(LocalDateTime.now());
            }).collect(Collectors.toList());
            auditService.add(configs);
        }
        KsBeanUtil.copyList(configList, response.getConfigVOList());
        return BaseResponse.success(response);
    }
}
