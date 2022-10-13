package org.startinglineup.component;

import org.startinglineup.StartingLineupException;

public class TeamNotFoundException extends StartingLineupException {

	/** The serial version ID */
	private static final long serialVersionUID = 1L;

	public TeamNotFoundException() {
		super();
	}

	public TeamNotFoundException(Exception e) {
		super(e);
	}

	public TeamNotFoundException(String message) {
		super(message);
	}

	public TeamNotFoundException(String message, Exception e) {
		super(message, e);
	}
}
