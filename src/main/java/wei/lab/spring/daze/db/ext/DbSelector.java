package wei.lab.spring.daze.db.ext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * created by weixuecai on 2018/6/13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbSelector {
    /**
     * 指向的数据源的key, 为常量，即直接表示某个数据源的key，允许多个key, 以"|"分割； 会将结果集汇总
     * @return
     */
    String dbKey() default "";

    /**
     * 如果dbKey为空，则需要依赖该值计算具体的dbKey. 目前只支持一个参数. 且为SpEl表达式
     * @return
     */
    String dependParam() default "";

    /**
     * 用来只是数据源类型。不同的dataType需要采用不同的计算规则
     * @return 0表示PersonInfo表，1表示PersonAlterLog表
     */
    int dataType() default 0;
}
