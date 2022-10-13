package org.startinglineup.component;

public class HitByPitch extends Outcome {

    private static final Outcome instance = new HitByPitch();

    private HitByPitch() {
        super(Outcome.TotalBases.HBP);
    }

    public static Outcome getInstance() {
        return instance;
    }

    public boolean isHit() {
        return false;
    }
}
