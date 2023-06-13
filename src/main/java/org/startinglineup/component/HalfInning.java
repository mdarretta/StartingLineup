package org.startinglineup.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.startinglineup.utils.Formatter;

public class HalfInning {

    public static final int REGULATION_OUTS = 3;
    
    enum Half { TOP, BOTTOM }

    private Half topOrBottom;
    private Collection<Run> runs; 
    private Collection<Outcome> outcomes;
    private int numOuts;

    /**
     * Constructor package scope to restrict instantiation.
     * @param topOrBottom Indicates whether this is a <code>TOP</code> or <code>BOTTOM</code> inning.
     */
    HalfInning(Half topOrBottom) {

        super();
        this.topOrBottom = topOrBottom;     
        this.runs = new ArrayList<Run>();
        this.outcomes = new ArrayList<Outcome>();
        this.numOuts = 0;
    }

    Half getTopOrBottom() {
        return topOrBottom;
    }

    public boolean isTopHalf() {
        return topOrBottom.equals(Half.TOP);
    }

    public void addOutcome(Outcome outcome) {
        outcomes.add(outcome);
    }

    public Collection<Outcome> getOutcomes() {
        return outcomes;
    }

    public void addRun(Run run) {
        runs.add(run);
    }

    public void addRuns(Collection<Run> runs) {
        runs.addAll(runs);
    }

    public Collection<Run> getRuns() {
        return runs;
    }

    public void incrementNumOuts() {
        numOuts++;
    }

    public Collection<Outcome> getHits() {
        Collection<Outcome> hits = new ArrayList<Outcome>();
        Iterator<Outcome> i = outcomes.iterator();
        while (i.hasNext()) {
            Outcome outcome = i.next();
            if (outcome.isHit()) {
                hits.add(outcome);
            }
        }

        return hits;
    }

    public int getNumOuts() {
        return numOuts;
    }

    public String toString() {
        return "" + Formatter.getPaddedInt(runs.size(),2,true);
    }
}
