package org.startinglineup.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Schedule {

	private static final Schedule instance = new Schedule();

	private Collection<Game> games;

	private Schedule() {
		super();
		games = new ArrayList<Game>();
	}

	public static Schedule getInstance() {
		return instance;
	}

	public void clear() {
		games.clear();
	}

	public void addGame(Game game) {
		this.games.add(game);
	}

	public void addGames(Collection<Game> games) {
		this.games.addAll(games);
	}

	public void addSeries(Series series) {
		this.games.addAll(series.getGames());
	}

	public void addSeries(Collection<Series> seriesCollection) {
		Iterator<Series> i = seriesCollection.iterator();
		while (i.hasNext()) {
			addSeries(i.next());
		}
	}

	public Collection<Game> getGames() {
		return games;
	}

	public Collection<GameStats> play() {
		Collection<GameStats> gameStats = new ArrayList<GameStats>();
		Iterator<Game> i = games.iterator();
		while (i.hasNext()) {
			GameStats stats = i.next().play();
			gameStats.add(stats);
		}

		return gameStats;
	}

	public String toString() {
		String rtnString = "";
		Iterator<Game> i = games.iterator();
		while (i.hasNext()) {
			rtnString += i.next();
			rtnString += "\n";
		}
		return rtnString;
	}
}
