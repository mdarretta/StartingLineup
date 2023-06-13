package org.startinglineup.data;

import java.io.File;

import org.startinglineup.Properties;
import org.startinglineup.StartingLineupException;
import org.startinglineup.component.Batter;
import org.startinglineup.component.Team;
import org.startinglineup.component.UniqueComponent;
import org.startinglineup.league.League;
import org.startinglineup.league.MajorLeagues;

public class StartingLineupImport extends StartingPlayerImport {
	
	private Team team;
	private Batter batter;

	public StartingLineupImport(File file) throws FileImportException {
		super(file);
	}

	protected void instantiateTemplate() throws FileImportException {
        this.reader = new TemplateReader(new File(
        		Properties.getInstance().get(Properties.LINEUP_IMPORT_TEMPLATE_PROP)));
        reader.run();		
	}

	protected boolean instantiateTarget(UniqueComponent target) throws StartingLineupException {
   		return ((target instanceof Team) || (target instanceof Batter));
	}
	
	protected void processTarget(UniqueComponent target) throws StartingLineupException {
		try {
    		if (target instanceof Team) {
    			team = (Team) target;
    		} else if (target instanceof Batter) {
    			batter = (Batter) target;
    			addBatter();
    		}
		} catch (Exception e) {
			throw new StartingLineupException(e);
		}
	}
        
    private void addBatter() throws PlayerNotFoundException {
    	
    	League league = MajorLeagues.getInstance().getLeagueForTeam(team);
    	batter = (Batter) BatterMap.getInstance().get(batter);
    	
		if (league.getLeague().equals(League.LeagueType.NATIONAL)) {
			NLStartingLineupMap.getInstance().add(team, batter);
			// @todo clean up lineups for AL
			ALStartingLineupMap.getInstance().add(team, batter);
		} else {
			ALStartingLineupMap.getInstance().add(team, batter);
			// @todo clean up lineups for NL
			NLStartingLineupMap.getInstance().add(team, batter);
		}
    }
}
