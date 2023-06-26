package org.startinglineup.component;

public class Player extends UniqueComponent {

	protected String lastname;
	protected String firstname;

	/** Either the batting or pitching handedness of the player */
	protected Handed handed;
	
	/** 
	 * Helper field for adding WAR metrics during templatted import.
	 */
	protected float war;

	/** 
	 * Helper field for adding WAA metrics during templatted import.
	 */
	protected float waa;

	/** Map of advanced metrics, such as WAR or BABIP */
	protected AdvancedMetrics advancedMetrics;

	public enum Handed { 
		LEFT("Left","L"), RIGHT("Right","R"), SWITCH("Switch","S");

		private String name;
		private String abbr;

		private Handed(String name, String abbr) {
			this.name = name;
			this.abbr = abbr;
		}

		public String getName() {
			return name;
		}

		public String getAbbr() {
			return abbr;
		}

		public Handed resolve(String str) {
			Handed rtnEnum = null;
			if (str.equals(Handed.RIGHT.getName()) || str.equals(Handed.RIGHT.getAbbr())) {
				rtnEnum = Handed.RIGHT;
			} else if (str.equals(Handed.LEFT.getName()) || str.equals(Handed.LEFT.getAbbr())) {
				rtnEnum = Handed.LEFT;
			} else if (str.equals(Handed.SWITCH.getName()) || str.equals(Handed.SWITCH.getAbbr())) {
				rtnEnum = Handed.SWITCH;
			}

			return rtnEnum;
		}
	}

	public Player() {
		super();
	}

	public Player(String lastname, String firstname, Handed handed, AdvancedMetrics advancedMetrics) {
		this();
		this.lastname = lastname;
		this.firstname = firstname;
		this.handed = handed;
		if (advancedMetrics == null) {
			this.advancedMetrics = new AdvancedMetrics();
		} else {
			this.advancedMetrics = advancedMetrics;
		}
	}

	public Handed getHanded() {
		return handed;
	}

	/*public void setHanded(Handed handed) {
		this.handed = handed;
	}*/

	public String getLastname() {
		return lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getFormattedName() {
		return lastname + ", " + firstname;
	}

	public AdvancedMetrics getAdvancedMetrics() {
		return advancedMetrics;
	}
	
	public void setAdvancedMetrics(AdvancedMetrics advancedMetrics) {
		this.advancedMetrics = advancedMetrics;
	}
	
	public void setWar(float war) {
		this.war = war;
	}
	
	public float getWar() {
		return war;
	}
	
	public void setWaa(float waa) {
		this.waa = waa;
	}
	
	public float getWaa() {
		return waa;
	}
	
	public boolean equals(Player player) {
		return this.getLastname().equals(player.getLastname()) &&
				this.getFirstname().equals(player.getFirstname());
	}

	public String toString() {
		return getFormattedName();
	}
}
