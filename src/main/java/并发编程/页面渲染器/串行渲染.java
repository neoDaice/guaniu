package 并发编程.页面渲染器;

import java.util.ArrayList;

/**
 * 先加载文本，图片处留出位置
 * 再加载图片
 */
public class 串行渲染 extends 渲染{
    void renderPage(CharSequence source){
        //先渲染文本
        renderText(source);
        ArrayList<Object> imageData = new ArrayList<>();
        //筛选出图片
        ArrayList<Object> objects = scanForImageInfo(source);
        for (Object object : objects) {
            //下载图片
            imageData.add(dowmload(object));
        }
        //将下载好的图片加载到页面中
        for (Object imageDatum : imageData) {
            renderImg(imageDatum);
        }
    }

}
