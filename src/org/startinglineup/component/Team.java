package org.startinglineup.component;

import java.util.ArrayList;
import java.util.List;

public class Team extends UniqueComponent {

    private String city;
    private String name;
    private String abbr;
    
	private List<Batter> batters;
	private Pitcher pitcher;
    private int wins;
    private int losses;
    
    public Team() {
    	super();
    	this.batters = new ArrayList<Batter>();
    }

    public Team(String city, String name, String abbr) {
    	this();
        this.city = city;
        this.name = name;
        this.abbr = abbr;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getLongName() {
        return city + " " + name;
    }

    public String getAbbr() {
        return abbr;
    }

    public String getAbbr(int padding) {
        String rtnString = getAbbr();
        if (rtnString.length() < padding) {
            for (int x=rtnString.length(); x < padding; x++) {
                rtnString += " ";
            }
        }

        return rtnString;
    }
        
    public List<Batter> getBatters() {
        return batters;
    }

    public Pitcher getPitcher() {
    	return pitcher;
    }
    
    public void addBatter(Batter batter, int position) {
        batters.add(position, batter);
    }

    public void addBatter(Batter batter) {
        batters.add(batter);
    }
    
    public void addBatters(List<Batter> batters) {
    	this.batters.addAll(batters);
    }

    public void setPitcher(Pitcher pitcher) {
    	this.pitcher = pitcher;
    }
    
    public void addWin() {
    	wins++;
    }
    
    public void addLoss() {
    	losses++;
    }
    
    public void addWins(int wins) {
    	this.wins += wins;
    }
    
    public void addLosses(int losses) {
    	this.losses += losses;
    }
    
    public int getWins() {
    	return wins;
    }
    
    public int getLosses() {
    	return losses;
    }
    
    public boolean equals(Object o) {
    	Team team = (Team) o;
    	return this.abbr.equals(team.getAbbr());
    }
    
    /**
     * @Override
     * Returns the hash code for the unique abbreviation string.
     * @return The hash code for the unique abbreviation string.
     */
    public int hashCode() {
    	return this.abbr.hashCode();
    }

    public String toString() {
        return getLongName();
    }
}
