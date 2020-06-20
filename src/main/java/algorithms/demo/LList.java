package algorithms.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;

public class LList {

    public static void main(String[] args) {
        //使用linkedlist将可用元素都放于队首
        String[] placementCodes = {"LM-1065","LM-1066","LM-1067","LM-1068","LM-1069","LM-1070"};
        LinkedList<String> list = new LinkedList<String>(Arrays.asList(placementCodes));
        for(int i=0;i<4;i++) {
            System.out.println(list.get(0));
            String remove = list.remove(0);
            list.addLast(remove);
        }
        String data = "[\n" +
                "    {\n" +
                "        \"ordersId\": 69625878,\n" +
                "        \"errorCode\": [\n" +
                "            50003\n" +
                "        ],\n" +
                "        \"queryIndex\": 0,\n" +
                "        \"errorMsg\": [\n" +
                "            \"订单状态必须为下单,物品信息必填\"\n" +
                "        ]\n" +
                "    }\n" +
                "]";
        JSONObject json = JSON.parseArray(data).getJSONObject(0);
        JSONArray errorCode = json.getJSONArray("errorCode");
        System.out.println(errorCode.contains(50003));
        System.out.println(json.getJSONArray("errorMsg"));
    }
}
