package codewars.kyu6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class EqualSidesOfAnArray {
    public static void main(String[] args) {
        findEvenIndex(new int[]{1, 2, 3, 4, 3, 2, 1});
    }

    public static int findEvenIndex(int[] arr) {
        System.out.println("***");
        System.out.println("Оригинальный массив: " + Arrays.toString(arr));
        int sumLeft = 0;
        int sumRight;
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sumLeft += arr[i - 1];
            sumRight = 0;
            for (int j = i + 1; j < arr.length; j++) {
                sumRight += arr[j];
            }
            if (sumLeft == sumRight) {
                List<String> list = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    list.add(String.valueOf(arr[j]));
                }
                list.add("== ");
                for (int j = i + 1; j < arr.length; j++) {
                    list.add(String.valueOf(arr[j]));
                }
                System.out.println("Равенство левой и правой частей: " + list);
                System.out.println("Индекс элемента в котором выполняется равенство: " + i);
                return i;
            }
        }
        System.out.println("Равенство левой и правой частей не выполняется: " + index);
        return index;
    }
}
