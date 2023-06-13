package org.startinglineup.league;

import java.util.ArrayList;
import java.util.Collection;

import org.startinglineup.league.Division.DivisionType;
import org.startinglineup.component.Team;

public class League {

	public enum LeagueType {
		NATIONAL("National","NL"),
		AMERICAN("American","AL");
		
		private String name;
		private String abbr;
		
		LeagueType(String name, String abbr) { 
			this.name = name;
			this.abbr = abbr;
		}
		
		public String getName() {
			return name;
		}
		
		public String getAbbr() {
			return abbr;
		}
	}
	
	private LeagueType league;
	private Division west;
	private Division central;
	private Division east;
	
	public League(LeagueType league) {
		this.league = league;
		this.west = new Division(Division.DivisionType.WEST);
		this.central = new Division(Division.DivisionType.CENTRAL);
		this.east = new Division(Division.DivisionType.EAST);
	}
	
	public LeagueType getLeague() {
		return league;
	}
	
	public static League.LeagueType getLeagueForAbbr(String abbr) {
		
		League.LeagueType rtnLeague = null;
		if (abbr.equals("NL")) {
			rtnLeague = League.LeagueType.NATIONAL;
		} else {
			rtnLeague = League.LeagueType.AMERICAN;
		}
		
		return rtnLeague;
	}
	
	public Division getDivision(DivisionType division) {
		Division rtnDivision = null;
		
		if (division.equals(DivisionType.WEST)) {
			rtnDivision = west;
		} else if (division.equals(DivisionType.CENTRAL)) {
			rtnDivision = central;
		} else {
			rtnDivision = east;
		}
		
		return rtnDivision;
	}
	
	public Collection<Team> getTeams() {
		Collection<Team> teams = new ArrayList<Team>();
		teams.addAll(west.getTeams());
		teams.addAll(central.getTeams());
		teams.addAll(east.getTeams());
		return teams;
	}
	
	public String toString() {
		return league.getName() + "\n" + east + "\n" + central + "\n" + west;
	}
}