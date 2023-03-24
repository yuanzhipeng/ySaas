package cc.sybx.saas.saas.provider.impl.domainstorerela;

import cc.sybx.saas.common.base.BaseResponse;
import cc.sybx.saas.common.util.KsBeanUtil;
import cc.sybx.saas.saas.api.provider.DomainStoreRelaQueryProvider;
import cc.sybx.saas.saas.api.request.DomainStoreRelaListRequest;
import cc.sybx.saas.saas.api.request.domainstorerela.DomainStoreRelaQueryRequest;
import cc.sybx.saas.saas.api.response.DomainStoreRelaListResponse;
import cc.sybx.saas.saas.bean.vo.DomainStoreRelaVO;
import cc.sybx.saas.saas.dominstorerela.model.root.DomainStoreRela;
import cc.sybx.saas.saas.dominstorerela.service.DomainStoreRelaService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DomainStoreRelaQuery implements DomainStoreRelaQueryProvider {
    @Resource
    private DomainStoreRelaService domainStoreRelaService;

    @Override
    public BaseResponse<DomainStoreRelaListResponse> list(DomainStoreRelaListRequest domainStoreRelaListReq) {
        DomainStoreRelaQueryRequest queryRequest = KsBeanUtil.convert(domainStoreRelaListReq, DomainStoreRelaQueryRequest.class);
        List<DomainStoreRela> domainStoreRelaList = domainStoreRelaService.list(queryRequest);
        List<DomainStoreRelaVO> domainStoreRelaVOS =
                domainStoreRelaList.stream().map(entity -> domainStoreRelaService.wrapperVo(entity)).collect(Collectors.toList());
        return BaseResponse.success(new DomainStoreRelaListResponse(domainStoreRelaVOS));
    }


}
