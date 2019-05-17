package wei.lab.spring.daze.service;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

/**
 * created by weixuecai on 2019/4/9
 */
@Service
public class TestAopService {
    public void testOne() {
        System.out.println("enter testOne.");
        ((TestAopService)(AopContext.currentProxy())).testTwo();
        System.out.println(this.getClass());
    }

    public void testTwo() {
        System.out.println("enter testTwo.");
    }
}
