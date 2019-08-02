import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

//CountDownLatch的demo
public class A0401 {
    public long timeTasks(int nThreads,final Runnable task)throws Exception{
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        for(int i=0;i<nThreads;i++){
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        //await在计数器不为0的时候会阻塞线程
                        startGate.await();
                        task.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        endGate.countDown();
                    }
                }
            };
            runnable.run();
        }
        long start = System.nanoTime();
        //startGate == 0,唤醒所有阻塞的线程
        startGate.countDown();
        //直到n个线程都执行完task，放开大门，统计出n个线程的执行时间
        endGate.await();
        long end = System.nanoTime();
        return end-start;
    }
    //模拟游戏加载

    /**
     * 10名玩家并行加载完毕后开始游戏
     */
    @Test
    public void goGame() {
        int players = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(players);
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(String.valueOf(i)) {
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName()+"号玩家加载中。。。");
                        Thread.sleep(new Random().nextInt(10)*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                            System.out.println(Thread.currentThread().getName()+"号玩家加载完毕");
                            countDownLatch.countDown();
                    }
                }
            };
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有玩家加载完毕，进入游戏");
    }
}
