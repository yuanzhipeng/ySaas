package cc.sybx.ms.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * 过滤器路径
     */
    private String[] urlPatterns = {};

    /**
     * 排除的url 被排除的不参与jwt验证
     */
    private String[] excludedUrls = {};

    /**
     * restful 排除
     */
    private String excludedRestUls;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 过滤器排序
     */
    public static int order = Ordered.LOWEST_PRECEDENCE;

    /**
     * jwt key
     */
    private String jwtHeaderKey = "Authorization";

    /**
     * jwt前缀
     */
    private String jwtHeaderPrefix = "Bearer ";
}
