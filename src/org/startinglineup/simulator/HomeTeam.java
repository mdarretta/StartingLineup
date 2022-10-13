package org.startinglineup.simulator;

public class HomeTeam extends Team {

    public HomeTeam(org.startinglineup.component.Team team) {
        super(team);
    }

    public boolean isHomeTeam() {
        return true;
    }
}
