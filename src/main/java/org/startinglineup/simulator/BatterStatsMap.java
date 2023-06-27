package org.startinglineup.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.startinglineup.component.Batter;
import org.startinglineup.component.Outcome;

public class BatterStatsMap {

	protected static final int SORT_BY_NAME = 0;
	protected static final int SORT_BY_BA = 1;
	protected static final int SORT_BY_HR = 2;
	protected static final int SORT_BY_RBI = 3;
	protected static final int SORT_BY_OBP = 4;
	protected static final int SORT_BY_SLG = 5;
	protected static final int SORT_BY_OPS = 6;

	private HashMap<String, BatterStats> map;
	private static BatterStatsMap instance = new BatterStatsMap();

	private BatterStatsMap() {
		map = new HashMap<String, BatterStats>();
	}

	public static BatterStatsMap getInstance() {
		return instance;
	}

	public void clear() {
		map = new HashMap<String, BatterStats>();
	}

	private void add(Batter batter) {
		map.put(batter.getFormattedName(), new BatterStats(batter));
	}

	public void update(Batter batter, Outcome outcome, int runs) {
		// For this version, do not include stats for "Pitchers Spot"
		if (!batter.getFormattedName().equals("Spot, Pitchers")) {
			BatterStats stats = map.get(batter.getFormattedName());
			if (stats == null) {
				add(batter);
				stats = map.get(batter.getFormattedName());
			}
			stats.update(outcome, runs);
			map.replace(batter.getFormattedName(), stats);
		}
	}
	
	public void updateStatsForSingleSeason(int divisor) {
		Iterator<String> i = map.keySet().iterator();
		while (i.hasNext()) {
			map.get(i.next()).updateStatsForSingleSeason(divisor);
		}
	}

	public Collection<BatterStats> getStatsByName() {
		return getSortedStats(SORT_BY_NAME);
	}

	public Collection<BatterStats> getStatsByBattingAverage() {
		return getSortedStats(SORT_BY_BA);
	}

	public Collection<BatterStats> getStatsByHomeRuns() {
		return getSortedStats(SORT_BY_HR);
	}

	public Collection<BatterStats> getStatsByRbis() {
		return getSortedStats(SORT_BY_RBI);
	}
	
	public Collection<BatterStats> getStatsByOnBasePercentage() {
		return getSortedStats(SORT_BY_OBP);
	}

	public Collection<BatterStats> getStatsBySluggingPercentage() {
		return getSortedStats(SORT_BY_SLG);
	}

	public Collection<BatterStats> getStatsByOPS() {
		return getSortedStats(SORT_BY_OPS);
	}
	
	private Collection<BatterStats> getSortedStats(int sortType) {
		List<BatterStatsComparable> stats = new ArrayList<BatterStatsComparable>();
		Iterator<BatterStats> i = map.values().iterator();
		while (i.hasNext()) {
			switch (sortType) {
				case SORT_BY_NAME:
					stats.add(new BatterStatsComparableForName(i.next()));
					break;
				case SORT_BY_BA:
					stats.add(new BatterStatsComparableForBattingAverage(i.next()));
					break;
				case SORT_BY_HR:
					stats.add(new BatterStatsComparableForHomeRuns(i.next()));
					break;
				case SORT_BY_RBI:
					stats.add(new BatterStatsComparableForRbis(i.next()));
					break;
				case SORT_BY_OBP:
					stats.add(new BatterStatsComparableForOnBasePercentage(i.next()));
					break;
				case SORT_BY_SLG:
					stats.add(new BatterStatsComparableForSluggingPercentage(i.next()));
					break;
				case SORT_BY_OPS:
					stats.add(new BatterStatsComparableForOPS(i.next()));
					break;
				default:
					stats.add(new BatterStatsComparableForName(i.next()));
					break;
			}
		}
		Collections.sort(stats);

		// Convert back to public type
		Collection<BatterStats> rtnStats = new ArrayList<BatterStats>();
		Iterator<BatterStatsComparable> i2 = stats.iterator();
		while (i2.hasNext()) {
			rtnStats.add(i2.next().getStats());
		}

		return rtnStats;
	}

