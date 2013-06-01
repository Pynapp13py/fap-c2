/**
 * 
 */
package com.fapddos.messages;

/**
 * The action reponse object, when True/False and simple answers do not fit!
 * @author Rev. H. Helix
 *
 */
public class ActionResponse {

	/**
	 * Was the action OK?
	 */
	private Boolean actionOK = null;
	/**
	 * Why was there a failure?
	 */
	private String reason = null;
	/**
	 * The long lost data we may have wanted from the unicorns
	 */
	private Object data = null;
	
	/**
	 * The new id of the object created.
	 */
	private String new_id = null;

	/**
	 * Default constructor
	 */
	public ActionResponse() {

	}

	/**
	 * Constructor
	 * @param ok True if the action was good
	 */
	public ActionResponse(boolean ok) {
		this.actionOK = ok;
	}

	/**
	 * Constructor
	 * @param ok True if the action was good
	 * @param reason Why was there a failure?
	 */
	public ActionResponse(boolean ok, String reason) {
		this.reason = reason;
		this.actionOK = ok;
	}

	/**
	 * Constructor
	 * @param ok True if the action was good
	 * @param reason Why was there a failure?
	 * @param data The long lost data we may have wanted from the unicorns
	 */
	public ActionResponse(boolean ok, String reason, Object data) {
		this.reason = reason;
		this.actionOK = ok;
		this.setData(data);
	}
	/**
	 * Was there goodness?
	 * @return the actionOK, True if the action was good
	 */
	public Boolean getActionOK() {
		return actionOK;
	}

	/**
	 * Set if goodness
	 * @param actionOK the actionOK to set,True if the action was good
	 */
	public void setActionOK(Boolean actionOK) {
		this.actionOK = actionOK;
	}

	/**
	 * Get Why U No Werk?!?!?!?
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Set Why U No Werk?!?!?!?
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * Get the candy
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Set the candy
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the new_id
	 */
	public String getNew_id() {
		return new_id;
	}

	/**
	 * @param new_id the new_id to set
	 */
	public void setNew_id(String new_id) {
		this.new_id = new_id;
	}

}
