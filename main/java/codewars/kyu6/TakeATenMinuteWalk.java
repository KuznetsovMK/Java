package codewars.kyu6;

import java.util.HashMap;
import java.util.Map;

public class TakeATenMinuteWalk {

    public static void main(String[] args) {

        System.out.println(isValid(new char[]{'n', 's', 'n', 's', 'n', 's', 'n', 's', 'n', 's'}));
    }


    public static boolean isValid(char[] walk) {
        Map<Character, Integer> map = new HashMap<>();

        map.put('n', 0);
        map.put('s', 0);
        map.put('e', 0);
        map.put('w', 0);

        for (char chr : walk) {
            map.put(chr, map.get(chr) + 1);
        }

        int res = 0;
        for (int val : map.values()) {
            res += val;
        }

        return (map.get('n') == map.get('s') && map.get('e') == map.get('w') && res == 10);
    }
}
