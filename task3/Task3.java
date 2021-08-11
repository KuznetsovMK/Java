package gb.lvl3lesson1.task3;

public class Task3 {
    public static void main(String[] args) {
        Box<Orange> orangeBox = new Box<>();
        Box<Apple> appleBox = new Box<>();

        orangeBox.add(new Orange());
        orangeBox.add(new Orange());
        orangeBox.add(new Orange());

        appleBox.add(new Apple());
        appleBox.add(new Apple());

        orangeBox.info();
        appleBox.info();

        System.out.println(appleBox.compare(orangeBox));

        Box<Orange> orangeBox1 = new Box<>();
        orangeBox1.transferFrom(orangeBox);
        orangeBox1.info();

        Box<Orange> orangeBox2 = new Box<>();
        orangeBox2.info();
    }
}
