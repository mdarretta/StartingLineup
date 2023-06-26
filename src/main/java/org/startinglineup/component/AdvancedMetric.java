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
		WAR("WAR", "Wins Against Replacement"), WAA("WAA", "Wins Against Average");
		
		private String abbr;
		private String name;
		
		MetricType(String abbr, String name) {
			this.abbr = abbr;
			this.name = name;
		}
		
		public String getAbbr() {
			return abbr;
		}
		
		public String getName() {
			return name;
		}
		
		public String toString() {
			return abbr;
		}
	}
    
    protected float advancedMetric;
    protected MetricType type;
    
    public AdvancedMetric() {
    	super();
    }
    
    public AdvancedMetric(MetricType type) {
    	this.type = type;
    }
    
    public AdvancedMetric(MetricType type, float advancedMetric) {
    	this.type = type;
    	this.advancedMetric = advancedMetric;
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
