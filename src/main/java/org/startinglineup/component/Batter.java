package org.startinglineup.component;

import org.startinglineup.utils.Formatter;

public class Batter extends Player {

	private int plateAppearances = 0;
	private int walks = 0;
	private int hbp = 0;
	private int hits = 0;
	private int singles = 0;
	private int doubles = 0;
	private int triples = 0;
	private int homeRuns = 0;
	private int rbis = 0;
	private boolean normalize;
	
	// @todo Implement these unused attributes
	/*
	private float homeAverage = 0F;
	private float awayAverage = 0F;
	*/

	public Batter() {
		super();
	}

	public Batter(String lastname, String firstname, Handed handed) {
	        this(lastname, firstname, handed, null);
	}

        public Batter(String lastname, String firstname, Handed handed, AdvancedMetrics advancedMetrics) {
                super(lastname, firstname, handed, advancedMetrics);
        }

	public Batter(String lastname, String firstname, Handed handed, int plateAppearances, int walks, 
			int hbp, int singles, int doubles, int triples, int homeRuns) {
                this(lastname, firstname, handed, plateAppearances, walks, hbp, singles, doubles, triples, homeRuns, null);
        }

	public Batter(String lastname, String firstname, Handed handed, int plateAppearances, int walks, 
			int hbp, int singles, int doubles, int triples, int homeRuns, AdvancedMetrics advancedMetrics) {

                this(lastname, firstname, handed, advancedMetrics);

		// Remove HBP from the total plate appearances
		this.plateAppearances = plateAppearances - hbp;
		this.walks = walks;

		// Do not include HBP in this model
		this.hbp = 0;

		this.singles = singles;
		this.doubles = doubles;
		this.triples = triples;
		this.homeRuns = homeRuns;
	}

	public int getPlateAppearances() {
		return plateAppearances;
	}

	public void setPlateAppearances(int plateAppearances) {
		this.plateAppearances = plateAppearances;
	}

	public void addPlateAppearances(int plateAppearances) {
		this.plateAppearances += plateAppearances;
	}

	public int getWalks() {
		return walks;
	}

	public void setWalks(int walks) {
		this.walks = walks;
	}

	public void addWalks(int walks) {
		this.walks += walks;
	}

	public int getHbp() {
		return hbp;
	}

	public void setHbp(int hbp) {
		this.hbp = hbp;
	}

	public void addHbp(int hbp) {
		this.hbp += hbp;
	}
	
	public int getHits() {
		return hits;
	}
	
	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getSingles() {
		return singles;
	}

	public void setSingles(int singles) {
		this.singles = singles;
	}

	public void addSingles(int singles) {
		this.singles += singles;
	}

	public int getDoubles() {
		return doubles;
	}

	public void setDoubles(int doubles) {
		this.doubles = doubles;
	}

	public void addDoubles(int doubles) {
		this.doubles += doubles;
	}

	public int getTriples() {
		return triples;
	}

	public void setTriples(int triples) {
		this.triples = triples;
	}

	public void addTriples(int triples) {
		this.triples += triples;
	}

	public int getHomeRuns() {
		return homeRuns;
	}

	public void setHomeRuns(int homeRuns) {
		this.homeRuns = homeRuns;
	}

	public void addHomeRuns(int homeRuns) {
		this.homeRuns += homeRuns;
	}
	
	public int getRbis() {
		return rbis;
	}
	
	public void addRbis(int rbis) {
		this.rbis += rbis;
	}

        public void setNormalize(boolean normalize) {
            this.normalize = normalize;
        }
	
	public boolean isNormalizable() {
		return normalize;
	}
	
	public void updateStatsForSingleSeason(int divisor) {
		// Cast values to float to ensure proper rounding by the Math.round method.
		plateAppearances = Math.round((float) plateAppearances / (float) divisor);
		walks = Math.round((float) walks / (float) divisor);
		hbp = Math.round((float) hbp / (float) divisor);
		singles = Math.round((float) singles / (float) divisor);
		doubles = Math.round((float) doubles / (float) divisor);
		triples = Math.round((float) triples / (float) divisor);
		homeRuns = Math.round((float) homeRuns / (float) divisor);
		rbis = Math.round((float) rbis / (float) divisor);
	}

	public int getNumOutcomes(Outcome outcome) {
		int rtnCount = 0;

		if (outcome.equals(Walk.getInstance())) {
			rtnCount = getWalks();
		} else if (outcome.equals(Single.getInstance())) {
			rtnCount = getSingles();
		} else if (outcome.equals(Double.getInstance())) {
			rtnCount = getDoubles();
		} else if (outcome.equals(Triple.getInstance())) {
			rtnCount = getTriples();
		} else if (outcome.equals(HomeRun.getInstance())) {
			rtnCount = getHomeRuns();
		}

		return rtnCount;
	}

	public float getPercentageWalks() {
		return (float) walks / (float) plateAppearances;
	}

	public float getPercentageHbp() {
		return (float) hbp / (float) plateAppearances;
	}

	public float getPercentageSingles() {
		return (float) singles / (float) plateAppearances;
	}

	public float getPercentageSingleBase() {
		return ((float) walks + (float) hbp + (float) singles) / (float) plateAppearances;
	}

	public float getPercentageDoubles() {
		return (float) doubles / (float) plateAppearances;
	}

	public float getPercentageTriples() {
		return (float) triples / (float) plateAppearances;
	}

	public float getPercentageHomeRuns() {
		return (float) homeRuns / (float) plateAppearances;
	}

	public float getBattingAverage() {
		int hits = singles + doubles + triples + homeRuns;
		int atBats = plateAppearances - walks - hbp;
		return (float) hits / (float) atBats;
	}

	public float getOnBasePercentage() {
		int hpw = walks + hbp + singles + doubles + triples + homeRuns;
		return (float) hpw / (float) plateAppearances;
	}
	
	public float getSluggingPercentage() {
		return (float) (singles + (2*doubles) + (3*triples) + (4*homeRuns))/ (float) getAtBats();
	}
	
	public int getAtBats() {
		return plateAppearances - walks - hbp;
	}

	public String getFormattedBattingAverage() {
		return Formatter.getFormattedStat(getBattingAverage(), 3);
	}
	
	public float getOPS() {
		return getOnBasePercentage() + getSluggingPercentage();
	}

	public String getFormattedOnBasePercentage() {
		return Formatter.getFormattedStat(getOnBasePercentage(), 3);
	}

	public String getFormattedSluggingPercentage() {
		return Formatter.getFormattedStat(getSluggingPercentage(), 3);
	}

	public String getFormattedOPS() {
		return Formatter.getFormattedStat(getOPS(), 3);
	}
}
