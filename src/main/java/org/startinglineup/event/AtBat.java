package org.startinglineup.event;

import org.startinglineup.component.Batter;
import org.startinglineup.component.Pitcher;

/**
 * Encapsulates a particular at bat for a batter/pitcher matchup. This is the
 * key component for executing the simulation.
 */
public class AtBat {

	/**
	 * The batter.
	 */
	private Batter batter;

	/**
	 * The pitcher.
	 */
	private Pitcher pitcher;

	/**
	 * Default constructor.
	 */
	public AtBat() {
		super();
	}

	/**
	 * The at bat encapsulation.
	 * 
	 * @param batter  The batter.
	 * @param pitcher THe pitcher.
	 */
	public AtBat(Batter batter, Pitcher pitcher) {
		this.batter = batter;
		this.pitcher = pitcher;
	}

	/**
	 * Returns the batter.
	 * 
	 * @return The batter.
	 */
	public Batter getBatter() {
		return batter;
	}

	/**
	 * Returns the pitcher.
	 * 
	 * @return The pitcher.
	 */
	public Pitcher getPitcher() {
		return pitcher;
	}
}
