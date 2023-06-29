package org.startinglineup;

/**
 * The base exception handling class for this application.
 * 
 * @author Mike Darretta
 */
public class StartingLineupException extends Exception {

	/** The serial version ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public StartingLineupException() {
		super();
	}
	
	/**
	 * Constructor to wrap a caught exception.
	 * @param e The caught exception.
	 */
	public StartingLineupException(Exception e) {
		super(e);
	}
	
	/**
	 * Constructor for a message.
	 * @param message The message.
	 */
	public StartingLineupException(String message) {
		super(message);
	}
	
	/**
	 * Constructor for a message and a caught exception.
	 * @param message The message.
	 * @param e The caught exception.
	 */
	public StartingLineupException(String message, Exception e) {
		super(message, e);
	}
}
