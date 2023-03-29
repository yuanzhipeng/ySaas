package cc.sybx.saas.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EsInitRequest implements Serializable {

    /**
     * 第几页
     */
    @ApiModelProperty(value = "初始化开始页码")
    private Integer pageNum = 0;

    /**
     * 每页显示多少条
     */
    @ApiModelProperty(value = "每批初始化数量")
    private Integer pageSize = 1000;
}
