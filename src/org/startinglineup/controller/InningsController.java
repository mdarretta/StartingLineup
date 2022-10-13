package org.startinglineup.controller;

import java.util.List;
import java.util.Observable;

import org.startinglineup.component.HalfInning;
import org.startinglineup.component.Inning;
import org.startinglineup.component.Innings;
import org.startinglineup.component.Out;
import org.startinglineup.component.Outcome;
import org.startinglineup.component.Run;

public class InningsController implements SuddenDeathObserver {

    private Innings innings;
    private int inningPtr;
    private HalfInning currentHalf;
    private RunsController runsCtrl;
    private boolean isDone;

    public InningsController() {
        this.innings = new Innings();
        this.runsCtrl = new RunsController(this);
        this.inningPtr = 0;
        this.currentHalf = getCurrentInning().getTopHalf();
        this.isDone = false;
    }

    public void addOutcome(Outcome outcome) {
        currentHalf.addOutcome(outcome);
        if (outcome.equals(Out.getInstance())) {
            currentHalf.incrementNumOuts();
        } else {
            runsCtrl.addOutcome(outcome);
        }
    }

    public void next() {
        runsCtrl = new RunsController(this);

        if (isSuddenDeath()) {
            addExtraInning(); 
        } else if (!currentHalf.isTopHalf()) {
            goNextInning();
        } else {
            currentHalf = getCurrentInning().getBottomHalf();
        }
    }
    
    public void update(Observable o, Object arg) {
    	Run run = (Run) arg;
    	currentHalf.addRun(run);
    	if (isSuddenDeath()) {
    		isDone = true;
    	}
    }

    /**
     * Returns whether the final inning (or last at-bat) has been completed.
     * @return True if the final inning (or last at-bat) has been completed.
     */
    public boolean isDone() {
        if (!isDone && (isNinth() || isExtraInnings())) {
            List<HalfInning> topInnings = getInnings().getTopHalfInnings();
            List<HalfInning> bottomInnings = getInnings().getBottomHalfInnings();

            isDone = (Innings.getRunsForHalfInnings(topInnings).size() !=
        			Innings.getRunsForHalfInnings(bottomInnings).size());
        }
        
        return isDone;
    }
    
    public Innings getInnings() {
        return innings;
    } 

    private void addExtraInning() {
        innings.addExtraInning();
        goNextInning();
    }

    private void goNextInning() {
        inningPtr++;
        currentHalf = getCurrentInning().getTopHalf();
    }
   
    public boolean isNinth() {
        return (inningPtr == (Innings.REGULATION_INNINGS-1));
    }

    public boolean isExtraInnings() {
        return (inningPtr >= Innings.REGULATION_INNINGS);
    }

    public boolean isSuddenDeath() {
        boolean isSuddenDeath = false;
        if (isNinth() || isExtraInnings()) {
            isSuddenDeath = (!currentHalf.isTopHalf());
        }

        return isSuddenDeath;
    }

    public int getCurrentInningNumber() {
        return (inningPtr+1);
    }

    public Inning getCurrentInning() {
        return innings.getInnings().get(inningPtr);
    }

    public HalfInning getCurrentHalfInning() {
        return currentHalf;
    }

    public boolean isInningComplete() {
        return (currentHalf.getNumOuts() >= 3);
    }

    public String toString() {
        return innings.toString();
    }
} 
