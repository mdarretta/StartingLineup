package org.startinglineup.data;

import java.util.Iterator;

import org.startinglineup.component.Batter;
import org.startinglineup.component.Player;
import org.startinglineup.component.Player.Handed;

public class BatterMap extends PlayerMap {

	private static final BatterMap instance = new BatterMap();
	private BatterNormalizer normalizer = null;
	public static final int MIN_PLATE_APPEARANCES_FOR_NOMRALIZE = 200;

	private BatterMap() {
		super();
		normalizer = new BatterNormalizer();
	}

	public static BatterMap getInstance() {
		return instance;
	}

	public void add(Player player) {
		super.add(player);
		normalizer.add(player);
	}

	public void normalize() throws PlayerNotFoundException {
		normalizer.normalize();
	}

	private class BatterNormalizer {

		private Batter batter;

		private BatterNormalizer() {
			batter = new Batter("Batter", "Average", Handed.RIGHT);
		}

		private void add(Player player) {
			Batter batter = (Batter) player;
			this.batter.addPlateAppearances(batter.getPlateAppearances());
			this.batter.addWalks(batter.getWalks());
			this.batter.addHbp(batter.getHbp());
			this.batter.addSingles(batter.getSingles());
			this.batter.addDoubles(batter.getDoubles());
			this.batter.addTriples(batter.getTriples());
			this.batter.addHomeRuns(batter.getHomeRuns());
		}

		private void normalize() throws PlayerNotFoundException {
			int dividend = batter.getPlateAppearances() / 1000;
			batter.setPlateAppearances(1000);
			batter.setWalks(batter.getWalks() / dividend);
			batter.setHbp(batter.getHbp() / dividend);
			batter.setSingles(batter.getSingles() / dividend);
			batter.setDoubles(batter.getDoubles() / dividend);
			batter.setTriples(batter.getTriples() / dividend);
			batter.setHomeRuns(batter.getHomeRuns() / dividend);

			// Modify all batter who don't meet the minimum number of
			// plate appearances to to the normalized stats
			Iterator<Player> i = BatterMap.getInstance().playerMap.values().iterator();
			Batter currentBatter = null;
			while (i.hasNext()) {
				currentBatter = (Batter) i.next();
				if (currentBatter.isNormalizable() && 
						currentBatter.getPlateAppearances() < MIN_PLATE_APPEARANCES_FOR_NOMRALIZE) {
					currentBatter.setPlateAppearances(batter.getPlateAppearances());
					currentBatter.setWalks(batter.getWalks());
					currentBatter.setHbp(batter.getHbp());
					currentBatter.setSingles(batter.getSingles());
					currentBatter.setDoubles(batter.getDoubles());
					currentBatter.setTriples(batter.getTriples());
					currentBatter.setHomeRuns(batter.getHomeRuns());
					BatterMap.getInstance().replace(currentBatter);
				}
			}
		}
	}
}
