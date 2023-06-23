package org.startinglineup.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.startinglineup.component.Player;
import org.startinglineup.component.Team;
import org.startinglineup.component.Teams;

public abstract class StartingPlayerMap {

	protected HashMap<String, Collection<Player>> map;

	protected StartingPlayerMap() {
		super();
		map = new HashMap<String, Collection<Player>>();
		populateKeys();
	}

	private void populateKeys() {
		// Ensure all teams are included in all maps due to interleague play
		Collection<Team> teams = Teams.getInstance().getTeams();
		Iterator<Team> i = teams.iterator();
		while (i.hasNext()) {
			map.put(i.next().getAbbr(), new ArrayList<Player>());
		}
	}

	public void add(Team team, Player player) {
		map.get(team.getAbbr()).add(player);
	}

	public Collection<Player> get(Team team) {
		return map.get(team.getAbbr());
	}
	
	public Set<String> getTeamAbbrs() {
		return map.keySet();
	}
	
	public int getNumPlayers() {
		Set<String> keys = getTeamAbbrs();
		Iterator<String> i = keys.iterator();
		int rtnInt = 0;
		while (i.hasNext()) {
			String key = i.next();
			rtnInt += map.get(key).size();
		}
		
		return rtnInt;
	}

	public String toString() {
		String rtnString = "";
		Set<String> keys = map.keySet();
		Iterator<String> i = keys.iterator();
		while (i.hasNext()) {
			String key = i.next();
			rtnString += key + "\n";
			Collection<Player> players = map.get(key);
			Iterator<Player> i2 = players.iterator();
			while (i2.hasNext()) {
				rtnString += i2.next() + "\n";
			}
		}

		return rtnString;
	}
}
