package org.startinglineup.component;

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

/**
 * Contains a <code>HashMap</code> of a compoenent's
 * advanced metrics.
 */
public class AdvancedMetrics {

    /**
     * A map of <code>AdvancedMetric</code> objects.
     */
    private HashMap<AdvancedMetric.MetricType, AdvancedMetric> metrics;
    
    public AdvancedMetrics() {
        this(new HashMap<AdvancedMetric.MetricType, AdvancedMetric>());
    }

    public AdvancedMetrics(HashMap<AdvancedMetric.MetricType, AdvancedMetric> metrics) {
        this.metrics = metrics;
    }

    public void addMetric(AdvancedMetric.MetricType key, AdvancedMetric metric) {
        metrics.put(key, metric);
    }

    public AdvancedMetric getMetric(AdvancedMetric.MetricType key) {
        return metrics.get(key);
    }

    public HashMap<AdvancedMetric.MetricType, AdvancedMetric> getMetrics() {
        return metrics;
    }
    
    public String toString() {
    	String rtnStr = "";
    	Set<AdvancedMetric.MetricType> keys = metrics.keySet();
    	Iterator<AdvancedMetric.MetricType> i = keys.iterator();
    	while (i.hasNext()) {
    		AdvancedMetric.MetricType key = i.next();
    		AdvancedMetric metric = getMetric(key);
    		rtnStr += metric;
    		rtnStr += ",";
    	}
    	return rtnStr;
    }
}
