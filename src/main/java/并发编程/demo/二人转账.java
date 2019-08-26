package 并发编程.demo;

public class 二人转账 {
    //A给B转帐的同时，B也给A转账，这就导致了锁顺序死锁
    public void transferMoney(Count from,Count to,Integer number) throws IllegalAccessException {
        //这里是模拟死锁的产生时机，使线程同时持有两个锁后才能执行
        //假如只加一把锁，比如只获取from的锁，当a -- > b的同时 b --> c，这时两次的转账是可以同时进行的。栈封闭的，貌似也没啥问题🙂
        synchronized(from){
            synchronized(to){
                if(from.money - number < 0){
                    throw new IllegalAccessException();
                }
                from.money = from.money - number;
                to.money = to.money + number;
            }
        }

    }
    class Count{
        String name;
        Integer money;
    }
    private static final Object tieLock = new Object();
    //解决锁顺序死锁的问题，为锁设定先后顺序
    public void transferMoneyV2(final Count from,final Count to,final Integer number) throws IllegalAccessException {
        class Helper{
            public void transferMoney() throws IllegalAccessException {
                if(from.money - number < 0){
                    throw new IllegalAccessException();
                }
                from.money = from.money - number;
                to.money = to.money + number;
            }
        }
        //拿到两个对象的散列值，通过散列值来定义获取锁的顺序
        //项目中更可以拿数据库的id字段来做比较，这样就不会产生加时的情况
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);
        if(fromHash > toHash){
            synchronized (from){
                synchronized (to){
                    new Helper().transferMoney();
                }
            }
        }else if(fromHash < toHash){
            synchronized (to){
                synchronized (from){
                    new Helper().transferMoney();
                }
            }
        }else{
            //产生hash冲突，来弄一个加时赛,看谁先拿到tieLock
            synchronized (tieLock){
                synchronized (from){
                    synchronized (to){
                        new Helper().transferMoney();
                    }
                }
            }
        }
    }
}
