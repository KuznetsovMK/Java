package GB.lvl3lesson1;

public class boxAndFruits {
    public static void main(String[] args) {
        Orange orange = new Orange(1.5f);
        Apple apple = new Apple(1.0f);
        Fruit fruit = new Fruit();

        System.out.println(orange.toString());
        System.out.println(fruit.toString());
        System.out.println(apple.toString());

        Box<Orange> orangeBox = new Box<>(orange, 3, orange.getWeight());
        Box<Apple> appleBox = new Box<>(apple, 4, apple.getWeight());
        Box<Fruit> emBox = new Box<>(fruit);

        System.out.println(orangeBox.toString());

        orangeBox.showInfo();
        appleBox.showInfo();

        orangeBox.addFruits(orange, 1);
        appleBox.addFruits(apple, 2);

        orangeBox.showInfo();
        appleBox.showInfo();
        emBox.showInfo();

        orangeBox.compare(appleBox);

        emBox.transferFrom(orangeBox);
        emBox.showInfo();
        orangeBox.showInfo();

        orangeBox.addFruits(orange, 3);
        orangeBox.showInfo();
    }
}
