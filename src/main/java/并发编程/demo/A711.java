package 并发编程.demo;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Joey i`m not angry any more
 * FutureTask的demo
 * 提前加载产品信息
 */
public class A711 {
    //futuretask 既继承了runnable也继承了callable
    private final FutureTask future = new FutureTask(new Callable() {
        public Integer call() throws Exception {
            System.out.println("加载产品信息。。。");
            Thread.sleep(10000);
            return 1;
        }
    });
    private final Thread thread = new Thread(future);
    public void start(){future.run();}
    /*@Test
    public void get() throws ExecutionException, InterruptedException {
        start();
        Integer o = (Integer)future.get();
        System.out.println("产品信息加载完毕，进入下面的操作。。。");
    }*/

    public static void main(String[] args) {
        FutureTask<Integer> task1 = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("task1开始运行");
                Thread.sleep(5000);
                System.out.println("task1结束运行");
                return 886;
            }
        });
        FutureTask<Integer> task2 = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("task2开始运行");
                Thread.sleep(5000);
                System.out.println("task2结束运行");
                return 233;
            }
        });
        /*task1.run();
        task2.run();*/
       /* new Thread(task1).start();
        new Thread(task2).start();*/
      /* for(int i = 0;i<4;i++){
           *//**
            * futuretask run 方法只执行一次
            *//*
           task1.run();
           new  Thread(task1).start();
       }*/
        HashMap<Integer, String> map = new HashMap<>();
        String s = map.putIfAbsent(1, "hello");
        System.out.println(s);
        String s1 = map.putIfAbsent(1, "hehe");
        System.out.println(s1);
        System.out.println(map.get(1));
        String wow = map.put(2, "wow");
        System.out.println(wow);
        String lol = map.put(2, "lol");
        System.out.println(lol);
        System.out.println(map.get(2));
    }
}
