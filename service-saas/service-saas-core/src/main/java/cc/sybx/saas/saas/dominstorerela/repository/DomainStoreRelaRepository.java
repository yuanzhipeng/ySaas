package cc.sybx.saas.saas.dominstorerela.repository;

import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.saas.dominstorerela.model.root.DomainStoreRela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainStoreRelaRepository extends JpaRepository<DomainStoreRela, Long>, JpaSpecificationExecutor<DomainStoreRela> {

    @Query(" from DomainStoreRela d where d.delFlag = ?1 and (d.pcDomain = ?2 or d.h5Domain = ?2) ")
    Optional<DomainStoreRela> findByDomain(DeleteFlag deleteFlag, String domain);
}
