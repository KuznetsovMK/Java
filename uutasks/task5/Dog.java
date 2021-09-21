package uutasks.task5;

public class Dog extends Сompetitor {
    private int highJump;
    private String name;

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
