package uutasks.task4;

public class Dog {
    private String name;
    private int highJump;
    private boolean isJumpOverWall = true;

    public Dog(String name, int highJump) {
        this.name = name;
        this.highJump = highJump;
    }

    public boolean isJumpOverWall() {
        return isJumpOverWall;
    }

    public String getName() {
        return name;
    }

    public int getHighJump() {
        return highJump;
    }

    public void setJumpOverWall(boolean jumpOverWall) {
        isJumpOverWall = jumpOverWall;
    }
}
