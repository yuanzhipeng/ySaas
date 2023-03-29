package cc.sybx.saas.customer.customer.service;

import cc.sybx.saas.common.util.KsBeanUtil;
import cc.sybx.saas.customer.api.request.customer.CustomerDetailInitEsRequest;
import cc.sybx.saas.customer.bean.vo.CustomerDetailInitEsVO;
import cc.sybx.saas.customer.bean.vo.StoreCustomerRelaVO;
import cc.sybx.saas.customer.customer.model.root.CustomerBase;
import cc.sybx.saas.customer.customer.model.root.CustomerDetailInitEs;
import cc.sybx.saas.customer.customer.repository.CustomerRepository;
import cc.sybx.saas.customer.detail.repository.CustomerDetailRepository;
import cc.sybx.saas.customer.storecustomer.model.root.StoreCustomerRela;
import cc.sybx.saas.customer.storecustomer.service.StoreCustomerService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class CustomerService {
    @Resource
    private CustomerRepository customerRepository;
    @Resource
    private CustomerDetailRepository customerDetailRepository;
    @Resource
    private StoreCustomerService storeCustomerService;


    /**
     * 分页查询会员信息
     *
     * @param request
     * @return
     */
    public List<CustomerDetailInitEsVO> listByPage(CustomerDetailInitEsRequest request) {
        List<CustomerDetailInitEs> customerDetailInitEsList = org.apache.commons.collections4.CollectionUtils.isEmpty(request.getIdList())
                ? customerDetailRepository.page(request.getPageRequest())
                : customerDetailRepository.queryByIdList(request.getIdList());
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(customerDetailInitEsList)) {
            return null;
        }
        List<String> customerIds = customerDetailInitEsList.stream().map(CustomerDetailInitEs::getCustomerId).collect(Collectors.toList());
        List<StoreCustomerRela> storeCustomerRelaList = storeCustomerService.findByCustomerIdIn(customerIds);

        Map<String, List<StoreCustomerRela>> map = new HashMap<>();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(storeCustomerRelaList)) {
            map = storeCustomerRelaList.stream().collect(Collectors.groupingBy(StoreCustomerRela::getCustomerId));
        }
        Map<String, List<StoreCustomerRela>> resultMap = map;
        List<CustomerBase> customerBaseList = this.findCustomerBaseByCustomerIds(customerIds);
        Map<String, CustomerBase> customerBaseMap = customerBaseList.stream().collect(Collectors.toMap(CustomerBase::getCustomerId, Function.identity()));
        List<CustomerDetailInitEsVO> result = KsBeanUtil.convert(customerDetailInitEsList, CustomerDetailInitEsVO.class);

        result.forEach(c -> {
            String customerId = c.getCustomerId();
            CustomerBase customerBase = customerBaseMap.get(customerId);
            if (nonNull(customerBase)) {
                c.setCustomerAccount(customerBase.getCustomerAccount());
                c.setCustomerLevelId(customerBase.getCustomerLevelId());
                c.setCheckState(customerBase.getCheckState());
                if (MapUtils.isNotEmpty(resultMap)) {
                    List<StoreCustomerRela> relaList = resultMap.get(customerId);
                    if (CollectionUtils.isNotEmpty(relaList)) {
                        List<StoreCustomerRelaVO> esStoreCustomerRelaList = new ArrayList<>();
                        KsBeanUtil.copyList(relaList, esStoreCustomerRelaList);
                        c.setEsStoreCustomerRelaList(esStoreCustomerRelaList);
                    }
                }
            }
        });
        return result;
    }

    /**
     * 根据会员ID查询会员账号、审核状态、企业会员状态、驳回原因
     *
     * @param customerIds
     * @return
     */
    public List<CustomerBase> findCustomerBaseByCustomerIds(List<String> customerIds) {
        return customerRepository.findCustomerBaseByCustomerIds(customerIds);
    }
}
