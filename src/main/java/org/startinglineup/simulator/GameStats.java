package org.startinglineup.simulator;

import java.util.Iterator;
import java.util.List;

import org.startinglineup.component.HalfInning;
import org.startinglineup.component.Innings;
import org.startinglineup.controller.InningsController;

public class GameStats {
	
	private org.startinglineup.component.Team home;
	private org.startinglineup.component.Team visitor;
	private org.startinglineup.component.Team winningTeam;
	private org.startinglineup.component.Team losingTeam;
	private int homeTeamRuns;
	private int visitingTeamRuns;
	private List<HalfInning> topHalfInnings;
	private List<HalfInning> bottomHalfInnings;
	
	public GameStats(
			org.startinglineup.component.Team home,
			org.startinglineup.component.Team visitor, 
			InningsController inningsCtrl) {
		this.home = home;
		this.visitor = visitor;
		
    	this.topHalfInnings = inningsCtrl.getInnings().getTopHalfInnings();
    	this.bottomHalfInnings = inningsCtrl.getInnings().getBottomHalfInnings();
    	
    	homeTeamRuns = getRuns(topHalfInnings);
    	visitingTeamRuns = getRuns(bottomHalfInnings);

    	setWinningAndLosingTeams(inningsCtrl);
	}
	
	private int getRuns(List<HalfInning> halfInnings) {
		int runs = 0;
		Iterator<HalfInning> i = halfInnings.iterator();
		
		while (i.hasNext()) {
			runs += i.next().getRuns().size();
		}
		
		return runs;
	}
	
    private void setWinningAndLosingTeams(InningsController inningsCtrl) {
    	
    	if (Innings.getRunsForHalfInnings(topHalfInnings).size() >
    	    Innings.getRunsForHalfInnings(bottomHalfInnings).size()) {
    		winningTeam = visitor;
    		losingTeam = home;
    	} else {
    		winningTeam = home;
    		losingTeam = visitor;
    	}
    }

	/**
	 * @return the home
	 */
	public org.startinglineup.component.Team getHome() {
		return home;
	}

	/**
	 * @return the visitors
	 */
	public org.startinglineup.component.Team getVisitor() {
		return visitor;
	}

	/**
	 * @return the winningTeam
	 */
	public org.startinglineup.component.Team getWinningTeam() {
		return winningTeam;
	}

	/**
	 * @return the losingTeam
	 */
	public org.startinglineup.component.Team getLosingTeam() {
		return losingTeam;
	}

	/**
	 * @return the homeTeamRuns
	 */
	public int getHomeTeamRuns() {
		return homeTeamRuns;
	}

	/**
	 * @return the visitingTeamRuns
	 */
	public int getVisitingTeamRuns() {
		return visitingTeamRuns;
	}

	/**
	 * @return the topHalfInnings
	 */
	public List<HalfInning> getTopHalfInnings() {
		return topHalfInnings;
	}

	/**
	 * @return the bottomHalfInnings
	 */
	public List<HalfInning> getBottomHalfInnings() {
		return bottomHalfInnings;
	}
	
	/**
	 * Returns a stringified rendition of this object.
	 * @return A stringified rendition of this object.
	 */
	public String toString() {
		return visitor.getAbbr() + " " + visitingTeamRuns + ", " + home.getAbbr() + " " + homeTeamRuns;
	}
}
