package base.other;

public class ClassOrder {
    static String a = "a";
    final String b = "b";
    String c = "c";
    static StrTesty strTesty = new StrTesty();
    other o = new other();
    public ClassOrder(String c) {
        System.out.println("执行带参构造");
        this.c = c;
    }

    public ClassOrder(){
        System.out.println("执行无参构造");
    }
    static{
        System.out.println("执行静态代码块");
    }
}
