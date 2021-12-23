package Task1;

import java.util.Arrays;

public class Task1 {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        System.out.println(loopArray(n, m));
    }

    public static String loopArray(int n, int m) {
        System.out.println(n + " " + m);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }

        int start = 0;
        int end;
        StringBuilder sb = new StringBuilder();
        while (true) {
            end = start + m - 1;
            sb.append(arr[start]);
            if (end >= n) {
                end = end - n;
                if (end == 0) {
                    return new String(sb);
                }
            }
            start = end;
        }
    }
}
