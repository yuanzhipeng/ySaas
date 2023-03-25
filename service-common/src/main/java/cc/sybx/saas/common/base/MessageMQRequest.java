package cc.sybx.saas.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel
public class MessageMQRequest {

    /**
     * 内容参数
     */
    @ApiModelProperty(value = "内容参数")
    private List<String> params;

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private String customerId;

    /**
     * 跳转路由参数
     */
    @ApiModelProperty(value = "跳转路由参数")
    private Map<String, Object> routeParam;

    /**
     * 节点类型
     */
    @ApiModelProperty(value = "节点类型")
    private Integer nodeType;

    /**
     * 节点code
     */
    @ApiModelProperty(value = "节点code")
    private String nodeCode;

    /**
     * 消息图片
     */
    @ApiModelProperty(value = "消息图片")
    private String pic;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty("店铺id")
    private Long storeId;
}
