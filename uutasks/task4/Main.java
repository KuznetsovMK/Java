package uutasks.task4;

/**
 * Задача.
 * <p>
 * 1. Создать класс Chelovek, 2. указать поля private String name, private int highJump,  private boolean isJumpOverWall = true;
 * 3. Создать Конструктор с полями name и highJump.
 * 4. Создать Геттеры для получения имени, высоты прыжка, и поля isJumpOverWall.
 * 4.1. Создать Сеттер для получения поля isJumpOverWall.
 * 5. Напечатать в консоль имя учасника, высоту его прыжка и высоту препятсвия
 * 6. Проверить сожет ли наш учасник перепрыгнуть препятсвие
 */

public class Main {

    public static void main(String[] args) {
        //Создали объект типа dog (указали его имя и высоту прыжка) и добавили их в массив
        Dog dog1 = new Dog("Шарик1", 10);
        Dog dog2 = new Dog("Шарик2", 4);
        Dog dog3 = new Dog("Шарик3", 6);
        Dog[] dogs = new Dog[]{dog1, dog2, dog3};

        //Указали высоту препятствия
        int highWall = 5;

        //Напечатаем в консоль высоту препятсвия
        System.out.println("Высота препятствия: " + highWall);
        for (Dog dog : dogs) {
            //Напечатаем в консоль имя каждого учасника, высоту его прыжка
            System.out.print("Имя: " + dog.getName() + ", высота прыжка: " + dog.getHighJump() + " - ");

            //Проверим сожет ли наш учасник перепрыгнуть препятсвие. Если нет - указываем в private boolean isJumpOverWall (false)
            if (dog.getHighJump() > highWall) System.out.println("перепрыгнул");
            else {
                System.out.println("не перепрыгнул");
                dog.setJumpOverWall(false);
            }
        }

        //Напечатаем в консоль имена всех собак прошедших препятствие
        System.out.println("Участники преодолевшие препятствие: ");
        for (Dog dog : dogs) {
            if (dog.isJumpOverWall()) {
                System.out.println("Имя: " + dog.getName());
            }

        }
    }
}
