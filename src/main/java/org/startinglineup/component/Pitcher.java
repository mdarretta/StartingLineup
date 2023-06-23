package org.startinglineup.component;

import org.startinglineup.utils.Formatter;

public class Pitcher extends Batter {

	private float whip;
	private Handed handed;

	private int inningsPitched;
	private int walksAllowed;

	private int singlesAllowed;
	private int doublesAllowed;
	private int triplesAllowed;
	private int homeRunsAllowed;
	private float homeWhip = 0F;
	private float awayWhip = 0F;

	public Pitcher() {
		super();
	}

	public Pitcher(Handed handed, float whip) {
		super();
		this.handed = handed;
		this.whip = whip;
	}

	public Pitcher(Batter batter, Handed handed, float whip) {
		super(batter.getLastname(), batter.getFirstname(), batter.getHanded(), batter.getPlateAppearances(),
				batter.getWalks(), batter.getHbp(), batter.getSingles(), batter.getDoubles(), batter.getTriples(),
				batter.getHomeRuns());
		this.handed = handed;
		this.whip = whip;
	}

	public Pitcher(Batter batter, Handed handed, float whip, int inningsPitched, int walksAllowed, int hbpAllowed,
			int singlesAllowed, int doublesAllowed, int triplesAllowed, int homeRunsAllowed) {
		super(batter.getLastname(), batter.getFirstname(), batter.getHanded(), batter.getPlateAppearances(),
				batter.getWalks(), batter.getHbp(), batter.getSingles(), batter.getDoubles(), batter.getTriples(),
				batter.getHomeRuns());
		this.handed = handed;
		this.whip = whip;
		this.inningsPitched = inningsPitched;
		this.walksAllowed = walksAllowed;
		this.setSinglesAllowed(singlesAllowed);
		this.setDoublesAllowed(doublesAllowed);
		this.setTriplesAllowed(triplesAllowed);
		this.homeRunsAllowed = homeRunsAllowed;
	}

	public Pitcher(String lastname, String firstname, Handed battingHanded, Handed pitchingHanded, int inningsPitched,
			int plateAppearances, int walks, int hbp, int singles, int doubles, int triples, int homeRuns, float whip) {
		super(lastname, firstname, battingHanded, plateAppearances, walks, hbp, singles, doubles, triples, homeRuns);
		this.handed = pitchingHanded;
		this.whip = whip;
	}

	public Handed getHanded() {
		return handed;
	}

	public void setHanded(Handed handed) {
		this.handed = handed;
	}

	public float getWhip() {
		return whip;
	}

	public void setWhip(float whip) {
		this.whip = whip;
	}

	public int getInningsPitched() {
		return inningsPitched;
	}

	public int getWalksAllowed() {
		return walksAllowed;
	}

	public int getHbpAllowed() {
		// return hbpAllowed;
		return 2;
	}

	/**
	 * Returns the singles allowed.
	 * <p>
	 * For this iteration, the singles are an estimate. We need a better import file
	 * that explicitly indicates the number of singles allowed.
	 * 
	 * @return Singles allowed.
	 */
	public int getSinglesAllowed() {
		if (singlesAllowed == 0) {
			int totalWH = (int) (whip * inningsPitched);
			int totalSDT = totalWH - walksAllowed - homeRunsAllowed;
			singlesAllowed = (int) (totalSDT * .75);
		}
		return singlesAllowed;
	}

	/**
	 * Returns the doubles allowed.
	 * <p>
	 * For this iteration, the doubles are an estimate. We need a better import file
	 * that explicitly indicates the number of doubles allowed.
	 * 
	 * @return Doubles allowed.
	 */
	public int getDoublesAllowed() {
		if (doublesAllowed == 0) {
			int totalWH = (int) (whip * inningsPitched);
			int totalSDT = totalWH - walksAllowed - homeRunsAllowed;
			doublesAllowed = (int) (totalSDT * .2);
		}
		return doublesAllowed;
	}

	/**
	 * Returns the triples allowed.
	 * <p>
	 * For this iteration, the triples are an estimate. We need a better import file
	 * that explicitly indicates the number of triples allowed.
	 * 
	 * @return Triples allowed.
	 */
	public int getTriplesAllowed() {
		if (triplesAllowed == 0) {
			int totalWH = (int) (whip * inningsPitched);
			int totalSDT = totalWH - walksAllowed - homeRunsAllowed;
			triplesAllowed = (int) (totalSDT * .05);
		}
		return triplesAllowed;
	}

	public int getHomeRunsAllowed() {
		return homeRunsAllowed;
	}

	public String getFormattedWhip() {
		return Formatter.getFormattedStat(getWhip(), 2);
	}

	public float getOpponentOnBasePercentage() {
		return ((getWhip() * 9) / (getWhip() * 9 + 27F));
	}

	public int getNumOutcomes(Outcome outcome) {
		int rtnCount = 0;

		if (outcome.equals(Walk.getInstance())) {
			rtnCount = getWalksAllowed();
		} else if (outcome.equals(HitByPitch.getInstance())) {
			rtnCount = getHbpAllowed();
		} else if (outcome.equals(Single.getInstance())) {
			rtnCount = getSinglesAllowed();
		} else if (outcome.equals(Double.getInstance())) {
			rtnCount = getDoublesAllowed();
		} else if (outcome.equals(Triple.getInstance())) {
			rtnCount = getTriplesAllowed();
		} else if (outcome.equals(HomeRun.getInstance())) {
			rtnCount = getHomeRunsAllowed();
		}

		return rtnCount;
	}

	public int getDoublesAllow() {
		return doublesAllowed;
	}

	public void setDoublesAllowed(int doublesAllowed) {
		this.doublesAllowed = doublesAllowed;
	}

	public float getAwayWhip() {
		return awayWhip;
	}

	public void setAwayWhip(float awayWhip) {
		this.awayWhip = awayWhip;
	}

	public float getHomeWhip() {
		return homeWhip;
	}

	public void setHomeWhip(float homeWhip) {
		this.homeWhip = homeWhip;
	}

	public void setSinglesAllowed(int singlesAllowed) {
		this.singlesAllowed = singlesAllowed;
	}

	public void setTriplesAllowed(int triplesAllowed) {
		this.triplesAllowed = triplesAllowed;
	}
}
