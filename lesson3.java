import java.text.BreakIterator;
import java.util.Random;
import java.util.Scanner;

public class lesson3 {
    public static void main(String[] args) {
        doTask1();

        doTask2();
    }

    /**
     * 1. Написать программу, которая загадывает случайное число от 0 до 9
     * и пользователю дается 3 попытки угадать это число. При каждой попытке
     * компьютер должен сообщить, больше ли указанное пользователем число,
     * чем загаданное, или меньше. После победы или проигрыша выводится
     * запрос – «Повторить игру еще раз? 1 – да / 0 – нет»(1 – повторить, 0 – нет).
     */

    static void doTask1() {
        boolean isFalse = false;
        int n = 3;
        int max = 9;
        int min = 0;
        while (!isFalse) {

            Random rand = new Random();
            int random = rand.nextInt(max - min) + 1;

            System.out.printf("Копьютер загадал число от %d до %d. Угадайте какое это число. %n", min, max);

            for (int i = 0; i < n; i++) {

                int trys = n - i - 1;

                Scanner scanner = new Scanner(System.in);
                int digit = scanner.nextInt();

                if (random > digit) {
                    System.out.print("Загаданное число больше. ");

                } else if (random < digit) {
                    System.out.print("Загаданное число меньше. ");

                } else if (random == digit) {
                    System.out.println("Вы угадали! ");
                }

                if (trys > 0 && random != digit) {
                    System.out.printf("Осталось попыток: %d. %n", trys);

                } else if (trys == 0 && random != digit) {
                    System.out.println("Вы проиграли!");
                }

                if (random == digit || trys == 0) {
                    System.out.println("Запустить игру еще раз? Да - 1 / Нет - 0");
                    digit = scanner.nextInt();
                    if (digit != 1)
                        isFalse = true;
                    break;
                }
            }
        }
    }

    /**
     * 2. * Создать массив из слов
     * String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado",
     * "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi",
     * "mango", "mushroom", "nut", "olive", "pea", "peanut", "pear", "pepper",
     * "pineapple", "pumpkin", "potato"}.
     * * При запуске программы компьютер загадывает слово, запрашивает ответ у пользователя,
     * сравнивает его с загаданным словом и сообщает, правильно ли ответил пользователь.
     * Если слово не угадано, компьютер показывает буквы, которые стоят на своих местах.
     * apple – загаданное
     * apricot - ответ игрока
     * ap############# (15 символов, чтобы пользователь не мог узнать длину слова)
     * Угаданные в прошлые ответы буквы запоминать не надо. То есть при следующем ответе:
     * carpet (ковер, не фрукт, но это всего лишь пример), будет выведено:
     * ####e##########
     */

    static void doTask2() {

        int digit = 1;
        while (digit == 1) {
            boolean isFalse = false;
            String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli",
                    "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom",
                    "nut", "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};

            Random random = new Random();
            int randDigit = random.nextInt(words.length);
            String word = words[randDigit];

            printArray(words);

            System.out.println("Выше приведен список слов. Компьютер загадал одно слово из этого списка. " +
                    "Ваша задача угадать, что это за слово.");

            System.out.println("Выбирите одно из слов, и запишите его в консоль");

            char[] codeChr = new char[15];
            for (int j = 0; j < codeChr.length; j++) {
                codeChr[j] = 'x';
            }

            while (!isFalse) {

                Scanner scanner = new Scanner(System.in);
                String inputWord = scanner.nextLine();

                if (word.equals(inputWord)) {
                    System.out.println("Вы угадали!");
                    System.out.println("Запустить игру еще раз? Да - 1 / Нет - 0");
                    digit = scanner.nextInt();
                    if (digit != 1)
                        break;
                    else {
                        isFalse = true;
                        continue;
                    }
                } else {
                    System.out.println("Ответ не верный. Выберите другое слово.");
                }

                int min;
                if (word.length() < inputWord.length()) {
                    min = word.length();
                } else {
                    min = inputWord.length();
                }

                for (int i = 0; i < min; i++) {
                    char charWord = word.charAt(i);
                    char charInput = inputWord.charAt(i);
                    if (charWord == charInput) {
                        codeChr[i] = charWord;
                    }
                }
                printArray(codeChr);
            }
        }
    }


    static void printArray(char[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
        }
        System.out.println();
    }

    static void printArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}






