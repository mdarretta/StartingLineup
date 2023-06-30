package org.startinglineup.event;

import org.startinglineup.component.Outcome;

/**
 * A "stubbed" engine to predict an outcome for a particular batter/pitcher
 * match up.
 * 
 * This class extends the <code>SimpleAtBatResultsGenerator</code> to account
 * for the propensity for batters to generate particular walks or hits versus a
 * pitcher's propensity to give up particular walks or hits.
 * 
 * This stubbed engine does not use real data at this time, but only provides
 * randomly generated data to account for pitcher propensity. A future
 * implementation needs to utilize real data.
 * 
 * @author Mike Darretta
 */

public class AdvancedAtBatResultGenerator extends SimpleAtBatResultGenerator {

	/**
	 * Default constructor.
	 */
	public AdvancedAtBatResultGenerator() {
		super();
	}

	/**
	 * Returns the predicted number of outcomes for the given outcome type.
	 * 
	 * @param outcome The outcome to calculate against.
	 * @return The predicted number of outcomes.
	 */
	protected int getPredictedNumOutcomes(Outcome outcome) {
		float batterOutcomes = (float) ((1000F / atBat.getBatter().getPlateAppearances())
				* atBat.getBatter().getNumOutcomes(outcome));
		float pitcherOutcomes = (float) ((atBat.getPitcher().getNumOutcomes(outcome) * 1000F)
				/ (atBat.getPitcher().getInningsPitched() + (atBat.getPitcher().getInningsPitched() * 3)));
		return (int) ((batterOutcomes + pitcherOutcomes) / 2);
	}
}