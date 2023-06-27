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
import org.startinglineup.utils.ResultsFormatter;

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
	
	private ResultsFormatter.StringMap getMapForTeams(
			    Division.DivisionType division, 
			    Collection<org.startinglineup.component.Team> teams) {
		
		Collection<AdvancedMetricTeamStats> stats = getStatsForTeams(teams);
		List<AdvancedMetricTeamStats> sortedList = new ArrayList<AdvancedMetricTeamStats>();
		sortedList.addAll(stats); 
		Collections.sort(sortedList);
		
		ResultsFormatter.StringMap map = new ResultsFormatter().createMap();
		Iterator<AdvancedMetricTeamStats> i = sortedList.iterator();
		int idx=0;
		map.put(idx++, Formatter.format(division.toString(), 1, false));
		while (i.hasNext()) {
			AdvancedMetricTeamStats teamStats = i.next();
			map.put(idx++, Formatter.format(teamStats.getTeam().getAbbr(), 1, false)
					+ Formatter.format((int) teamStats.getMetric(), 0, false));
		}
		
		return map;
	}

	public String toString() {
		
		String rtnStr = "\nStandings by " + metric.getAbbr() + "\n\n";

		Collection<org.startinglineup.component.Team> nlWestTeams = MajorLeagues.getInstance().getNLWestTeams();
		Collection<org.startinglineup.component.Team> nlCentralTeams = MajorLeagues.getInstance().getNLCentralTeams();
		Collection<org.startinglineup.component.Team> nlEastTeams = MajorLeagues.getInstance().getNLEastTeams();

		Collection<org.startinglineup.component.Team> alWestTeams = MajorLeagues.getInstance().getALWestTeams();
		Collection<org.startinglineup.component.Team> alCentralTeams = MajorLeagues.getInstance().getALCentralTeams();
		Collection<org.startinglineup.component.Team> alEastTeams = MajorLeagues.getInstance().getALEastTeams();

		rtnStr += League.LeagueType.AMERICAN;
		rtnStr += "\n--------------\n";
		ResultsFormatter formatter = new ResultsFormatter();
		formatter.addResults(getMapForTeams(Division.DivisionType.EAST, alEastTeams));
		formatter.addResults(getMapForTeams(Division.DivisionType.CENTRAL, alCentralTeams));
		formatter.addResults(getMapForTeams(Division.DivisionType.WEST, alWestTeams));
		
		rtnStr += formatter.formatResultsAsColumnarStr(8);
				
		rtnStr += League.LeagueType.NATIONAL;
		rtnStr += "\n--------------\n";
		formatter = new ResultsFormatter();
		formatter.addResults(getMapForTeams(Division.DivisionType.EAST, nlEastTeams));
		formatter.addResults(getMapForTeams(Division.DivisionType.CENTRAL, nlCentralTeams));
		formatter.addResults(getMapForTeams(Division.DivisionType.WEST, nlWestTeams));
		
		rtnStr += formatter.formatResultsAsColumnarStr(8);
		rtnStr += "\n";
		
		return rtnStr;
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
