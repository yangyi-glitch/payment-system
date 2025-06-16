package mav.shan.common.utils;

import java.util.concurrent.*;

public class ThreadPoolUtils {
    public static ExecutorService pool = new ThreadPoolExecutor(8, 16, 2,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(200),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());
}
