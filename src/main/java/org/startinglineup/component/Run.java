package org.startinglineup.component;

public class Run {

    private boolean earned;
 
    public Run() {
        this(true);
    }

    public Run(boolean earned) {
        this.earned = earned;
    }
    
    public int getRun() {
    	return 1;
    }
    
    public boolean isEarned() {
    	return earned;
    }
}
