package GB.Lvl2lesson1;

public class Course {

    public class Wall implements Obstacle {
        private int height;

        public Wall(int height) {
            this.height = height;
        }

        @Override
        public void doSomething(Team team) {
            System.out.printf("Wall, height: %s. ", height);
            for (Team.TeamMember teamMember : team.teamMembers) {
                if (!teamMember.getIsOut()) {
                    if (height > teamMember.getHeight()) {
                        teamMember.setOut(true);
                        System.out.print(teamMember.getName() + " Out. ");
                    }
                }
            }
        }
    }

    public class Road implements Obstacle {
        private int length;

        public Road(int length) {
            this.length = length;
        }

        @Override
        public void doSomething(Team team) {
            System.out.printf("Road, length: %s. ", length);
            for (Team.TeamMember teamMember : team.teamMembers) {
                if (!teamMember.getIsOut()) {
                    if (length > teamMember.getLength()) {
                        teamMember.setOut(true);
                        System.out.print(teamMember.getName() + " Out. ");
                    }
                }
            }
        }
    }

    Obstacle[] obstacles = {
            new Wall(3),
            new Road(12),
            new Wall(4),
            new Road(13),
            new Wall(5),
            new Road(14),
            new Wall(6),
            new Road(15)
    };

    public void dolt(Team team) {
        System.out.println();
        System.out.println("*** WALLS & ROAD TEST *** ");
        int i = 1;

        for (Obstacle obstacle : obstacles) {
            System.out.printf("#%s ", i);
            obstacle.doSomething(team);
            System.out.println();
            i++;
        }
    }
}

