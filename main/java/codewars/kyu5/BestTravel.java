package codewars.kyu5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BestTravel {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(50));
        System.out.println(chooseBestSum(163, 3, list));

    }

    public static Integer chooseBestSum(int t, int k, List<Integer> ls) {
        Integer returnMax;
        Integer bestSum = -1;
        for (int i = 0; i < ls.size(); i++) {
            if (k == 1) {
                bestSum = Math.max(bestSum, ls.get(i)) <= t ? Math.max(bestSum, ls.get(i)) : bestSum;
            } else {
                returnMax = chooseBestSum(t - ls.get(i), k - 1, ls.subList(i + 1, ls.size()));

                if (returnMax != null && ls.get(i) + returnMax <= t) bestSum = Math.max(bestSum, ls.get(i) + returnMax);
            }
        }
        if (bestSum < 0) return null;
        return bestSum;
    }
}
