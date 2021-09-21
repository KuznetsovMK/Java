package uutasks.task1;

public class Cat implements Jumpable {
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    @Override
    public void jump() {
        System.out.println(name + " подпрыгнул");
    }
}


