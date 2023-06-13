package org.startinglineup.component;

public class Walk extends Outcome {

    private static final Outcome instance = new Walk();

    private Walk() {
        super(Outcome.TotalBases.WALK);
    }

    public static Outcome getInstance() {
        return instance;
    }

    public boolean isHit() {
        return false;
    }
}
