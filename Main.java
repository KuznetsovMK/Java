package lesson5;

public class Main {
    public static void main(String[] args) {
        Employee[] employeeArray = new Employee[5];

        employeeArray[0] = new Employee("Semenov S.M.", "Manager",
                "Semenov.SM@mail.ru","89998887766",20000,37);
        employeeArray[1] = new Employee("Goncharov M.E", "Manager",
                "Goncharov.ME@mail.ru","89998887767",20000,39);
        employeeArray[2] = new Employee("Naumova E.V","Senor manager",
                "Naumova.EV@mail.ru","89998887768",30000, 41);
        employeeArray[3] = new Employee("Krasilnikova A.N", "Accountant",
                "Krasilnikova.AN@mail.ru","89998887769",30000, 42);
        employeeArray[4] = new Employee("Mihailov V.K", "Director",
                "Mihailov.VK@mail.ru","89998887770",50000, 45);

        for (int i = 0; i < employeeArray.length; i++) {
            if (employeeArray[i].getAge() > 40)
                employeeArray[i].info();
        }
    }
}

