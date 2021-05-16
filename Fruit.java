package GB.lvl3lesson1;

public class Fruit {
    private float weight;
    private String name;

    public Fruit(float weight) {
        this.weight = weight;
        name = "Fruit";
    }

    public Fruit() {
        name = "Fruit";
    }

    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "weight=" + weight +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }
}

