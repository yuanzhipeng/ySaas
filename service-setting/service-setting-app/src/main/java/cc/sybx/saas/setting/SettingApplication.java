package cc.sybx.saas.setting;

import cc.sybx.saas.common.configure.CompositePropertySourceFactory;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.net.InetAddress;

@Slf4j
@EnableFeignClients(basePackages = {"cc.sybx.saas"})
@PropertySource(value = {"api-application.properties"}, factory = CompositePropertySourceFactory.class)
@EnableJpaAuditing
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class}, scanBasePackages = {"cc.sybx.saas"})
public class SettingApplication {

    public static void main(String[] args) throws Exception {
        System.setProperty("nacos.logging.default.config.enabled", "false");
        Environment env = SpringApplication.run(SettingApplication.class, args).getEnvironment();
        String port = env.getProperty("server.port", "8091");
        String healthPort = env.getProperty("management.server.port", "9092");
        String localHostAddr = InetAddress.getLocalHost().getHostAddress();

        log.info("Access URLs:\n----------------------------------------------------------\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n\t"
                        + "health: \thttp://{}:{}/act/health\n----------------------------------------------------------",
                port,
                localHostAddr,
                port,
                localHostAddr,
                healthPort
        );
    }
}
