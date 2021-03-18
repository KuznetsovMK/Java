package lesson7;

public class Main {
    public static void main(String[] args) {
        Cat[] arrayCats = {
                new Cat("Barsik"),
                new Cat("Murzic"),
                new Cat("Tima")};
        Plate plate = new Plate();
        plate.info();

        plate.addFood(20);

        for (Cat arrayCat : arrayCats) {
            arrayCat.eat(plate);
            arrayCat.info();
            plate.info();
        }
    }
}
