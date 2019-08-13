package base.动态代理;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test01 {
    public static void main(String[] args) {
        ElectricCar car = new ElectricCar();
        // 1.获取对应的ClassLoader
        ClassLoader classLoader = car.getClass().getClassLoader();

        // 2.获取ElectricCar 所实现的所有接口
        Class[] interfaces = car.getClass().getInterfaces();
        // 3.设置一个来自代理传过来的方法调用请求处理器，处理所有的代理对象上的方法调用
        InvocationHandler handler = new InvocationHandlerImpl(car);
        /*
          4.根据上面提供的信息，创建代理对象 在这个过程中，
                         a.JDK会通过根据传入的参数信息动态地在内存中创建和.class 文件等同的字节码
                 b.然后根据相应的字节码转换成对应的class，
                         c.然后调用newInstance()创建实例
         */
        //Object o = Proxy.newProxyInstance(classLoader, interfaces, handler);
        Object o = Proxy.newProxyInstance(ElectricCar.class.getClassLoader(), ElectricCar.class.getInterfaces(), (proxy, method, args1) -> {
            System.out.println("开始执行方法" + method.getName());
            method.invoke(car, null);
            System.out.println(method.getName() + "方法执行完毕");
            return null;
        });
        Vehicle vehicle = (Vehicle) o;
        vehicle.drive();
        Rechargable rechargeable = (Rechargable) o;
        rechargeable.recharge();
        //cglib中的增强器
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ElectricCar.class);
        //设置回调函数，methodInterceptor的功能类似于InvocationHandler的invoke
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("开始使用cglib调用方法"+method.getName());
                method.invoke(car,null);
                System.out.println(method.getName()+"方法调用完毕");
                return null;
            }
        });
        ElectricCar o1 = (ElectricCar)enhancer.create();
        o1.stop();
    }
}
