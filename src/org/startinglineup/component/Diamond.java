package org.startinglineup.component;

public class Diamond {

    public static final int TOTAL_BASES = 4;

    private Base[] bases;

    public Diamond() {
   
        bases = new Base[TOTAL_BASES];
        bases[Base.Position.HOME.getPosition()] = new Base(Base.Position.HOME);
        bases[Base.Position.FIRST.getPosition()] = new Base(Base.Position.FIRST);
        bases[Base.Position.SECOND.getPosition()] = new Base(Base.Position.SECOND);
        bases[Base.Position.THIRD.getPosition()] = new Base(Base.Position.THIRD);
 
    }

    public Base getBase(Base.Position position) {
        return bases[position.getPosition()];
    }
    
    public Base getBase(int position) {
    	return bases[position];
    }

    public String toString() {
        return (bases[Base.Position.SECOND.getPosition()].isOccupied()  ? "    X    \n" : "    -    \n") +
               (bases[Base.Position.THIRD.getPosition()].isOccupied()   ? "X"         : "-") +
               (bases[Base.Position.FIRST.getPosition()].isOccupied()   ? "       X\n" :  "       -\n") +
               (bases[Base.Position.HOME.getPosition()].isOccupied()    ? "    X    " : "    -      ");
    }
}
