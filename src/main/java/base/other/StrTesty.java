package base.other;

import javax.print.DocFlavor;

public class StrTesty {
    final char[] value = {'a','b','c'};

    public StrTesty() {
        System.out.println("执行strTesty的无参构造");
    }

    public static void main(String[] args) {
        StrTesty strTesty = new StrTesty();
        //为什么String类内部的value是final的，还可以重新赋值呢？
        //StrTesty strTesty只是一个普普通通的变量啊，没有用final修饰，它想指哪个就指哪个😥
    }
}
