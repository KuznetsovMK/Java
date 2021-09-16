package codewars.kyu6;

public class WhoLikesItApp {
    public static void main(String[] args) {
        System.out.println(whoLikesIt("Peter"));
    }
    public static String whoLikesIt(String... names) {
        String like = "like this";
        String none = "no one likes this";

        String str = "";

        if (names.length == 0) {
            str = "no one likes this";
        }
        if (names.length == 1) {
            str = names[0] + " likes this";
        }
        if (names.length == 2) {
            str = names[0] + " and " + names[1] +  " like this";
        }
        if (names.length == 3) {
            str = names[0] +", "+ names[1] + " and " + names[2] +  " like this";
        }

        if (names.length > 3) {
            str = names[0] +", "+ names[1] + " and " + (names.length - 2) +  " others like this";
        }


        return str;
    }
}
