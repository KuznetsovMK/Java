package GB.lvl3lesson1;

public class Orange extends Fruit {
    private String name;

    public Orange(float weight) {
        super(weight);
        name = "Orange";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Orange{" +
                "weight=" + super.getWeight() +
                ", name='" + name + '\'' +
                '}';
    }
}
