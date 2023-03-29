package cc.sybx.saas.elasticsearch.customer.service;

import cc.sybx.saas.common.enums.SortType;
import cc.sybx.saas.common.exception.SaasRuntimeException;
import cc.sybx.saas.common.util.KsBeanUtil;
import cc.sybx.saas.customer.api.provider.customer.CustomerQueryProvider;
import cc.sybx.saas.customer.api.request.customer.CustomerDetailInitEsRequest;
import cc.sybx.saas.customer.bean.vo.CustomerDetailInitEsVO;
import cc.sybx.saas.elasticsearch.api.request.customer.EsCustomerDetailInitRequest;
import cc.sybx.saas.elasticsearch.bean.dto.customer.EsCustomerDetailDTO;
import cc.sybx.saas.elasticsearch.customer.model.root.EsCustomerDetail;
import cc.sybx.saas.elasticsearch.customer.repository.EsCustomerDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class EsCustomerDetailService {
    @Resource
    private CustomerQueryProvider customerQueryProvider;
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    private EsCustomerDetailRepository esCustomerDetailRepository;

    private void createIndexAddMapping(){
        if(!elasticsearchTemplate.indexExists(EsCustomerDetail.class)){
            elasticsearchTemplate.createIndex(EsCustomerDetail.class);
            elasticsearchTemplate.putMapping(EsCustomerDetail.class);
        }
    }

    public void init(EsCustomerDetailInitRequest esCustomerDetailInitRequest){
        boolean initCustomerDetail = true;
        Integer pageNum = esCustomerDetailInitRequest.getPageNum();
        Integer pageSize = esCustomerDetailInitRequest.getPageSize();

        CustomerDetailInitEsRequest request = KsBeanUtil.convert(esCustomerDetailInitRequest, CustomerDetailInitEsRequest.class);

        try{
            while (initCustomerDetail) {
                if(CollectionUtils.isNotEmpty(request.getIdList())){
                    pageNum = 0;
                    pageSize = request.getIdList().size();
                    initCustomerDetail = false;
                }
                request.putSort("createTime", SortType.DESC.toValue());
                request.setPageNum(pageNum);
                request.setPageSize(pageSize);
                List<CustomerDetailInitEsVO> customerDetailInitEsVOList = customerQueryProvider.listByPage(request).getContext().getCustomerDetailInitEsVOList();
                if (CollectionUtils.isEmpty(customerDetailInitEsVOList)){
                    initCustomerDetail = false;
                    log.info("==========ES初始化会员详情结束，结束pageNum:{}==============",pageNum);
                }else {
                    List<EsCustomerDetail> esCustomerDetailList = KsBeanUtil.convert(customerDetailInitEsVOList, EsCustomerDetail.class);
                    this.saveAll(esCustomerDetailList);
                    log.info("==========ES初始化会员详情成功，当前pageNum:{}==============",pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.error("==========ES初始化会员详情异常，异常pageNum:{}==============",pageNum, e);
            throw new SaasRuntimeException("K-220001",new Object[]{pageNum});
        }

    }

    /**
     * 保存会员详情ES数据
     * @param customerDetailList
     * @return
     */
    public Iterable<EsCustomerDetail> saveAll(List<EsCustomerDetail> customerDetailList){
        this.createIndexAddMapping();
        return esCustomerDetailRepository.saveAll(customerDetailList);
    }

    /**
     * 保存会员详情ES数据
     * @param esCustomerDetailDTO
     * @return
     */
    public EsCustomerDetail save(EsCustomerDetailDTO esCustomerDetailDTO){
        this.createIndexAddMapping();
        EsCustomerDetail esCustomerDetail = KsBeanUtil.convert(esCustomerDetailDTO, EsCustomerDetail.class);
        return esCustomerDetailRepository.save(esCustomerDetail);
    }
}
