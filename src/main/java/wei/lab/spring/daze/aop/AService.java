package wei.lab.spring.daze.aop;

import wei.lab.spring.daze.service.TestAopService;

/**
 * created by weixuecai on 2019/4/9
 */
public class AService extends TestAopService{
    @Override
    public void testOne() {
        super.testOne();
    }

    @Override
    public void testTwo() {
        System.out.println("enter subclass testTwo");
    }
}
