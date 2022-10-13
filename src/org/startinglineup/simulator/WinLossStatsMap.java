package org.startinglineup.simulator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class WinLossStatsMap {
	
	private HashMap<org.startinglineup.component.Team, WinLossStats> map;
	
	public WinLossStatsMap() {
		this.map = new HashMap<org.startinglineup.component.Team, WinLossStats>();
	}
	
	public void addStatsForTeam(org.startinglineup.component.Team team, WinLossStats stats) {
		WinLossStats currentStats = map.get(team);
		currentStats.combineStats(stats);
		map.replace(currentStats.getTeam(), currentStats);
	}

	public void addStats(Collection<WinLossStats> statsCollection) {
		Iterator<WinLossStats> i = statsCollection.iterator();
		while (i.hasNext()) {
			WinLossStats stats = i.next();
			addStatsForTeam(stats.getTeam(), stats);
		}
	}
}
