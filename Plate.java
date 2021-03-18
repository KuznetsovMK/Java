package lesson7;

public class Plate {
    private int food;


    public Plate() {
    }

    public boolean decreaseFood(int appetite) {
        boolean satietyIsFalse = (food >= appetite);
        if (satietyIsFalse) food -= appetite;
        return satietyIsFalse;
    }

    public void addFood(int food) {
        this.food += food;
        System.out.printf("%s meals added to the plate. %n", food);
        System.out.println("Plate: " + this.food);
    }

    public void info() {
        System.out.println("Plate: " + food);
    }
}
