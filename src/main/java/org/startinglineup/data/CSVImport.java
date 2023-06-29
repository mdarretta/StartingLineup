package org.startinglineup.data;

public abstract class CSVImport extends FileImport {
	
	/**
	 * Constructor a pathname.
	 * @param pathname The path to the file to import.
	 */
	public CSVImport(String pathname) {
		super(pathname);
		super.pattern = ",";
	}
}
