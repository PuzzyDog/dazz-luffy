package wei.lab.spring.daze;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * created by weixuecai on 2019/3/26
 */
class Foo {
    public void fun1(){
        System.out.println("fun1");
        fun2();
    }
    public void fun2() {
        System.out.println("fun2");
    }
}
   class CGlibProxyEnhancer implements MethodInterceptor {
        public Object getProxy(Class clazz) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(clazz);
            enhancer.setCallback(this);
            return enhancer.create();
        }
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.print("before ");
            Object result = proxy.invokeSuper(obj,args);
            return result;
        }
    }

    class Test {
        public static void main(String[] args) {
            CGlibProxyEnhancer pf = new CGlibProxyEnhancer();
            Foo foo = (Foo) pf.getProxy(Foo.class);
            foo.fun1();
        }
    }
