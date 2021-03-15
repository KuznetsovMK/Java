package lesson6;

public abstract class Animal {
    private static int animalCount;

    public Animal() {
        animalCount++;
    }

    public abstract void swim(int swimDistance);

    public abstract void run(int runDistance);

    public static void summary() {
        System.out.printf("Total animal - %s.%n", animalCount);
    }
}
