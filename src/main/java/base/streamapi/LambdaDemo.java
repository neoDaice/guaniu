package base.streamapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

public class LambdaDemo {

    public static void main(String[] args) {
        NoReturnMultiParam noReturnMultiParam = (int a,int b) -> { //参数列表还可以省略类型，直接写成(a,b)
            System.out.println(a);
            System.out.println(b); };

        NoReturnNoParam noReturnNoParam = () -> {
            System.out.println("nothing");
        };
        ReturnMultiParam returnMultiParam = (a,b) ->{  //有返回值的
            System.out.println(a+","+b);
            return a+b;
        };
        //NoReturnOneParam hello = a -> System.out.println("hello");
        //NoReturnOneParam noReturnOneParam = a -> cc(a);  //还可以直接指向你写好的方法
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list,1,2,3,4,5,6);
        list.forEach(element -> {
            System.out.println(element);
        });
        list.forEach(System.out::println);
        //NoReturnOneParam cc = LambdaDemo::cc;
        Consumer<String> cc = LambdaDemo::cc;
        cc.accept("hello world"); //类似于 a -> cc(a)
        
    }

    public static void cc (String a){
        System.out.println(a);
    }

    /**多参数无返回*/
    @FunctionalInterface
    public interface NoReturnMultiParam {
        void method(int a, int b);
        default void methodA() {
        }
    }

    /**无参无返回值*/
    @FunctionalInterface
    public interface NoReturnNoParam {
        void method();
    }

//    /**一个参数无返回*/
//    @FunctionalInterface
//    public interface NoReturnOneParam {
//        void method(int a);
//    }

    /**多个参数有返回值*/
    @FunctionalInterface
    public interface ReturnMultiParam {
        int method(int a, int b);
    }

    /*** 无参有返回*/
    @FunctionalInterface
    public interface ReturnNoParam {
        int method();
    }

    /**一个参数有返回值*/
    @FunctionalInterface
    public interface ReturnOneParam {
        int method(int a);
    }
}
