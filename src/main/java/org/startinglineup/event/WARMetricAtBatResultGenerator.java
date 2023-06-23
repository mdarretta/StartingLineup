package org.startinglineup.event;

import org.startinglineup.component.AdvancedMetric;
import org.startinglineup.component.WARMetric;
import org.startinglineup.component.Outcome;

/**
 * This class extends the <code>SimpleAtBatResultsGenerator</code> to calculate the
 * predicted outcomes of  particular at bat to include the "Wins Above Replacement"
 * (WAR) advanced metric.
 *
 * WAR indicatest the amount of wins a player is predicted to generate against the
 * average replacement player of the same position. For instance, a WAR of 5.1 for a catcher
 * means that player generates 5.1 more wins than the average MLB catcher. A similar
 * measure is used for pitchers. A negative WAR indicates a player is less effective 
 * than the average player at his position.
 *
 * While WAR is not normally considered on an at-bat basis, this class uses WAR
 * as an additional metric to determine an outcome based upon both the batter's and
 * pitcher's WAR.
 *
 * @author Mike Darretta
 */
 
 public class WARMetricAtBatResultGenerator extends SimpleAtBatResultGenerator {
	 
	public WARMetricAtBatResultGenerator() {
		super();
	}

        /**
         * Returns the "Wins Above Replacement" (WAR) delta of an at bat calculated as follows:
         * <p>
         * <i>(b(WAR) - p(WAR))/i>
         * <p>
         * A positive delta is in the batter's favor. A negative delta is in the pitcher's favor.
         * Since WAR is a very minor contributor to a particular at bat, the delta in WAR between
         * a batter and pitcher will only slightly against the predicted outcome for the at bat.
         * For instance, if the batter contributes a +2 WAR delta against a pitcher, the metric 
         * simply adds 2 additional outcomes in the batter's favor.
         * <p>
         * The delta is cast to an integer value for calculation purposes.
         *
         * @return The delta "Wins Above Replacement" (WAR) for an at bat
         */
        protected int getDeltaWinsAboveReplacement() {
                WARMetric bWar = (WARMetric) atBat.getBatter().getAdvancedMetrics().getMetrics().get(AdvancedMetric.WAR);
                WARMetric pWar = (WARMetric) atBat.getPitcher().getAdvancedMetrics().getMetrics().get(AdvancedMetric.WAR);
                return (int) (bWar.getWar() - pWar.getWar());
        }

        /** 
         * Returns the predicted number of outcomes for the given outcome type, calculated as:
         * <p>
         * <i>num(PO) = (b(num(O) * deltaWar / b(WAR))) / (b(PA) * 1000)</i> 
         * @param outcome The outcome to calculate against. 
         * @return The predicted number of outcomes.
         */
        protected int getPredictedNumOutcomes(Outcome outcome) {
                return super.getPredictedNumOutcomes(outcome) + getDeltaWinsAboveReplacement();
        }
 
}
