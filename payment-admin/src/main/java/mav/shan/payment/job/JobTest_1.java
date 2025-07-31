package mav.shan.payment.job;

import mav.shan.payment.start_redis.queue.RichListQueue;
import mav.shan.payment.start_redis.queue.TimeLapseQueue;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.*;

@Component
public class JobTest_1 {
    @Resource
    private ExecutorService jobPoolTask;
    @Resource
    private TimeLapseQueue timeLapseQueue;
    @Resource
    private RichListQueue richListQueue;


    @Scheduled(fixedRate = 1000)
    public void test_1() {
        Set<String> strings = timeLapseQueue.consumeQueue(System.currentTimeMillis());
        if (strings != null) {
            jobPoolTask.execute(() -> execute(strings));
        }
    }

    @Scheduled(fixedRate = 1000)
    public void test_2() {
        Set<String> strings = richListQueue.consumeQueue();
        for (String string : strings) {
            System.out.println(string);
        }
    }

    private void execute(Set<String> strings) {
        for (String string : strings) {
            System.out.println("开始执行任务");
            System.out.println(LocalDateTime.now());
            System.out.println(string);
            timeLapseQueue.removeQueue(string);
        }
    }
}
