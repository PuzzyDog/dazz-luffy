package com.qq.qd.platform.daze;

import com.alibaba.fastjson.JSON;
import com.qq.qd.platform.daze.configuration.AppConfiguration;
import com.qq.qd.platform.daze.db.entity.TbPersonInfo;
import com.qq.qd.platform.daze.service.DataAccessService;
import com.qq.qd.platform.daze.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * created by weixuecai on 2018/6/13
 */
public class App {
    public static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        App app = new App();
        logger.info("spring context inited.");
        TbPersonInfo personInfo = app.queryPersonInfo(9);
        System.out.println(JSON.toJSONString(personInfo));
//        System.out.println(JSON.toJSONString(app.dataAccessService.selectPersonByIdRange(1, 20)));

//        System.out.println(app.testService.getTmpVar());
    }

    private DataAccessService dataAccessService;
    private TestService testService;

    public App() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        dataAccessService = context.getBean(DataAccessService.class);
        testService = context.getBean(TestService.class);
    }

    public TbPersonInfo queryPersonInfo(long id) {
        return dataAccessService.selectPersonById(id);
    }
}
