package 并发编程.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class 未捕获异常处理器 {

    public static void main(String[] args) {
        //ExecutorService executorService = Executors.newCachedThreadPool();
        //发现线程并不会立即结束掉
        //executorService.execute(new Task());
        //通过在线程池中设置ThreadFactory,捕获异常
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        //这里会捕获到线程抛出的异常
                        System.out.println("caught this exception" + e.getMessage());
                        //Thread.currentThread().interrupt();
                        //System.out.println(Thread.currentThread().isInterrupted());
                        //System.exit(0);  终止jvm
                    }
                });
                return thread;
            }
        };
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1, threadFactory);
        fixedThreadPool.execute(new Task());
    }
    static class Task implements Runnable{

        @Override
        public void run() {
            throw new RuntimeException("throw self-exception");
        }
    }
}
