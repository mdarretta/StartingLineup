package org.startinglineup.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.startinglineup.component.Batter;
import org.startinglineup.component.Player;
import org.startinglineup.component.Team;

public abstract class StartingLineupMap extends StartingPlayerMap {

	protected StartingLineupMap() {
		super();
	}

	public void addBatter(Team team, Batter batter) {
		map.get(team.getAbbr()).add(batter);
	}

	public Collection<Batter> getLineup(Team team) {
		Collection<Batter> batters = new ArrayList<Batter>();
		Iterator<Player> i = map.get(team.getAbbr()).iterator();
		while (i.hasNext()) {
			batters.add((Batter) i.next());
		}
		
		return batters;
	}
}
