package susan;

/**
 * 有 n 个人围成一圈，顺序排号。从第一个人开始报数（从 1 报到 3），凡是报到 3 的人退出圈子，
 * 问最后留下的是原来第几号的那位。
 * 有时候你会想到错误的路上去，静下心来，从头分析，就会找到正确的路
 */
public class zhuanquan {
    public static void main(String[] args) {
        int[] array = new int[2];
        int k=0,t=0,i=0;
        while(true){
            if(array[i]==0){
                if(k == array.length-1){
                    break;
                }
                t++;
                if(t%3 ==0){
                    array[i] = 1;
                    k++;
                }
            }
            i++;
            if(i == array.length){
                i = 0;
            }
        }
        System.out.println(i);
    }
}
