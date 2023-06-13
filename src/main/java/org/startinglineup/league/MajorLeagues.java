package org.startinglineup.league;

import java.util.Collection;

import java.util.Iterator;

import org.startinglineup.component.Team;
import org.startinglineup.component.Teams;
import org.startinglineup.league.Division.DivisionType;

import java.util.ArrayList;

public class MajorLeagues {

	private static final MajorLeagues instance = new MajorLeagues();

	private League national;
	private League american;

	private MajorLeagues() {
		this.national = new League(League.LeagueType.NATIONAL);
		this.american = new League(League.LeagueType.AMERICAN);
		createLeagues();
	}

	public static MajorLeagues getInstance() {
		return instance;
	}

	private void createLeagues() {
		create(national, Division.DivisionType.WEST, createNLWestTeams());
		create(national, Division.DivisionType.CENTRAL, createNLCentralTeams());
		create(national, Division.DivisionType.EAST, createNLEastTeams());
		create(american, Division.DivisionType.WEST, createALWestTeams());
		create(american, Division.DivisionType.CENTRAL, createALCentralTeams());
		create(american, Division.DivisionType.EAST, createALEastTeams());
	}

	private League create(League league, Division.DivisionType division, Collection<Team> teams) {
		league.getDivision(division).addTeams(teams);
		return league;
	}

	private Collection<Team> createNLWestTeams() {
		Collection<Team> teams = new ArrayList<Team>();
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.SF));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.LAD));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.ARI));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.SD));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.COL));

		return teams;
	}

	private Collection<Team> createNLCentralTeams() {
		Collection<Team> teams = new ArrayList<Team>();
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.STL));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.PIT));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.CHI));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.MIL));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.CIN));

		return teams;
	}

	private Collection<Team> createNLEastTeams() {
		Collection<Team> teams = new ArrayList<Team>();
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.NYM));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.WAS));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.MIA));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.ATL));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.PHI));

		return teams;
	}

	private Collection<Team> createALWestTeams() {
		Collection<Team> teams = new ArrayList<Team>();
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.TEX));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.HOU));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.LAA));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.SEA));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.OAK));

		return teams;
	}

	private Collection<Team> createALCentralTeams() {
		Collection<Team> teams = new ArrayList<Team>();
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.KC));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.MIN));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.CLE));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.CWS));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.DET));

		return teams;
	}

	private Collection<Team> createALEastTeams() {
		Collection<Team> teams = new ArrayList<Team>();
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.TOR));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.NYY));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.BAL));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.TB));
		teams.add(Teams.getInstance().getTeam(Teams.TeamAbbr.BOS));

		return teams;
	}

	public League getNational() {
		return national;
	}

	public League getAmerican() {
		return american;
	}

	public Collection<Team> getNLTeams() {
		Collection<Team> nlTeams = new ArrayList<Team>();

		nlTeams.addAll(getNLWestTeams());
		nlTeams.addAll(getNLCentralTeams());
		nlTeams.addAll(getNLEastTeams());

		return nlTeams;
	}

	public Collection<Team> getNLWestTeams() {
		return national.getDivision(DivisionType.WEST).getTeams();
	}

	public Collection<Team> getNLCentralTeams() {
		return national.getDivision(DivisionType.CENTRAL).getTeams();
	}

	public Collection<Team> getNLEastTeams() {
		return national.getDivision(DivisionType.EAST).getTeams();
	}

	public Collection<Team> getALTeams() {
		Collection<Team> alTeams = new ArrayList<Team>();

		alTeams.addAll(getALWestTeams());
		alTeams.addAll(getALCentralTeams());
		alTeams.addAll(getALEastTeams());

		return alTeams;
	}

	public Collection<Team> getALWestTeams() {
		return american.getDivision(DivisionType.WEST).getTeams();
	}

	public Collection<Team> getALCentralTeams() {
		return american.getDivision(DivisionType.CENTRAL).getTeams();
	}

	public Collection<Team> getALEastTeams() {
		return american.getDivision(DivisionType.EAST).getTeams();
	}

	public League getLeagueForTeam(Team team) {
		League rtnLeague = national;
		boolean done = false;
		
		Iterator<Team> i = national.getTeams().iterator();
		while (!done && i.hasNext()) {
			if (i.next().getAbbr().equals(team.getAbbr())) {
				rtnLeague = national;
				done = true;
			}
		}
		
		i = american.getTeams().iterator();
		while (!done && i.hasNext()) {
			if (i.next().getAbbr().equals(team.getAbbr())) {
				rtnLeague = american;
				done = true;
			}
		}
		
		return rtnLeague;
	}
	
	public String toString() {
		return (american + "\n" + national);
	}
}
