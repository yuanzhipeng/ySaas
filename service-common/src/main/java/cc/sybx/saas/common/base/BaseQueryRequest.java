package cc.sybx.saas.common.base;

import cc.sybx.saas.common.enums.SortType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class BaseQueryRequest extends BaseRequest implements Serializable {
    /**
     * 第几页
     */
    @ApiModelProperty(value = "第几页")
    private Integer pageNum = 0;

    /**
     * 每页显示多少条
     */
    @ApiModelProperty(value = "每页显示多少条")
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String sortColumn;

    /**
     * 排序规则 desc asc
     */
    @ApiModelProperty(value = "排序规则 desc asc")
    private String sortRole;

    /**
     * 排序类型
     */
    @ApiModelProperty(value = "排序类型")
    private String sortType;

    /**
     * 多重排序
     * 内容：key:字段,value:desc或asc
     */
    @ApiModelProperty(value = "多重排序", notes = "内容：key:字段,value:desc或asc", hidden = true)
    private Map<String, String> sortMap = new LinkedHashMap<>();

    /**
     * 获取分页参数对象与排序条件
     *
     * @return
     */
    @ApiModelProperty(value = "分页请求", hidden = true)
    public PageRequest getPageRequest() {
        //无排序
        Sort sort = getSort();
        if (Objects.nonNull(sort)) {
            return PageRequest.of(pageNum, pageSize, sort);
        } else {
            return PageRequest.of(pageNum, pageSize,Sort.unsorted());
        }
    }

    @ApiModelProperty(value = "分页排序", hidden = true)
    public Sort getSort() {
        // 单个排序
        if (StringUtils.isNotBlank(sortColumn)) {
            // 判断规则 DESC ASC
            Sort.Direction direction = SortType.ASC.toValue().equalsIgnoreCase(sortRole) ? Sort.Direction.ASC : Sort
                    .Direction.DESC;
            return Sort.by(direction, sortColumn);
        }

        //多重排序
        if (MapUtils.isNotEmpty(sortMap)) {
            List<Sort.Order> orders =
                    sortMap.keySet().stream().filter(StringUtils::isNotBlank)
                            .map(column -> new Sort.Order(SortType.ASC.toValue().equalsIgnoreCase(sortMap.get(column)
                            ) ? Sort.Direction.ASC : Sort.Direction.DESC, column))
                            .collect(Collectors.toList());
            return Sort.by(orders);
        }
        return Sort.unsorted();
    }

    /**
     * 获取分页参数对象
     *
     * @return
     */
    @ApiModelProperty(value = "分页参数对象", hidden = true)
    public PageRequest getPageable() {
        return PageRequest.of(pageNum, pageSize);
    }

    /**
     * 填序排序
     *
     * @param column
     * @param sort
     */
    @ApiModelProperty(value = "填序排序", hidden = true)
    public void putSort(String column, String sort) {
        sortMap.put(column, sort);
    }
}
