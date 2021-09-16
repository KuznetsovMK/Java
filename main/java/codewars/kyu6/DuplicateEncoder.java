package codewars.kyu6;

import java.util.HashMap;
import java.util.Map;

public class DuplicateEncoder {
    public static void main(String[] args) {
        encode("(( @");
    }

    static String encode(String word) {
        char[] chars = word.toLowerCase().toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for (char chr : chars) {
            if (map.containsKey(chr)) {
                map.put(chr, map.get(chr) + 1);
            } else {
                map.put(chr, 1);
            }
        }
        word = "";
        for (char chr : chars) {
            if (map.get(chr) > 1) {
                word = word + ")";
            } else {
            word = word + "(";
            }
        }
        return word;
    }
}
