package org.startinglineup.controller;

import org.startinglineup.component.Base;
import org.startinglineup.component.Diamond;
import org.startinglineup.component.Outcome;
import org.startinglineup.component.Run;

import java.beans.PropertyChangeSupport;

class RunsController extends PropertyChangeSupport {

    private static final long serialVersionUID = 1L;
	private Diamond diamond;

    RunsController(InningsController inningsCtrl) {
        super(inningsCtrl);
        addPropertyChangeListener(inningsCtrl);
        this.diamond = new Diamond();
    }
    
    void addOutcome(Outcome outcome) {
        if (outcome.isHit()) {
            advanceRunners(outcome);
        } else {
            addWalkOrHbpOutcome(outcome);
        }
    }
    
    private void addRun(Run run) {
    	firePropertyChange("Run", null, run);
    }

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

    private void advanceRunners(Outcome outcome) {
        advanceRunner(Base.Position.THIRD, outcome);
        advanceRunner(Base.Position.SECOND, outcome);
        advanceRunner(Base.Position.FIRST, outcome);
        advanceHitter(outcome);
    }

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

    private void advanceHitter(Outcome outcome) {
        if (outcome.getTotalBases() == Outcome.TotalBases.HOMERUN) {
        	addRun(new Run());
        } else {
            diamond.getBase(outcome.getTotalBases().getTotalBases()).setOccupied(true);
        }
    }
    
    private boolean isOut(Outcome outcome) {
    	return outcome.getTotalBases().equals(Outcome.TotalBases.OUT);
    }
    
    private boolean isFirstOccupied() {
    	return diamond.getBase(Base.Position.FIRST.getPosition()).isOccupied();
    }
    
    private boolean isSecondOccupied() {
    	return diamond.getBase(Base.Position.SECOND.getPosition()).isOccupied();
    }
}
