package gb.lvl3lesson1.task3;

import java.util.ArrayList;
import java.util.List;

class Box<T extends Fruit> {

    private List<T> list = new ArrayList<>();

    public Box() {
    }

    public void add(T fruit) {
        list.add(fruit);
    }

    public void transferFrom(Box<T> anotherBox) {
        for (T fruit : anotherBox.list) {
            list.add(fruit);
        }
        anotherBox.list.clear();

        System.out.println("Transfer over!");
    }

    public boolean compare(Box<?> box) {
        System.out.print("Compare: ");
        return Math.abs(this.getWeight() - box.getWeight()) < 0.00001;
    }

    public double getWeight() {
        double sum = 0.0;
        for (T fruit : list) {
            sum += fruit.getWeight();
        }
        return sum;
    }

    public void info() {
        if (list.size() < 1) {
            System.out.println("Box is empty!");
            return;
        }

        String name = list.get(0).getName();
        double weight = list.get(0).getWeight();
        double totalWeight = weight * list.size();

        System.out.println("fruit: " + name + ", item(s): " + list.size() + ", Total weight: " + totalWeight);
    }

}