	public String forString(Collection<BatterStats> stats, int limit) {
		Iterator<BatterStats> i = stats.iterator();
		String rtnString = "";
		int ctr = 0;
		while (i.hasNext()) {
			if (++ctr > limit) {
				break;
			}
			rtnString += i.next() + "\n";
		}

		return rtnString;
	}

	public String toString() {
		String rtnString = "";
		Collection<BatterStats> stats = map.values();
		Iterator<BatterStats> i = stats.iterator();
		while (i.hasNext()) {
			rtnString += i.next() + "\n";
		}

		return rtnString;
	}

	private abstract class BatterStatsComparable implements Comparable<BatterStatsComparable> {

		private BatterStats stats;

		protected BatterStatsComparable(BatterStats stats) {
			this.stats = stats;
		}

		protected BatterStats getStats() {
			return stats;
		}
	}

	private class BatterStatsComparableForName extends BatterStatsComparable {

		private BatterStatsComparableForName(BatterStats stats) {
			super(stats);
		}

		public int compareTo(BatterStatsComparable obj) {
			return this.getStats().getBatter().getFormattedName()
					.compareTo(obj.getStats().getBatter().getFormattedName());
		}
	}

	private class BatterStatsComparableForBattingAverage extends BatterStatsComparable {

		private BatterStatsComparableForBattingAverage(BatterStats stats) {
			super(stats);
		}

		public int compareTo(BatterStatsComparable obj) {
			return -1 * (Float.valueOf(this.getStats().getBatter().getBattingAverage())
					.compareTo(Float.valueOf(obj.getStats().getBatter().getBattingAverage())));
		}
	}

	private class BatterStatsComparableForHomeRuns extends BatterStatsComparable {

		private BatterStatsComparableForHomeRuns(BatterStats stats) {
			super(stats);
		}

		public int compareTo(BatterStatsComparable obj) {
			return -1 * (Integer.valueOf(this.getStats().getBatter().getHomeRuns())
					.compareTo(Integer.valueOf(obj.getStats().getBatter().getHomeRuns())));
		}
	}

	private class BatterStatsComparableForRbis extends BatterStatsComparable {

		private BatterStatsComparableForRbis(BatterStats stats) {
			super(stats);
		}

		public int compareTo(BatterStatsComparable obj) {
			return -1 * (Integer.valueOf(this.getStats().getBatter().getRbis())
					.compareTo(Integer.valueOf(obj.getStats().getBatter().getRbis())));
		}
	}

	private class BatterStatsComparableForSluggingPercentage extends BatterStatsComparable {

		private BatterStatsComparableForSluggingPercentage(BatterStats stats) {
			super(stats);
		}

		public int compareTo(BatterStatsComparable obj) {
			return -1 * (Float.valueOf(this.getStats().getBatter().getSluggingPercentage())
					.compareTo(Float.valueOf(obj.getStats().getBatter().getSluggingPercentage())));
		}
	}

	private class BatterStatsComparableForOnBasePercentage extends BatterStatsComparable {

		private BatterStatsComparableForOnBasePercentage(BatterStats stats) {
			super(stats);
		}

		public int compareTo(BatterStatsComparable obj) {
			return -1 * (Float.valueOf(this.getStats().getBatter().getOnBasePercentage())
					.compareTo(Float.valueOf(obj.getStats().getBatter().getOnBasePercentage())));
		}
	}

	private class BatterStatsComparableForOPS extends BatterStatsComparable {

		private BatterStatsComparableForOPS(BatterStats stats) {
			super(stats);
		}

		public int compareTo(BatterStatsComparable obj) {
			return -1 * (Float.valueOf(this.getStats().getBatter().getOPS())
					.compareTo(Float.valueOf(obj.getStats().getBatter().getOPS())));
		}
	}
}
