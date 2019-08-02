package 并发编程.demo;

public class CocaCola {
    private int a;
    private static int b;
    //static 修饰类用于内部类

    public void ss(){

    }
    static class Haha{
        public void t(){
            //静态内部类只能使用外部类的静态资源
            System.out.println(b);
            //ss();
        }
    }
    class hehe{
        public void t(){
            //非静态内部类能调用外部类的所有资源
            System.out.println(a);
            ss();
        }
    }
}
class Tt{
    public static void main(String[] args) {
        //静态内部类的初始化方法
        CocaCola.Haha haha = new CocaCola.Haha();
        haha.t();
        //非静态内部类的初始化方法
        CocaCola cocaCola = new CocaCola();
        CocaCola.hehe hehe = cocaCola.new hehe();
        hehe.t();
    }
}
