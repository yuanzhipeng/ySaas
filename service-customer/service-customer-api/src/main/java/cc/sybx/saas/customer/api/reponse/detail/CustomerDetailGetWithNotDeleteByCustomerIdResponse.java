package cc.sybx.saas.customer.api.reponse.detail;

import cc.sybx.saas.customer.bean.vo.CustomerDetailVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = true)
public class CustomerDetailGetWithNotDeleteByCustomerIdResponse extends CustomerDetailVO implements Serializable {

}
