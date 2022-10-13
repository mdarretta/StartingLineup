package org.startinglineup.event;

import org.startinglineup.component.Batter;
import org.startinglineup.component.Pitcher;

public class AtBat {

    private Batter batter;
    private Pitcher pitcher;

    public AtBat() {}

    public AtBat(Batter batter, Pitcher pitcher) {
       this.batter = batter;
       this.pitcher = pitcher;
    }

    public Batter getBatter() {
       return batter;
    }

    public Pitcher getPitcher() {
       return pitcher;
    }
}
