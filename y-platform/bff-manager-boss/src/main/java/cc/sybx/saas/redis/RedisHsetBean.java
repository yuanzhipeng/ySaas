package cc.sybx.saas.redis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 适用于redis hset 结构的对象
 * Created by daiyitian on 2016/12/24.
 */
@Data
@ApiModel
public class RedisHsetBean {

    @ApiModelProperty(value = "key")
    private String key;

    @ApiModelProperty(value = "field")
    private String field;

    @ApiModelProperty(value = "value")
    private String value;

}
