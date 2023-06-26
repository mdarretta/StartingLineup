package org.startinglineup.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.startinglineup.component.AdvancedMetric;
import org.startinglineup.component.Player;
import org.startinglineup.data.NLStartingLineupMap;
import org.startinglineup.data.ALStartingLineupMap;
import org.startinglineup.data.StartingRotationMap;
import org.startinglineup.league.Division;
import org.startinglineup.league.League;
import org.startinglineup.league.MajorLeagues;
import org.startinglineup.utils.Formatter;

/**
 * Generates a final standings based upon a static list of advanced metrics.
 * This class does not utilize any of the data generated during the simulation.
 * Instead, this static outcome can be used to compare simulated results against
 * results calculated via advanced metrics such as WAR. This class is called for
 * zero or more sets of selected advanced metrics.
 * 
 * @author Mike Darretta
 */
public class StandingsByAdvancedMetrics {
	
	/**
	 * The advanced metric type to run this model against.
	 */
	private AdvancedMetric.MetricType metric;

	public StandingsByAdvancedMetrics(AdvancedMetric.MetricType metric) {
		super();
		this.metric = metric;
	}

	private AdvancedMetricTeamStats getStatsForTeam(org.startinglineup.component.Team team) {
		float metric = getTotalMetricForPlayers(NLStartingLineupMap.getInstance().get(team))
				+ getTotalMetricForPlayers(ALStartingLineupMap.getInstance().get(team))
				+ getTotalMetricForPlayers(StartingRotationMap.getInstance().get(team));

		return new AdvancedMetricTeamStats(team, metric);
	}

	public Collection<AdvancedMetricTeamStats> getStatsForTeams(Collection<org.startinglineup.component.Team> teams) {

		Iterator<org.startinglineup.component.Team> i = teams.iterator();
		Collection<AdvancedMetricTeamStats> metrics = new ArrayList<AdvancedMetricTeamStats>();
		while (i.hasNext()) {
			metrics.add(getStatsForTeam(i.next()));
		}

		return metrics;
	}

	public Collection<AdvancedMetricTeamStats> getStatsForDivision(League.LeagueType league,
			Division.DivisionType division) {

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

	public Collection<AdvancedMetricTeamStats> getStatsForLeague(League.LeagueType league) {

		Collection<AdvancedMetricTeamStats> stats = new ArrayList<AdvancedMetricTeamStats>();
		stats.addAll(getStatsForDivision(league, Division.DivisionType.EAST));
		stats.addAll(getStatsForDivision(league, Division.DivisionType.CENTRAL));
		stats.addAll(getStatsForDivision(league, Division.DivisionType.WEST));

		return stats;
	}

	private float getTotalMetricForPlayers(Collection<Player> players) {
		Player player = null;
		float total = 0.0F;
		Iterator<Player> i = players.iterator();

		while (i.hasNext()) {
			player = i.next();
			if (player.getAdvancedMetrics() != null) {
				AdvancedMetric metric = player.getAdvancedMetrics().getMetric(this.metric);
				if (metric != null) {
					total += metric.getAdvancedMetric();
				}
			}
		}
		return total;
	}

	private String getStatsStrForTeams(Collection<org.startinglineup.component.Team> teams) {

		String rtnStr = "";

		
		Collection<AdvancedMetricTeamStats> stats = getStatsForTeams(teams);
		List<AdvancedMetricTeamStats> sortedList = new ArrayList<AdvancedMetricTeamStats>();
		sortedList.addAll(stats); 
		Collections.sort(sortedList);
		
		Iterator<AdvancedMetricTeamStats> i = sortedList.iterator();
		while (i.hasNext()) {
			AdvancedMetricTeamStats teamStats = i.next();
			rtnStr += Formatter.getPaddedString(teamStats.getTeam().getAbbr() + " " + (int) teamStats.getMetric(), 4, true);
			rtnStr += "\n";
		}

		return rtnStr;
	}

	private String getStatsStrForLeague(League.LeagueType league,
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

	private String getStatsStrForDivision(Division.DivisionType division,
			Collection<org.startinglineup.component.Team> teams) {

		String rtnString = (division.getName() + "\n");
		rtnString += (getStatsStrForTeams(teams) + "\n");

		return rtnString;
	}

	public String toString() {

		Collection<org.startinglineup.component.Team> nlWestTeams = MajorLeagues.getInstance().getNLWestTeams();
		Collection<org.startinglineup.component.Team> nlCentralTeams = MajorLeagues.getInstance().getNLCentralTeams();
		Collection<org.startinglineup.component.Team> nlEastTeams = MajorLeagues.getInstance().getNLEastTeams();

		Collection<org.startinglineup.component.Team> alWestTeams = MajorLeagues.getInstance().getALWestTeams();
		Collection<org.startinglineup.component.Team> alCentralTeams = MajorLeagues.getInstance().getALCentralTeams();
		Collection<org.startinglineup.component.Team> alEastTeams = MajorLeagues.getInstance().getALEastTeams();

		return "--------------------------\n" + "Standings based on " + metric + "\n\n" + 
		    getStatsStrForLeague(League.LeagueType.AMERICAN, alEastTeams, alCentralTeams, alWestTeams)
				+ getStatsStrForLeague(League.LeagueType.NATIONAL, nlEastTeams, nlCentralTeams, nlWestTeams);

	}

	private class AdvancedMetricTeamStats implements Comparable<AdvancedMetricTeamStats> {

		private org.startinglineup.component.Team team;
		private float metric;

		private AdvancedMetricTeamStats(org.startinglineup.component.Team team, float metric) {
			this.team = team;
			this.metric = metric;
		}

		public int compareTo(AdvancedMetricTeamStats stats) {
			float delta = stats.getMetric() - metric;
			int rtnInt = 0;
			if (delta < 0) {
				rtnInt = -1;
			} else if (delta > 0) {
				rtnInt = 1;
			}
			
			return rtnInt;
		}

		private float getMetric() {
			return metric;
		}
		
		private org.startinglineup.component.Team getTeam() {
			return team;
		}
		
		public String toString() {
			return team + " " + metric;
		}
	}

}
