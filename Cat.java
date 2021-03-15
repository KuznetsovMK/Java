package lesson6;

public class Cat extends Animal {
    private static int catCount;
    private int runDistance = 200;
    private int swimDistance;
    private String name;
    private int id;

    public Cat(String name) {
        this.name = name;
        this.id = getCatCount();
    }

    public int getCatCount() {
        this.catCount++;
        return this.catCount;
    }

    public void catInfo() {
        System.out.printf("Cat #%s. Name: %s %n", id, name);
    }

    public static void summary() {
        System.out.printf("Total cat - %s.%n", catCount);
    }

    @Override
    public void run(int runDistance) {
        if (runDistance > 0 && runDistance <= this.runDistance)
            System.out.printf("The cat %s ran %s meter(s). %n", name, runDistance);
        else if (runDistance > this.runDistance)
            System.out.printf("The cat %s ran %s meters and stopped to rest. %n",
                    name, this.runDistance);
        else if (runDistance < 0 && runDistance >= this.runDistance * -1)
            System.out.printf("The cat %s ran %s meter(s) in the opposite " +
                    "direction. %n ", name, runDistance * -1);
        else if (runDistance < this.runDistance * -1)
            System.out.printf("The cat %s ran %s meters in the opposite direction " +
                    "and stopped to rest. %n", name, this.runDistance);
        else
            System.out.printf("The cat %s stands still. %n", name);
    }

    @Override
    public void swim(int swimDistance) {
        System.out.printf("The cat %s can't swim. %n", name);
    }
}
