package codewars.kyu5;


import java.util.ArrayList;
import java.util.List;

/**
 * 1, 246, 2, 123, 3, 82, 6, 41 are the divisors of number 246. Squaring these divisors we get:
 * 1, 60516, 4, 15129, 9, 6724, 36, 1681. The sum of these squares is 84100 which is 290 * 290.
 * <p>
 * Task
 * <p>
 * Find all integers between m and n (m and n integers with 1 <= m <= n)
 * such that the sum of their squared divisors is itself a square.
 * <p>
 * We will return an array of subarrays or of tuples (in C an array of Pair) or a string.
 * The subarrays (or tuples or Pairs) will have two elements: first the number the squared divisors
 * of which is a square and then the sum of the squared divisors.
 */

public class IntegersRecreationOne {
    public static void main(String[] args) {
        System.out.println(listSquared(1, 250));
    }

    public static String listSquared(long m, long n) {
        List<List<Long>> list2 = new ArrayList<>();
        for (long i = m; i < n; i++) {
            List<Long> list = new ArrayList<>();
            long sum = 0;
            for (long j = 1; j < i; j++) {
                if (i % j == 0 && j < (i / j)) {
                    long res = i / j;
                    long d1 = (int) Math.pow(j, 2);
                    long d2 = (int) Math.pow(res, 2);
                    sum = sum + d1 + d2;
                }
            }
            if (i == 1) sum = 1;

            if (Math.sqrt(sum) % 1 == 0) {
                list.add(i);
                list.add(sum);
                list2.add(list);
            }
        }
        return list2.toString();
    }
}
