package org.startinglineup.data;

public class PitcherMap extends PlayerMap {
	
	private static final PitcherMap instance = new PitcherMap();
	
	private PitcherMap() {
		super();
	}
	
	public static PitcherMap getInstance() {
		return instance;
	}
}
