package codewars.kyu6;

public class PlayingWithDigits {
    public static void main(String[] args) {
        System.out.println(digPow(89, 1));
    }

    public static long digPow(int n, int p) {
        char[] a = ("" + n).toCharArray();
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum = (int) (sum + Math.pow(Integer.parseInt(String.valueOf(a[i])), p + i));
        }
        return sum % n == 0 ? sum / n : -1;
    }
}
