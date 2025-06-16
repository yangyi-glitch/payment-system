package mav.shan.common.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {

    @Bean("poolExecutor")
    public Executor poolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Async-Executor-");
        executor.initialize();
        return executor;
    }

//    public static ExecutorService pool = new ThreadPoolExecutor(8, 16, 2,
//            TimeUnit.SECONDS,
//            new ArrayBlockingQueue<>(200),
//            Executors.defaultThreadFactory(),
//            new ThreadPoolExecutor.AbortPolicy());
}
