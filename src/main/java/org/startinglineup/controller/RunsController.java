package org.startinglineup.controller;

import org.startinglineup.component.Base;
import org.startinglineup.component.Diamond;
import org.startinglineup.component.Outcome;
import org.startinglineup.component.Run;

import java.beans.PropertyChangeSupport;

/**
 * Controller to manage runs during a game simulation.
 * 
 * @author Mike Darretta
 */
class RunsController extends PropertyChangeSupport {

	/**
	 * The serial ID.
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Ballpark diamond representation.
     */
	private Diamond diamond;

	/**
	 * Constructor.
	 * @param inningsCtrl The game innings controller.
	 */
    RunsController(InningsController inningsCtrl) {
        super(inningsCtrl);
        addPropertyChangeListener(inningsCtrl);
        this.diamond = new Diamond();
    }
    
    /**
     * Adds the current at bat outcome.
     * @param outcome The current at bat outcome.
     */
    void addOutcome(Outcome outcome) {
        if (outcome.isHit()) {
            advanceRunners(outcome);
        } else {
            addWalkOrHbpOutcome(outcome);
        }
    }
    
    /**
     * Fires a property change to indicate a run was scored.
     * @param run A run.
     */
    private void addRun(Run run) {
    	firePropertyChange("Run", null, run);
    }

    /**
     * Advanced runners for a wolk or HBP outcome.
     * @param outcome The outcome.
     */
    private void addWalkOrHbpOutcome(Outcome outcome) {
        if (!(isOut(outcome))) {
            if (!(isFirstOccupied())) {
                diamond.getBase(Base.Position.FIRST.getPosition()).setOccupied(true);
            } else if (!(isSecondOccupied())) {
                diamond.getBase(Base.Position.SECOND.getPosition()).setOccupied(true);
                diamond.getBase(Base.Position.FIRST.getPosition()).setOccupied(true);
            } else {
                advanceRunners(outcome);
            }
        }

    }

    /**
     * Advances all current runners for an outcome.
     * @param outcome The outcome.
     */
    private void advanceRunners(Outcome outcome) {
        advanceRunner(Base.Position.THIRD, outcome);
        advanceRunner(Base.Position.SECOND, outcome);
        advanceRunner(Base.Position.FIRST, outcome);
        advanceHitter(outcome);
    }

    /**
     * Advances a runner for an outcome to the appropriate next base.
     * @param base The current base.
     * @param outcome The outcome to advance the runner.
     */
    private void advanceRunner(Base.Position base, Outcome outcome) {
        if (diamond.getBase(base).isOccupied()) {
            if ((diamond.getBase(base).getPosition().getPosition() + 
            		outcome.getTotalBases().getTotalBases()) < Diamond.TOTAL_BASES) {
                diamond.getBase(base).setOccupied(false);
                diamond.getBase(base.getPosition() + 
                	outcome.getTotalBases().getTotalBases()).setOccupied(true);
            } else {
                diamond.getBase(base).setOccupied(false);
                addRun(new Run());
            }
        }
    }

    /**
     * Advances a hitter to the appropriate base.
     * @param outcome The outcome.
     */
    private void advanceHitter(Outcome outcome) {
        if (outcome.getTotalBases() == Outcome.TotalBases.HOMERUN) {
        	addRun(new Run());
        } else {
            diamond.getBase(outcome.getTotalBases().getTotalBases()).setOccupied(true);
        }
    }
    
    /**
     * Determines if this outcome is an out.
     * @param outcome The outcome.
     * @return Whether this outcome is an out.
     */
    private boolean isOut(Outcome outcome) {
    	return outcome.getTotalBases().equals(Outcome.TotalBases.OUT);
    }
    
    /**
     * Determines whether first base is occupied.
     * @return Whether first base is occupied.
     */
    private boolean isFirstOccupied() {
    	return isOccupied(Base.Position.FIRST);
    }
    
    /**
     * Determines whether second base is occupied.
     * @return Whether second base is occupied.
     */
    private boolean isSecondOccupied() {
    	return isOccupied(Base.Position.SECOND);
    }
    
    /**
     * Determines whether a base is occupied.
     * @param position The current base position.
     * @return Whether a base is occupied.
     */
    private boolean isOccupied(Base.Position position) {
    	return diamond.getBase(position.getPosition()).isOccupied();
    }
}
