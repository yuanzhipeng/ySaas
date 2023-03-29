package cc.sybx.saas.customer;

import cc.sybx.saas.common.configure.CompositePropertySourceFactory;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.net.InetAddress;
import java.util.Collections;

@Slf4j
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cc.sybx.saas"})
@PropertySource(value = {"api-application.properties"}, factory = CompositePropertySourceFactory.class)
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class},scanBasePackages = {"cc.sybx.saas"})
public class CustomerApplication {

    public static void main(String[] args) throws Exception {
        Environment env = SpringApplication.run(CustomerApplication.class, args).getEnvironment();
        String port = env.getProperty("server.port", "8940");
        String actPort = env.getProperty("management.server.port", "8941");
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        log.info("Access URLs:\n----------------------------------------------------------\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n\t"
                        + "health: \thttp://{}:{}/act/health\n----------------------------------------------------------",
                port,
                hostAddress,
                port,
                hostAddress,
                actPort
        );
    }}
