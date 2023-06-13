package org.startinglineup.data;

import org.startinglineup.StartingLineupException;

/**
 * Exception to throw when a file import error occurs.
 * @author Mike Darretta
 */
public class FileImportException extends StartingLineupException {

	/** The serial version ID */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public FileImportException() {
		super();
	}

	/**
	 * Constructor to wrap a caught exception.
	 * @param e The caught exception.
	 */
	public FileImportException(Exception e) {
		super(e);
	}
	
	/**
	 * Constructor for a message.
	 * @param message The message.
	 */
	public FileImportException(String message) {
		super(message);
	}

	/**
	 * Constructor for a message and a caught exception.
	 * @param message The message.
	 * @param e The caught exception.
	 */
	public FileImportException(String message, Exception e) {
		super(message, e);
	}
}
