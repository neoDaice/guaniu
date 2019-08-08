package 并发编程.中断;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        BigInteger one = BigInteger.ONE;
        try {
            //检查线程中断标志
            while (!Thread.currentThread().isInterrupted()) {
                one = one.nextProbablePrime();
                //put 为阻塞方法，主动中断会抛出InterruptedException
                queue.put(one);
            }
        } catch (InterruptedException e) {
            //线程中断退出
            System.out.println("线程中断退出");
        }
    }
    public void cancel(){
        //interrupt对于block和runnable状态的线程实际上只是设置了中断标志为ture，而不是立即中断掉线程
        interrupt();
    }
    public synchronized List get(){
        ArrayList<BigInteger> bigIntegers = new ArrayList<>();
        bigIntegers.addAll(queue);
        return bigIntegers;
    }

    public static void main(String[] args) throws InterruptedException {
        PrimeProducer producer = new PrimeProducer(new LinkedBlockingQueue<BigInteger>());
        producer.start();
        Thread.sleep(1000);
        producer.cancel();
        System.out.println(producer.get());
    }

}
