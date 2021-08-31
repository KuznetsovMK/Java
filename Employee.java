package lesson5;

public class Employee {
    private String name;
    private String position;
    private String email;
    private String tel;
    private int salary;
    private int age;

    public Employee (String name, String position,
                     String email, String tel,
                     int salary, int age) {
        this.name = name;
        this.position = position;
        this.email = email;
        this.tel = tel;
        this.salary = salary;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void info() {
        System.out.println("name: " + name + ", position: " + position +
                ", email: " + email + ", tel: " + tel + ", salary: "+ salary +
                ", age: " + age + ".");
    }
}
