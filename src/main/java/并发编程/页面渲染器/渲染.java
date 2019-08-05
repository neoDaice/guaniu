package 并发编程.页面渲染器;

import java.util.ArrayList;

public abstract class 渲染 {
    abstract void renderPage(CharSequence source);
    protected void renderImg(Object imageDatum) {
        System.out.println("加载载图片到HTML对应的位置中");
    }

    protected Object dowmload(Object img){
        System.out.println("下载图片中。。。");
        return new Object();
    }

    protected ArrayList<Object> scanForImageInfo(CharSequence source) {
        System.out.println("查找HTML中的图片地址");
        return new ArrayList<>();
    }
    protected void renderText(CharSequence source) {
        System.out.println("解析HTML文本内容并渲染");
    }
}
