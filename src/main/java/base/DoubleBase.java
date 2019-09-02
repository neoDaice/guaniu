package base;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DoubleBase {
    public static void main(String[] args) {
        String a = "[\n" +
                "\t{\n" +
                "\t\t\"qty\":1,\n" +
                "\t\t\"sales\":29.37\n" +
                "\t}\n" +
                "]";
        JSONArray array = JSON.parseArray(a);
        JSONObject jsonObject = array.getJSONObject(0);
        if(jsonObject == null){
            System.out.println("hahaha");
        }else{
            System.out.println(jsonObject.getInteger("qty"));
        }

    }
}
