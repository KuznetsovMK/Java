package codewars.kyu6;

public class BitCounting {

    public static void main(String[] args) {
        countBits(1234);
    }

    public static int countBits(int n){
        return Integer.bitCount(n);
    }

}
