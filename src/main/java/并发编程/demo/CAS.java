package 并发编程.demo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
//一个自旋锁
public class CAS {
    private AtomicReference<Thread> sign = new AtomicReference<>();
    public synchronized void lock(){
        Thread thread = Thread.currentThread();
        while(!sign.compareAndSet(null,thread)){

        }
    }
    public void unlock(){
        Thread thread = Thread.currentThread();
        sign.compareAndSet(thread,null);
    }
}
