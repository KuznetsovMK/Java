package codewars.kyu6;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CountingDuplicates {
    public static void main(String[] args) {
        duplicateCount("AaBbC");
    }

    public static int duplicateCount(String text) {
        System.out.println("***");
        System.out.println("Оригинальная строка: " + text);
        char[] chars = text.toLowerCase().toCharArray();
        System.out.println("Массив символов в нижнем регистре: " + Arrays.toString(chars));
        Map<Character, Integer> map = new HashMap<>();
        int cout = 0;
        for (char chr : chars) {
            map.put(chr, map.containsKey(chr)? map.get(chr) + 1 : 1);
        }
        System.out.println("{Символ=количество повторов}: " + map);
        for (Map.Entry<Character, Integer> o : map.entrySet()) {
            if (o.getValue() > 1) {
                cout++;
            }
        }
        System.out.println("Количество одинаковых символов (> 1): " + cout);
        return cout;
    }
}
