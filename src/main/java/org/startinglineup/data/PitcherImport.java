package org.startinglineup.data;

import org.startinglineup.Properties;
import org.startinglineup.StartingLineupException;
import org.startinglineup.component.Pitcher;
import org.startinglineup.component.UniqueComponent;

public class PitcherImport extends PlayerImport {
	
	private Pitcher pitcher;

	public PitcherImport(String pathname) throws FileImportException {
		super(pathname);
	}

	protected void instantiateTemplate() throws FileImportException {
        this.reader = new TemplateReader(
        		Properties.getInstance().get(Properties.PITCHER_IMPORT_TEMPLATE_PROP));
        reader.run();		
	}

	protected boolean instantiateTarget(UniqueComponent target) throws StartingLineupException {
		boolean rtnBoolean = false;
		if (target instanceof Pitcher) {
			pitcher = (Pitcher) target;
			rtnBoolean = true;
		}
		
		return rtnBoolean;
	}
	
	protected void processTarget(UniqueComponent target) throws StartingLineupException {
		try {
			pitcher = (Pitcher) reader.getCurrentTarget();
	    	pitcher = (Pitcher) TemplateReader.parsePlayerName(pitcher);
			PitcherMap.getInstance().add(pitcher);		
		} catch (IllegalAccessException iae) {
			throw new StartingLineupException(iae);
		}
	}
}
