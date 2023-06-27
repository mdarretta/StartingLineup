package org.startinglineup.simulator;

import org.startinglineup.Properties;
import org.startinglineup.utils.Formatter;

public class WinLossStats implements Comparable<WinLossStats> {
	
	private org.startinglineup.component.Team team;
	private int wins;
	private int losses;
	private int homeWins;
	private int homeLosses;
	private int visitorWins;
	private int visitorLosses;
	
	public WinLossStats(org.startinglineup.component.Team team) {
		super();
		this.team = team;
		this.wins = 0;
		this.losses = 0;
		this.homeWins = 0;
		this.homeLosses = 0;
		this.visitorWins = 0;
		this.visitorLosses = 0;
	}
	
	public void incrementStats(GameStats gameStats) {
		
		boolean isWinningTeam = false;
		
		if (team.equals(gameStats.getWinningTeam())) {
			wins++;
			isWinningTeam = true;
		} else if (team.equals(gameStats.getLosingTeam())) {
			losses++;
		}
		
		if (gameStats.getHome().equals(team)) {
			if (isWinningTeam) {
				homeWins++;
			} else {
				homeLosses++;
			}
		} else if (gameStats.getVisitor().equals(team)) {
			if (isWinningTeam) {
				visitorWins++;
			} else {
				visitorLosses++;
			}
		}
	}
	
	public void combineStats(WinLossStats stats) {
		if (stats.getTeam().equals(this.getTeam())) {
			// throw exception
		}
		
		this.wins += stats.getWins();
		this.losses += stats.getLosses();
		this.homeWins += stats.getHomeWins();
		this.homeLosses += stats.getHomeLosses();
		this.visitorWins += stats.getVisitorWins();
		this.visitorLosses += stats.getVisitorLosses();
	}
	
	/**
	 * Normalizes stats against a divisor. This is useful when a season is run several times
	 * without resetting the <code>Standings</code>, allowing the client to average the
	 * season results for these stats against the number of season iterations.
	 * @param divisor The divisor used to normalize the results.
	 */
	public void normalize(int divisor) {
		
		// Get the number of games played per season, calculate the number of homes games, 
		// then derive the number of visitor games to ensure no rounding issues.
		int startGame = Integer.valueOf(Properties.getInstance().get(Properties.STARTING_GAME_TO_MODEL_PROP));
		int endGame = Integer.valueOf(Properties.getInstance().get(Properties.ENDING_GAME_TO_MODEL_PROP));
		int totalGamesPerSeason = endGame - startGame + 1;
		
		// This is an estimate. There needs to be more intelligent calculations
		// based upon the actual sub-schedule chosen
		int homeGamesPerSeason = Math.round((int) totalGamesPerSeason/2);
		
		// To reduce error on rounding, only calculate the wins and homeWins attributes using the divisor,
		// then derive the losses from those rounded values.
		this.wins = Math.round((float) wins/ (float) divisor);
		this.homeWins = Math.round((float) homeWins/ (float) divisor);
		
		this.losses = totalGamesPerSeason - this.wins;
		this.homeLosses = homeGamesPerSeason - this.homeWins;
		this.visitorWins = this.wins - this.homeWins;
		this.visitorLosses = this.losses - this.homeLosses;
	}
	
	/**
	 * @return the team
	 */
	public org.startinglineup.component.Team getTeam() {
		return team;
	}

	/**
	 * @return the wins
	 */
	int getWins() {
		return wins;
	}

	/**
	 * @return the losses
	 */
	public int getLosses() {
		return losses;
	}

	/**
	 * @return the homeWins
	 */
	public int getHomeWins() {
		return homeWins;
	}

	/**
	 * @return the homeLosses
	 */
	public int getHomeLosses() {
		return homeLosses;
	}

	/**
	 * @return the visitorWins
	 */
	public int getVisitorWins() {
		return visitorWins;
	}

	/**
	 * @return the visitorLosses
	 */
	public int getVisitorLosses() {
		return visitorLosses;
	}
	
	public int compareTo(WinLossStats stats) {
		
		int rtnInt = 0;
		if (getWins() == stats.getWins()) {
			rtnInt = stats.getLosses() - getLosses();
		} else {
			rtnInt = stats.getWins() - getWins();
		}
		
		return rtnInt;
	}
	
	public String toString() {
		return Formatter.format(getWins(), 0, 0, true) +
				Formatter.format(getLosses(), 0, 1, true) +
				Formatter.format(getHomeWins(), 0, 1, true) +
				Formatter.format(getHomeLosses(), 0, 1, true) +
				Formatter.format(getVisitorWins(), 0, 1, true) +
				Formatter.format(getVisitorLosses(), 0, 1, true);
	}
}
