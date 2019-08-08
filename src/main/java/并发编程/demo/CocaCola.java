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
    String name;

    public Tt(String name) {
        this.name = name;
    }

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
class Re{
    final Integer a;
    final Tt t1;
    static Tt t2;

    Re(Integer a, Tt t1) {
        this.a = a;
        this.t1 = t1;
    }

    //看看final和static的区别
    public static void main(String[] args) {
        Re moon = new Re(1, new Tt("moon"));
        System.out.println(moon.t1.name+":"+moon.a);
        Re sun = new Re(2, new Tt("sun"));
        System.out.println(sun.t1.name+" "+sun.a);
        //这里可以看到在类中对于final成员变量，不能在对象中修改，但是仍可以new新的对象，并对其赋值。内存仍旧是在堆中以对象隔离开来的
        //static的变量的内存就不堆中了，在和类一致的方法区中。
    }
}
