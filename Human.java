package GB.Lvl2lesson1;

public class Human implements Jump, Run {
    private String name;

    public Human(String name) {
        this.name = name;
    }

    @Override
    public void jump() {
        System.out.println("Human: " + name + " jumped up");
    }

    @Override
    public void run() {
        System.out.println("Human: " + name + " is running");
    }

    public void jump(Wall wall) {
        System.out.println("Human: " + name + " jumped up over the wall");
    }

    public void run(Treadmill treadmill) {
        System.out.println("Human: " + name + " running on a treadmill");
    }
}
