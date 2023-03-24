package cc.sybx.saas.common.base;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
public class BaseRequest implements Serializable {
    /**
     * 登陆用户ID
     */
    private String userId;

    /**
     * 店铺ID Saas
     */
    private Long storeIdAtSaas;

    /**
     * 公司ID Saas
     */
    private Long companyInfoIdAtSaas;

    /**
     * 参数统一check
     */
    public void checkParam(){
        log.info("统一参数校验入口...");
    }

    /**
     * 敏感词check
     * @return
     */
    public String checkSensitiveWord(){
        return null;
    }
}
