package 并发编程.demo;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class 父子线程 {
    static class Parent extends Thread{
        @Override
        public void run(){
            try {
                System.out.println("父线程执行中");
                Son son = new Son();
                son.start();
                Thread.sleep(2000);
                System.out.println("父线程执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        void normal(){
            System.out.println("Parent 开始执行 normal 方法");
            new Thread(() -> {
                System.out.println("normal 中的线程方法启动");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("normal 中的线程方法执行完毕");
            }).start();
            System.out.println("Parent normal 方法执行完毕");
        }
    }
    static class Son extends Thread{
        @Override
        public void run(){
            try {
                System.out.println("子线程执行中");
                Thread.sleep(5000);
                System.out.println("子线程执行完毕");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws MalformedURLException {
        Parent parent = new Parent();
//        parent.run();
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    System.out.println("线程池中的线程开始工作啦");
//                    Thread.sleep(10000);
//                    System.out.println("线程池中的线程工作结束啦");
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
        parent.normal();
        URL url = new URL("DASFAS");
    }
}
