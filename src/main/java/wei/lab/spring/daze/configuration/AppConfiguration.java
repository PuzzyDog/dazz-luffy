package wei.lab.spring.daze.configuration;

import wei.lab.spring.daze.configuration.ext.TypeSafeConfigFactory;
import org.springframework.context.annotation.*;

/**
 * created by weixuecai on 2018/6/13
 */
@Configuration
@Import({DatasourceConfiguration.class, CacheConfiguration.class})
@ComponentScan(basePackages = "com.qq.qd.platform.daze")
@EnableAspectJAutoProxy
@PropertySource(name = "typeSafeConfig", value = "classpath:resources.conf", factory = TypeSafeConfigFactory.class)
@PropertySource("classpath:plain.properties")
public class AppConfiguration {
}
