package wei.lab.spring.daze.configuration.ext;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;

/**
 * created by weixuecai on 2018/9/25
 */
public class TypeSafeConfigFactory implements PropertySourceFactory{
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        return name != null? new TypeSafePropertySource(name, resource): new TypeSafePropertySource(resource);
    }

    /*见@TypeSafePropertySource中的class说明*/
    public static final String CONFIG_OBJ_SUFFIX = ".@cObj";

    /*见@TypeSafePropertySource中的class说明*/
    public static final String CONFIG_SUFFIX = ".@c";
}
