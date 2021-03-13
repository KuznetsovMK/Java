package lesson6;

public class Dog extends Animal {
    private static int dogCount;
    private int runDistance = 500;
    private int swimDistance = 10;
    private String name;
    private int id;

    public Dog(String name) {
        super();
        this.name = name;
        this.id = getId();
    }

    public int getId() {
        this.dogCount++;
        return this.dogCount;
    }

    public void dogInfo() {
        System.out.printf("Id: %s. Dog %s %n", id, name);
    }

    public void dogSummary() {
        System.out.printf("%s - total number of dogs", dogCount);
    }

    @Override
    public void run(int runDistance) {
        if (runDistance > 0 && runDistance <= this.runDistance)
            System.out.printf("The dog %s ran %s meter(s). %n", name, runDistance);
        else if (runDistance > this.runDistance)
            System.out.printf("The dog %s ran %s meters and stopped to rest. %n",
                    name, this.runDistance);
        else if (runDistance < 0 && runDistance >= this.runDistance * -1)
            System.out.printf("The dog %s ran %s meter(s) in the opposite " +
                    "direction. %n", name, runDistance * -1);
        else if (runDistance < this.runDistance * -1)
            System.out.printf("The dog %s ran %s meters in the opposite direction " +
                    "and stopped to rest. %n", name, this.runDistance);
        else
            System.out.printf("The dog %s stands still. %n", name);
    }

    @Override
    public void swim(int swimDistance) {
        if (swimDistance > 0 && swimDistance <= this.swimDistance)
            System.out.printf("The dog %s swim %s meter(s). %n", name, swimDistance);
        else if (swimDistance > this.swimDistance)
            System.out.printf("The dog %s swim %s meters and stopped to rest. %n",
                    name, this.swimDistance);
        else if (swimDistance < 0 && swimDistance >= this.swimDistance * -1)
            System.out.printf("The dog %s swim %s meter(s) in the opposite " +
                    "direction. %n ", name, swimDistance);
        else if (swimDistance < this.swimDistance * -1)
            System.out.printf("The dog %s swim %s meters in the opposite direction " +
                    "and stopped to rest. %n", name, this.swimDistance);
        else
            System.out.printf("The dog %s swims still. %n", name);
    }
}
