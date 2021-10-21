package gb.lvl3lesson1.task2;

import java.util.ArrayList;
import java.util.List;

class Task2 {

    public static void main(String[] args) {

        Integer[] intArr = {4, 5, 8, 11, 3};
        String[] strArr = {"Hello", "World", "!"};

        //Array to list
        System.out.println("\nArray to list");
        System.out.println(arrToList(intArr));
        System.out.println(arrToList(strArr));
    }

    public static <T> List<T> arrToList(T[] arr) {
        List<T> list = new ArrayList<>();

        for (T item : arr) {
            list.add(item);
        }
        return list;
    }
}

