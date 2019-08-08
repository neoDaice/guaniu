package 并发编程.中断;

public class Interrupted {

    //不断睡眠的线程（阻塞）
    static  class SleepRunner implements  Runnable{

        public void run() {
            while(true){
                try {
                    Thread.sleep(10000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    // 不停忙碌的线程（非阻塞）
    static  class BusyRunner implements  Runnable{

        public void run() {
            while (true){

            }
        }
    }

    public static void main(String[] args) throws Exception{
        Thread sleepThread=new Thread(new SleepRunner());
        Thread busyThread=new Thread(new BusyRunner());

        sleepThread.start();
        busyThread.start();

        Thread.sleep(1000);

        //中断睡眠线程
        sleepThread.interrupt();

        //中断忙碌线程
        busyThread.interrupt();
        System.out.println("SleepThread interrupted is "+sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is "+busyThread.isInterrupted());
        /**
         *从结果上来看busyThread并没有中断,而标志位变为了true。
         * sleepThread中断了，这个因为在源码中处在waiting状态的线程会结束掉线程
         *        synchronized (blockerLock) {
         *             Interruptible b = blocker;
         *             if (b != null) {
         *                 interrupt0();           // Just to set the interrupt flag
         *                 b.interrupt(this);
         *                 return;
         *             }
         *         }
         *         interrupt0();      //do not interrupt the thread
         * sleepThread的中断位变化了，是因为sleep方法在抛出Interrupted之前会清除中断状态。
         */

    }
}

