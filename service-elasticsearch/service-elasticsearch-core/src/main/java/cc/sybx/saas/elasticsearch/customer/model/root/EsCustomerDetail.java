package cc.sybx.saas.elasticsearch.customer.model.root;

import cc.sybx.saas.common.enums.DefaultFlag;
import cc.sybx.saas.common.util.CustomLocalDateTimeDeserializer;
import cc.sybx.saas.common.util.CustomLocalDateTimeSerializer;
import cc.sybx.saas.common.util.EsConstants;
import cc.sybx.saas.customer.bean.enums.CheckState;
import cc.sybx.saas.customer.bean.enums.CustomerStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Setting(settingPath = "es/mapping/esCommonSetting.json")
@Mapping(mappingPath = "es/mapping/esCustomerDetail.json")
@Document(indexName = EsConstants.DOC_CUSTOMER_DETAIL, type = EsConstants.DOC_CUSTOMER_DETAIL)
public class EsCustomerDetail {

    @Id
    private String customerId;

    /**
     * 会员名称
     */
    private String customerName;

    /**
     * 账户
     */
    private String customerAccount;

    /**
     * 省
     */
    private Long provinceId;

    /**
     * 市
     */
    private Long cityId;

    /**
     * 区
     */
    private Long areaId;

    /**
     * 街道
     */
    private Long streetId;

    /**
     * 详细地址
     */
    private String customerAddress;

    /**
     * 联系人名字
     */
    private String contactName;

    /**
     * 客户等级ID
     */
    private Long customerLevelId;


    /**
     * 联系方式
     */
    private String contactPhone;


    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    private CheckState checkState;

    /**
     * 可用积分
     */
    private Long pointsAvailable;

    /**
     * 账号状态 0：启用中  1：禁用中
     */
    private CustomerStatus customerStatus;

    /**
     * 负责业务员
     */
    private String employeeId;


    /**
     * 是否为分销员 0：否  1：是
     */
    private DefaultFlag isDistributor;


    /**
     * 审核驳回理由
     */
    private String rejectReason;

    /**
     * 禁用原因
     */
    private String forbidReason;

    /**
     * 店铺等级标识
     */
    private List<EsStoreCustomerRela> esStoreCustomerRelaList = new ArrayList<>();

    /**
     * 企业购会员审核拒绝原因
     */
    private String enterpriseCheckReason;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 品牌商城id
     */
    private Long storeId;

    public List<EsStoreCustomerRela> getEsStoreCustomerRelaList() {
        return CollectionUtils.isEmpty(esStoreCustomerRelaList) ? Lists.newArrayList() : esStoreCustomerRelaList;
    }
}
