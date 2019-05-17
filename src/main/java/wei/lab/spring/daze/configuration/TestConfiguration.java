package wei.lab.spring.daze.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import wei.lab.spring.daze.entity.TestEntity;

/**
 * created by weixuecai on 2019/3/16
 */
@Configuration
public class TestConfiguration {
    @Bean
    @Lazy//默认为true, lazy-init可以理解为先生成bean-definition, 但暂时不进入life-cycle
    public TestEntity createTestEntity() {
        return new TestEntity();
    }
}
