package 并发编程.demo;

import java.util.concurrent.LinkedBlockingQueue;

public class 段坤 {
    public static void main(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.put("c");
            queue.put("l");
            queue.put("o");
            queue.put("s");
            queue.put("e");
            queue.put("r");
            for (int i = 0;i<6;i++){
                System.out.println(queue.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
