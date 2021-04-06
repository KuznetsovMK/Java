package GB.Lvl2lesson1;


public class Team {
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public class TeamMember {
        private String name;
        private int height;
        private boolean isOut;

        public String getName() {
            return name;
        }

        public int getHeight() {
            return height;
        }

        public boolean getIsOut() {
            return isOut;
        }

        public void setOut(boolean out) {
            isOut = out;
        }

        public TeamMember(String name, int height) {
            this.name = name;
            this.height = height;
            isOut = false;
        }
    }

    TeamMember[] teamMembers = {
            new TeamMember("Yulia", 7),
            new TeamMember("Nadezda", 3),
            new TeamMember("Evgeniya", 5),
            new TeamMember("Tatyana", 5)
    };

    public void info() {
        System.out.println();
        System.out.println("TEAM: " + name + "");
        int i = 1;

        for (TeamMember teamMember : teamMembers) {
            System.out.printf("#%s: %s\n", i, teamMember.getName());
            i++;
        }
    }

    public void showResults() {
        System.out.println();
        System.out.println("*** RESULTS ***");
        int i = 0;

        for (TeamMember teamMember : teamMembers) {
            if (!teamMember.isOut) {
                i++;
                System.out.printf("#%s: %s - test passed", i, teamMember.name);
            }
        }
        if (i == 0)
            System.out.println("No one passed the test");
    }
}
