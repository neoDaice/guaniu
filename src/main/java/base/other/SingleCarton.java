package base.other;

public class SingleCarton {

    class S1{
        private S1  s = new S1();
        private S1 (){

        }
        public S1 newInstance(){
            return s;
        }
    }
    class S2{
        private S2 s;
        private S2(){

        }
        public S2  newInstance(){
            if(s == null){
                s = new S2();
            }
            return s;
        }
    }
    class S3{
        private S3 s;
        private S3(){

        }
        public S3 newInstance(){
            if(s == null) {  //这一层check避免每次都来获取锁
                synchronized (this) {
                    if (s == null) {
                        s = new S3();
                    }
                }
            }
            return s;
        }
    }
}
