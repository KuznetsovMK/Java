package uutasks.task4;

public class Dog extends Сompetitor {
    private String name;
    private int highJump;

    public Dog(String name, int highJump) {
        this.name = name;
        this.highJump = highJump;
    }

    public String getName() {
        return name;
    }

    public int getHighJump() {
        return highJump;
    }

}
