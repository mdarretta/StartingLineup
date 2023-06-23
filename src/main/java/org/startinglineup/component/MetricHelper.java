package org.startinglineup.component;

public class MetricHelper extends UniqueComponent {

    private WARMetric metric;

    public MetricHelper() {
        super();
    }

    public void setMetric(WARMetric metric) {
        this.metric = metric;
    }

    public WARMetric getMetric() {
        return metric;
    }
}
