package cc.sybx.saas.elasticsearch;

import cc.sybx.saas.common.configure.CompositePropertySourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;

@SpringBootApplication(scanBasePackages = {"cc.sybx.saas"})
@EnableAsync
@EnableDiscoveryClient
@Slf4j
@EnableFeignClients(basePackages = {"cc.sybx.saas"})
@PropertySource(value = {"api-application.properties"}, factory = CompositePropertySourceFactory.class)
public class ElasticsearchApplication {

    public static void main(String[] args) throws Exception{
        System.setProperty("nacos.logging.default.config.enabled", "false");
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        Environment env = SpringApplication.run(ElasticsearchApplication.class, args).getEnvironment();
        String port = env.getProperty("server.port", "8090");
        String actPort = env.getProperty("management.server.port", "8091");
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
    }
}
