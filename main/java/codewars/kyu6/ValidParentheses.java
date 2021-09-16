package codewars.kyu6;

public class ValidParentheses {
    public static void main(String[] args) {
        System.out.println(validParentheses("adasdasfa"));
    }

    public static boolean validParentheses(String parens) {
        int cnt = 0;
        char[] chars = parens.toCharArray();
        for (char chr : chars) {
            if (chr == '(') cnt++;
            if (chr == ')') cnt--;
            if (cnt < 0) return false;
        }
        return cnt == 0;
    }
}
