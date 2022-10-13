package org.startinglineup.controller;

import org.startinglineup.component.Batter;
import org.startinglineup.simulator.Team;

public class TeamController {
	
    private int batterPtr;
    private Team team;
    
    public TeamController(Team team) {
    	this.team = team;
    	this.batterPtr = 0;
    }

    public Batter nextBatter() {
        if (batterPtr >= (team.getTeam().getBatters().size())) {
            batterPtr = 0;
        }
        
        return team.getTeam().getBatters().get(batterPtr++);
    }
}
