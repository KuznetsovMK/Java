package GB.Lvl2lesson1;


public class Team {
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public class TeamMember {
        private String name;
        private int height;
        private int length;
        private boolean isOut;

        public String getName() {
            return name;
        }

        public int getHeight() {
            return height;
        }

        public int getLength() {
            return length;
        }

        public boolean getIsOut() {
            return isOut;
        }

        public void setOut(boolean out) {
            isOut = out;
        }

        public TeamMember(String name, int height, int length) {
            this.name = name;
            this.height = height;
            this.length = length;
            isOut = false;
        }
    }

    TeamMember[] teamMembers = {
            new TeamMember("Yulia", 5, 16),
            new TeamMember("Nadezda", 6, 15),
            new TeamMember("Evgeniya", 7, 14),
            new TeamMember("Tatyana", 4, 17)
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
                System.out.printf("#%s: %s - test passed. \n", i, teamMember.name);
            }
        }
        if (i == 0)
            System.out.println("No one passed the test");
    }
}
