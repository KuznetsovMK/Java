package codewars.kyu6;

public class YourOrderPlease {
    public static void main(String[] args) {
        System.out.println(order("is2 Thi1s T4est 3a"));
    }

    public static String order(String words) {
        String str = "";
        for (int i = 0; i < words.split(" ").length; i++) {
            for (String word : words.split(" ")) {
                for (char chr : word.toCharArray()) {
                    if (chr == String.valueOf(i + 1).charAt(0)) str = str + word + " ";
                }
            }
        }
        return str.trim();
    }
}