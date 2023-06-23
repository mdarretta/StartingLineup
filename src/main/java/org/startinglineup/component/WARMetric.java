package org.startinglineup.component;

public class WARMetric extends AdvancedMetric {

    public WARMetric() {
        super();
    }

    public WARMetric(float war) {
        super.advancedMetric = war;
    }

    public float getWar() {
        return super.advancedMetric;
    }
}
