package org.startinglineup.data;

import org.startinglineup.component.Team;
import org.startinglineup.component.TeamNotFoundException;
import org.startinglineup.component.Teams;

public abstract class StartingPlayerImport extends TemplattedFileImport {
	
	protected StartingPlayerImport(String pathname) throws FileImportException {
		super(pathname);
	}

	protected Team getTeam(String abbr) throws TeamNotFoundException {
		return Teams.getInstance().getTeam(abbr);
	}
}
