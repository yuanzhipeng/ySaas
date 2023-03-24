package cc.sybx.saas.saas.dominstorerela.repository;

import cc.sybx.saas.saas.dominstorerela.model.root.DomainStoreRela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainStoreRelaRepository extends JpaRepository<DomainStoreRela, Long>, JpaSpecificationExecutor<DomainStoreRela> {

}
