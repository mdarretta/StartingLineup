package org.startinglineup.component;

public class HomeRun extends Outcome {

    private static final Outcome instance = new HomeRun();

    private HomeRun() {
        super(Outcome.TotalBases.HOMERUN);
    }

    public static Outcome getInstance() {
        return instance;
    }

    public boolean isHit() {
        return true;
    }
}
