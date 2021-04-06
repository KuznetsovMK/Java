package GB.Lvl2lesson1;

public class Course {

    public class Wall {
        private int height;

        public Wall(int height) {
            this.height = height;
        }
    }

    Wall[] walls = {
            new Wall(4),
            new Wall(5),
            new Wall(6),
            new Wall(7),
    };

    public void dolt(Team team) {
        System.out.println();
        System.out.println("*** WALLS TEST *** ");
        int i = 1;

        for (Wall wall : walls) {
            System.out.printf("#%s height: %s.", i, wall.height);
            i++;

            for (Team.TeamMember teamMember : team.teamMembers) {
                if (!teamMember.getIsOut()) {
                    if (wall.height > teamMember.getHeight()) {
                        teamMember.setOut(true);
                        System.out.print(teamMember.getName() + " Out. ");
                    }
                }
            }
            System.out.println();
        }
    }
}
