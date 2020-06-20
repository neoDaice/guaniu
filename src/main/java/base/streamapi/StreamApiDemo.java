package base.streamapi;

import java.util.ArrayList;
import java.util.Comparator;

public class StreamApiDemo {
    public static void main(String[] args) {
        ArrayList<Item> list = new ArrayList<>();
        list.add(new Item(13, "背心", 7.80));
        list.add(new Item(11, "半袖", 37.80));
        list.add(new Item(14, "风衣", 139.80));
        list.add(new Item(12, "秋裤", 55.33));
        Comparator<Item> comparator = (t1, t2) -> t1.getId() - t2.getId();
        list.sort(comparator);
        list.forEach(System.out::println);
    }
}
