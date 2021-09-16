package codewars.kyu5;

public class MaximumSubarraySum {
    public static void main(String[] args) {
        sequence(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
    }
    public static int sequence(int[] arr) {
//        int maxSum = 0;
//        for (int i = 0; i < arr.length; i++) {
//            int sum = 0;
//            for (int j = i; j < arr.length; j++) {
//                sum = sum + arr[j];
//                if (sum > maxSum) maxSum = sum;
//            }
//        }
//        return maxSum;

        int sum = 0, maxSum = 0;
        for (int digit : arr) {
            sum = Math.max(0, sum + digit);
            maxSum = Math.max(sum, maxSum);
            System.out.println("digit " + digit + " sum " + sum + " maxSum " + maxSum);
        }
        return maxSum;
    }
}
