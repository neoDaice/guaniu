package 并发编程.lagou;

import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableScheduling
public class LagouDay02 {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 10, 10L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2 << 8), new ThreadPoolExecutor.CallerRunsPolicy());
        executor.execute(()->{
            System.out.println("i'm coming");
        });

    }
}
