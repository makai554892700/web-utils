package www.mys.com.utils.base;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyPropertySourceFactory extends DefaultPropertySourceFactory {
    private static final Logger log = Logger.getLogger(DefaultPropertySourceFactory.class.getName());

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        log.log(Level.WARNING, "createPropertySource fileName=" + resource.getResource().getFile().getAbsolutePath() + ";name=" + name);
        if (resource == null) {
            return super.createPropertySource(name, resource);
        }
        List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());
        return sources.get(0);
    }

}