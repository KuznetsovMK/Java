package uutasks.task4;

public class Wall {
    private String name = "Стена";
    private int high;

    public String getName() {
        return name;
    }

    public int getHigh() {
        return high;
    }

    public Wall(int high) {
        this.high = high;
    }
}
