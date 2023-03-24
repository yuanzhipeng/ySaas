package cc.sybx.saas.setting.config.repository;

import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.setting.config.model.root.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends JpaRepository<Config, String>, JpaSpecificationExecutor<Config> {

    /**
     * 根据configKey查询配置list
     * @param configKey
     * @param storeId
     * @param deleteFlag
     * @return
     */
    List<Config> findByConfigKeyAndStoreIdAndDelFlag(String configKey, Long storeId, DeleteFlag deleteFlag);
}
