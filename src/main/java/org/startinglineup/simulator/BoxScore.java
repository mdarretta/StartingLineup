package org.startinglineup.simulator;

import java.util.List;

import org.startinglineup.component.HalfInning;
import org.startinglineup.component.Innings;
import org.startinglineup.controller.InningsController;
import org.startinglineup.utils.Formatter;

public class BoxScore {

    private HomeTeam home;
    private VisitingTeam visitors;

    private InningsController inningsCtrl;

    public BoxScore(HomeTeam home, VisitingTeam visitors, InningsController inningsCtrl) {
        this.home = home;
        this.visitors = visitors;
        this.inningsCtrl = inningsCtrl;
    }
    
    public Team getWinningTeam() {
    	List<HalfInning> topHalf = inningsCtrl.getInnings().getTopHalfInnings();
    	List<HalfInning> bottomHalf = inningsCtrl.getInnings().getBottomHalfInnings();
    	Team winningTeam = null;
    	
    	if (Innings.getRunsForHalfInnings(topHalf).size() >
    	    Innings.getRunsForHalfInnings(bottomHalf).size()) {
    		winningTeam = visitors;
    	} else {
    		winningTeam = home;
    	}
    	
    	return winningTeam;
    }

    public Team getLosingTeam() {
    	List<HalfInning> topHalf = inningsCtrl.getInnings().getTopHalfInnings();
    	List<HalfInning> bottomHalf = inningsCtrl.getInnings().getBottomHalfInnings();
    	Team losingTeam = null;
    	
    	if (Innings.getRunsForHalfInnings(topHalf).size() <
    	    Innings.getRunsForHalfInnings(bottomHalf).size()) {
    		losingTeam = visitors;
    	} else {
    		losingTeam = home;
    	}
    	
    	return losingTeam;
    }

    public String toString() {

        String[] teamInnings = inningsCtrl.toString().split("\n");
        return 
            "***********\n" + 
            " Box Score\n" + 
            "***********\n" + "    " + getInningNumbers() + " R |  H \n" +
            visitors.getTeam().getAbbr(3) + "|| " + teamInnings[0] + "\n" +
            home.getTeam().getAbbr(3) + "|| " + teamInnings[1];
    }

    private String getInningNumbers() {
        String returnStr = "| ";

        for (int x=0; x < inningsCtrl.getInnings().getInnings().size(); x++) {
            returnStr += Formatter.getPaddedInt((x+1), 2, true);
            returnStr += " | ";
        }

        return returnStr;
    }
}
