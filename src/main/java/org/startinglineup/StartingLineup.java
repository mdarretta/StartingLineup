package org.startinglineup;

import org.startinglineup.gui.MainWindow;

/**
 * Main class to start up the StartingLineup application.
 * 
 * @author Mike Darretta
 */
public class StartingLineup {
	
	/**
	 * Startup method.
	 * @param args No parameters are used.
	 */
    public static void main(String[] args) {
        new MainWindow().setVisible(true);
    }
}
