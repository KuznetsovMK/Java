package codewars.kyu5;

/**
 * Complete the function scramble(str1, str2) that returns true if a portion of str1 characters can be rearranged to
 * match str2, otherwise returns false.
 *
 * Notes:
 *
 *     Only lower case letters will be used (a-z). No punctuation or digits will be included.
 *     Performance needs to be considered
 *
 * Input strings s1 and s2 are null terminated.
 *
 * Examples
 *
 * scramble('rkqodlw', 'world') ==> True
 * scramble('cedewaraaossoqqyt', 'codewars') ==> True
 * scramble('katas', 'steak') ==> False
 *
 */

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
