package org.startinglineup.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.startinglineup.league.Division;
import org.startinglineup.league.League;
import org.startinglineup.league.MajorLeagues;
import org.startinglineup.utils.Formatter;

public class Standings {
	
	private HashMap<org.startinglineup.component.Team, WinLossStats> statsMap;
	private static final Standings instance = new Standings();
		
	private Standings() {
		super();
		reset();
	}
	
	public static Standings getInstance() {
		return instance;
	}
	
	public void reset() {
		this.statsMap = new HashMap<org.startinglineup.component.Team, WinLossStats>();
	}
	
	public void addStats(GameStats gameStats) {
		org.startinglineup.component.Team home = gameStats.getHome();
		org.startinglineup.component.Team visitor = gameStats.getVisitor();
		
		WinLossStats homeStats = getWinLossStats(home);
		WinLossStats visitorStats = getWinLossStats(visitor);

		if (homeStats == null) {
			homeStats = new WinLossStats(home);
		}
		
		if (visitorStats == null) {
			visitorStats = new WinLossStats(visitor);
		}
		
		homeStats.incrementStats(gameStats);
		visitorStats.incrementStats(gameStats);
				
		statsMap.put(home, homeStats);
		statsMap.put(visitor, visitorStats);
	}
	
	public void addStats(Collection<GameStats> statsCollection) {
		Iterator<GameStats> i = statsCollection.iterator();
		while (i.hasNext()) {
			addStats(i.next());
		}
	}
	
	public WinLossStats getWinLossStats(org.startinglineup.component.Team team) {
		org.startinglineup.component.Team teamKey = null;
		java.util.Set<?> set = statsMap.keySet();
		Iterator<?> i = set.iterator();
		while (i.hasNext()) {
			org.startinglineup.component.Team team2 = (org.startinglineup.component.Team) i.next();
			if (team.getAbbr().equals(team2.getAbbr())) {
				teamKey = team2;
				break;
			}
		}
	
		return statsMap.get(teamKey);
	}
	
	public HashMap<org.startinglineup.component.Team, WinLossStats> getWinLossStatsMap() {
		return statsMap;
	}
	
	public Collection<WinLossStats> getStatsForTeams(Collection<org.startinglineup.component.Team> teams) {
		Iterator<org.startinglineup.component.Team> i = teams.iterator();
		Collection<WinLossStats> stats = new ArrayList<WinLossStats>();
		
		while (i.hasNext()) {
			stats.add(getWinLossStats(i.next()));
		}
		
		return stats;
	}
	
	/**
	 * Normalizes stats against a divisor. This is useful when a season is run several times
	 * without resetting the <code>Standings</code>, allowing the client to average the
	 * season results against the number of season iterations.
	 * @param divisor The divisor used to normalize the results.
	 */
	public void normalizeStats(int divisor) {
		Iterator<org.startinglineup.component.Team> i = statsMap.keySet().iterator();
		org.startinglineup.component.Team team = null;
		WinLossStats stats = null;
		
		while (i.hasNext()) {
			team = i.next();
			stats = getWinLossStats(team);
			stats.normalize(divisor);
			statsMap.replace(team, stats);
		}
		
	}
	
	public Collection<WinLossStats> getStatsForDivision(
			    League.LeagueType league, Division.DivisionType division) {
		
		Collection<org.startinglineup.component.Team> teams = null;
		
		if (league.equals(League.LeagueType.AMERICAN)) {
			if (division.equals(Division.DivisionType.EAST)) {
				teams = MajorLeagues.getInstance().getALEastTeams();
			} else if (division.equals(Division.DivisionType.CENTRAL)) {
				teams = MajorLeagues.getInstance().getALCentralTeams();
			} else {
				teams = MajorLeagues.getInstance().getALWestTeams();
			}
		} else {
			if (division.equals(Division.DivisionType.EAST)) {
				teams = MajorLeagues.getInstance().getNLEastTeams();
			} else if (division.equals(Division.DivisionType.CENTRAL)) {
				teams = MajorLeagues.getInstance().getNLCentralTeams();
			} else {
				teams = MajorLeagues.getInstance().getNLWestTeams();
			}
		}
		
		return getStatsForTeams(teams);
	}
	
