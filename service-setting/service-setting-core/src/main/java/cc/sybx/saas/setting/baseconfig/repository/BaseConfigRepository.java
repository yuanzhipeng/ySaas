package cc.sybx.saas.setting.baseconfig.repository;

import cc.sybx.saas.setting.baseconfig.model.root.BaseConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseConfigRepository extends JpaRepository<BaseConfig, Long>, JpaSpecificationExecutor<BaseConfig> {

    Optional<BaseConfig> findByStoreId(Long storeId);
}
