package codewars.kyu6;

public class PersistentBugger {
    public static void main(String[] args) {
        System.out.println(persistence(39));
    }

    public static int persistence(long n) {
        int cnt = 0;
        while (n > 9) {
            long sum = 1;
            char[] chars = ("" + n).toCharArray();
            for (char chr : chars) {
                sum = sum * Integer.parseInt(String.valueOf(chr));
            }
            n = sum;
            cnt++;
        }
        return cnt;
    }
}
