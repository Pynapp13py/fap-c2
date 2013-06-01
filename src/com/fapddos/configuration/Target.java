/**
 * 
 */
package com.fapddos.configuration;


/**
 * Target Object - Where your lazahs are going to be pointed
 * @author Rev. H. Helix
 *
 */
public class Target {
	/**
	 * Is this active
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
	 * The IP
	 */
	private String ip = null;
	/**
	 * Default constructor
	 */
	public Target() {
		
	}
	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the IP
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

}
