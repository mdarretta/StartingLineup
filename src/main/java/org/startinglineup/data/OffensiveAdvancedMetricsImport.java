package org.startinglineup.data;

import java.io.File;

import org.startinglineup.Properties;
import org.startinglineup.StartingLineupException;
import org.startinglineup.component.Batter;
import org.startinglineup.component.UniqueComponent;
import org.startinglineup.component.AdvancedMetric;
import org.startinglineup.component.AdvancedMetrics;
import org.startinglineup.component.WARMetric;

public class OffensiveAdvancedMetricsImport extends PlayerImport {
	
	private Batter batter;

	public OffensiveAdvancedMetricsImport(File file) throws FileImportException {
		super(file);
	}

	protected void instantiateTemplate() throws FileImportException {
        this.reader = new TemplateReader(new File(
        		Properties.getInstance().get(
        				Properties.OFFENSIVE_ADVANCED_METRICS_IMPORT_TEMPLATE_PROP)));
        reader.run();		
	}

	protected boolean instantiateTarget(UniqueComponent target) throws StartingLineupException {
   		return target instanceof Batter;
	}
		
	protected void processTarget(UniqueComponent target) throws StartingLineupException {
		try {
    		if (target instanceof Batter) {
    			batter = (Batter) target;
    			addWAR(batter.getMetric());
    		}
		} catch (Exception e) {
			throw new StartingLineupException(e);
		}
	}
        
    private void addWAR(float metric) throws PlayerNotFoundException {
    	
		Batter b = (Batter) BatterMap.getInstance().get(batter);
		if (b == null) {
			return;
		}
		
		batter = b;
		
		WARMetric war = new WARMetric(metric);
		
		if (batter.getAdvancedMetrics() == null) {
		    AdvancedMetrics advancedMetrics = new AdvancedMetrics();
	        batter.setAdvancedMetrics(advancedMetrics);
		}
		
		batter.getAdvancedMetrics().addMetric(AdvancedMetric.MetricType.WAR, war);
		BatterMap.getInstance().replace(batter);
    }
}
