package com.qq.qd.platform.daze.configuration;

import com.qq.qd.platform.daze.configuration.ext.TypeSafeConfigFactory;
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
