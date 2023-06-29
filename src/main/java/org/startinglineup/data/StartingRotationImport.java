package org.startinglineup.data;

import org.startinglineup.Properties;
import org.startinglineup.StartingLineupException;
import org.startinglineup.component.Pitcher;
import org.startinglineup.component.Team;
import org.startinglineup.component.UniqueComponent;

public class StartingRotationImport extends StartingPlayerImport {
	
	private Team team;
	private Pitcher pitcher;

	public StartingRotationImport(String pathname) throws FileImportException {
		super(pathname);
	}

	protected void instantiateTemplate() throws FileImportException {
        this.reader = new TemplateReader(
        		Properties.getInstance().get(Properties.ROTATION_IMPORT_TEMPLATE_PROP));
        reader.run();		
	}

	protected boolean instantiateTarget(UniqueComponent target) throws StartingLineupException {
   		return ((target instanceof Team) || (target instanceof Pitcher));
	}
		
	protected void processTarget(UniqueComponent target) throws StartingLineupException {
		try {
    		if (target instanceof Team) {
    			team = (Team) target;
    		} else if (target instanceof Pitcher) {
    			pitcher = (Pitcher) target;
    			addPitcher();
    		}
		} catch (Exception e) {
			throw new StartingLineupException(e);
		}
	}
        
    private void addPitcher() throws PlayerNotFoundException {
    	
		pitcher = (Pitcher) PitcherMap.getInstance().get(pitcher);
		if (pitcher == null) {
			throw new PlayerNotFoundException(
					"Cannot find pitcher: " + pitcher.getLastname() + ", " + pitcher.getFirstname() + ".");
		}
		StartingRotationMap.getInstance().add(team, pitcher);
    }
}
