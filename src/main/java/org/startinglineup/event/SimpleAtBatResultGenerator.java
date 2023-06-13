package org.startinglineup.event;

import java.util.Random;

import org.startinglineup.component.Double;
import org.startinglineup.component.HomeRun;
import org.startinglineup.component.Out;
import org.startinglineup.component.Outcome;
import org.startinglineup.component.Single;
import org.startinglineup.component.Triple;
import org.startinglineup.component.Walk;

/**
 * The primary engine to predict an outcome for a particular batter/pitcher match up.
 * The engine uses the principle of "all things considered" - not relying too heavily
 * on advanced Sabermetrics (which are very useful in calculating results for a more
 * broad scope [e.g., WAR]), but instead on a semi-random outcome that better emulates
 * real-life game outcomes.
 * <p>
 * The key parameters to generate an outcome for a batter/pitcher matchup are (normalized
 * over a common time period, typically the previous season):
 * <p>
 * 1. The batter's on-base percentage (OBP).
 * <br>
 * 2. The pitcher's walks/hits per innings pitched (WHIP) This is used to calculate the 
 * opponent OBP for the pitcher, which is slightly different than the more conventional 
 * opponent OBP typically used.
 * <br>
 * 3. The batter's previous season's outcomes (broken out for outs, walks, singles, doubles, 
 * triples, and home runs). <i>(Note: For this release, hit by pitches (HBP) are not included.)</i>
 * <p>
 * Using these metrics, a mean percentage of outs, walks, and hits (broken out by type)
 * is calculated for the batter against the particular pitcher. These outcomes (randomly
 * added to a 1000 element array) are then selected using a randomly generated number
 * to determine the outcome.
 * <p>
 * The algorithm for generating the mean on-base percentage (MP) is:
 * <p>
 * <i>
 * MP = (b(OBP) + ((p(WHIP)*9) / (p(WHIP)*9 + 27))) / 2
 * </i>
 * <p>
 * where <i>b(OBP)</i> is the batter's on-base percentage, and <i>p(WHIP)</i> is the pitcher's walks/hits 
 * per innings pitched.
 * <p>
 * The <i>MP</i> is then used to calculate a straight-line distribution of walks, hit types, 
 * and outs across the 1000 element outcome array for each batter/pitcher matchup. The algorithm
 * used to populate the predicted number of outcomes per type (num(PO)) across the array is:
 * <p>
 * <i>
 * num(PO) = (b(num(O) * MP / b(OBP))) / (b(PA) * 1000)
 * </i>
 * <p>
 * where <i>b(num(O)</i> is the batter's number of outcomes for type <i>x</i>, <i>b(OBP)</i>
 * is the batter's on-base percentage, and <i>b(PA)</i> is the batter's number of plate appearances
 * (all gathered over a normalized period, typically the prior season).
 * <p>
 * Finally, a random number between 0 and 999 is generated to select an outcome from the 1000
 * element array.
 * <p>
 * The benefits of using this algorithm are:
 * <p>
 * 1. A better than average hitter gains an advantage over any pitcher.
 * <br>
 * 2. A better than average pitcher gains an advantage over any hitter.
 * <br>
 * 3. There is a fairly predictable OBP for the matchup (though the actual hit type leans
 * more heavily on the batter's statistics).
 * <br>
 * 4. Due to the randomness of both the array generation and outcome selection, the less
 * predictable elements such as errors, outstanding defense, poor decision making, weather-
 * related outcomes, injuries, or even streakiness (to name a few) are given their fair place 
 * over the course of both a single game and an entire season. 
 * <p>
 * The "all things considered" rule does not take into account such elements as BABIP, 
 * ball/strike ratio (in fact, balls and strikes are not even considered by this engine), 
 * sacrifice hits (flies or bunts), or (as previously mentioned) HBP. The rule assumes that
 * simplified metrics across a entire season add enough complexity to the engine while 
 * allowing for the less predictable elements mentioned above. As Bill James (the father
 * of Sabermetrics) discovered, any outcome outside of a strikeout or home run is largely
 * attributable to chance. Fine-tuning the engine too much will lead to more predictable
 * results, but less realistic results over the course of a true baseball season.
 * <p>
 * <i>Notes:</i>
 * <p>
 * Here are some additional metrics to consider for future releases:
 * <p>
 * 1. Break out statistics between right-handed and left-handed matchups.
 * <br>
 * 2. Break out statistics between home and away (or against particular ballparks).
 * <br>
 * 3. Add the <i>HBP</i> outcome into the calculations.
 * <br>
 * 4. Break out a pitchers opponent OBP against walks and hit types. (So, a pitcher who gives
 * up 0 home runs will very rarely, if ever, surrender a home run even to the most prodigious
 * power hitter.)
 * 
 * @author Mike Darretta
 *
 */
