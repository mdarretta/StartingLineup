package org.startinglineup.component;

public class WARMetric extends AdvancedMetric {

    public WARMetric() {
        super(MetricType.WAR);
    }

    public float getWar() {
        return advancedMetric;
    }
}
