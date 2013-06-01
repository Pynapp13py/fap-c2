/**
 * 
 */
package com.fapddos.configuration;

import java.util.Hashtable;

/**
 * The Contention object
 * @author Rev. H. Helix
 *
 */
public class Contention {
	/**
	 * The list of campaigns associated with this contention
	 */
	private Hashtable<String,Campaign> Campaigns = new Hashtable<String,Campaign>();
	/**
	 * Is this conention active
	 */
	private Boolean active = false;
	/**
	 * The ID of the contention
	 */
	private String id = null;
	/**
	 * The title/name of the contention
	 */
	private String title = null;
	/**
	 * This is why you are mad bro.
	 */
	private String reason = null;
	/**
	 * Default constructor
	 */
	public Contention() {
	}
	/**
	 * Is this active?
	 * @return the active state
	 */
	public Boolean getActive() {
		return active;
	}
	/**
	 * Set the active state
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	/**
	 * Get the campaigns
	 * @return A list of campaigns 
	 */
	public Hashtable<String, Campaign> getCampaigns() {
		return Campaigns;
	}
	/**
	 * Set the campaigns
	 * @param campaigns The Campaigns
	 */
	public void setCampaigns(Hashtable<String, Campaign> campaigns) {
		Campaigns = campaigns;
	}
	/**
	 * Get the title / name
	 * @return A Title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Set the title / name
	 * @param title A title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Get why you are mad bro
	 * @return Why U mad!
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * Set why you are mad bro
	 * @param reason Why U mad!
	 */
	public void setReason(String reason) {
		this.reason = reason;
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

}
