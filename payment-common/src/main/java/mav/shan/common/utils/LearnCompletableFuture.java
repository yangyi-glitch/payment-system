package mav.shan.common.utils;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import mav.shan.common.entity.FileDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LearnCompletableFuture {
    static AtomicInteger count = new AtomicInteger(0);

    static Object lock = new Object();

    static ExecutorService pool = new ThreadPoolExecutor(
            3,
            20,
            2,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        try {
//            for (int i = 0; i < 10; i++) {
//                CompletableFuture<String> future = supplyAsync("张三" + i);
//                futures.add(future);
//            }
//            runAsync("张三" + i);
//            System.out.println(thenApply("张三").get());
//            thenAccept("张三");
//            thenRun("张三");
//            System.out.println(thenCombine("张三").get());
//            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
//            voidCompletableFuture.join();
//            CompletableFuture<Object> voidCompletableFuture = CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()]));
//            Object o = voidCompletableFuture.get();
//            System.out.println("主线程任务~" + o);
//            CompletableFuture<String> future = whenComplete();
//            System.out.println(future.get());
//            CompletableFuture<String> handle = handle();
//            System.out.println(handle.get());
//            System.out.println(exceptionally().get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            pool.shutdown();
        }
    }

    /**
     * 有返回值
     */
    public static CompletableFuture<String> supplyAsync(String name) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(name);
            return name;
        }, pool);
        return future;
    }

    /**
     * 无返回值
     */
    public static void runAsync(String name) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println(name);
        }, pool);
    }

    /**
     * 有返回值 修改结果
     */
    public static CompletableFuture<String> thenApply(String name) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(name);
            return "hello";
        }, pool).thenApply(s -> "yangyi");
        return future;
    }

    /**
     * 无返回值 消费结果
     */
    public static void thenAccept(String name) {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(name);
            return "hello";
        }, pool).thenAccept(s -> {
            System.out.println(s);
        });
    }

    /**
     * 无返回值 不依赖上一步结果
     */
    public static void thenRun(String name) {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(name);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFileName("张三");
            return fileDTO;
        }, pool).thenRun(() -> {
            System.out.println("thenARun");
        });
    }

    /**
     * thenCompose 将前一个任务的结果作为下一个任务的输入 （串行）
     */
    public static CompletableFuture<String> thenCompose(String name) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(name);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFileName("张三");
            return fileDTO;
        }, pool).thenCompose(s -> CompletableFuture.supplyAsync(() -> s.getFileName(), pool));
    }

    /**
     * thenCombine 两个任务都完成后，合并结果 （并行）
     */
    public static CompletableFuture<List<String>> thenCombine(String name) {
        CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> "World"), (s1, s2) -> Arrays.asList(s1, s2));
        return future;
    }

    /**
     * whenComplete 无论成功还是失败都会执行，但不改变结果
     */
    public static CompletableFuture<String> whenComplete() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(1 / 0);
            return "Hello";
        }, pool);
        return future.whenComplete((result, throwable) -> {
            if (throwable != null) {
                System.err.println("Error: " + throwable.getMessage());
            } else {
                System.out.println("Result: " + result);
            }
        });
    }

    /**
     * handle 统一处理正常结果或异常
     */
    public static CompletableFuture<String> handle() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(1 / 0);
            return "Hello";
        }, pool).handle((result, throwable) -> {
            if (throwable != null) {
                System.err.println("Error: " + throwable.getMessage());
                return "Error";
            } else {
                System.out.println("Result: " + result);
                return "Success";
            }
        });
        return future;
    }

    /**
     * exceptionally 只有出现错误才会执行
     */
    public static CompletableFuture<String> exceptionally() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(1 / 0);
            return "Success";
        }, pool).exceptionally(throwable -> {
            System.err.println("Error: " + throwable.getMessage());
            return "Error";
        });
        return future;
    }
}
