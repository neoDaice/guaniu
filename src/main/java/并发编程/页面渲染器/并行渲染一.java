package 并发编程.页面渲染器;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 创建两个任务执行
 * 渲染文本    CPU密集任务
 * 渲染图片    IO密集任务
 * 基于future，callable来下载图片，在图片都下载完成后，渲染图片
 */
public class 并行渲染一  extends 渲染{
    private final ExecutorService executor = Executors.newCachedThreadPool();
    @Override
    void renderPage(CharSequence source) {
        ArrayList<Object> im = scanForImageInfo(source);
        Callable<List<Object>> callable = new Callable<List<Object>>() {
            @Override
            public List<Object> call() throws Exception {
                ArrayList<Object> result = new ArrayList<>();
                for (Object o : im) {
                    result.add(dowmload(o));
                }
                return result;
            }
        };
        //使用submit提交callable任务，并获得future对象。这里是先开启了任务来下载图片
        Future<List<Object>> future = executor.submit(callable);
        renderText(source);
        try {
            List<Object> list = future.get();
            for (Object o : list) {
                renderImg(o);
            }
        } catch (InterruptedException e) {
            //中断当前线程
            Thread.currentThread().interrupt();
            //取消任务
            future.cancel(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
