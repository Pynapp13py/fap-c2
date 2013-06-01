/**
 * 
 */
package com.fapddos.jstree;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSTree Root Object
 * @author Rev. H. Helix
 *
 */
public class JSTreeRoot {
	/**
	 * Elements of the tree
	 */
	private JSTreeElement[] data = null;

	/**
	 * Default constructor
	 */
	public JSTreeRoot() {
	}

	/**
	 * Add an element to the tree
	 * @param item An element
	 */
	public void add (JSTreeElement item) {
		if (data != null) {
			int size = data.length;
			int newsize = size +1;
			JSTreeElement[] ndata = new JSTreeElement[newsize];
			System.arraycopy(this.data, 0, ndata, 0, size);
			ndata[size] = item;
			data = ndata;
		} else {
			data = new JSTreeElement[1];
			data[0] = item;
		}
	}

	/**
	 * Make a JSON Object of this class
	 * @return A JSON Object of this class
	 */
	public String jsonify () {
		String retVal = null;
		try {
			Gson g = new GsonBuilder().setPrettyPrinting().create();
			retVal = g.toJson(this);
		} catch (Exception e) {

		}
		return retVal;
	}

}
