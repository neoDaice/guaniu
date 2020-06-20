package algorithms.leetcode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class 滑动窗口 {
    /**
     * 滑动窗口算法
     * 广泛应用于网络传输协议避免发生阻塞
     * 该算法可以使o(n2) 降为o(n)
     */
    public static void main(String[] args) {
        test1();
        //test2();
        String source = "abca";
        String target = "abc";
        List<Integer> integers = findAnagrams(source, target);
        System.out.println(integers);
    }
    public static void test1(){
        int[] a = {4,3,5,4,3,3,6,7};
        //双端队列，保证队列头部为最大值
        //保证队列长度，当i-head >= width,说明对首元素已经过期，应当移除
        //每次循环取队列头部
        //why so silly
        //窗口长度为3
        Deque<Integer> list = new LinkedList<>();
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0;i<a.length;i++){
            if(list.isEmpty()||a[list.getLast()]>a[i]){
                list.addLast(i);
            }else{
                while(!list.isEmpty()&& a[list.getLast()]<a[i]){
                    list.removeLast();
                }
                list.addLast(i);
            }
            //要清除过期的头，
            if(i - list.getFirst() >= 3 ){
                list.removeFirst();
            }
            //窗口长度为3，i=2 开始，弹出结果加入到result中
            if(i<2){
                continue;
            }
            result.add(a[list.getFirst()]);
        }
        System.out.println(result);
    }

    /**
     * leetcode
     * 数组{abcarckopvcfdbcaabucdbzzbcab} 目标数组{abc}
     * 实现移动匹配，不考虑顺序
     */
    public static void test2(){
        String source = "abcarckopvcfdbcaabucdbzzbcab";
        String target = "abc";
        //考虑到target可能有重复，建立map
        HashMap<Character, Integer> map = new HashMap<>();
        char[] chars = target.toCharArray();
        for (char aChar : chars) {
            if(map.containsKey(aChar)){
                map.put(aChar,map.get(aChar)+1);
            }else{
                map.put(aChar,1);
            }
        }
        HashMap<Character,Integer> zMap = new HashMap<>();
        ArrayList<Integer> result = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            zMap.put(entry.getKey(),entry.getValue());
        }
        //双指针滑动，右指针移动，匹配到字符，map 的value -1
        //当 a b c 的value 均小于等于0，该双指针中间字符比包含 a b c
        //判断连续性 当 end - start = 2 ，连续 --->弹出start
        //非连续，开始收缩start指针，每匹配一个+1，当 a b c 的值出现等于0说明后段值的个数满足要求
        //在进行长度判断，如果长度<2 舍弃该段，start end 合并，重新开始
        //参考下面算法，引入一个计数器，当key的值为0是，改变计数器！！！！！
        char[] sourceChars = source.toCharArray();
        int start = 0;
        int flag = 0;
        for(int i = 0;i<sourceChars.length;i++){
            if(map.containsKey(sourceChars[i])){
                map.put(sourceChars[i],map.get(sourceChars[i])-1);
            }
            Collection<Integer> values = map.values();

            for (Integer value : values) {
                if(value <= 0){
                    flag++;
                }
            }
            //这里有问题,当第一组数据出现，接下来
            if(flag == map.size()) {
                if (i - start == 2) {
                    result.add(start);
                    start = i -1 ;
                    //初始化map
                    map.clear();
                    map = zMap;
                } else {
                    //收缩start
                    while(start < i){
                        if(map.containsKey(sourceChars[start])){
                            map.put(sourceChars[start],map.get(sourceChars[start])+1);
                            if(map.get(sourceChars[start]) == 1){
                                if(i - start == 2){
                                    result.add(start);
                                    //start = i - 1;
                                    map.clear();
                                    map = zMap;
                                    break;
                                }else{
                                    start = i;
                                    map.clear();
                                    map = zMap;
                                    break;

                                }
                            }
                        }
                        start = start + 1;
                    }
                }
            }
        }
        System.out.println(result);
    }

    /**
     *
     * @param s
     * @param t
     * @return
     * 这玩意太绕了 全部匹配情况下，当key值>0时，计数器加 1，下一次循环
     * 通过计数器是否为0，来匹配
     * fuck
     */
    public static List<Integer> findAnagrams(String s, String t) {
        List<Integer> result = new LinkedList<>();
        if(t.length()> s.length()) return result;
        Map<Character, Integer> map = new HashMap<>();
        for(char c : t.toCharArray()){
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int counter = map.size();

        int begin = 0, end = 0;
        int head = 0;
        int len = Integer.MAX_VALUE;


        while(end < s.length()){
            char c = s.charAt(end);
            if( map.containsKey(c) ){
                map.put(c, map.get(c)-1);
                if(map.get(c) == 0)
                    counter--;
            }
            end++;

            while(counter == 0){
                char tempc = s.charAt(begin);
                //移动左边
                if(map.containsKey(tempc)){
                    map.put(tempc, map.get(tempc) + 1);
                    if(map.get(tempc) > 0){
                        //该数据已经全部使用完，必须跳出该循环
                        //counter++
                        counter++;
                    }
                }
                if(end-begin == t.length()){
                    result.add(begin);
                }
                begin++;
            }

        }
        return result;
    }
}
