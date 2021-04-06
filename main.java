package GB.Lvl2lesson1;

public class main {
    public static void main(String[] args) {

        Run[] runs = {
                new Cat("Barsik"),
                new Robot("Instein"),
                new Human("Eva")
        };

        Jump[] jumps = {
                new Cat("Barsik"),
                new Robot("Instein"),
                new Human("Eva")
        };

        Wall wall = new Wall();
        Treadmill treadmill = new Treadmill();

        for (Run run : runs) {
            run.run(treadmill);
        }

        for (Jump jump : jumps) {
            jump.jump(wall);
        }

        Human human1 = new Human("Vasiliy");
        Jump jump = new Human("Patric");

        human1.run(treadmill);
        human1.jump(wall);

        Team team = new Team("VICTORY");

        team.info();

        Course course = new Course();

        course.dolt(team);

        team.showResults();
    }
}
