package cc.sybx.saas.customer.api.reponse.customer;

import cc.sybx.saas.customer.bean.vo.CustomerDetailInitEsVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@Data
public class CustomerDetailInitEsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户列表")
    private List<CustomerDetailInitEsVO> customerDetailInitEsVOList;
}
