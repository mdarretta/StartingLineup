package org.startinglineup.component;

import java.util.UUID;

/**
 * Represents a component object that requires a unique identifier.
 * @author Mike Darretta
 *
 */
public class UniqueComponent {

	/** The unique component ID */
	protected int id;

	/** Default constructor which sets the unique component ID. */
	public UniqueComponent() {
		super();
		this.id = UUID.randomUUID().hashCode();
	}

	/** 
	 * Returns the component ID.
	 * @return The unique comppnent ID.
	 * */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the component ID as an Integer.
	 * @return The component ID as an Integer.
	 */
	public Integer getIntId() {
		return Integer.valueOf(this.id);
	}
	
	/**
	 * Determines if UniqueComponent in question has the same ID as the input UniqueComponent.
	 * @param id The UniqueComponent ID to compare.
	 * @return True if the input ID equals this instance's ID.
	 */
	public boolean compare(int id) {
		return this.id == id;
	}
}
