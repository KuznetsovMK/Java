package TicTacToe;

public class main {
    public static void main(String[] args) {
        TicTacToe.start();
    }
}

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    static void start() {

        int x = 5;
        int y = 5;
        int winRow = 4;
        int n = x - winRow;

        char[][] field = creatField(y, x);

        printArray(field);
        System.out.printf("%d in a row to win.%n%n", winRow);
        while (true) {
            getMove(field, x, y);
            if (checkWin(field, 'X', winRow, n)) {
                printWin(field);
                break;
            }
            if (!checkDraw(field)) {
                printDraw(field);
                break;
            }

            blockAIMove(field, 'X', winRow, n, x, y);
            printArray(field);
            if (checkWin(field, 'O', winRow, n)) {
                printLose(field);
                break;
            }
            if (!checkDraw(field)) {
                printDraw(field);
                break;
            }
        }
    }

    static void printLose(char[][] field) {
        printArray(field);
        System.out.println();
        System.out.println("Sorry! You are lose!");
    }

    static void printWin(char[][] field) {
        printArray(field);
        System.out.println();
        System.out.println("Congratulations! You are winner!");
    }


    static void printDraw(char[][] field) {
        printArray(field);
        System.out.println();
        System.out.println("It's a draw");
    }

    static boolean checkDraw(char[][] field) {
        boolean isNotFilled = false;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == '-') {
                    isNotFilled = true;
                    i = field.length - 1;
                    break;
                }
            }
        }
        return isNotFilled;
    }

    static boolean checkWin(char[][] field, char chr, int winRow, int n) {

        /**
         * Horizontal check.
         */
        for (int i = 0; i < field.length; i++) {
            int horizontalSum = 1;
            for (int j = 0; j < field[i].length - 1; j++) {
                if ((field[i][j] == chr) && (field[i][j + 1] == chr)) {
                    horizontalSum++;
                    if (horizontalSum == winRow)
                        return true;
                }
            }
        }
        /**
         * Vertical check.
         */
        for (int i = 0; i < field.length; i++) {
            int verticalSum = 1;
            for (int j = 0; j < field[i].length - 1; j++) {
                if ((field[j][i] == chr) && (field[j + 1][i] == chr)) {
                    verticalSum++;
                    if (verticalSum == winRow)
                        return true;
                }
            }
        }
        /**
         * diagonal check.
         */
        int diagonalSum = 1;
        int diagonalRevSum = 1;
        for (int i = 0; i < field.length - 1; i++) {
            if ((field[i][i] == chr) && (field[i + 1][i + 1] == chr)) {
                diagonalSum++;
                if (diagonalSum == winRow)
                    return true;
            }
            if ((field[field.length - 1 - i][i] == chr) && (field[field.length - 2 - i][i + 1] == chr)) {
                diagonalRevSum++;
                if (diagonalRevSum == winRow)
                    return true;
            }
        }

        /**
         * new 2nd and more... diagonal check.
         * n = x - winrow;
         */
        for (int j = 0; j < n; j++) {
            int sDiagonalHighSum = 1;
            int sDiagonalLowerSum = 1;
            int sDiagonalHighRevSum = 1;
            int sDiagonalLowRevSum = 1;
            for (int i = 0; i < field.length - 1 - n; i++) {

                /**
                 * Diagonal high forward
                 */
                if ((field[i][i + 1 + j] == chr) && (field[i + 1][i + 2 + j] == chr)) {
                    sDiagonalHighSum++;
                    if (sDiagonalHighSum == winRow)
                        return true;
                }

                /**
                 * Diagonal low forward
                 */
                if ((field[i + 1 + j][i] == chr) && (field[i + 2 + j][i + 1] == chr)) {
                    sDiagonalLowerSum++;
                    if (sDiagonalLowerSum == winRow)
                        return true;
                }
                /**
                 * Diagonal high revers
                 */
                if ((field[field.length - 2 - i - j][i] == chr) && (field[field.length - 3 - i - j][i + 1] == chr)) {
                    sDiagonalHighRevSum++;
                    if (sDiagonalHighRevSum == winRow)
                        return true;
                }

                /**
                 * Diagonal low revers
                 */
                if ((field[i + 1 + j][field.length - 1 - i] == chr) && (field[i + 2 + j][field.length - 2 - i] == chr)) {
                    sDiagonalLowRevSum++;
                    if (sDiagonalLowRevSum == winRow)
                        return true;
                }
            }
        }
        return false;
    }


    static boolean checkNoEmpty(char[][] field, int h, int v) {
        return (field[h][v] != '-');
    }

    static boolean checkEmpty(char[][] field, int h, int v) {
        return (field[h][v] == '-');

    }

    static int checkRight(int x, int y) {
        int number;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.printf("Value from 1 to %d", x);
            System.out.println();
            number = scanner.nextInt() - 1;
        } while (number < 0 || number >= x || number >= y);
        return number;
    }

    static void getAIMove(char[][] field, int x, int y) {
        Random random = new Random();
        int h;
        int v;
        do {
            h = random.nextInt(y);
            v = random.nextInt(x);
        }
        while (checkNoEmpty(field, h, v));
        field[h][v] = 'O';
    }

    static void getMove(char[][] field, int x, int y) {
        int h;
        int v;
        do {
            System.out.println("Insert h-value...");
            h = checkRight(x, y);
            System.out.println("Insert v-value...");
            v = checkRight(x, y);
        } while (checkNoEmpty(field, h, v));
        field[h][v] = 'X';
    }

    static char[][] creatField(int y, int x) {
        char[][] field = new char[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                field[i][j] = '-';
            }
        }
        return field;
    }

    static void printArray(char[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void blockAIMove(char[][] field, char chr, int winRow, int n, int x, int y) {

        int nextAIMoveH = -1;
        int nextAIMoveV = -1;


        /**
         * Horizontal check.
         */
        for (int i = 0; i < field.length; i++) {
            int horizontalSum = 1;
            for (int j = 0; j < field[i].length - 1; j++) {
                if ((field[i][j] == chr) && (field[i][j + 1] == chr)) {
                    horizontalSum++;
                    if (horizontalSum == winRow - 1) {
                        if ((j + 2 < x) && checkEmpty(field, i, (j + 2))) {
                            nextAIMoveH = i;
                            nextAIMoveV = j + 2;
                        } else if (((j - (winRow - 2)) >= 0) && checkEmpty(field, i, (j - (winRow - 2)))) {
                            nextAIMoveH = i;
                            nextAIMoveV = j - (winRow - 2);
                        }
                    }
                }
            }
        }
        /**
         * Vertical check.
         */
        for (int i = 0; i < field.length; i++) {
            int verticalSum = 1;
            for (int j = 0; j < field[i].length - 1; j++) {
                if ((field[j][i] == chr) && (field[j + 1][i] == chr)) {
                    verticalSum++;
                    if (verticalSum == winRow - 1) {
                        if ((j + 2 < x) && checkEmpty(field, (j + 2), i)) {
                            nextAIMoveV = i;
                            nextAIMoveH = j + 2;
                        } else if (((j - (winRow - 2)) >= 0) && checkEmpty(field, (j - (winRow - 2)), i)) {
                            nextAIMoveV = i;
                            nextAIMoveH = j - (winRow - 2);
                        }
                    }
                }
            }
        }
        /**
         * diagonal check.
         */
        int diagonalSum = 1;
        int diagonalRevSum = 1;
        for (int i = 0; i < field.length - 1; i++) {
            if ((field[i][i] == chr) && (field[i + 1][i + 1] == chr)) {
                diagonalSum++;
                if (diagonalSum == winRow - 1) {
                    if ((i + 2 < x) && checkEmpty(field, (i + 2), (i + 2))) {
                        nextAIMoveV = i + 2;
                        nextAIMoveH = i + 2;
                    } else if (((i - (winRow - 2)) >= 0) && checkEmpty(field, (i - (winRow - 2)), (i - (winRow - 2)))) {
                        nextAIMoveV = i - (winRow - 2);
                        nextAIMoveH = i - (winRow - 2);
                    }
                }
            }
            if ((field[field.length - 1 - i][i] == chr) && (field[field.length - 2 - i][i + 1] == chr)) {
                diagonalRevSum++;
                if (diagonalRevSum == winRow - 1) {
                    if ((i + 2 < x) && (field.length - 1 - i >= 0) && !checkNoEmpty(field, (field.length - 1 - i), (i + 2))) {
                        nextAIMoveV = i + 2;
                        nextAIMoveH = field.length - 1 - i - 2;
                    } else if (((i - (winRow - 2)) >= 0) && (i + (winRow - 2)) < x && !checkNoEmpty(field, (i + (winRow - 2)), (i - (winRow - 2)))) {
                        nextAIMoveV = i - (winRow - 2);
                        nextAIMoveH = i + (winRow - 2);
                    }
                }
            }
        }
//
//        /**
//         * new 2nd and more... diagonal check.
//         * n = x - winrow;
//         */
//        for (int j = 0; j < n; j++) {
//            int sDiagonalHighSum = 1;
//            int sDiagonalLowerSum = 1;
//            int sDiagonalHighRevSum = 1;
//            int sDiagonalLowRevSum = 1;
//            for (int i = 0; i < field.length - 1 - n; i++) {
//
//                /**
//                 * Diagonal high forward
//                 */
//                if ((field[i][i + 1 + j] == chr) && (field[i + 1][i + 2 + j] == chr)) {
//                    sDiagonalHighSum++;
//                   // if (sDiagonalHighSum == winRow)
//                     //   return true;
//                }
//
//                /**
//                 * Diagonal low forward
//                 */
//                if ((field[i + 1 + j][i] == chr) && (field[i + 2 + j][i + 1] == chr)) {
//                    sDiagonalLowerSum++;
//                    if (sDiagonalLowerSum == winRow)
//                      //  return true;
//                }
//                /**
//                 * Diagonal high revers
//                 */
//                if ((field[field.length - 2 - i - j][i] == chr) && (field[field.length - 3 - i - j][i + 1] == chr)) {
//                    sDiagonalHighRevSum++;
//                    if (sDiagonalHighRevSum == winRow)
//                      //  return true;
//                }
//
//                /**
//                 * Diagonal low revers
//                 */
//                if ((field[i + 1 + j][field.length - 1 - i] == chr) && (field[i + 2 + j][field.length - 2 - i] == chr)) {
//                    sDiagonalLowRevSum++;
//                    if (sDiagonalLowRevSum == winRow)
//                      //  return true;
//                }
        //         }
        //       }

        if (nextAIMoveH == -1) {
            getAIMove(field, x, y);
        } else {
            field[nextAIMoveH][nextAIMoveV] = 'O';
        }

    }
}
