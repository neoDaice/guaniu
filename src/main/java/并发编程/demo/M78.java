package 并发编程.demo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

public class M78 implements Runnable {
    //给定一个标志位来作为任务的开关
    private volatile boolean cancelled;
    private final List<BigInteger> primes = new ArrayList<>();
    @Override
    public void run() {
        BigInteger one = BigInteger.ONE;
        while (!cancelled){
            one = one.nextProbablePrime();
            synchronized (this) {
                primes.add(one);
            }
        }
    }
    public void cancel(){
        cancelled = true;
    }
    public synchronized List<BigInteger> get(){
        return new ArrayList<BigInteger>(primes);
    }

    public static void main(String[] args) throws InterruptedException {
        //1秒的素数生成器
        M78 m78 = new M78();
        new Thread(m78).start();
        try{
            SECONDS.sleep(1);
        }finally {
            m78.cancel();
        }
        System.out.println(m78.get());
    }

}
