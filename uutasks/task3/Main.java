package uutasks.task3;

/**
 * Задача.
 *
 * 1. Создать класс Chelovek, 2. указать поля private String name, private int highJump
 * 3. Конструктор с полями name и highJump.
 * 4. Геттеры для получения имени и высоты прыжка.
 * 5. Напечатать в консоль имя учасника, высоту его прыжка и высоту препятсвия
 * 6. Проверить сожет ли наш учасник перепрыгнуть препятсвие
 */

public class Main {

    public static void main(String[] args) {
        //Создали объект типа dog (указали его имя и высоту прыжка)
        Dog dog = new Dog("Шарик", 10);

        //Указали высоту препятствия
        int highWall = 5;

        //Напечатаем в консоль имя учасника, высоту его прыжка и высоту препятсвия
        System.out.println("Имя: " + dog.getName() + ", высота прыжка: " + dog.getHighJump() + " высота препятствия: " + highWall);

        //Проверим сожет ли наш учасник перепрыгнуть препятсвие
        if (dog.getHighJump() > highWall) System.out.println("сможет перепрыгнуть");
        else System.out.println("не сможет перепрыгнуть");
    }
}
