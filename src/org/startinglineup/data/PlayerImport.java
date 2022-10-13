package org.startinglineup.data;

import java.io.File;

public abstract class PlayerImport extends TemplattedFileImport {
	
	public PlayerImport(File file) throws FileImportException {
		super(file);
	}
}
