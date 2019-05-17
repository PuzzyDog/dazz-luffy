package wei.lab.spring.daze.common.lab.schedule.uuid;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 63   ： 0
 * 20 - 62 : 存放相对epochTime的毫秒是，允许278年时间
 * 10 - 19 : 允许部署1024台机器
 * 0 - 9 : 允许单台机器1ms内生成1024的自增序号
 * created by weixuecai on 2019/4/25
 */
public class UniqueLongIdGenerator {
    //大爆炸时间
    private final long epochTime = 1556183264000L; // "2019/4/25 17:7:44"

    private final long machineId;
    private final static int MACHINE_ID_OFFSET = 10;
    private AtomicInteger counter = new AtomicInteger(0);
    private long curMillis = 0;

    private final static int TIME_OFFSET = 20;

    public UniqueLongIdGenerator(int machineId) {
        if(machineId < 0 || machineId >= 1024) {
            throw new RuntimeException("invalid machineId");
        }

        this.machineId = machineId << MACHINE_ID_OFFSET;
    }

    public long next() {
    }

    public static void main(String[] args) {
        UniqueLongIdGenerator generator = new UniqueLongIdGenerator(3);
        long id = generator.next();
        System.out.println(id);

        String bits = Long.toBinaryString(id);
        if(bits.length() < 64) {
            int diff = 64 - bits.length();
            StringBuilder builder = new StringBuilder(64);
            while(diff-- > 0) {
                builder.append("0");
            }
            builder.append(bits);
            bits = builder.toString();
        }
        System.out.print(bits.substring(0, 1) + " ");
        System.out.print(bits.substring(1, 44) + " ");
        System.out.print(bits.substring(44, 54) + " ");
        System.out.print(bits.substring(54, 64));
    }
}
