package mav.shan.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

@Configuration
public class JobThreadPoolConfig {

    private ExecutorService jobPoolTask;

    @Bean("jobPoolTask")
    public ExecutorService threadPoolTaskExecutor() {
        jobPoolTask = new ThreadPoolExecutor(8, 16, 2,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        return jobPoolTask;
    }

    @PreDestroy
    public void destroy() {
        if (jobPoolTask != null && !jobPoolTask.isShutdown()) {
            jobPoolTask.shutdown();
        }
    }
}
