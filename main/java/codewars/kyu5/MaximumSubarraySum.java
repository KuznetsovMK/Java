package codewars.kyu5;

import java.util.Arrays;

/**
 * The maximum sum subarray problem consists in finding
 * the maximum sum of a contiguous subsequence in an array or list of integers:
 *
 * Max.sequence(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
 * // should be 6: {4, -1, 2, 1}
 *
 * Easy case is when the list is made up of only positive numbers and
 * the maximum sum is the sum of the whole array. If the list is made
 * up of only negative numbers, return 0 instead.
 *
 * Empty list is considered to have zero greatest sum.
 * Note that the empty list or array is also a valid sublist/subarray.
 */

public class MaximumSubarraySum {
    public static void main(String[] args) {
        sequence(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
    }
    public static int sequence(int[] arr) {
        System.out.println("* * *");
        System.out.println("Оригинальный массив: " + Arrays.toString(arr));
        int sum = 0, maxSum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum = Math.max(0, sum + arr[i]);
            maxSum = Math.max(sum , maxSum);
            System.out.printf("Digit-%d: %d, sum: %d, maxSum: %d \n", i, arr[i], sum, maxSum);
        }
     return maxSum;
    }
}
