package 并发编程.demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class 饥饿死锁 {

    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            Thread.sleep(3000);
            return 2;
        });
        FutureTask<Integer> futureTask1 = new FutureTask<>(() -> 4);
        service.execute(futureTask);
        service.execute(futureTask1);

        try {
            Integer i1 = futureTask.get();
            Integer i2 = futureTask1.get();
            System.out.println(i1 +","+i2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
