package wei.lab.spring.daze.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by weixuecai on 2018/9/10
 */
@Configuration
@EnableScheduling
public class ScheduleConfiguration implements SchedulingConfigurer{
    private AtomicInteger schedulerIndexer = new AtomicInteger(0);

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        System.out.println("a task is scheduled......");
        taskRegistrar.setScheduler(taskScheduler());
    }

    @Bean(destroyMethod = "shutdown")
    public ScheduledThreadPoolExecutor taskScheduler() {
        return new ScheduledThreadPoolExecutor(8, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                System.out.println("new thread created!!");
                Thread thread = new Thread(r);
                thread.setName("custom-scheduler-" + schedulerIndexer.getAndIncrement());
                return thread;
            }
        });
    }
}
