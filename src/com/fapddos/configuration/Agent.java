package com.fapddos.configuration;
/**
 * Agent Object
 * @author Rev. H. Helix
 *
 */
public class Agent {
	/**
	 * If the agent active
	 */
	private Boolean active = false;
	/**
	 * The ID of the agent
	 */
	private String id = null;
	/**
	 * The Title/Name of the Agent
	 */
	private String title = null;
	/**
	 * The URL of the agent
	 */
	private String url = null;
	
	/**
	 * Default constructor
	 */
	public Agent() {
	}

	/**
	 * If this agent active?
	 * @return the active state
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * Set the active state
	 * @param active the active state to set
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
	 * Get the title/name
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title / name
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * The URL of the agent
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set a URL for the agent
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
