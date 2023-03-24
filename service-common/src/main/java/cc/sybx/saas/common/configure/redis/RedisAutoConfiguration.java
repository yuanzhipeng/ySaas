package cc.sybx.saas.common.configure.redis;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnProperty(prefix = "redisson", name = "injection", havingValue = "true")
public class RedisAutoConfiguration {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress(redisProperties.getUrl());
        config.useSingleServer().setDatabase(redisProperties.getDatabase());
        config.useSingleServer().setConnectionMinimumIdleSize(0);
        if(StringUtils.isNotEmpty(redisProperties.getPassword())){
            config.useSingleServer().setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
