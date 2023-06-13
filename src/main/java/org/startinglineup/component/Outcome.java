package org.startinglineup.component;

public abstract class Outcome {

	public enum TotalBases {
		OUT(0, "Out"), WALK(1, "Walk"), HBP(1, "Hit By Pitch"), SINGLE(1, "Single"), 
		DOUBLE(2, "Double"), TRIPLE(3, "Triple"), HOMERUN(4, "Home Run");
		
		private int totalBases;
		private String name;
		
		private TotalBases(int totalBases, String name) {
			this.totalBases = totalBases;
			this.name = name;
		}
		
		public int getTotalBases() {
			return totalBases;
		}
		
		public String getName() {
			return name;
		}
	}
	
    private TotalBases totalBases;
    
    public Outcome(TotalBases totalBases) {
    	this.totalBases = totalBases;
    }

    public TotalBases getTotalBases() {
        return totalBases;
    }

    public abstract boolean isHit();

    public String toString() {
        return getTotalBases().getName();
    }
}
