package org.startinglineup.controller;

import java.util.List;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.startinglineup.component.HalfInning;
import org.startinglineup.component.Inning;
import org.startinglineup.component.Innings;
import org.startinglineup.component.Out;
import org.startinglineup.component.Outcome;
import org.startinglineup.component.Run;

/**
 * Controller to manage current and extra innings during game simulation.
 * 
 * @author Mike Darretta
 */
public class InningsController implements PropertyChangeListener {

	/** 
	 * The innings in a game, taking into account extra innings when needed.
	 */
    private Innings innings;
    
    /**
     * The current inning.
     */
    private int inningPtr;
    
    /**
     * The current half inning.
     */
    private HalfInning currentHalf;
    
    /**
     * The controller to manage runs during a game simulation.
     */
    private RunsController runsCtrl;
    
    /**
     * Indicates whether a game is complete, taking into account extra innings.
     */
    private boolean isDone;

    /**
     * Default constructor.
     */
    public InningsController() {
        this.innings = new Innings();
        this.runsCtrl = new RunsController(this);
        this.inningPtr = 0;
        this.currentHalf = getCurrentInning().getTopHalf();
        this.isDone = false;
    }

    /**
     * Adds an at bat outcome to the current half inning.
     * @param outcome The at bat outcome.
     */
    public void addOutcome(Outcome outcome) {
        currentHalf.addOutcome(outcome);
        if (outcome.equals(Out.getInstance())) {
            currentHalf.incrementNumOuts();
        } else {
            runsCtrl.addOutcome(outcome);
        }
    }

    /**
     * Determines the next half inning.
     */
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
    
    /**
     * Captures a property change event signifying a run has been scored.
     * @param e The property change event.
     */
    public void propertyChange(PropertyChangeEvent e) {
    	Run run = (Run) e.getNewValue();
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
    
    /**
     * Returns the number of innings for this controller.
     * @return The number of innings.
     */
    public Innings getInnings() {
        return innings;
    } 

    /**
     * Adds an additional inning - i.e., entering extra innings.
     */
    private void addExtraInning() {
        innings.addExtraInning();
        goNextInning();
    }

    /**
     * Returns the next inning to manage.
     */
    private void goNextInning() {
        inningPtr++;
        currentHalf = getCurrentInning().getTopHalf();
    }
   
    /**
     * Indicates whether this is the regulation inning.
     * @return Whether this is the last regulation inning.
     */
    public boolean isNinth() {
        return (inningPtr == (Innings.REGULATION_INNINGS-1));
    }

    /**
     * Indicates whether the simulation has entered extra innings.
     * @return Whether the simulation has entered extra innings.
     */
    public boolean isExtraInnings() {
        return (inningPtr >= Innings.REGULATION_INNINGS);
    }

    /**
     * Indicates whether the game is in "sudden death" - i.e., it's the bottom
     * half of the ninth inning or above.
     * @return Whether the game is in "sudden death".
     */
    public boolean isSuddenDeath() {
        boolean isSuddenDeath = false;
        if (isNinth() || isExtraInnings()) {
            isSuddenDeath = (!currentHalf.isTopHalf());
        }

        return isSuddenDeath;
    }

    /**
     * Returns the current inning number.
     * @return The current inning number.
     */
    public int getCurrentInningNumber() {
        return (inningPtr+1);
    }

    /**
     * Returns the current inning.
     * @return The current inning.
     */
    public Inning getCurrentInning() {
        return innings.getInnings().get(inningPtr);
    }

    /**
     * Returns the current half inning.
     * @return The current half inning.
     */
    public HalfInning getCurrentHalfInning() {
        return currentHalf;
    }

    /**
     * Returns whether the inning is complete - i.e., there are now
     * three outs.
     * @return Whether the current inning is complete.
     */
    public boolean isInningComplete() {
        return (currentHalf.getNumOuts() >= 3);
    }

    /**
     * Returns the string representation of this instantiation.
     * @return The string representation of this instantiation.
     */
    public String toString() {
        return innings.toString();
    }
} 
