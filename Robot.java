package GB.Lvl2lesson1;

public class Robot implements Run, Jump {
    private String name;

    public Robot(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Robot: " + name + " is running");
    }

    @Override
    public void run(Treadmill treadmill) {
        System.out.println("Robot: " + name + " running on a treadmill");
    }

    @Override
    public void jump() {
        System.out.println("Robot: " + name + " jumped up");
    }

    @Override
    public void jump(Wall wall) {
        System.out.println("Robot: " + name + " jumped up over the wall");
    }
}
