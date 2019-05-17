package wei.lab.spring.daze.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * created by weixuecai on 2019/4/9
 */
@Component
@Aspect
public class TestAopServiceAspect {
    @Pointcut("within(wei.lab.spring.daze.service.TestAopService)")
    public void test(){}

    @Around("test()")
    public void intercept(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("I am coming in.");
        joinPoint.proceed();
        System.out.println("proxy:" + joinPoint.getThis().getClass().getInterfaces()[2]);
        System.out.println("I am coming out.");
    }
}
