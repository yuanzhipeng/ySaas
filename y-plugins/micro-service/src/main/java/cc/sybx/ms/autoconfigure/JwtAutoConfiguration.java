package cc.sybx.ms.autoconfigure;

import cc.sybx.ms.jwt.JwtInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 100)
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(prefix = "jwt", name = "secret-key")
public class JwtAutoConfiguration implements WebMvcConfigurer {
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private RedisTemplate<String, ?> redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        String secretKey = jwtProperties.getSecretKey();
        String jwtHeaderKey = jwtProperties.getJwtHeaderKey();
        String jwtHeaderPrefix = jwtProperties.getJwtHeaderPrefix();
        String excludedRestUls = jwtProperties.getExcludedRestUls();

        registry.addWebRequestInterceptor(new JwtInterceptor(secretKey, jwtHeaderKey, jwtHeaderPrefix,
                excludedRestUls, redisTemplate))
                .addPathPatterns(jwtProperties.getUrlPatterns())
                .excludePathPatterns(jwtProperties.getExcludedUrls());
    }
}
