package algorithms.demo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 最近最少使用，在一块缓存中，当缓存满的时候，先删除最近不常访问的数据
 * LinkedHashMap的get方法会将查询对象放置到head.before的位置，这样map的尾部就是不常访问的数据
 * 重写removeEldestEntry：
 * LinkedHashMap在addEntry的时候会进行删除：
 * void addEntry(int hash, K key, V value, int bucketIndex) {
 *         super.addEntry(hash, key, value, bucketIndex);
 *         // Remove eldest entry if instructed
 *         Entry<K,V> eldest = header.after;
 *         if (removeEldestEntry(eldest)) {
 *             removeEntryForKey(eldest.key);
 *         }
 *     }
 * 但是removeEldestEntry方法是始终返回false的，这就需要重写这个方法
 */
public class LRU extends LinkedHashMap {
    protected int maxSize;
    public LRU(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor,true);
        maxSize = initialCapacity;
    }

    public LRU() {
        super();
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        //当map的动态扩容后添加的元素大于初始大小的时候，开始删除
        return size() > maxSize;
    }

    public static void main(String[] args) {
        LRU lru = new LRU(4, 0.75F);
        lru.put(1,'a');
        lru.put(2,'b');
        lru.put(3,'c');
        lru.put(4,'d');
        lru.get(1);
        lru.put(5,'e');
        System.out.println(lru);

        LRU lru1 = new LRU();
        lru1.put(1,'a');
        lru1.put(2,'b');
        lru1.put(3,'c');
        lru1.put(4,'d');
        lru1.get(1);
        lru1.put(5,'e');
        System.out.println(lru1);

        HashMap<Integer, Character> map = new LinkedHashMap<Integer,Character>(5,0.75F,false);
        map.put(1,'a');
        map.put(2,'b');
        map.put(3,'c');
        map.put(4,'d');
        System.out.println(map);
        map.put(5,'e');
        map.put(null,'f');
        map.put(null,'g');
        System.out.println(map);
        System.out.println(1 << 4);
        new ConcurrentHashMap<>();
        System.out.println(1 >>> 3);
    }
}
