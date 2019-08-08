package 并发编程.中断;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 使用wait/notify实现等待通知机制
 * wait 使得当前线程释放锁，加入到等待唤醒队列
 * notify 随机唤醒队列中的一个线程
 * 引起当前线程等待,直到另一个线程调用此对象的notify()方法或notifyAll()方法.或者指定线程超时等待一定时间后。
 * 这个超时时间单位是纳秒，其计算公式为: 1000000*timeout+nanos
 * 如果使用wait(0)和wait(0,0)是等价的
 * 如果当前对象在没有获得锁的监视器的情况下就调用wait或者notify/notifyAll方法就是抛出IllegalMonitorStateException异常
 * 当前对象的wait方法会暂时释放掉对象监视器的锁,所以wait必须是在synchronized同步块中使用,因为synchronized同步块进入是默认是要获取对象监视器的。同理notify/notifyAll操作也要在对象获取监视器的情况下去唤醒一个等待池中的线程
 * wait操作还要在一个循环中使用,防止虚假唤醒
 * (1)可以使用wait（）和notify（）方法在Java中实现线程间通信。不只是一个或两个线程，而是多个线程可以使用这些方法相互通信。
 * (2)在synchronized方法或synchronized块中调用wait（），notify（）和notifyAll（）方法，否则JVM将抛出IllegalMonitorStateException。
 * (3)从while（条件）循环调用wait和notify方法，而不是从if（）块调用，因为要重复检查条件，而不仅仅是一次。
 * (4)多使用notifyAll方法而不是notify。
 */
public class WaitNotify {
    /**
     * 生产者
     */
    public static void main(String[] args) {
        List<Integer> container = new ArrayList<>();
        Thread producer = new Thread(new Producer(container));
        Thread consumer = new Thread(new Consumer(container));
        producer.start();
        consumer.start();
    }
    static class Producer implements Runnable {
        /**
         * 产品容器
         */
        private final List<Integer> container;
        public Producer(List<Integer> container) {
            this.container = container;
        }

        /**
         * 生产者生产方法
         */
        private void produce() throws InterruptedException {
            //产品容器容量
            int capacity = 5;
            synchronized (container) {
                //当容器已满,暂停生产
                while (container.size() == capacity) {
                    System.out.println("...容器已经满了，暂停生产...");
                    container.wait();
                }
                Random random = new Random();
                int p = random.nextInt(50);
                //模拟1秒生产一个产品
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println("生产产品：" + p);
                container.add(p);
                container.notifyAll();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("produce error");
                }
            }
        }
    }
    /**
     * 消费者
     */
    static class Consumer implements Runnable {

        /**
         * 产品容器
         */
        private final List<Integer> container;

        public Consumer(List<Integer> container) {
            this.container = container;
        }

        /**
         * 消费者消费产品
         */
        private void consume() throws InterruptedException {
            synchronized (container) {
                while (container.isEmpty()) {
                    System.out.println("...容器是空的，暂停消费...");
                    container.wait();
                }
                Integer p = container.remove(0);
                //模拟1秒消费一个产品
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println("消费产品：" + p);
                container.notifyAll();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("consume error");
                }
            }
        }
    }
}

