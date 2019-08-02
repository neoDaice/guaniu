package 并发编程.little_cache;

import java.math.BigInteger;

public class SsCompute implements LongCompute<String,BigInteger> {
    public BigInteger compute(String arg) throws InterruptedException {
        Thread.sleep(5000);
        return new BigInteger(arg);
    }
}
