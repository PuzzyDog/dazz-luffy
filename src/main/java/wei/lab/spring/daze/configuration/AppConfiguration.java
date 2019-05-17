package wei.lab.spring.daze.configuration;

import org.springframework.context.annotation.*;
import wei.lab.spring.daze.configuration.ext.TypeSafeConfigFactory;

/**
 * created by weixuecai on 2018/6/13
 */
@Configuration
@Import({DatasourceConfiguration.class, CacheConfiguration.class})
@ComponentScan(basePackages = "wei.lab.spring.daze")
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@PropertySource(name = "typeSafeConfig", value = "classpath:resources.conf", factory = TypeSafeConfigFactory.class)
@PropertySource("classpath:plain.properties")
public class AppConfiguration {
}
