package uutasks.task1;

/**
 * Создать класс Human по аналогии с классом Cat.
 * Класс Human должен:
 * 1. наследовать интерефейс Jumpable и его реализацию.
 * 2. Иметь поле String name.
 * 3. Иметь контсруктор с указанием имении при создании объекта.
 */

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat("Барсик");
        cat.jump();
    }
}
