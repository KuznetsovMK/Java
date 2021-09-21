package uutasks.task5;

public class Main {
    public static void main(String[] args) {
        Сompetitor dog1 = new Dog("шарик", 4);
        Сompetitor dog2 = new Dog("бартон", 7);
        Сompetitor dog3 = new Dog("тор", 6);

        Сompetitor[] competitors = new Сompetitor[]{dog1, dog2, dog3};
        jumpOverWall(competitors);
    }

    public static void jumpOverWall(Сompetitor[] competitors) {
        Wall wall = new Wall(5);
        System.out.println("Впереди препятствие... Высота стены " + wall.getHigh() + " м. Пусть каждый из участников попробует ее перепрыгнуть...");
        for (Сompetitor competitor : competitors) {
            System.out.print(competitor.getName() + "- высота прыжка: " + competitor.getHighJump() + " м. ");
            if (competitor.getHighJump() > wall.getHigh()) {
                System.out.println(" перепрыгнул через стену");
            } else System.out.println(" не перепрыгнул");
        }
    }
}
