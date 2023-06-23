package org.startinglineup.component;

/**
 * This class is used to handle metrics beyond the normal
 * metrics found in the batter and picher instantiations.
 *
 * The default metric type is <code>float</code>. Subclasses
 * should provide helper methods to caste to different types
 * if required.
 */
public class AdvancedMetric {

	public enum MetricType {
		WAR("WAR"), WAA("WAA");
		
		private String name;
		
		MetricType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
    
    protected float advancedMetric;
    
    public AdvancedMetric() {
    	super();
    }

	public float getAdvancedMetric() {
		return advancedMetric;
	}

	public void setAdvancedMetric(float advancedMetric) {
		this.advancedMetric = advancedMetric;
	}
	
	public String toString() {
		return "" + advancedMetric;
	}
}
