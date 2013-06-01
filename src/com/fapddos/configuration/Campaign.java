package com.fapddos.configuration;

import java.util.Hashtable;
/**
 * A Campaign Object
 * @author Rev. H. Helix
 *
 */
public class Campaign {
	/**
	 * The ID of the campaign
	 */
	private String id = null;
	
	/**
	 * The target.
	 */
	private String target = null;
	/**
	 * The attack type id
	 */
	private String attacktype = null;
	/**
	 * The title
	 */
	private String title = "asdf";
	/**
	 * Is this active
	 */
	private Boolean active = false;
	
	/**
	 * The agent to use, null for all!
	 */
	private String agent = null;
	/**
	 * Default constructor
	 */
	public Campaign() {
	}

	/**
	 * Is this campaign active
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * Set this campaigns active state
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * Get the target ID
	 * @return the target id
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Set the target id
	 * @param target the target id
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Get the attack type id
	 * @return the attacktype id
	 */
	public String getAttacktype() {
		return attacktype;
	}

	/**
	 * Set the attack type id
	 * @param attacktype the attacktype id
	 */
	public void setAttacktype(String attacktype) {
		this.attacktype = attacktype;
	}

	/**
	 * Get the id
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the id
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the Agent ID
	 * @return the agent
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * Set the Agent ID
	 * @param agent the agent to set
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * Get the title of the campaign
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the campaign
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
