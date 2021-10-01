package codewars.kyu5;

public class PaginatingAHugeBook {

    public static void main(String[] args) {
        System.out.println(pageDigits(12));

    }

    public static long pageDigits(long pages) {
        long len = String.valueOf(pages).length();
        if (len == 1) return pages;
        long a = (long) Math.pow(10, len - 1);
        long b = pages - (a - 1);
        long res = b * len;
//        System.out.println(" len " + len + " a " + a + " b " + b + " res " + res);
        return res + pageDigits(pages - b);

    }
}
