import java.util.Random;
import java.util.concurrent.CyclicBarrier;

//栅栏的使用
public class AoAo {
    public static void main(String[] args) {
        final CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
            public void run() {
                System.out.println("over 了");
            }
        });
        for(int i = 0;i<5;i++) {
            new Thread(i+"") {
                public void run() {
                    try {
                        Thread.sleep(new Random().nextInt(10)*1000);
                        System.out.println(Thread.currentThread().getName()+"抵达第一步");
                        barrier.await();
                        Thread.sleep(new Random().nextInt(10)*1000);
                        System.out.println(Thread.currentThread().getName()+"抵达第二步");
                        barrier.await();
                        System.out.println("各回各家");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
    }
}
