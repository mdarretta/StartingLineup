package org.startinglineup.data;

public class NLStartingLineupMap extends StartingLineupMap {
	
	private static NLStartingLineupMap instance = new NLStartingLineupMap();
	
	private NLStartingLineupMap() {
		super();
	}
	
	public static final NLStartingLineupMap getInstance() {
		return instance;
	}
}
