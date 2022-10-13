package org.startinglineup.component;

public class Out extends Outcome {

    private static final Outcome instance = new Out();

    private Out() {
        super(Outcome.TotalBases.OUT);
    }

    public static final Outcome getInstance() {
        return instance;
    }

    public boolean isHit() {
        return false;
    }
}
