package GB.lvl3lesson1;

import java.util.ArrayList;
import java.util.List;

class Box<T> {
    private static int cnt = 1;
    private int id;
    private float weight;
    private T fruit;
    List<Integer> list = new ArrayList<>();

    public Box(T fruit, int item, float weight) {
        this.weight = weight;
        this.fruit = fruit;
        list.add(item);
        id = getId();
        cnt++;
    }

    public Box(T fruit) {
        this.fruit = fruit;
        id = getId();
        cnt++;
    }

    public int getId() {
        id = cnt;
        return id;
    }

    public int sumItems() {
        int sum = 0;
        for (int item : list) {
            sum += item;
        }
        return sum;
    }

    public T getFruit() {
        return fruit;
    }

    public void setFruit(T fruit) {
        this.fruit = fruit;
    }

    public float getWeight() {
        return this.sumItems() * weight;
    }

    public void compare(Box box) {
        System.out.print("Сравнение веса коробок #" + id + " и #" + box.id + " : ");
        System.out.println(Math.abs(this.getWeight() - box.getWeight()) < 0.00001);
    }

    public void addFruits(T fruit, int items) {
        list.add(items);
        System.out.println("В коробку #" + id + " добавлено " + items + " " + fruit.getClass().getName());
    }

    public void transferFrom(Box box) {
        fruit = (T) box.fruit;
        this.weight = box.weight;
        int sum = 0;
        for (Object o : box.list) {
            sum = sum + (Integer) o;
        }
        list.add(sum);
        System.out.println("Из коробки #" + box.id + " " + box.fruit.getClass().getName() +
                " фрукты перенесены в коробку #" + id + " " + fruit.getClass().getName());
        box.clearBox();
    }

    public void clearBox() {
        this.list.clear();
    }

    public void showInfo() {
        System.out.println("Коробка #" + id + ". Тип Т: " + fruit.getClass().getName() + ", сумма итемов: " + sumItems() +
                ", Вес коробки: " + getWeight());
    }

    @Override
    public String toString() {
        return "Box{#" + this.id +
                " fruit=" + fruit +
                '}';
    }
}
