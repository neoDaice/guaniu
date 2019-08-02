import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 的demo
 * 使用计数信号量为容器设限
 */
public class A0967<T> {
    private final Set<T> set;
    private final Semaphore sem;

    public A0967(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(bound);
    }
    public boolean add(T t) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        try{
            wasAdded = set.add(t);
            return wasAdded;
        }finally {
            if(!wasAdded){
                sem.release();
            }
        }
    }
    public boolean remove(T t){
        boolean remove = set.remove(t);
        if(remove)
            sem.release();
        return remove;
    }
    public static void main(String[] args) throws InterruptedException {
        A0967<Integer> a0967 = new A0967<Integer>(3);
        boolean add = a0967.add(1);
        boolean add1 = a0967.add(2);
        boolean add2 = a0967.add(3);
        boolean add3 = a0967.add(4);  // 到这一步就发生了阻塞！！！
        a0967.remove(3);
    }
}
