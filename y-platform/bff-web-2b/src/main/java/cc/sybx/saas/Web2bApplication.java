package cc.sybx.saas;

import cc.sybx.saas.common.configure.CompositePropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"web-base-application.properties", "application.properties", "api-application.properties"},
        factory = CompositePropertySourceFactory.class)
public class Web2bApplication {

    public static void main(String[] args) {
        SpringApplication.run(Web2bApplication.class, args);
    }

}
