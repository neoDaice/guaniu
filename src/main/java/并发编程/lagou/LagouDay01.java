package 并发编程.lagou;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

public class LagouDay01 {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("a");
            }
        };
        Thread thread = new Thread(runnable);
        new Thread(() -> {
            System.out.println("mj");
        }).start();
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 3;
            }
        };
        thread.interrupt();
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(8);
        try {
            queue.put(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
