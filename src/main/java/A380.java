import org.apache.commons.lang.RandomStringUtils;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 1000米考试
 * 100人参加考试,赛道每次最多跑10个人，记录下出每个考生的成绩后开始下一批考生考试
 */
public class A380 {
    static ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<String, Long>();
    public static void main(String[] args) throws InterruptedException {
        int total = 100;
        int examNumber = 10;

        for(int i = 0;i<total/examNumber;i++){
            CyclicBarrier barrier = new CyclicBarrier(examNumber);
            CountDownLatch countDownLatch = new CountDownLatch(10);
            for (int j =0 ;j<examNumber;j++){
                /*ExamWatch watch = new ExamWatch(new Random().nextInt(10) * 1000, RandomStringUtils.randomAlphanumeric
                        (3),barrier);*/
                ExamWatch watch = new ExamWatch(new Random().nextInt(10) * 1000, RandomStringUtils.randomAlphanumeric
                        (3),countDownLatch);
                new Thread(watch).start();
            }
            countDownLatch.await();
            System.out.println("第"+(i+1)+"轮结束");
        }
        System.out.println(map);
    }


    static class  ExamWatch implements Runnable{
        private String name;
        private long time;
        private CyclicBarrier barrier;
        private CountDownLatch countDownLatch;
        public ExamWatch(long time,String name,CyclicBarrier barrier) {
            this.time = time;
            this.name = name;
            this.barrier = barrier;
        }

        public ExamWatch(long time,String name, CountDownLatch countDownLatch) {
            this.name = name;
            this.time = time;
            this.countDownLatch = countDownLatch;
        }

        public void run() {
            System.out.println(name+"正在跑步");
            try {
                Thread.sleep(time);
                System.out.println(name+"到达终点");
                //barrier.await();
                map.put(name,time);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
