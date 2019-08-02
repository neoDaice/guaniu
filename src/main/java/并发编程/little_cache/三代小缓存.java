package 并发编程.little_cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 为解决次代小缓存提出的问题，我们希望计算同值的线程能够知道有其他的线程正在计算，他们只需要等待计算结果即可
 * 这种通过等待结果来阻塞线程的操作可以使用FutureTask
 * 这里concurrentHashMap的value存储的是futureTask
 * 当缓存中缓存的是任务而不再是结果值的时候，就会产生缓存污染问题：任务可能执行失败或被中途取消，这里需要
 * 1.缓存预期问题可以通过FutureTask的子类为结果设置一个过期时间
 * 2.考虑实现定期清理功能，腾出宝贵的jvm内存空间，避免把内存撑爆！！！
 */
public class 三代小缓存<A,V> implements LongCompute<A,V> {
    private final Map<A,Future> cache = new ConcurrentHashMap<A, Future>();
    private final LongCompute<A,V> c;

    public 三代小缓存(LongCompute<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        while(true) {
            //缓存中保存futureTask对象
            Future future = cache.get(arg);
            /**
             * 多线程中先检查再运算，总会出现检查时的漏洞
             */
            if (future == null) {
                FutureTask<V> vf = new FutureTask<V>(new Callable<V>() {
                    public V call() throws Exception {
                        V v = c.compute(arg);
                        return v;
                    }
                });
                //假如同值计算的多条线程都走到了这一步
                //putIfAbsent 返回值为null说明是首次插入，则需要启动FutureTask,否则说明已经有其他的线程创建了task，这里创建的task就不再启动了
                future = cache.putIfAbsent(arg, vf);
                if (future == null) {
                    future = vf;
                    //同一个task对象只会执行一次run
                    vf.run();
                }
            }
            try {
                //产生阻塞代表任务正在执行
                V o = (V) future.get();
                return o;
            } catch (ExecutionException e) {
                //任务执行异常，将任务移除，再重新循环计算。解决缓存污染问题
                cache.remove(arg,future);
            }
        }
    }
}
