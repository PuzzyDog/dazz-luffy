package wei.lab.spring.daze.db.ext;

import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import wei.lab.spring.daze.configuration.DatasourceConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * created by weixuecai on 2018/6/13
 */
@Aspect
@Component
@Order(value = 1)
public class DbSelectorAOP {
    public static final Logger LOGGER = LoggerFactory.getLogger(DbSelectorAOP.class);

    private ExpressionParser expressionParser = new SpelExpressionParser();

    @Around("dbSelector()")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature)pjp.getSignature()).getMethod();

        System.out.println(pjp.getThis().getClass());

        //注册参数
        Object[] argValues = pjp.getArgs(); //参数值
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        EvaluationContext context = new StandardEvaluationContext();
        for(int i=0; i<argValues.length; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            if(annotations.length == 1 && annotations[0] instanceof Param) {
                String paramName = ((Param)annotations[0]).value();
                context.setVariable(paramName, argValues[i]);
            }
        }

        DbSelector dbSelector = method.getDeclaredAnnotation(DbSelector.class);
        String dbKey = dbSelector.dbKey();
        String dependParamSpElKey = dbSelector.dependParam();
        int dataType = dbSelector.dataType();

        String dataSourceLookupKey;
        if(!dbKey.isEmpty()) {
            dataSourceLookupKey = dbKey;
        } else {
            Expression expression = expressionParser.parseExpression(dependParamSpElKey);
            long longValue = expression.getValue(context, long.class);
            dataSourceLookupKey = calculateLookupKey(longValue, dataType);
        }

        String[] lookupKeys = dataSourceLookupKey.split("\\|");
        Object[] objs = new Object[lookupKeys.length];
        for(int i=0; i<lookupKeys.length; i++) {
            String str = lookupKeys[i];
            if(str != null || !str.isEmpty()) {
                DatasourceConfiguration.dataSourceKey.set(str);
                try {
                    objs[i] = pjp.proceed();
                    if(!(objs[i] instanceof List)) {
                        return objs[i];
                    } else if(i >0) {
                        List list = (List)objs[i];
                        ((List)objs[0]).addAll(list);
                    }
                } finally {
                    DatasourceConfiguration.dataSourceKey.remove();
                }
            } else {
                throw new RuntimeException("watch your dbSelector!!");
            }
        }

        return objs[0];
    }

    /**/
    private String calculateLookupKey(long longValue, int dataType) {
        if(dataType == 0) {//PersonInfo表
            long remaining = longValue%2;
            return remaining==0?"one":"three";

        } else {
            return "two";
        }
    }

    @Pointcut("within(wei.lab.spring.daze.service.DataAccessService)" +
            "&& @annotation(DbSelector)")
    public void dbSelector(){}
}
