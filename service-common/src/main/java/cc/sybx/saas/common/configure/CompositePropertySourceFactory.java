package cc.sybx.saas.common.configure;

import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CompositePropertySourceFactory implements PropertySourceFactory {
    //jar中存在重名的properties配置
    private static final String DUPLICATION_PROPERTIES = "api-application.properties";

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {

        String filename = resource.getResource().getFilename();
        if(DUPLICATION_PROPERTIES.equalsIgnoreCase(filename)){
            Map<String, Object> map = new ConcurrentHashMap<>();
            this.getPackageInsideResourcesByPattern(filename).forEach(res -> {
                try{
                    ResourcePropertySource source = new ResourcePropertySource(res);
                    map.putAll(source.getSource());
                }catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return new MapPropertySource(filename, map);
        }
        return name != null ? new ResourcePropertySource(name, resource) : new ResourcePropertySource(resource);
    }

    private List<Resource> getPackageInsideResourcesByPattern(String resourceName) throws IOException {
        String resourcePathPattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resourceName;
        return Arrays.asList(new PathMatchingResourcePatternResolver().getResources(resourcePathPattern));
    }
}