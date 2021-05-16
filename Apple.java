package GB.lvl3lesson1;

public class Apple extends Fruit {
    private String name;

    public Apple(float weight) {
        super(weight);
        name = "Apple";
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weight=" + super.getWeight() +
                ", name='" + name + '\'' +
                '}';
    }
}
