package 并发编程.demo;

import java.util.Random;

public class DemoOfDeadLock {
    private static final int NUM_THREADS = 20;
    private static final int NUM_ACCOUNTS = 5;
    private static final int NUM_ITERATIONS = 1000000;

    public static void main(String[] args) {
        final Random random = new Random();
        final Account[] accounts = new Account[NUM_ACCOUNTS];
        for(int i = 0;i< accounts.length;i++){
            accounts[i] = new Account();
        }
        class TransferThread extends Thread{
            public void run(){
                for(int i=0;i<NUM_ITERATIONS;i++){
                    int from = random.nextInt(NUM_ACCOUNTS);
                    int to = random.nextInt(NUM_ACCOUNTS);
                    int money = random.nextInt(1000);
                    transferMoney(accounts[from],accounts[to],money);
                }
            }
            public void transferMoney(Account from, Account to, Integer number) {
                synchronized(from){
                    synchronized(to){
                        from.money = from.money - number;
                        to.money = to.money + number;
                    }
                }

            }
        }
        for(int i = 0;i<NUM_THREADS;i++){
            new TransferThread().start();
        }
    }
    static class Account{
        String name;
        Integer money = 2000;
    }
}
