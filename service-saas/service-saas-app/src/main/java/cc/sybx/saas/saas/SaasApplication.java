package cc.sybx.saas.saas;

import cc.sybx.saas.common.configure.CompositePropertySourceFactory;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.net.InetAddress;

@Slf4j
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cc.sybx.saas"})
@PropertySource(value = {"api-application.properties"}, factory = CompositePropertySourceFactory.class)
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class}, scanBasePackages = {"cc.sybx.saas"})
public class SaasApplication {

    public static void main(String[] args) throws Exception {
        Environment env = SpringApplication.run(SaasApplication.class, args).getEnvironment();
        String port = env.getProperty("server.port", "8090");
        String actPort = env.getProperty("management.server.port", "8091");
        log.info("Access URLs:\n----------------------------------------------------------\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n\t"
                        + "health: \thttp://{}:{}/act/health\n----------------------------------------------------------",
                port,
                InetAddress.getLocalHost().getHostAddress(),
                port,
                InetAddress.getLocalHost().getHostAddress(),
                actPort
        );
    }}
