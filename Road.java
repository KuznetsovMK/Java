package gb.lvl3lesson5;

public class Road extends Stage {
    boolean finish;
    boolean winner;

    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    public Road(int length, boolean finish) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
        this.finish = finish;
    }

    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            if (!winner & finish) {
                synchronized ((Object) winner) {
                    System.out.println(c.getName() + " закончил этап: " + description);
                    winner = true;
                    System.out.println(c.getName() + " WIN! ");

                }
            } else {
                System.out.println(c.getName() + " закончил этап: " + description);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

