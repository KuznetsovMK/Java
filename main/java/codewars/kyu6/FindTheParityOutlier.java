package codewars.kyu6;

import java.util.Arrays;

public class FindTheParityOutlier {
    public static void main(String[] args) {
        System.out.println("Найти единственное четное или нечетное число в массиве.");
        find(new int[]{1, 2, 4, 6});
    }

    static int find(int[] integers) {
        System.out.println("* * *");
        System.out.println("Оригинальный массив " + Arrays.toString(integers));
        boolean isEven = true, isOdd = true;
        int evenCnt = 0, oddCnt = 0;
        int even = 0, odd = 0;
        for (int i = 0; i < integers.length; i++) {
            if (isEven && integers[i] % 2 == 0) {
                even = integers[i];
                evenCnt++;
                if (evenCnt == 2) {
                    isEven = false;
                    System.out.printf("#%d Найдено второе четное число: %d. \n", i + 1, even);
                    continue;
                }
                System.out.printf("#%d Найдено четное число: %d. \n", i + 1, even);
            } else if (isOdd && integers[i] % 2 != 0) {
                odd = integers[i];
                oddCnt++;
                if (oddCnt == 2) {
                    isOdd = false;
                    System.out.printf("#%d Найдено второе нечетное число: %d. \n", i + 1, odd);
                    continue;
                }
                System.out.printf("#%d Найдено нечетное число: %d. \n", i + 1, odd);
            }
            if (i > 1 && (isEven ^ isOdd) && (oddCnt == 1 || evenCnt == 1)) {
                System.out.println("Ответ: " + (isEven ? even : odd));
                return isEven ? even : odd;
            }
        }
        return 0;
    }
}
