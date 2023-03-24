package cc.sybx.saas.setting.audit;

import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.setting.config.model.root.Config;
import cc.sybx.saas.setting.config.repository.ConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuditService {
    @Resource
    private ConfigRepository configRepository;

    @Transactional
    public List<Config> add(List<Config> configs){
        return configRepository.saveAll(configs);
    }

    /**
     * 根据配置键查询配置
     * @param configKey
     * @param storeId
     * @return
     */
    public List<Config> findByConfigKey(String configKey, Long storeId){
        return configRepository.findByConfigKeyAndStoreIdAndDelFlag(configKey, storeId, DeleteFlag.NO);
    }
}
