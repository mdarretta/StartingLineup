package org.startinglineup.league;

import java.util.Collection;
import java.util.Iterator;

import org.startinglineup.component.Team;
import org.startinglineup.component.UniqueComponent;

import java.util.ArrayList;

public class Division extends UniqueComponent {
	
	public enum DivisionType {
		WEST("West"), CENTRAL("Central"), EAST("East");
		
		private String name;
		
		DivisionType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	private DivisionType division;
	private Collection<Team> teams;
	
	public Division(DivisionType division) {
		super();
		this.division = division;
		this.teams = new ArrayList<Team>();
	}
	
	public DivisionType getDivision() {
		return division;
	}
	
	public Collection<Team> getTeams() {
		return teams;
	}

	public void addTeam(Team team) {
		teams.add(team);
	}
	
	public void addTeams(Collection<Team> teams) {
		this.teams.addAll(teams);
	}
	
	private String getTeamsString() {
		String teamStr = "";
		
		Iterator<Team> i = teams.iterator();
		while (i.hasNext()) {
			teamStr += i.next() + "\n";
		}
		
		return teamStr;
	}
	
	public String toString() {
		return division.getName() + "\n" + getTeamsString();
	}
}
