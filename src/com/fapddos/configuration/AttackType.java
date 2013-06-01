/**
 * 
 */
package com.fapddos.configuration;

import java.util.Hashtable;

/**
 * An attack Type Data object
 * @author Rev. H. Helix
 *
 */
public class AttackType {
	/**
	 * Is the attack type active?
	 */
	private Boolean active = false;
	/**
	 * The ID of the attack type
	 */
	private String id = null;
	/**
	 * The title / name of the attack type
	 */
	private String title = null;
	/**
	 * The list of attacks for this attack type
	 */
	private Hashtable <String,String> attacks = null;
	/**
	 * Default constructor
	 */
	public AttackType() {
		
	}
	/**
	 * Get the Active State
	 * @return the active state
	 */
	public Boolean getActive() {
		return active;
	}
	/**
	 * Set the Active State
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	/**
	 * Get the ID
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * Set the ID
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * Get the title/name of the attack
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Set the title/name of the attack
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Get the attack list
	 * @return the attacks
	 */
	public Hashtable<String, String> getAttacks() {
		return attacks;
	}
	/**
	 * Set the attack list
	 * @param attacks the attacks to set
	 */
	public void setAttacks(Hashtable<String, String> attacks) {
		this.attacks = attacks;
	}

}
