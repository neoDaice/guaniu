package 并发编程.lagou;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemp {
    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    public void testRead(){
        readLock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"---获取到读锁");
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName()+"---释放掉读锁");
        }
    }

    public void testWrite(){
        writeLock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"---获取到写锁");
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName()+"---释放掉写锁");
        }
    }


    public static void main(String[] args) {
        //读锁共享，写锁互斥
        ReadWriteLockDemp demp = new ReadWriteLockDemp();
        new Thread(()->{demp.testRead();},"001").start();
        new Thread(()->{demp.testRead();},"002").start();
        new Thread(()->{demp.testWrite();},"003").start();
        new Thread(()->{demp.testWrite();},"004").start();
    }
}
