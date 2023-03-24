package cc.sybx.saas.common.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Slf4j
public class DataSourceProxyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        boolean flag = "true".equals(conditionContext.getEnvironment().getProperty("spring.datasource.hikari.enable"));
        return flag;
    }
}
