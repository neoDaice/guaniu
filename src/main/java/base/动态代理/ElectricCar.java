package base.动态代理;

public class ElectricCar implements Vehicle,Rechargable {
    @Override
    public void recharge() {
        System.out.println("start to charge");
    }

    @Override
    public void drive() {
        System.out.println("this car is driving");
    }

    public void stop(){
        System.out.println("this car is stopped");
    }
}
