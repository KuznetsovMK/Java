public class lesson1 {

    public static void main(String[] args) {
        byte byt = 1;
        short shor = 1;
        int num1 = -1;
        int num2 = 6;
        int year = 2020;
        long lon = 1232020;
        float flot1 = 1.5f;
        float flot2 = 2.3f;
        float flot3 = 1.4f;
        float flot4 = 4.6f;
        double doble = 10.2;
        char chr = 'т';
        boolean isTrue = true;
        String name = "Михаил";



        System.out.println(calculationFloat(flot1, flot2, flot3,flot4));

        System.out.println(checkNumber(num1, num2));

        checkPositiveOrNegative(num1);

        System.out.println(checkPositiveOrNegative1(num1));

        greetingName(name);

        checkHighGradeYear(year);

    }

    static float calculationFloat(float a, float b, float c, float d) {
        float results = a * (b + ((c / d)));
        return results;
    }

    static boolean checkNumber(int a, int b) {
        if (a + b >= 10 & a + b <= 20)
            return true;
        return false;
    }

    static void checkPositiveOrNegative(int a) {
        if (a >= 0)
            System.out.println(a + ": Число положительное");
        else
            System.out.println(a + ": Число отрицательное");
    }

    static boolean checkPositiveOrNegative1(int a) {
        if (a < 0)
            return true;
        return false;
    }

    static void greetingName(String name) {
        System.out.println("Привет, " + name + "!");
    }

    static void checkHighGradeYear(int year) {
        if ((year % 4 == 0) ^ ((year % 100 == 0) ^ (year % 400 == 0))) {
            System.out.println(year + " год - високосный");
        } else {
            System.out.println(year + " год - не является високосным");
        }
    }
}