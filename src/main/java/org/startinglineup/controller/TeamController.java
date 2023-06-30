package org.startinglineup.controller;

import org.startinglineup.component.Batter;
import org.startinglineup.simulator.Team;

/**
 * Controller to manage current team and batter for a game simulation.
 * 
 * @author Mike Darretta
 */
public class TeamController {
	
	/**
	 * The current batter pointer.
	 */
    private int batterPtr;
    
    /**
     * The team to manage.
     */
    private Team team;
    
    /**
     * Constructor.
     * @param team The team to manage.
     */
    public TeamController(Team team) {
    	this.team = team;
    	this.batterPtr = 0;
    }

    /**
     * Returns the current batter in the team lineup.
     * @return The current batter.
     */
    public Batter nextBatter() {
        if (batterPtr >= (team.getTeam().getBatters().size())) {
            batterPtr = 0;
        }
        
        return team.getTeam().getBatters().get(batterPtr++);
    }
}
