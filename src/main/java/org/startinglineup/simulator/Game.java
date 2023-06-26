package org.startinglineup.simulator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;

import org.startinglineup.Properties;
import org.startinglineup.component.Batter;
import org.startinglineup.component.Outcome;
import org.startinglineup.component.Pitcher;
import org.startinglineup.component.UniqueComponent;
import org.startinglineup.controller.InningsController;
import org.startinglineup.controller.TeamController;
import org.startinglineup.data.ALStartingLineupMap;
import org.startinglineup.data.NLStartingLineupMap;
import org.startinglineup.data.StartingLineupMap;
import org.startinglineup.data.StartingRotationMap;
import org.startinglineup.event.AtBat;
import org.startinglineup.event.AtBatResultGenerator;
import org.startinglineup.league.League;
import org.startinglineup.league.MajorLeagues;

public class Game extends UniqueComponent {

    private HomeTeam home;
    private VisitingTeam visitor;
    private Date date;
    private InningsController inningsCtrl;
    private TeamController homeTeamCtrl;
    private TeamController visitingTeamCtrl;
    
    public Game() {
    }

    public Game(HomeTeam home, VisitingTeam visitor, Date date) {
        this.home = home;
        this.visitor = visitor;
        this.date = date;
        reset();
    }
    
    public void reset() {
        this.inningsCtrl = new InningsController();
        this.homeTeamCtrl = new TeamController(home);
        this.visitingTeamCtrl = new TeamController(visitor);
    }
    
    public GameStats play() {
    	    	
    	reset();
        addLineups();
    	    	
        while (!isDone()) {
            if (inningOver()) {
               inningsCtrl.next();
            }

            Outcome outcome = null;
            Batter batter = null;
            Pitcher pitcher = null;

            if (isTopHalf()) {
            	batter = visitingTeamCtrl.nextBatter();
            	pitcher = home.getTeam().getPitcher();
            } else {
            	batter = homeTeamCtrl.nextBatter();
            	pitcher = visitor.getTeam().getPitcher();
            }

            int runs = inningsCtrl.getCurrentHalfInning().getRuns().size();
            
            try {
	            outcome = getNextOutcome(batter, pitcher);
	            addOutcome(outcome);
	            runs = (inningsCtrl.getCurrentHalfInning().getRuns().size() - runs);
	            BatterStatsMap.getInstance().update(batter, outcome, runs);
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        
        return (new GameStats(home.getTeam(), visitor.getTeam(), inningsCtrl));
    }
    
    private void addLineups() {     
    	
    	StartingLineupMap map = null;
    	
	    League league = MajorLeagues.getInstance().getLeagueForTeam(home.getTeam());
	    if (league != null) {
		    if (league.getLeague().equals(League.LeagueType.AMERICAN)) {
		    	map = ALStartingLineupMap.getInstance();
		    } else {
		    	map = NLStartingLineupMap.getInstance();
		    }
	    }
	    
		ArrayList<Batter> homeLineup = new ArrayList<Batter>();
    	homeLineup.addAll(map.getLineup(home.getTeam()));

    	ArrayList<Batter> visitorLineup = new ArrayList<Batter>();
    	visitorLineup.addAll(map.getLineup(visitor.getTeam()));
    	
    	// CHeck whether this is the first time the lineups will be added
    	if (home.getTeam().getBatters().size() == 0) {
    		home.getTeam().addBatters(homeLineup);
    	}
    	
    	if (visitor.getTeam().getBatters().size() == 0) {
    		visitor.getTeam().addBatters(visitorLineup);    
    	}
        
        home.getTeam().setPitcher(StartingRotationMap.getInstance().getNext(home.getTeam()));
        visitor.getTeam().setPitcher(StartingRotationMap.getInstance().getNext(visitor.getTeam()));
    }

    private boolean isDone() {
        return inningsCtrl.isDone();
    }

    private Outcome getNextOutcome(Batter batter, Pitcher pitcher) 
    		throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
    		InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
    
    	String className = Properties.getInstance().get(Properties.AT_BAT_RESULT_GENERATOR_CLASS_PROP);
    	AtBatResultGenerator atBatResultGenerator = 
    			(AtBatResultGenerator) Class.forName(className).getDeclaredConstructor().newInstance();
            atBatResultGenerator.initialize(new AtBat(batter, pitcher));
    	
        return atBatResultGenerator.getOutcome();

    }

    private boolean inningOver() {
        return inningsCtrl.isInningComplete();
    }

    private boolean isTopHalf() {
        return inningsCtrl.getCurrentHalfInning().isTopHalf();
    }

    private void addOutcome(Outcome outcome) {
        inningsCtrl.addOutcome(outcome);
    }
    
    public HomeTeam getHomeTeam() {
    	return home;
    }
    
    public VisitingTeam getVisitingTeam() {
    	return visitor;
    }
    
    public Date getDate() {
    	return date;
    }
    
    public String toString() {
    	return date + ": " + visitor.getTeam().getAbbr() + " @ " + home.getTeam().getAbbr() +
    			", " + visitor.getTeam().getPitcher() + " v. " + home.getTeam().getPitcher();
    }
}
