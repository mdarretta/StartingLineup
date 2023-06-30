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
 * opponent on base percentage (OBP) for the pitcher, which is slightly different than 
 * the more conventional opponent OBP typically used.
 * <br>
 * 3. The batter's previous season's outcomes (broken out for outs, walks, singles, doubles, 
 * triples, and home runs). <i>(Note: For this release, hit by pitches (HBP) are not included.)</i>
 * <p>
 * Using these metrics, a mean percentage of outs, walks, and hits (broken out by type)
 * is calculated for the batter against the particular pitcher. These outcomes (randomly
 * added to a 1000 element array) are then selected using a randomly generated number
 * to determine the outcome. <i>Note: The outs are added last to the array to replace all
 * existing <code>null</code> outcomes</i>.
 * <p>
 * The algorithm for generating the mean on-base percentage (MOBP) is:
 * <p>
 * <i>
 * MOBP = (batter(OBP) + ( (pitcher(WHIP)*9) / (pitcher(WHIP)*9 + 27)) ) / 2
 * </i>
 * <p>
 * In short, the mean on base percentage is mean between the batter's OBP + the pitcher's calculated
 * walk/hits for an entire regulation game, divided by 2. For instance:
 * <p>
 * If a batter's OBP is .400, and a picher gives up 12 hits/walks on average for an entire game, the MOBP is:
 * <p>
 * <i>(.400 + 12/27) / 2 = .422</i>
 * <p>
 * That is, there is a 42.2% chance that the at bat will results in something other than an out for this matchup.
 * <p>
 * The <i>MOBP</i> is then used to calculate a distribution of walks, hit types, and outs 
 * across the 1000 element outcome array for each batter/pitcher matchup. The algorithm
 * used to populate the predicted number of predicted outcomes per walk/hit type (pOUT) across 
 * the array is:
 * <p>
 * <i>
 * pOUT = (batter((outcome per type) * MOBP / batter(OBP))) / (batter(plate appearance) * 1000)
 * </i>
 * <p>
 * In short, the predicted number of outcomes is calculated using the batter's normal outcome per type
 * (single, double, etc) times the MOBP, divided by the batter's OBP - then divided by the batter's
 * normalized number of plate appearances.
 * <p>
 * If a batter averages 20 home runs in a season, his OBP is .400, he has 600 recorded plate appearances
 * in a season and the predicted MOBP for the matchup is .422 (see prior calculation), the pOUT is:
 * <p>
 * <i>(20 * .422 / .400) / 600 * 1000) = 35.16 (rounded down to 35 home runs per 1000 matchups)
 * <p>
 * Once the array is populated for each outcome type, a random number between 0 and 999 is generated
 * for the particular at bat to select an outcome from the 1000 element array. If the selected element is
 * anything except an out, the simulation manages advancing any existing runner plus the batter, and
 * adding runs to the score if needed. Otherwise, an out will be recorded.
 * <p>
 * The benefits of using this algorithm are:
 * <p>
 * 1. A better than average hitter gains an advantage over any pitcher.
 * <br>
 * 2. A better than average pitcher gains an advantage over any hitter.
 * <br>
 * 3. Due to the randomness of both the array generation and outcome selection, the less
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
 * attributable to chance. (This has been refuted to some degree with new metrics such as
 * launch angle, exit velocity, etc. But even these metrics rely partially on chance.)
 * Extensions of this class can utilize such advanced metrics for more fine-grained predictions.
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
