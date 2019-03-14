package wei.lab.spring.daze.cache;

import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.concurrent.Callable;

/**
 * created by weixuecai on 2018/9/18
 */
public class MyConcurrentMapCache extends ConcurrentMapCache{

    public MyConcurrentMapCache(String name) {
        super(name);
    }

    @Override
    public ValueWrapper get(Object key) {
        System.out.println("invoking ValueWrapper get(Object key):" + key + ", type:" + key.getClass());
        return super.get(key);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        System.out.println("invoking <T> T get(Object key, Class<T> type)");
        return super.get(key, type);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        System.out.println("<T> T get(Object key, Callable<T> valueLoader)");
        return super.get(key, valueLoader);
    }

    @Override
    public void put(Object key, Object value) {
        System.out.println("invoking put(Object key, Object value):" + key + ", value:" + value);
        super.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        System.out.println("invoking ValueWrapper putIfAbsent(Object key, Object value)");
        return super.putIfAbsent(key, value);
    }
}
