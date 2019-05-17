package wei.lab.spring.daze.common.lab.schedule;

import java.util.concurrent.TimeUnit;

/**
 * created by weixuecai on 2019/4/22
 */
public interface ITimeWheelSchedule {
    /**
     * 提交一个任务
     * @param task
     * @param delay 推迟多久执行， >=0
     * @param timeUnit
     * @param loopTimes 执行的次数， -1无限循环， 0执行一次
     */
    void execute(Runnable task, long delay, TimeUnit timeUnit, boolean loopTimes);
}
