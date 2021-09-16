package codewars.kyu6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayDiff {

    public static void main(String[] args) {
        arrayDiff(new int[]{1, 2}, new int[]{1});

    }

    public static int[] arrayDiff(int[] a, int[] b) {
//        for (int digit : b) {
//            for (int i = 0; i < a.length; i++) {
//                if (digit == a[i]) a[i] -= digit;
//            }
//        }
//        int[] c = new int[0];
//        for (int digit : a) {
//            if (digit != 0) {
//                int[] temp = c;
//                c = new int[c.length + 1];
//                for (int i = 0; i < c.length - 1; i++) {
//                    c[i] = temp[i];
//                }
//                c[c.length - 1] = digit;
//            }
//        }
        Map<Integer, Boolean> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        boolean flag;
        for (int j : a) {
            if (!map.containsKey(j)) {
                flag = false;
                for (int digit : b) {
                    if (j == digit) {
                        flag = true;
                        break;
                    }
                }
                map.put(j, flag);
            }
        }
        for (int digit : a) {
            if (!map.get(digit))
                list.add(digit);
        }
        int[] c = new int[list.size()];
        for (int i = 0; i < c.length; i++) {
            c[i] = list.get(i);
        }
        return c;
    }
}
