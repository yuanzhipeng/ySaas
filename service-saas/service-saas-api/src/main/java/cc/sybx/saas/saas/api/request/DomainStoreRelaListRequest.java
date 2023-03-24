package cc.sybx.saas.saas.api.request;

import cc.sybx.saas.common.enums.DeleteFlag;
import cc.sybx.saas.common.util.CustomLocalDateTimeDeserializer;
import cc.sybx.saas.common.util.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class DomainStoreRelaListRequest implements Serializable {
    /**
     * 批量查询-idList
     */
    @ApiModelProperty(value = "批量查询-idList")
    private List<Long> idList;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * PC端域名
     */
    @ApiModelProperty(value = "PC端域名")
    private String pcDomain;

    /**
     * H5端域名
     */
    @ApiModelProperty(value = "H5端域名")
    private String h5Domain;

    /**
     * 店铺Id
     */
    @ApiModelProperty(value = "店铺Id")
    private Long storeId;

    /**
     * 公司信息ID
     */
    @ApiModelProperty(value = "公司信息ID")
    private Long companyInfoId;

    /**
     * 搜索条件:创建时间开始
     */
    @ApiModelProperty(value = "搜索条件:创建时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeBegin;
    /**
     * 搜索条件:创建时间截止
     */
    @ApiModelProperty(value = "搜索条件:创建时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeEnd;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createPerson;

    /**
     * 搜索条件:更新时间开始
     */
    @ApiModelProperty(value = "搜索条件:更新时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeBegin;
    /**
     * 搜索条件:更新时间截止
     */
    @ApiModelProperty(value = "搜索条件:更新时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeEnd;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updatePerson;

    /**
     * 是否删除标志 0：否，1：是；默认0
     */
    @ApiModelProperty(value = "是否删除标志 0：否，1：是；默认0")
    private DeleteFlag delFlag;
}
