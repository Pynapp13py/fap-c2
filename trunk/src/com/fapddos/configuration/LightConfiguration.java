package com.fapddos.configuration;

import java.util.ArrayList;

public class LightConfiguration {
	private ArrayList<String> targets = new ArrayList<String>();
	private String agent_url = null;
	
	public LightConfiguration() {
		
	}


	/**
	 * @return the targets
	 */
	public ArrayList<String> getTargets() {
		return targets;
	}


	/**
	 * @param targets the targets to set
	 */
	public void setTargets(ArrayList<String> targets) {
		this.targets = targets;
	}


	/**
	 * @return the agent_url
	 */
	public String getAgent_url() {
		return agent_url;
	}


	/**
	 * @param agent_url the agent_url to set
	 */
	public void setAgent_url(String agent_url) {
		this.agent_url = agent_url;
	}

}
