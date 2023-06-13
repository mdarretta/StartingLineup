package org.startinglineup.component;

public class Double extends Outcome {

    private static final Outcome instance = new Double();

    private Double() {
        super(Outcome.TotalBases.DOUBLE);
    }

    public static Outcome getInstance() {
        return instance;
    }

    public boolean isHit() {
        return true;
    }
}
