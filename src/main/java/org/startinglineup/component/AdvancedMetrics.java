package org.startinglineup.component;

import java.util.HashMap;

/**
 * Contains a <code>HashMap</code> of a compoenent's
 * advanced metrics.
 */
public class AdvancedMetrics {

    /**
     * A map of <code>AdvancedMetric</code> objects.
     */
    private HashMap<String, AdvancedMetric> metrics;

    public AdvancedMetrics() {
        this(new HashMap());
    }

    public AdvancedMetrics(HashMap<String, AdvancedMetric> metrics) {
        this.metrics = metrics;
    }

    public void addMetric(String key, AdvancedMetric metric) {
        metrics.put(key, metric);
    }

    public AdvancedMetric getMetric(String key) {
        return metrics.get(key);
    }

    public HashMap<String, AdvancedMetric> getMetrics() {
        return metrics;
    }
}
