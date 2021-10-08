package codewars.kyu5;

/**
 * The Fibonacci numbers are the numbers in the following integer sequence (Fn):
 *     0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, ...
 * such as  F(n) = F(n-1) + F(n-2) with F(0) = 0 and F(1) = 1.
 * Given a number, say prod (for product), we search two Fibonacci numbers F(n) and F(n+1) verifying
 *     F(n) * F(n+1) = prod.
 * Your function productFib takes an integer (prod) and returns an array:
 * [F(n), F(n+1), true] or {F(n), F(n+1), 1} or (F(n), F(n+1), True)
 * depending on the language if F(n) * F(n+1) = prod.
 * If you don't find two consecutive F(n) verifying F(n) * F(n+1) = prodyou will return
 * [F(n), F(n+1), false] or {F(n), F(n+1), 0} or (F(n), F(n+1), False)
 * F(n) being the smallest one such as F(n) * F(n+1) > prod.
 *
 * Some Examples of Return:
 * productFib(714) # should return {21, 34, 1},
 * productFib(800) # should return {34, 55, 0},
 */

import java.util.Arrays;

public class ProductOfConsecutiveFibNumbers {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(productFib(1)));
    }

    public static long[] productFib(long prod) {
        long temp, res = 1, x = 0, y = 1, isBoolean = 0;
        do {
            temp = res;
            res = x + y;
            x = y;
            y = res;
            isBoolean = res * temp == prod ? 1 : 0;
        } while (temp * res < prod);
        return new long[]{temp, res, isBoolean};
    }
}
