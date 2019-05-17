package wei.lab.spring.daze.service;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * created by weixuecai on 2018/9/25
 */
@Service
public class TestService {

    @Value("${db.driverName}")
    private String tmpVar;

    @Value("${db.driverName}")
    public static String staticValue;

    @Value("${db.maxTotal}")
    private int intVar;

    @Value("#{'${list.source}'.split(',')}")
    private List<String> strList;

    @Value("#{'${plain.list}'.split(',')}")
    private List<String> plainStrList;

    @PostConstruct
    public void registerFlowQpsRule() {
        List<FlowRule> flowRules = new ArrayList<>();
        FlowRule rule = new FlowRule("test");
        rule.setCount(2);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");

        flowRules.add(rule);
        FlowRuleManager.loadRules(flowRules);
//        System.out.println("flow Rule loaded.");
    }

    @SentinelResource(value = "test", entryType = EntryType.OUT, blockHandler = "exceptionHandler", fallback = "testFallback")
    public String getTmpVar() {
//        return tmpVar + ", " + strList.get(0) + ", " + plainStrList.get(0);
        System.out.println("i'm coming");
        try {
            Thread.sleep(2000);
        } catch (Exception ex) {}
        return "it's ok, " + Thread.currentThread().getName() + ", " + System.currentTimeMillis()/1000;
    }

    public String exceptionHandler(BlockException ex) {
        ex.printStackTrace();
        return "exception, but ok." + Thread.currentThread().getName();
    }

    public String testFallback() {
        return "fallback, yeah.";
    }
}
