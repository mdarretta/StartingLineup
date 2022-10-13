package org.startinglineup.simulator;

import org.startinglineup.component.Batter;
import org.startinglineup.component.Double;
import org.startinglineup.component.HomeRun;
import org.startinglineup.component.Outcome;
import org.startinglineup.component.Single;
import org.startinglineup.component.Triple;
import org.startinglineup.component.Walk;

public class BatterStats {
	
	private Batter batter;

	public BatterStats(Batter batter) {
		// Create a batter object with no stats.
		this.batter = new Batter(batter.getLastname(), batter.getFirstname(), batter.getHanded());
	}

	public void update(Outcome outcome, int runs) {
		batter.setPlateAppearances(batter.getPlateAppearances()+1);
		
		if (outcome.equals(Walk.getInstance())) {
			batter.setWalks(batter.getWalks()+1);
		} else if (outcome.equals(Single.getInstance())) {
			batter.setSingles(batter.getSingles()+1);
		} else if (outcome.equals(Double.getInstance())) {
			batter.setDoubles(batter.getDoubles()+1);
		} else if (outcome.equals(Triple.getInstance())) {
			batter.setTriples(batter.getTriples()+1);
		} else if (outcome.equals(HomeRun.getInstance())) {
			batter.setHomeRuns(batter.getHomeRuns()+1);
		}
		
		batter.addRbis(runs);
	}
	
	protected void updateStatsForSingleSeason(int divisor) {
		batter.updateStatsForSingleSeason(divisor);
	}
	
	protected Batter getBatter() {
		return batter;
	}
	
	public String toString() {
		return batter + " | " + batter.getPlateAppearances() + " | " +
	        batter.getFormattedBattingAverage() + " | " + batter.getHomeRuns() + " | " +
				batter.getRbis() + " | " + batter.getFormattedOPS();
	}
}
