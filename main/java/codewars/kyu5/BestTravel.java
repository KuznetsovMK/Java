package codewars.kyu5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BestTravel {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 10, 5, 9, 6, 13, 1));
        System.out.println(chooseBestSum(14, 2, list));

    }

    public static Integer chooseBestSum(int t, int k, List<Integer> ls) {
        if (ls.size() < k) return null;
        Collections.sort(ls);
        List<Integer> temp = new ArrayList<>(ls);
        temp = ls.subList(0, k);
        ls = ls.subList(k, ls.size());
        System.out.println(ls);
        System.out.println(temp);
        for (int j = 0; j < k; j++) {
            int max = -1;
            for (int i = 0; i < ls.size(); i++) {
//                System.out.println("1 " + temp.subList(0, j));
                if ((saumArray(temp) - temp.get(j) + ls.get(i) <= t) && (saumArray(temp) <= saumArray(temp) - temp.get(j) + ls.get(i))) {
                    max = Math.max(ls.get(i), max);
                    ls.set(i, max == ls.get(i) ? temp.get(j) : ls.get(i));
                    temp.set(j, max);
                }
            }
        }
        System.out.println(ls);
        System.out.println(temp);

        return saumArray(temp);
    }

    public static int saumArray(List<Integer> list) {
        int sum = 0;
        for (int a : list) {
            sum += a;
        }
        return sum;
    }
}
