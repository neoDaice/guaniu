package algorithms.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ArrayTest {
    public static void main(String[] args) {
       /*int[] nums = {0,0,1,1,1,2,2,3,3,4};
        removeDuplicates(nums);
        System.out.println('-----------------------');
        buyShares();
        int[] nums1 = {4,1,2,1,2};
        singleNumber(nums1);*/
       /*int[] nums={17,12,5,-6,12,4,17,-5,2,-3,2,4,5,16,-3,-4,15,15,-4,-5,-6};
        int i = singleNumber(nums);*/
        //insertselect();
        // moveZeros();
        //twoSum();
        boolean validSudoku = isValidSudoku();
        System.out.println(validSudoku);

    }
    //原地去重算法

    /**跳出思维定式：去重就要比较，就要把重复的元素的位置移开或者用其他元素来覆盖，在第一次循序数组的时候
     * 数组（该数组为已排序数组）
     * 一般在程序中去重是通过set来实现，原地算法要求不开辟额外的空间，在原有数组中操作
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int index = 0;
        for(int i=1;i<nums.length;i++){
            //index 代表着偏移，它是要将所有不同的元素都覆盖过来，数组只循环一遍。只能作用于已排好序的数组
            if(nums[index] != nums[i]){
                index++;
                nums[index] = nums[i];
            }
        }
        //此时数组的前index+1 位即已去重
        return index+1;
    }

    /**
     * 贪心算法
     * @param nums
     * @return
     */
    /**
     *当你在规划一件事，面对多个情况，需要作出最优方案时，可以从局部出发，制定局部最优解方案，将这些局部最优解方案累加起来，得到最终的方法
     * 有时贪心算法得到的不一定是最优解，但也是最优解的近似值，仍然具有参考意义
     */
    public static int buyShares(){
        //股票交易
        int[] prices = new int[]{6,1,3,2,4,7};
        /**
         * 局部最优方案
         * 1.在进行交易时，如果一段时间内，价格一直是递增的，那么取首尾之差作为一次交易。将所有的这些交易累加起来，得到最优解
         */
        if(prices == null)
            return 0;
        int sum = 0;
        for(int i =0;i< prices.length - 1;i++){
            if(prices[i] < prices[i+1])
                sum += prices[i+1] - prices[i];
        }
        return sum;
    }
    /**
     * 旋转数组（要求原地算法）
     * 智商各种被摩擦。。。
     * 1.整体翻转数组
     * 2.以 k 为分界点，这两个数组分别对调
     * 哪个机灵鬼能想到这个方法 0_o!!
     */
    public void rotate(int[] nums, int k) {

        int l = nums.length;
        k = k % l;
        nums = resverse(nums,0,l-1);
        nums = resverse(nums,0,k-1);
        nums = resverse(nums,k,l-1);
    }
    public int[] resverse(int[] nums,int start,int end){
        //翻转
        for(int i=start;i<=end;i++){
            if(start < end){
                int temp = nums[end];
                nums[end] = nums[start];
                nums[start] = temp;
                start++;
                end--;
            }
        }
        return nums;
    }
    /**
     * 找出那一个
     * 输入: [4,1,2,1,2]
     * 输出: 4
     */
    public static int singleNumber(int[] nums) {
        Arrays.sort(nums);
        for(int i = 0;i<nums.length-1;i=i+2){
            if(nums[i] != nums[i+1]){
                return nums[i];
            }
        }
        return nums[nums.length-1];
    }
    /**
     * 计算交集
     *  输出结果中每个元素出现的次数，应与元素在两个数组中出现的次数一致。
     */
    public static void insertselect(){
        int[] nums2 = {1,2,2,1}, nums1 = {2,2};
        //涉及到计数与值，一般来说会使用map
        //计数器在程序中有着很重要的控制作用，什么时候增加，什么时候减少，以此来控制结果
        //在python中，有更加简便的操作来获取交集，并集
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<nums2.length;i++){
            map.put(nums2[i],map.getOrDefault(nums2[i],0)+1);
        }
        ArrayList<Integer> result = new ArrayList<>();
        for(int i=0;i<nums1.length;i++){
            if(map.containsKey(nums1[i])&&map.get(nums1[i])>0){
                map.replace(nums1[i],map.get(nums1[i])-1);
                result.add(nums1[i]);
            }
        }
        int[] ints = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            ints[i] = result.get(i);

        }
        System.out.println(result);

    }
    /**
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序
     * 要求原地操作
     */
    public static void moveZeros(){
        int[] nums = {12,0,3,5,0,1};
        int count = 0;
        //史上最慢算法在下面
        /*for(int i = 0;i<nums.length;i++){
            if(nums[i] == 0){
                count++;
            }
        }
        while(count > 0) {
            for (int i = 0; i < nums.length - 1; i++) {
                if (nums[i] == 0) {
                    nums[i] = nums[i + 1];
                    nums[i + 1] = 0;
                }

            }
            count--;
        }*/
        /**
         * 注意 j++ 操作，现有j 再去加
         顺序移位算法：i指针向前移动，j指针来负责更新数据！！
         */

        int j=0;
        for(int i=0;i<nums.length;i++)
            if(nums[i]!=0)
                nums[j++]=nums[i];
        for(;j<nums.length;j++)
            nums[j]=0;

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            System.out.println(num);
        }
    }
    //两数之和
    public static void twoSum() {
        int[] nums = {2, 7, 11, 15}; int target = 9;
        int[] result = new int[2];
        for(int i=0;i<nums.length;i++){
            int a = nums[i];
            for(int j=i+1;j<nums.length;j++){
                if(a+nums[j] == target){
                    result[0] = i;
                    result[1] = j;
                }
            }
        }

    }
    /** @有效的数独
     *
     * 判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。
     *
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
     */
    public static boolean isValidSudoku() {
        char[][] board = {
                {'8', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'.', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        //二維數組使用x,y坐標
        for(int x=0;x<9;x++){

            HashMap<Character, Integer> map1 = new HashMap<>();
            HashMap<Character, Integer> map2 = new HashMap<>();
            for(int y=0;y<9;y++){
                //列
                if(board[x][y] != '.'){
                    map1.put(board[x][y],map1.getOrDefault(board[x][y],0)+1);
                }
                //行
                if(board[y][x] != '.'){
                    map2.put(board[y][x],map2.getOrDefault(board[y][x],0)+1);
                }
            }
            for (Character character : map1.keySet()) {
                if(map1.get(character)>1)
                    return false;
            }
            for (Character character : map2.keySet()) {
                if(map2.get(character)>1)
                    return false;
            }

        }
        //九宫格 做3循环
        for(int i=0;i<3;i++){

            for(int j=0;j<3;j++){
                HashMap<Character, Integer> map3 = new HashMap<>();
                for(int k=j*3;k<j*3+3;k++){
                    char a1 = board[i*3][k];
                    char a2 = board[i*3+1][k];
                    char a3 = board[i*3+2][k];
                    map3.put(a1,map3.getOrDefault(a1,0)+1);
                    map3.put(a2,map3.getOrDefault(a2,0)+1);
                    map3.put(a3,map3.getOrDefault(a3,0)+1);
                }
                for (Character character : map3.keySet()) {
                    if(character != '.' && map3.get(character)>1){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //旋转图像
    /**
     * 给定一个 n × n 的二维矩阵表示一个图像。
     * 将图像顺时针旋转 90 度。
     * 原地算法实现
     * 给定 matrix =
     * [
     *   [1,2,3],
     *   [4,5,6],
     *   [7,8,9]
     * ],
     *
     * 原地旋转输入矩阵，使其变为:
     * [
     *   [7,4,1],
     *   [8,5,2],
     *   [9,6,3]
     * ]
     */
    public static void rotate() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        //怎样原地实现呢？


    }
}
