package 并发编程.little_cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 较之初代小缓存，使用了支持并发编程的concurrenthashmap
 * 问题： 有可能a，b线程进行了同一个值的计算，再将值put到map中
 * @param <A>
 * @param <V>
 */
public class 次代小缓存<A,V> implements LongCompute<A,V> {
    private final Map<A,V> cache = new ConcurrentHashMap<A,V>();
    private final LongCompute<A,V> c;

    public 次代小缓存(LongCompute<A, V> c) {
        this.c = c;
    }

    public V compute(A arg) throws InterruptedException {
        V v = cache.get(arg);
        if(v == null){
            v = (V) c.compute(arg);
            cache.put(arg,v);
        }
        return v;
    }
}
