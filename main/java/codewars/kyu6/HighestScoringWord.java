package codewars.kyu6;

import java.util.*;

public class HighestScoringWord {

    public static void main(String[] args) {
        System.out.println(high("itax taxi"));
    }

    public static String high(String s) {
        List<String> list = new ArrayList<>(Arrays.asList(s.split(" ")));
        Map<Integer, Integer> map = new HashMap<>();
        int sum, cnt = 0, max = 0, index = 0;
        for (String str : list) {
            sum = 0;
            char[] chars = str.toCharArray();
            for (char chr : chars) {
                sum = sum + (chr - 96);
            }
            map.put(cnt, sum);
            cnt++;
        }


        for (Map.Entry<Integer, Integer> o : map.entrySet()) {
            if (max < o.getValue()) {
                max = o.getValue();
                index = o.getKey();
            }
        }

        return list.get(index);
    }
}
