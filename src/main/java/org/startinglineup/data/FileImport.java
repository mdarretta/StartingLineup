package org.startinglineup.data;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class FileImport {
	
	/** The string to delineate patterns */
	protected String pattern;
	
	/** The file pathname to import */
	protected String pathname;
	
	/**
	 * Constructor for a particular file pathname.
	 * @param pathname The path to the file.
	 */
	public FileImport(String pathname) {
		this.pathname = pathname;
	}
	
	/**
	 * Processes the file import.
	 * @throws FileImportException Error processing the import file.
	 */
	public void run() throws FileImportException {

		BufferedReader br = null;
		String line = "";

		try {
			InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(pathname);
			br = new BufferedReader(new InputStreamReader(stream));
			while ((line = br.readLine()) != null) {
				String[] data = line.split(pattern,-1);
				process(data);
			}
		} catch (FileNotFoundException e) {
			throw new FileImportException("Cannot find file: " + pathname, e);
		} catch (IOException e) {
			throw new FileImportException(e);
		} catch (FileImportException e) {
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new FileImportException(e);
				}
			}
		}
	}
	
	/**
	 * Processes a line of data from the import file.
	 * @param data The line of data to process.
	 * @throws FileImportException Exception processing file data.
	 */
	protected abstract void process(String[] data) throws FileImportException;
}
