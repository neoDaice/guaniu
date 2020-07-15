package 并发编程.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestSync {
    public void sync(){
        synchronized (this){
            System.out.println("just a test");
        }
    }

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
    }
}
