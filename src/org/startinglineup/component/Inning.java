package org.startinglineup.component;

public class Inning {

    private HalfInning topHalf;
    private HalfInning bottomHalf;

    public Inning() {
        super();
        this.topHalf = new HalfInning(HalfInning.Half.TOP);
        this.bottomHalf = new HalfInning(HalfInning.Half.BOTTOM);
    }

    public HalfInning getTopHalf() {
        return topHalf;
    }

    public HalfInning getBottomHalf() {
        return bottomHalf;
    }

    public String toString() {
        return "[" + topHalf + "|" + bottomHalf + "]";
    }
}
