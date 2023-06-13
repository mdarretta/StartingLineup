package org.startinglineup.simulator;

import org.startinglineup.component.UniqueComponent;

public abstract class Team extends UniqueComponent {

	private org.startinglineup.component.Team team;

    public Team(org.startinglineup.component.Team team) {
        this.team = team;
    }

    public org.startinglineup.component.Team getTeam() {
        return team;
    }

    public abstract boolean isHomeTeam();
}
