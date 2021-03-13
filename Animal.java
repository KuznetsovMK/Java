package lesson6;

public abstract class Animal {
    private static int animalId = 0;

    public Animal() {
        animalId++;
    }

    public abstract void swim(int swimDistance);

    public abstract void run(int runDistance);

    public static void summary() {
        System.out.printf("Animal summary is %s %n", animalId);
    }
}
