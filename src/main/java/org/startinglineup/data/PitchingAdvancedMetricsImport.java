package org.startinglineup.data;

import java.io.File;

import org.startinglineup.Properties;
import org.startinglineup.StartingLineupException;
import org.startinglineup.component.Pitcher;
import org.startinglineup.component.UniqueComponent;
import org.startinglineup.component.AdvancedMetric;
import org.startinglineup.component.AdvancedMetrics;
import org.startinglineup.component.WARMetric;

public class PitchingAdvancedMetricsImport extends PlayerImport {
	
	private Pitcher pitcher;

	public PitchingAdvancedMetricsImport(File file) throws FileImportException {
		super(file);
	}

	protected void instantiateTemplate() throws FileImportException {
        this.reader = new TemplateReader(new File(
        		Properties.getInstance().get(
        				Properties.PITCHING_ADVANCED_METRICS_IMPORT_TEMPLATE_PROP)));
        reader.run();		
	}

	protected boolean instantiateTarget(UniqueComponent target) throws StartingLineupException {
   		return target instanceof Pitcher;
	}
		
	protected void processTarget(UniqueComponent target) throws StartingLineupException {
		try {
    		if (target instanceof Pitcher) {
    			pitcher = (Pitcher) target;
    			addWAR(pitcher.getMetric());
    		}
		} catch (Exception e) {
			throw new StartingLineupException(e);
		}
	}
        
    private void addWAR(float metric) throws PlayerNotFoundException {
    	
		Pitcher p = (Pitcher) PitcherMap.getInstance().get(pitcher);
		if (p == null) {
			return;
		}
		
		pitcher = p;
		
		WARMetric war = new WARMetric(metric);
		
		if (pitcher.getAdvancedMetrics() == null) {
		    AdvancedMetrics advancedMetrics = new AdvancedMetrics();
	        pitcher.setAdvancedMetrics(advancedMetrics);
		}
		
		pitcher.getAdvancedMetrics().addMetric(AdvancedMetric.MetricType.WAR, war);
		PitcherMap.getInstance().replace(pitcher);
    }
}
