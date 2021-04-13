package GB.lvl2lesson3;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList(
                "Cat", "Dog", "Duck",
                "Bee", "Duck", "Cat",
                "Duck", "Bee", "Dog",
                "Dog", "Cat", "Duck",
                "Cat", "Duck", "Bee"
        ));

        Map<String, Integer> hashMap = new HashMap<>();

        for (String word : list) {
            hashMap.put(word, 0);
        }

        System.out.print("Array of unique words: ");
        for (Map.Entry<String, Integer> o : hashMap.entrySet()) {
            System.out.print(o.getKey() + ". ");
        }
        System.out.println();

        for (Map.Entry<String, Integer> o : hashMap.entrySet()) {
            int count = 0;

            for (String word : list) {
                if (o.getKey().equals(word)) {
                    o.setValue(++count);
                }
            }
        }

        System.out.println(hashMap);

        System.out.println();

        PhoneBook pb = new PhoneBook();

        List<String> name = new ArrayList<>(Arrays.asList(
                "Semenov", "Ivanova", "Sergeev",
                "Kojevatova", "Filimonov", "Sergeeva",
                "Goncharova", "Bezborodov", "Filimonova",
                "Goncharov"
        ));

        List<String> phone = new ArrayList<>(Arrays.asList(
                "7(495)840-16-56", "7(495)794-59-90",
                "7(495)907-28-51", "7(495)359-13-23",
                "7(495)732-38-82", "7(495)862-86-25",
                "7(495)238-09-01", "7(495)514-83-70",
                "7(495)133-02-67", "7(495)399-01-56"
        ));

        for (int i = 0; i < name.size(); i++) {
            pb.add(name.get(i), phone.get(i));
        }

        pb.add("Ivanov", "7(495)817-27-64");

        pb.get("Goncharov");

    }
}
