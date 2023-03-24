package cc.sybx.saas.saas.dominstorerela.service;

import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.common.util.KsBeanUtil;
import cc.sybx.saas.saas.api.request.domainstorerela.DomainStoreRelaQueryRequest;
import cc.sybx.saas.saas.bean.vo.DomainStoreRelaVO;
import cc.sybx.saas.saas.dominstorerela.model.root.DomainStoreRela;
import cc.sybx.saas.saas.dominstorerela.repository.DomainStoreRelaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DomainStoreRelaService {
    @Resource
    private DomainStoreRelaRepository domainStoreRelaRepository;

    /**
     * 列表查询域名映射关系
     * @param queryReq
     * @return
     */
    public List<DomainStoreRela> list(DomainStoreRelaQueryRequest queryReq) {
        return domainStoreRelaRepository.findAll(DomainStoreRelaWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     */
    public DomainStoreRelaVO wrapperVo(DomainStoreRela domainStoreRela) {
        if (domainStoreRela != null) {
            DomainStoreRelaVO domainStoreRelaVO = KsBeanUtil.convert(domainStoreRela, DomainStoreRelaVO.class);
            return domainStoreRelaVO;
        }
        return null;
    }

    /**
     * 根据域名查询店铺信息
     */
    public DomainStoreRela findByDomain(String domain) {
        return domainStoreRelaRepository.findByDomain(DeleteFlag.NO, domain).orElse(null);
    }
}
