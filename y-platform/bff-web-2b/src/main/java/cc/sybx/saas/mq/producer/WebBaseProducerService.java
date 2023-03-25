package cc.sybx.saas.mq.producer;

import cc.sybx.saas.customer.bean.vo.CustomerDetailVO;
import cc.sybx.saas.customer.bean.vo.CustomerVO;
import cc.sybx.saas.elasticsearch.bean.dto.customer.EsCustomerDetailDTO;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//@EnableBingding //如果使用stream-bind-mq
public class WebBaseProducerService {

    /**
     * 会员注册后，发送MQ消息
     * @param customerVO
     */
    public void sendMQForCustomerRegister(CustomerVO customerVO) {
        log.info("用户注册 mq 推送 es 用户信息 ['{}']", JSONObject.toJSONString(wrapperEsCustomerDetailDTO(customerVO)));
    }


    /**
     * 包装同步ES会员对象
     * @param customerVO
     * @return
     */
    public EsCustomerDetailDTO wrapperEsCustomerDetailDTO(CustomerVO customerVO){
        CustomerDetailVO customerDetail = customerVO.getCustomerDetail();
        EsCustomerDetailDTO esCustomerDetail = new EsCustomerDetailDTO();
        esCustomerDetail.setCustomerId( customerDetail.getCustomerId() );
        esCustomerDetail.setCustomerName( customerDetail.getCustomerName() );
        esCustomerDetail.setCustomerAccount( customerVO.getCustomerAccount() );
        esCustomerDetail.setProvinceId( customerDetail.getProvinceId() );
        esCustomerDetail.setCityId( customerDetail.getCityId() );
        esCustomerDetail.setAreaId( customerDetail.getAreaId() );
        esCustomerDetail.setStreetId( customerDetail.getStreetId() );
        esCustomerDetail.setCustomerAddress( customerDetail.getCustomerAddress() );
        esCustomerDetail.setContactName( customerDetail.getContactName() );
        esCustomerDetail.setCustomerLevelId( customerVO.getCustomerLevelId() );
        esCustomerDetail.setContactPhone( customerDetail.getContactPhone() );
        esCustomerDetail.setCheckState( customerVO.getCheckState() );
        esCustomerDetail.setCustomerStatus( customerDetail.getCustomerStatus() );
        esCustomerDetail.setEmployeeId( customerDetail.getEmployeeId() );
        esCustomerDetail.setIsDistributor( customerDetail.getIsDistributor() );
        esCustomerDetail.setRejectReason( customerDetail.getRejectReason() );
        esCustomerDetail.setForbidReason( customerDetail.getForbidReason() );
        esCustomerDetail.setEsStoreCustomerRelaList(Lists.newArrayList() );
        esCustomerDetail.setPointsAvailable(customerVO.getPointsAvailable());
        esCustomerDetail.setCreateTime( customerDetail.getCreateTime());
        esCustomerDetail.setStoreId(customerVO.getStoreId());
        return esCustomerDetail;
    }
}
