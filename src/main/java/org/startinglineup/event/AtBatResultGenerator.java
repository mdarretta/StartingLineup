package org.startinglineup.event;

import org.startinglineup.component.Outcome;

/**
 * An abstract class used to generate a result for a particular batter/pitcher
 * matchup. This is the lowest level engine for the season's simulation.
 * 
 * @author Mike Darretta
 *
 */
public abstract class AtBatResultGenerator {

	/** The batter/pitcher matchup */
	protected AtBat atBat;
	
	/**
	 * Constructor for a particular batter/pitcher matchup.
	 */
	protected AtBatResultGenerator() {}
	
	/**
	 * Initializes the batter/pitcher matchup
	 * 
	 * @param atBat The matchup.) {
	 */
	public void initialize(AtBat atBat) {
		this.atBat = atBat;
	}

	/**
	 * Returns the batter/pitcher matchup outcome.
	 * @return The batter/pitcher matchup outcome.
	 */
	public abstract Outcome getOutcome();
}
