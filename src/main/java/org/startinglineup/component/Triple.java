package org.startinglineup.component;

public class Triple extends Outcome {

    private static final Outcome instance = new Triple();

    private Triple() {
        super(Outcome.TotalBases.TRIPLE);
    }

    public static Outcome getInstance() {
        return instance;
    }

    public boolean isHit() {
        return true;
    }
}
