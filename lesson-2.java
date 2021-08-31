public class lesson2 {
    public static void main(String[] args) {

        doTask1();

        doTask2();

        doTask3();

        doTask4();

        System.out.println(doTask5());

        int[] array = {9,1,3,4,1};
        System.out.println(doTask6(array));


        int[] arrayMove = {3, 5, 6, 1};
        int n = 3;
        doTask7(arrayMove, n);

    }

    /**
     *
     * 1. Задать целочисленный массив, состоящий из элементов 0 и 1.
     * Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ].
     *С помощью цикла и условия заменить 0 на 1, 1 на 0;
     */

    static void doTask1() {
        int[] numbers = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == 1)
                numbers[i] = 0;
            else numbers[i] = 1;
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
    }

    /**
     *
     * 2. Задать пустой целочисленный массив размером 8.
     * С помощью цикла заполнить его значениями 0 3 6 9 12 15 18 21;
     */

    static void doTask2() {
        int n = 8;
        int[] numbers = new  int[n];
        for (int i = 0, j = 0; i < n; i++) {
            numbers[i] += j;
            j += 3;
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
    }

    /**
     *
     * 3. Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ]
     * пройти по нему циклом, и числа меньшие 6 умножить на 2;
     */

    static void doTask3() {
        int[] numbers = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < 6)
                numbers[i] *= 2;
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
    }

    /**
     *
     * 4. Создать квадратный двумерный целочисленный массив
     * (количество строк и столбцов одинаковое) и с помощью
     * цикла(-ов) заполнить его диагональные элементы единицами;
     */

    static void doTask4() {
        int n = 10;
        int[][] matrix = new int[n][n];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j || i + j == matrix[i].length - 1) {
                    matrix[i][j] = 1;
                }
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     *
     * 5.** Задать одномерный массив и найти в нем минимальный
     * и максимальный элементы (без помощи интернета);
     */

    static int doTask5 () {
        int[] numbers = {11, 5, 6, 4, 9, 3, 12, 2, 6, 1, -12 };
        int min = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < min)
                min = numbers[i];
        }
        return min;
    }

    /**
     *
     * 6.** Написать метод, в который передается не пустой одномерный целочисленный массив,
     * метод должен вернуть true, если в массиве есть место, в котором сумма левой и правой части массива равны.
     * Примеры: checkBalance([2, 2, 2, 1, 2, 2, || 10, 1]) → true, checkBalance([1, 1, 1, || 2, 1]) → true,
     * граница показана символами ||, эти символы в массив не входят.
     */

    static boolean doTask6(int[] array) {
        int sumL = 0;
        int sumR = 0;
        for (int i = 0; i < array.length - 1; i++) {
            sumL = sumL + array[i];
            sumR = 0;
            for (int j = array.length - 1; j > i; j--) {
                sumR = sumR + array[j];
            }
            if (sumL == sumR)
                break;
        }
        return sumL == sumR;
    }

    /**
     *
     * 7. **** Написать метод, которому на вход подается одномерный массив и число n
     * (может быть положительным, или отрицательным), при этом метод должен сместить
     * все элементы массива на n позиций. Элементы смещаются циклично. Для усложнения
     * задачи нельзя пользоваться вспомогательными массивами.
     * Примеры: [ 1, 2, 3 ] при n = 1 (на один вправо) -> [ 3, 1, 2 ]; [ 3, 5, 6, 1]
     * при n = -2 (на два влево) -> [ 6, 1, 3, 5 ]. При каком n в какую сторону
     * сдвиг можете выбирать сами.
     */

    static void doTask7(int[] array, int n) {
        int temp = 0;
        if (n < 0) {
            int modN = n * -1;
            for (int j = 0; j < modN; j++) {
                for (int i = 0; i < array.length - 1; i++) {
                    int nextIndex = i + 1;
                    temp = array[nextIndex];
                    array[nextIndex] = array[i];
                    array[i] = temp;
                }
            }
        } else if (n >= 0) {
            for (int j = 0; j < n; j++) {
                for (int i = array.length - 2; i >= 0; i--) {
                    int nextIndex = i + 1;
                    temp = array[nextIndex];
                    array[nextIndex] = array[i];
                    array[i] = temp;
                }
            }
        }
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }
}
