package org.startinglineup.component;

public class Single extends Outcome {

    private static Outcome instance = new Single();

    private Single() {
        super(Outcome.TotalBases.SINGLE);
    }

    public static Outcome getInstance() {
        return instance;
    }

    public boolean isHit() {
        return true;
    }
}
