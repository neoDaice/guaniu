package little_cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个缓存包装了longCompute的计算，实际上缓存应该作为一个单纯的读写单元，不参与运算
 * jvm内的缓存单元应使用单例模式
 * 在此使用hashmap作为存储容器，由于hashmap线程不安全，进行了synchronized，很显然这是一次副作用的缓存操作。
 * @param <A>
 * @param <V>
 */
public class 初代小缓存<A,V> implements LongCompute<A,V> {
    private final Map<A,V> cache = new HashMap<A, V>();
    private final LongCompute c;

    public 初代小缓存(LongCompute c) {
        this.c = c;
    }

    public synchronized V compute(A arg) throws InterruptedException {
        V v = cache.get(arg);
        if(v == null){
            v = (V) c.compute(arg);
            cache.put(arg,v);
        }
        return v;
    }
}
