package GB.lvl2lesson2;

public class HWLesson2 {

    public static void main(String[] args) {
        String[][] strArray = {
                {"2", "5", "7", "6"},
                {"2", "4", "7", "6"},
                {"6", "-7", "1", "3"},
                {"1", "2", ",", "4"},
        };
        int sum = 0;

        try {
            sum = sumDigitArray(strArray);
            System.out.println("Sum digits in array: " + sum);

        } catch (MyArraySizeException e) {
            System.out.println("The array size is not 4x4");

        } catch (MyArrayDataException e) {
            System.out.println("Invalid cell value");
            e.printStackTrace();
        }
    }

    public static int sumDigitArray(String[][] array) throws MyArraySizeException, MyArrayDataException {
        int sum = 0;

        if (array.length != 4 || array[0].length != 4) {
            throw new MyArraySizeException();
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {

                try {
                    sum += Integer.parseInt(array[i][j]);

                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j, array[i][j]);
                }
            }
        }
        return sum;
    }
}
