package org.startinglineup.data;

import org.startinglineup.StartingLineupException;

public class PlayerNotFoundException extends StartingLineupException {
	
	/** The serial version ID. */
	private static final long serialVersionUID = 1L;

	public PlayerNotFoundException() {
		super();
	}
	
	public PlayerNotFoundException(Exception e) {
		super(e);
	}
	
	public PlayerNotFoundException(String message) {
		super(message);
	}
	
	public PlayerNotFoundException(String message, Exception e) {
		super(message, e);
	}
}
