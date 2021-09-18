package GB.Lvl2lesson1;

public class Cat implements Run, Jump {
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    @Override
    public void jump() {
        System.out.println("Cat: " + name + " jumped up");
    }

    @Override
    public void jump(Wall wall) {
        System.out.println("Cat: " + name + " jumped up over the wall");
    }

    @Override
    public void run() {
        System.out.println("Cat: " + name + " is running");
    }

    @Override
    public void run(Treadmill treadmill) {
        System.out.println("Cat: " + name + " running on a treadmill");
    }
}
