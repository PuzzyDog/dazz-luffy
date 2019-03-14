package wei.lab.spring.daze.schedule;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by weixuecai on 2018/9/10
 */
@Component
public class TestScheduleTask {

//    @Scheduled(fixedRate = 1000 * 2)
    public void testOne() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss,SSS");
        System.out.println("a new task begin, now time:" + dateFormat.format(new Date()));
//        System.out.println("hello, testOne executed once after sleep 3 seconds. now time:" + dateFormat.format(new Date()));
        try { Thread.sleep(3000); } catch (Exception ex) {}
    }
}
