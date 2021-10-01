package codewars.kyu5;

/**
 * Write a function, which takes a non-negative integer (seconds)
 * as input and returns the time in a human-readable format (HH:MM:SS)
 * <p>
 * HH = hours, padded to 2 digits, range: 00 - 99
 * MM = minutes, padded to 2 digits, range: 00 - 59
 * SS = seconds, padded to 2 digits, range: 00 - 59
 * <p>
 * The maximum time never exceeds 359999 (99:59:59)
 * <p>
 * You can find some examples in the test fixtures.
 */

public class HumanReadableTime {
    public static void main(String[] args) {
        System.out.println(makeReadable(11635));
    }

    public static String makeReadable(int seconds) {
        int h = seconds / 3600;
        int m = seconds % 3600 / 60;
        int s = (seconds % 3600) % 60;
        int[] arr = new int[]{h, m, s};

        StringBuilder stringBuilder = new StringBuilder();
        String str;
        for (int i = 0; i < arr.length; i++) {
            str = "00" + arr[i];
            stringBuilder.append(str.substring(str.length() - 2));
            stringBuilder.append(":");
        }
        stringBuilder.setLength(8);
        return stringBuilder.toString();

    }
}
