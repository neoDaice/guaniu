package base.other;

import java.io.IOException;
import java.util.Objects;

/**
 * 重写equals和hashcode的方法
 * 一般是为了避免以此对象作为key存储时，equal的key并不会更新而是add
 * object的hashcode取得的是地址值
 * 要保证hashcode和equls的一致性
 * 如果要使对象变得可克隆，要实现克隆接口，不然会抛出java.lang.CloneNotSupportedException！！！
 */
public class D implements Cloneable{
    int a;
    String b;
    E e;
    @Override
    public int hashCode() {
        return Objects.hash(a,b,e);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj==null||obj.getClass()!=D.class) return false;
        D d = (D)obj;
        return d.a == this.a && d.b.equals(this.b) && Objects.equals(d.e,this.e);
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        D d1 = new D();
        E e = new E();
        e.e = "纵贯线";
        d1.a = 1;
        d1.b = "a";
        d1.e = e;
        D d2 = new D();
        d2.a = 1;
        d2.b = "a";
        d2.e = e;
        System.out.println(d1.hashCode()+"  "+d2.hashCode());
        System.out.println(d1.equals(d2));
        D clone = (D)d1.clone();
        System.out.println(clone.a+","+clone.b+","+clone.e.e);
        d1.e.e="just the way you are";
        System.out.println(clone.e.e);  //浅克隆对于引用类型的字段，copy的是地址值，并没有new一个新的，这就导致改变原来的对象会影响到新对象
        //解决浅克隆可以重写一下clone方法。
    }
}
