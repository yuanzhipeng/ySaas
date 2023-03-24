package cc.sybx.saas.common.configure.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "redisson")
public class RedisProperties {

    private String url;

    private Integer database = 1;

    private String password;
}
