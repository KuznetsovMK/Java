package lesson6;

public class Main {
    public static void main(String[] args) {
        Cat[] catArray =new Cat[] {
                new Cat("Barsic"),
                new Cat("Grizlic"),
                new Cat("Murzik")
        };

        Dog[] dogArray = new Dog[] {
                new Dog("Rex"),
                new Dog("Barton"),
                new Dog("Busik"),
                new Dog("Bim")
        };

        for (int i = 0; i < catArray.length; i++) {
            catArray[i].catInfo();
        }

        for (int i = 0; i < dogArray.length; i++) {
            dogArray[i].dogInfo();
        }

        Animal.summary();

        catArray[0].run(50);
        catArray[1].run(-350);
        catArray[2].swim(50);

        dogArray[0].run(700);
        dogArray[1].run(-5);
        dogArray[2].swim(15);
        dogArray[3].swim(-3);


    }
}
