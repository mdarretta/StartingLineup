package org.startinglineup.simulator;

public class VisitingTeam extends Team {

    public VisitingTeam(org.startinglineup.component.Team team) {
        super(team);
    }

    public boolean isHomeTeam() {
        return false;
    }
}
