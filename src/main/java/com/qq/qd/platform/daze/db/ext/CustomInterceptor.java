package com.qq.qd.platform.daze.db.ext;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;
import java.util.Properties;

/**
 * created by weixuecai on 2018/7/31
 */
@Intercepts({
//        @Signature(method = "query", type = Executor.class,
//                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class,
//                        BoundSql.class}),
        @Signature(method = "query", type = Executor.class,
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//        @Signature(method = "queryCursor", type = Executor.class,
//                args = {MappedStatement.class, Object.class, RowBounds.class}),
        @Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})})
public class CustomInterceptor implements Interceptor{
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("start to invoke Custom mybatis plugin.");
        Object[] args = invocation.getArgs();
        System.out.println(invocation.getTarget().getClass());
        System.out.println(invocation.getMethod().toString());
        for(Object obj: args) {
            if(obj == null) {
                continue;
            }
            System.out.println(obj.getClass() + ", value:" + obj);
        }

        System.out.println("end to invoke Custom mybatis plugin.");

//        MappedStatement mappedStatement = (MappedStatement) args[0];
//        System.out.println("paramMap:" + JSON.toJSONString(mappedStatement.getParameterMap()));

        Object paramObj = args[1];
        if(paramObj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>)paramObj;
            map.put("subTable", "person_info");
        }
//        if(!(paramObj instanceof Map)) {
//            Map<String, Object> mapParam = new MapperMethod.ParamMap<>();
//            mapParam.put("param2", paramObj);
//            mapParam.put("subTable", "person_info");
//            mapParam.put("param1", "person_info");
//        }

        return invocation.proceed();
//        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
//        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
