package susan;

/**
 * 有 n 个人围成一圈，顺序排号。从第一个人开始报数（从 1 报到 3），凡是报到 3 的人退出圈子，
 * 问最后留下的是原来第几号的那位。
 */
public class zhuanquan {
    public static void main(String[] args) {
        int len = 4;
        int a[] = new int[len];
        int c=0,i=0,num=0;
        while(true){
            if(a[i]==0){
                if(c == len-1){
                    break;
                }
                num++;
                if(num%3 ==0){
                    a[i] = 1;
                    c++;
                }
            }
            i++;
            if(i == len){
                i = 0;
            }
        }
        System.out.println("the last one is "+(i+1));
    }
}
