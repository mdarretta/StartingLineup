package org.startinglineup.data;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class FileImport {
	
	/** The import file */
	protected File file;
	
	/** The string to delineate patterns */
	protected String pattern;
	
	/**
	 * Constructor for a file.
	 * @param file The file to process.
	 */
	public FileImport(File file) {
		this.file = file;
	}
	
	/**
	 * Processes the file import.
	 * @throws FileImportException Error processing the import file.
	 */
	public void run() throws FileImportException {

		BufferedReader br = null;
		String line = "";

		try {
			
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				String[] data = line.split(pattern,-1);
				process(data);
			}

		} catch (FileNotFoundException e) {
			throw new FileImportException("Cannot find file: " + file.getAbsolutePath(), e);
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
