package gb;

/**
 * Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
 * Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
 * идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, иначе в методе
 * необходимо выбросить RuntimeException. Написать набор тестов для этого метода (по 3-4 варианта входных данных).
 * Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
 */

public class Task2 {

    public static void main(String[] args) {
        Task2 task2 = new Task2();
        int[]arr = task2.lastFour(new int[]{1, 2, 4, 2, 3, 1, 7});

    }

    public int[] lastFour(int[] array) {
        for (int i = array.length - 1; i >= 0 ; i--) {
            if (array[i] == 4) {
                int[] newArray = new int[array.length - (i + 1)];
                if (newArray.length >= 0) System.arraycopy(array, (i + 1), newArray, 0, newArray.length);
                return newArray;
            }
        }
        throw new RuntimeException();
    }
}

