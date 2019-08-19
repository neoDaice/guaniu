package base.other;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class other {
    List<String> names;

    public other() {
        System.out.println("执行other的无参构造");
    }

    public other(List<String> names) {
        this.names = names;
    }

    public List<String> getName() {
        if(names == null){
            names = new ArrayList<String>();
        }
        return names;
    }

    public static void main(String[] args) {
        other other = new other();
        List<String> names= other.getName();
        String name = "f";
        names.add(name);
        System.out.println(other.names);
        String s = JSON.toJSONString(other);
        System.out.println(s);
    }
}
