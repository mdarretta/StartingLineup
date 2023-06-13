package org.startinglineup.data;

public class ALStartingLineupMap extends StartingLineupMap {
	private static ALStartingLineupMap instance = new ALStartingLineupMap();
	
	private ALStartingLineupMap() {
		super();
	}
	
	public static final ALStartingLineupMap getInstance() {
		return instance;
	}
}