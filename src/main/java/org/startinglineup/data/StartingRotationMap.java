package org.startinglineup.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.startinglineup.component.Pitcher;
import org.startinglineup.component.Player;
import org.startinglineup.component.Team;

public class StartingRotationMap extends StartingPlayerMap {
	
	private static StartingRotationMap instance = new StartingRotationMap();
	private StartingRotationTracker tracker;

	private StartingRotationMap() {
		super();
		tracker = new StartingRotationTracker();
	}
	
	public static StartingRotationMap getInstance() {
		return instance;
	}
	
	public void addPitcher(Team team, Pitcher pitcher) {
		map.get(team.getAbbr()).add(pitcher);
	}

    public Pitcher getNext(Team team) {
    	Collection<Player> players = map.get(team.getAbbr());
    	int index = tracker.getNext(team.getAbbr(), players.size()-1);
    	Object[] playerArray = players.toArray();
    	return (Pitcher) playerArray[index];
    }
	
	private class StartingRotationTracker {
		
		private HashMap<String, Integer> ptrMap;
		
		private StartingRotationTracker() {
			ptrMap = new HashMap<String, Integer>();
			Iterator<String> i = map.keySet().iterator();
			while (i.hasNext()) {
				ptrMap.put(i.next(), Integer.valueOf(0));
			}
		}
		
		private int getNext(String abbr, int maxValue) {
			int next = ptrMap.get(abbr).intValue();
			if (next == maxValue) {
				ptrMap.replace(abbr, Integer.valueOf(0));
			} else {
				ptrMap.replace(abbr, Integer.valueOf(next+1));
			}
			
			return next;
		}
	}
}
