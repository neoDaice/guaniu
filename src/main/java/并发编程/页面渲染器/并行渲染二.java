package 并发编程.页面渲染器;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 异构并发任务的问题：
 * 在一中，显然两个任务的执行时间是有很大差别的。如果渲染文本非常快，结果就是这种异构在结果上来看并没有提升太多的效率，任务
 * 的瓶颈还是卡在图片下载上。所以任务之间要协调一致
 * 使用completionService实现每个图片在下载完成后即可显示出来。
 * 将下载图片并行化
 */
public class 并行渲染二 extends 渲染{
    private final Executor executor = Executors.newCachedThreadPool();
    @Override
    void renderPage(CharSequence source) {
        //completionService 维护了一个blockingQueue 来保存future的executor
        ArrayList<Object> imageInfo = scanForImageInfo(source);
        ExecutorCompletionService<Object> completionService = new ExecutorCompletionService<Object>(executor);
        for (final Object o : imageInfo) {
            completionService.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    Object dowmload = dowmload(o);
                    return dowmload;
                }
            });
        }
        renderText(source);
        for(int i=0;i<imageInfo.size();i++){
            try {
                Future<Object> future = completionService.take();
                Object o = future.get();
                renderImg(o);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
