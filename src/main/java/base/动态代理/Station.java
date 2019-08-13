package base.动态代理;

public class Station implements TicketService {

    @Override
    public void sell() {
        System.out.println("售票");
    }

    @Override
    public void inquire() {
        System.out.println("问票");
    }

    @Override
    public void withdraw() {
        System.out.println("退票");
    }
}
