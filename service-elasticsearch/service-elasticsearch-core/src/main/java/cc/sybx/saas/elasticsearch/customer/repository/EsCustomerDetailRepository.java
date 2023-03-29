package cc.sybx.saas.elasticsearch.customer.repository;

import cc.sybx.saas.elasticsearch.customer.model.root.EsCustomerDetail;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsCustomerDetailRepository extends ElasticsearchRepository<EsCustomerDetail, String> {

}