public class SimpleAtBatResultGenerator extends AtBatResultGenerator {

	/** The array of predicted outcomes */
	protected Outcome[] outcomeArray;
	
	/**
	 * Default constructor.
	 */
	public SimpleAtBatResultGenerator() {
		super();
	}
	
	/**
	 * Initializes the batter/pitcher matchup while also populating the outcome array.
	 * @param atBat The batter/pitcher matchup.
	 */
	public void initialize(AtBat atBat) {
		super.initialize(atBat);
		this.outcomeArray = new Outcome[1000];
		populateOutcomeArray();
	}

	/**
	 * Returns the mean on-base percentage (MP) calculated as follows:
	 * <p>
	 * <i>(b(OBP) + p(OBP)) / 2</i>
	 * @return The mean on-base percentage.
	 */
	protected float getMeanOnBasePercentage() {
		return (atBat.getBatter().getOnBasePercentage() + atBat.getPitcher().getOpponentOnBasePercentage()) / 2F;
	}
	
	/**
	 * Returns the predicted number of outcomes for the given outcome type, calculated as:
	 * <p>
	 * <i>num(PO) = (b(num(O) * MP / b(outcome))) / (b(PA) * 1000)</i>
	 * @param outcome The outcome to calculate against.
	 * @return The predicted number of outcomes.
	 */
	protected int getPredictedNumOutcomes(Outcome outcome) {
		return (int) ((atBat.getBatter().getNumOutcomes(outcome) * getMeanOnBasePercentage() / 
				atBat.getBatter().getOnBasePercentage()) / 
				atBat.getBatter().getPlateAppearances() * 1000F);
	}

	/**
	 * Populates the outcome array for the predicted number of walks and hit types.
	 */
	private void populateOutcomeArray() {
				
		populateOutcomeArrayForOutcomeType(Walk.getInstance(), 
				getPredictedNumOutcomes(Walk.getInstance()));
		populateOutcomeArrayForOutcomeType(Single.getInstance(), 
				getPredictedNumOutcomes(Single.getInstance()));
		populateOutcomeArrayForOutcomeType(Double.getInstance(), 
				getPredictedNumOutcomes(Double.getInstance()));
		populateOutcomeArrayForOutcomeType(Triple.getInstance(), 
				getPredictedNumOutcomes(Triple.getInstance()));
		populateOutcomeArrayForOutcomeType(HomeRun.getInstance(), 
				getPredictedNumOutcomes(HomeRun.getInstance()));
		
		for (int x = 0; x < outcomeArray.length; x++) {
			if (outcomeArray[x] == null) {
				outcomeArray[x] = Out.getInstance();
			}
		}
	}

	/**
	 * Calculates the outcome array for the particular outcome type.
	 * @param outcome The outcome type.
	 * @param numOutcomes The number of outcomes to populate for the outcome type.
	 */
	private void populateOutcomeArrayForOutcomeType(Outcome outcome, int numOutcomes) {
		for (int x = 0; x < numOutcomes; x++) {
			boolean done = false;
			while (!done) {
				int index = getRandom();
				if (outcomeArray[index] == null) {
					outcomeArray[index] = outcome;
					done = true;
				}
			}
		}
	}
	
	/**
	 * Returns a random generated number from 0-999.
	 * @return A random generated number from 0-999.
	 */
	private int getRandom() {
		return new Random().nextInt(outcomeArray.length);
	}

	/**
	 * Returns a randomly selected outcome from the outcome array.
	 * @return A randomly selected outcome.
	 */
	public Outcome getOutcome() {
		return outcomeArray[getRandom()];
	}
}
