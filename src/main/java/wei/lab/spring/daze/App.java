package wei.lab.spring.daze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import wei.lab.spring.daze.configuration.AppConfiguration;
import wei.lab.spring.daze.db.entity.TbPersonInfo;
import wei.lab.spring.daze.service.DataAccessService;
import wei.lab.spring.daze.service.TestAopService;
import wei.lab.spring.daze.service.TestService;

/**
 * created by weixuecai on 2018/6/13
 */
public class App {
    public static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        App app = new App();
        app.testAopService.testOne();
    }

    private DataAccessService dataAccessService;
    private TestService testService;
    private TestAopService testAopService;

    public App() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        dataAccessService = context.getBean(DataAccessService.class);
        testService = context.getBean(TestService.class);
        testAopService = context.getBean(TestAopService.class);
    }

    public TbPersonInfo queryPersonInfo(long id) {
        System.out.println("staticValue:" + testService.staticValue);
        System.out.println("other:" + testService.getTmpVar());
        return dataAccessService.selectPersonById(id);
    }
}
