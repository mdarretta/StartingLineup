package org.startinglineup.data;

import java.io.File;

import org.startinglineup.component.Team;
import org.startinglineup.component.TeamNotFoundException;
import org.startinglineup.component.Teams;

public abstract class StartingPlayerImport extends TemplattedFileImport {
	
	protected StartingPlayerImport(File file) throws FileImportException {
		super(file);
	}

	protected Team getTeam(String abbr) throws TeamNotFoundException {
		return Teams.getInstance().getTeam(abbr);
	}
}
