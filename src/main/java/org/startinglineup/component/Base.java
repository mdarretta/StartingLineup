package org.startinglineup.component;

public class Base {

    private Position position;
    private boolean occupied;

    public enum Position {
    	
    	HOME(0), FIRST(1), SECOND(2), THIRD(3);
    	
    	private int position;
    	
    	private Position(int position) {
    		this.position = position;
    	}
    	
    	public int getPosition() {
    		return position;
    	}
    }

    public Base(Position position) {
        super();
        this.position = position;
        occupied = false;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void toggleOccupied() {
        occupied = !isOccupied();
    }

    public boolean isOccupied() {
        return occupied;
    }

    public Position getPosition() {
        return position;
    }
}
