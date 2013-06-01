/**
 * 
 */
package com.fapddos.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This will act as a basic response object that is expected to be made into JSON
 * @author Rev. H. Helix
 *
 */
public class JSONResponseObject {
	/**
	 * Do we have an error?
	 */
	private Boolean error = null;
	/**
	 * Were we authenticated
	 */
	private Boolean authOK = null;
	/**
	 * Did our action get performed without any issues?
	 */
	private Boolean actionOK = null;
	/**
	 * Is there any reason why the action failed? or is there something I need to know?
	 */
	private String reason = null;
	/**
	 * Did we log the action.
	 */
	private Boolean log = null;
	/**
	 * A data object, if the action is expected to retun a value.
	 */
	private Object data = null;
	/**
	 * The default constructor 
	 */
	public JSONResponseObject() {
	
	}
	/**
	 * Was there an error
	 * @return True if there was an error
	 */
	public Boolean getError() {
		return error;
	}
	/**
	 * Set if there was an error
	 * @param error True if there is an error
	 */
	public void setError(Boolean error) {
		this.error = error;
	}
	/**
	 * Was the user authenticated
	 * @return True if the user is authenticated
	 */
	public Boolean getAuthOK() {
		return authOK;
	}
	/**
	 * Set the user auth OK, true if cuddles.
	 * @param authOK True if the user is good
	 */
	public void setAuthOK(Boolean authOK) {
		this.authOK = authOK;
	}
	/**
	 * Did the action get performed as expected?
	 * @return True means Yay, and quite possibly Rawr!
	 */
	public Boolean getActionOK() {
		return actionOK;
	}
	
	/**
	 * Set the action if it was ok
	 * @param actionOK True means we are happy!
	 */
	public void setActionOK(Boolean actionOK) {
		this.actionOK = actionOK;
	}
	
	/**
	 * Make the object into JSON
	 * @return A JSON Object representing this class
	 */
	public String jsonify() {
		String retVal = null;
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		try {
			retVal = g.toJson(this);
		} catch (Exception e) {
			retVal = "{\"error\":1,\"reason\":\"Can not JSONify object.\",\"log\":true}";
		}
		return retVal;
	}
	/**
	 * The reason why something isn't quite right
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * Set the reason why something isn't quite right
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * Are we logging?
	 * @return True if big brother is around.
	 */
	public Boolean getLog() {
		return log;
	}
	/**
	 * Set if we are logging
	 * @param log True to be big brother.
	 */
	public void setLog(Boolean log) {
		this.log = log;
	}
	/**
	 * Did we have any data goodness?
	 * @return the data we may have wanted.
	 */
	public Object getData() {
		return data;
	}
	/**
	 * Set the data object payload
	 * @param data the data we may have wanted
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
