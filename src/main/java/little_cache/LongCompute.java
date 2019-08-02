package little_cache;
//模拟耗时计算的接口
public interface LongCompute<A,V> {
    V compute(A arg) throws InterruptedException;
}
