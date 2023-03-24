package cc.sybx.saas;

import cc.sybx.saas.common.configure.CompositePropertySourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@EnableJpaAuditing
@EnableFeignClients(basePackages = {"cc.sybx.saas"})
@SpringBootApplication(scanBasePackages = {"cc.sybx.saas"})
@PropertySource(value = {"manage-base-application.properties","api-application.properties"},
		factory = CompositePropertySourceFactory.class)
public class BossApplication {

	public static void main(String[] args) throws UnknownHostException {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		Environment env = SpringApplication.run(BossApplication.class, args).getEnvironment();
		String port = env.getProperty("server.port", "8086");
		String healthPort = env.getProperty("management.server.port", "9005");

		log.info("Access URLs:\n----------------------------------------------------------\n\t"
						+ "Local: \t\thttp://127.0.0.1:{}\n\t"
						+ "External: \thttp://{}:{}\n\t"
						+ "health: \thttp://{}:{}/act/health\n" +
						"----------------------------------------------------------",
				port,
				InetAddress.getLocalHost().getHostAddress(),
				port,
				InetAddress.getLocalHost().getHostAddress(),
				healthPort
		);
	}
}
