package GB.lvl3lesson1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HMLesson1 {

    public static void main(String[] args) {

        Changer changer = new Changer();

        int i = 0;
        int j = 1;

        Integer[] intArr = {4, 5, 8, 11, 3};
        String[] strArr = {"Hello", "World", "!"};

        System.out.printf("\n%s and %s items swapped.\n\n", i, j);
        changer.<Integer>swapItems(intArr, i, j);
        changer.<String>swapItems(strArr, i, j);

        //Array to list
        System.out.println("\nArray to list");
        changer.arrToList(intArr);
        changer.arrToList(strArr);
    }
}

class Changer {
    public <T> void swapItems(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        System.out.println(Arrays.toString(arr));
    }

    public <T> void arrToList(T[] arr) {
        List<T> list = new ArrayList<>();

        for (T item : arr) {
            list.add(item);
        }
        System.out.println(list.toString());
    }
}