	public Collection<WinLossStats> getStatsForLeague(League.LeagueType league) {
		
		Collection<WinLossStats> stats = new ArrayList<WinLossStats>(); 
		stats.addAll(getStatsForDivision(league, Division.DivisionType.EAST));
		stats.addAll(getStatsForDivision(league, Division.DivisionType.CENTRAL));
		stats.addAll(getStatsForDivision(league, Division.DivisionType.WEST));
		
		return stats;
	}
	
	private String getStatsStrForTeams(Collection<org.startinglineup.component.Team> teams) {

		String rtnStr = "";

		Collection<WinLossStats> statsCollection = getStatsForTeams(teams);
		List<WinLossStats> sortedList = new ArrayList<WinLossStats>();
		sortedList.addAll(statsCollection);
		Collections.sort(sortedList);
		
		WinLossStats stats = null;
		Iterator<WinLossStats> i = sortedList.iterator();
		while (i.hasNext()) {
			stats = i.next();
			rtnStr += Formatter.getPaddedString(stats.getTeam().getAbbr(),4,true);
			rtnStr += stats + "\n";
		}
		
		// Test code
		
		/*
		 * float metrics = 0.0F; java.util.Iterator<org.startinglineup.component.Team>
		 * i2 = teams.iterator(); while (i2.hasNext()) {
		 * org.startinglineup.component.Team team = i2.next();
		 * java.util.List<org.startinglineup.component.Batter> batters =
		 * team.getBatters(); Iterator<org.startinglineup.component.Batter> i3 =
		 * batters.iterator(); while (i3.hasNext()) { metrics +=
		 * i3.next().getAdvancedMetrics().getMetric(org.startinglineup.component.
		 * AdvancedMetric.WAR) .getAdvancedMetric(); } } System.out.println("TOTAL WAR="
		 * + metrics);
		 */		 		
		return rtnStr;
	}
	
	private String getStatsStrForLeague(
			League.LeagueType league,
			Collection<org.startinglineup.component.Team> eastTeams,
			Collection<org.startinglineup.component.Team> centralTeams,
			Collection<org.startinglineup.component.Team> westTeams) {
		
		String rtnString = league.getName() + "\n";
		rtnString += "--------------------------\n\n";
		rtnString += getStatsStrForDivision(Division.DivisionType.EAST, eastTeams);
		rtnString += ("\n");
		rtnString += getStatsStrForDivision(Division.DivisionType.CENTRAL, centralTeams);
		rtnString += ("\n");
		rtnString += getStatsStrForDivision(Division.DivisionType.WEST, westTeams);
		
		return rtnString;
	}
	
	private String getStatsStrForDivision(
			Division.DivisionType division, Collection<org.startinglineup.component.Team> teams) {
		
		String rtnString = (division.getName() + "\n");
		rtnString += Formatter.getPaddedString("W",8,true) + 
				     Formatter.getPaddedString("L",4,true) +
				     Formatter.getPaddedString("Home",8,true) + 
				     Formatter.getPaddedString("Away",8,true) + "\n";
		rtnString += (getStatsStrForTeams(teams) + "\n");		
		
		return rtnString;
	}
	
	public String toString() {
				
		Collection<org.startinglineup.component.Team> nlWestTeams = 
				MajorLeagues.getInstance().getNLWestTeams();
		Collection<org.startinglineup.component.Team> nlCentralTeams = 
				MajorLeagues.getInstance().getNLCentralTeams();
		Collection<org.startinglineup.component.Team> nlEastTeams = 
				MajorLeagues.getInstance().getNLEastTeams();
		
		Collection<org.startinglineup.component.Team> alWestTeams = 
				MajorLeagues.getInstance().getALWestTeams();
		Collection<org.startinglineup.component.Team> alCentralTeams = 
				MajorLeagues.getInstance().getALCentralTeams();
		Collection<org.startinglineup.component.Team> alEastTeams = 
				MajorLeagues.getInstance().getALEastTeams();
				
		return 
		    getStatsStrForLeague(
				League.LeagueType.AMERICAN, alEastTeams, alCentralTeams, alWestTeams) +
		    getStatsStrForLeague(
				League.LeagueType.NATIONAL, nlEastTeams, nlCentralTeams, nlWestTeams);
	}
}
