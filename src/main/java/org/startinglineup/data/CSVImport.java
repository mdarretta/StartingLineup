package org.startinglineup.data;

import java.io.File;

public abstract class CSVImport extends FileImport {
	
	/**
	 * Constructor for a file.
	 * @param file The file to process.
	 */
	public CSVImport(File file) {
		super(file);
		super.pattern = ",";
	}
}
