package codewars.kyu5;

public class Scramblies {
    public static void main(String[] args) {
        System.out.println(scramble("aabbcamaomsccdd","commas"));
    }

    public static boolean scramble(String str1, String str2) {
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int counter = 0;

        for (int i = 0; i < chars2.length; i++) {
            for (int j = 0; j < chars1.length; j++) {
                if (chars1[j] != ' ') {
                    if (chars2[i] == chars1[j]) {
                        counter++;
                        chars1[j] = ' ';
                        break;
                    }
                }
            }
            if (counter == chars2.length) return true;
        }
        return false;
    }
}
