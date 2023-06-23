package org.startinglineup.data;

import java.io.File;

import org.startinglineup.Properties;
import org.startinglineup.StartingLineupException;
import org.startinglineup.component.Pitcher;
import org.startinglineup.component.UniqueComponent;
import org.startinglineup.component.AdvancedMetric;
import org.startinglineup.component.WARMetric;

public class PitchingWARImport extends PlayerImport {
	
	private Pitcher pitcher;
        private WARMetric war;

	public PitchingWARImport(File file) throws FileImportException {
		super(file);
	}

	protected void instantiateTemplate() throws FileImportException {
        this.reader = new TemplateReader(new File(
        		Properties.getInstance().get(Properties.PITCHING_WAR_IMPORT_TEMPLATE_PROP)));
        reader.run();		
	}

	protected boolean instantiateTarget(UniqueComponent target) throws StartingLineupException {
   		return ((target instanceof WARMetric) || (target instanceof Pitcher));
	}
		
	protected void processTarget(UniqueComponent target) throws StartingLineupException {
		try {
    		if (target instanceof WARMetric) {
    			war = (WARMetric) target;
                        addWAR();
    		} else if (target instanceof Pitcher) {
    			pitcher = (Pitcher) target;
    		}
		} catch (Exception e) {
			throw new StartingLineupException(e);
		}
	}
        
    private void addWAR() throws PlayerNotFoundException {
    	
		pitcher = (Pitcher) PitcherMap.getInstance().get(pitcher);
		if (pitcher == null) {
			throw new PlayerNotFoundException(
					"Cannot find pitcher: " + pitcher.getLastname() + ", " + pitcher.getFirstname() + ".");
		}
                pitcher.getAdvancedMetrics().addMetric(AdvancedMetric.WAR, war);
		StartingRotationMap.getInstance().replace(team, pitcher);
    }
}
