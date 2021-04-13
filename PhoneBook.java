package GB.lvl2lesson3;

import java.util.HashMap;
import java.util.Map;

public class PhoneBook {

    Map<String, String> hashMap = new HashMap<>();

    public void add(String name, String phone) {
        hashMap.put(phone, name);
    }

    public void get(String name) {
        int cnt = 0;
        System.out.println(name + " phone: ");

        for (Map.Entry<String, String> o : hashMap.entrySet()) {
            if (o.getValue().equals(name)) {
                System.out.println(o.getKey());
                cnt++;
            }
        }

        if (cnt == 0)
            System.out.println("Not found.");
    }

//    public void info() {
//        System.out.println(hashMap);
//    }
}
