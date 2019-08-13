
package 并发编程.中断;

import org.apache.commons.lang.RandomStringUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


/**
 *多生产者 多消费者毒丸demo
 */

public class 毒丸 {
    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private static final String SNAKE = "斯内克";
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(6);
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        singleThreadExecutor.execute(new Coordinator(latch));
        for(int i=1;i<=6;i++) {
            fixedThreadPool.execute(new Productor(i + "号生产者",latch));
        }
        for(int i=1;i<=4;i++){
            fixedThreadPool.execute(new Consumer(i + "号消费者"));
        }
    }

     static class Productor extends Thread{
        private String name;
        private final CountDownLatch latch;
        public Productor(String name,CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        public void run(){
            int i = 50;
            while(i != 0) {
                try {
                    String s = RandomStringUtils.randomAlphanumeric(3);
                    System.out.println(this.name + "向队列中添加" + s);
                    queue.put(s);
                    if(--i == 0){
                        System.out.println(this.name + "完成生产任务");
                        this.latch.countDown();
                    }
                } catch (InterruptedException e) {
                    System.out.println(this.name +"发生异常");
                    this.latch.countDown();
                }
            }
        }
    }

     static class Consumer extends Thread{
        private String name;
        private int count;
        public Consumer(String name) {
            this.name = name;
        }

        public void run(){
            while(true){
                try {
                    String s = queue.take();
                    if(s == SNAKE){
                        System.out.println(this.name+"拿到了毒药,结束任务,共消费消息"+count+"条");
                        int i;
                        for(i = 1;i<=10;i++){
                            try {
                                queue.put(s);
                                break;
                            }catch (Exception e){
                                System.out.println("发生异常，重试"+i);
                            }
                        }
                        if(i == 10){
                            throw new InterruptedException();
                        }
                        break;
                    }
                    count++;
                    System.out.println(this.name + "消费字符串" + s);
                } catch (InterruptedException e) {
                    System.out.println(this.name + "异常，请联系管理员");
                    break;
                }
            }
        }
    }
    static class Coordinator extends Thread{
        private final CountDownLatch latch;

        Coordinator(CountDownLatch latch) {
            this.latch = latch;
        }

        public void run(){
            try {
                this.latch.await();
                while(true) {
                    queue.put(SNAKE);
                    break;
                }
            } catch (InterruptedException e) {
                //阻塞过程中发生中断,应该要向上抛出异常
            }
        }
    }
}

