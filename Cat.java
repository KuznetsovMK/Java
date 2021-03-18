package lesson7;

import java.util.Random;

public class Cat {
    private final String name;
    private final int appetite;
    private boolean satiety;

    public Cat(String name) {
        Random random = new Random();
        appetite = random.nextInt(10) + 5;
        this.name = name;
    }

    public void info() {
        System.out.printf("Cat: %s, appetite: %s, satiety: %s.%n ",
                name, appetite, satiety);
    }

    public void eat(Plate food) {
        satiety = food.decreaseFood(appetite);
    }
}
