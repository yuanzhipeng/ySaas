package cc.sybx.saas.configure;

import cc.sybx.saas.filter.XssFilter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Value("#{'${cors.allowedOrigins:*}'.split(',')}")
    private List<String> allowedOrigins;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(allowedOrigins);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new CorsFilter(source));

        registrationBean.setFilter(xssFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("excludeFieldsName", "x-pingplusplus-signature,applyList," +
                "QRCodeImgSrc,arrList,skus,ids,skuIds,items,province,user-agent,signPubKeyCert,fund_bill_list");
        registrationBean.setName("xssFilter");
        registrationBean.setOrder(0);
        return registrationBean;
    }

    @Bean
    public Filter xssFilter() {
        return new XssFilter();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins(allowedOrigins.stream().toArray(String[]::new))
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
