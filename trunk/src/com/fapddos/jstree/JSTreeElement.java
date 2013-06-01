/**
 * 
 */
package com.fapddos.jstree;

import java.util.Hashtable;

/**
 * A JSTree Element
 * @author Rev. H. Helix
 *
 */
public class JSTreeElement {

	/**
	 * The data object
	 */
	private Hashtable<String, Object> data = new Hashtable<String,Object>();
/**
 * The children of this object
 */
	private JSTreeElement[] children = null;
	/**
	 * Attributes of this object
	 */
	private Hashtable<String, Object> attr = null;
	/**
	 * The folder state
	 */
	private String state = "open";
	/**
	 * The icon to use for this element
	 */
	private String icon = null;
	/**
	 * Default constructor
	 */
	public JSTreeElement() {
		
	}
	
	/**
	 * Add a data item to the object
	 * @param key Name of data item
	 * @param value The Value
	 */
	public void addData (String key, Object value) {
		if (this.data == null) {
			data = new Hashtable<String,Object>();
		}
		data.put(key, value);
	}
	
	/**
	 * Add an attribute to the object
	 * @param key Name of data item
	 * @param value The Value
	 */
	public void addAttr (String key, Object value) {
		if (this.attr == null) {
			attr = new Hashtable<String,Object>();
		}
		attr.put(key, value);
	}
	
	
	/**
	 * Add a child element
	 * @param item A child Element
	 */
	public void addChild (JSTreeElement item) {
		if (this.children!= null) {
			int size = this.children.length;
			int newsize = size +1;
			JSTreeElement[] ndata = new JSTreeElement[newsize];
			System.arraycopy(this.children, 0, ndata, 0, size);
			ndata[size] = item;
			this.children = ndata;
		} else {
			this.children = new JSTreeElement[1];
			this.children[0] = item;
		}
	}

	/**
	 * Get the folder state
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Set the folder state
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Get the icon
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Set the icon
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
