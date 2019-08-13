package 并发编程.demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureCancel {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<?> future = service.submit(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("任务执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        try {
            //future.get(1000,TimeUnit.MILLISECONDS);
        } /*catch (InterruptedException e) {
            //向上抛出异常
            System.out.println("任务中断");
            throw new RuntimeException();
        } catch (ExecutionException e) {
            //向上抛出异常
            System.out.println("任务异常");
            throw new RuntimeException();
        } catch (TimeoutException e) {
            //超时
            System.out.println("任务超时");
        }*/finally {
            System.out.println("任务将被取消");
            future.cancel(true);
            /**
             * Attempts to cancel execution of this task.  This attempt will
             * fail if the task has already completed, has already been cancelled,
             * or could not be cancelled for some other reason. If successful,
             * and this task has not started when {@code cancel} is called,
             * this task should never run.  If the task has already started,
             * then the {@code mayInterruptIfRunning} parameter determines
             * whether the thread executing this task should be interrupted in
             * an attempt to stop the task.
             */
            //已完成的任务调用cancel是不受影响的。如果一个task还没有启动就cancel，它将不会run，如果已经启动,将会取消到这个任务
            System.out.println(future.isCancelled());
            //不shutdown线程池，则池子中的线程一直是活跃状态,jvm则不会退出
            //service.shutdown();
            //对于已经cancel掉的任务，再get会抛出异常
            System.out.println(future.get());
        }
    }
}
