package codewars.kyu5;

/**
 * Move the first letter of each word to the end of it, then add "ay" to the end of the word.
 * Leave punctuation marks untouched.
 * Examples
 *
 * pigIt('Pig latin is cool'); // igPay atinlay siay oolcay
 * pigIt('Hello world !');     // elloHay orldway !
 */

public class SimplePigLatin {

    public static void main(String[] args) {
        System.out.println(pigIt("Hello world !"));
    }

    public static String pigIt(String str) {
        StringBuilder resStr = new StringBuilder();
        String[] strings = str.split(" ");
        if (str.equals("")) return "";
        for (String strng : strings) {
            if (strng.charAt(0) > 64) {
                for (int i = 1; i < strng.toCharArray().length; i++) {
                    resStr.append(strng.charAt(i));
                }
                resStr.append(strng.charAt(0)).append("ay ");
            } else resStr.append(strng);
        }
        return resStr.toString().trim();
    }
}
