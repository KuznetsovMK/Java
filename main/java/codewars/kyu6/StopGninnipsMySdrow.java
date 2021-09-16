package codewars.kyu6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StopGninnipsMySdrow {

    public static void main(String[] args) {
        System.out.println(spinWords("Hey fellow warriors"));
    }
    public static String spinWords(String sentence) {
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(sentence.split("\\s")));

        StringBuilder sb = new StringBuilder();

        for (String str : list) {
            if (str.length() >= 5) {
                char[] chars = new char[str.length()];
                int j = str.length() - 1;
                for (int i = 0; i < str.length(); i++) {
                    chars[i] = str.charAt(j);
                    j--;
                }
                str = new String(chars);
            }
            sb.append(str);
            sb.append(" ");
        }
        sb.setLength(sb.length() - 1);
        return new String(sb) ;
    }
}

