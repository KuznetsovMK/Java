package codewars.kyu5;

public class HumanReadableTime {
    public static void main(String[] args) {
        System.out.println(makeReadable(5));
    }

    public static String makeReadable(int seconds) {
        int h = seconds / 3600;
        int m = seconds % 3600 / 60;
        int s = (seconds % 3600) % 60;
        int[] arr = new int[]{h, m, s};
        return converter(arr);
    }

    public static String converter(int[] arr) {
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
