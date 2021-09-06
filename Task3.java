package gb;

/**
 * 3. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы,
 * то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).
 */

public class Task3 {

    public static void main(String[] args) {
        Task3 task3 = new Task3();
        System.out.println(task3.checkArray(new int[]{1,3,4}));

    }

    public boolean checkArray(int[] array) {
        boolean isOne = false, isFour = false;
        for (int digit : array) {
            if (digit != 1 && digit != 4) {
                return false;
            }
            if (!isOne) isOne = digit == 1;
            if (!isFour) isFour = digit == 4;
        }
        return (isOne && isFour);
    }
}
