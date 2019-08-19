package base.other;

/**
 * 类的实例化顺序
 */
public class TheClassOrder extends ClassOrder{
    static StrTesty strTesty = new StrTesty();
    String dd;
    other o = new other();
    public TheClassOrder() {
        System.out.println("执行TheClass的无参构造");
    }

    public TheClassOrder(String dd) {
        super(); //默认是先去执行父类的构造方法
        System.out.println("执行TheClass的带参构造");
        this.dd = dd;
    }
    static{
        System.out.println("执行TheClass的静态代码块");
    }

    public static void main(String[] args) {
        new TheClassOrder("dd");
    }
}
