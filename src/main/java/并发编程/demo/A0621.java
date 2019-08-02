package 并发编程.demo;

public class A0621 {
    public static void main(String[] args) {
        System.out.println(status.open.id);
        A  a = new A(){
            public void print(){
                System.out.println("i am fool");
            }
        };
        Thread t = new Thread(){
            public void run(){
                System.out.println("i am hehe");
            }
        };
        t.start();
    }
    public static enum status {
        open(1),
        close(2);

        public Integer id;

        private status(Integer id) {
            this.id = id;
        }
    }
    static class A{
        int a;

        public A(int a) {
            this.a = a;
        }

        public A() {
            print();
        }

        private void print() {
        }
    }
}


