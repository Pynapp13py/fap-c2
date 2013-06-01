/**
 * This is the main configuration for FAP C2
 */
package com.fapddos.configuration;

import java.util.Hashtable;

/**
 * The Configuration Object for this Application
 * @author Rev. H. Helix
 *
 */
public class FAPConfig {
	/**
	 * Administrative password
	 */
	private String adminPassword = "21232f297a57a5a743894a0e4a801fc3";
	private Hashtable<String,Contention> contentions = new Hashtable<String,Contention>();
	private Hashtable<String,AttackType> AttackTypes = new Hashtable<String,AttackType>();
	private Hashtable<String,Agent> agents = new Hashtable<String,Agent>();
	private Hashtable<String,Target> targets = new Hashtable<String,Target>();
	/**
	 * Default constructor
	 */

	public FAPConfig() {

	}

	/**
	 * Get the password
	 * @return the adminPassword
	 */
	public String getAdminPassword() {
		return adminPassword;
	}

	/**
	 * Set the password
	 * @param adminPassword the adminPassword to set
	 */
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	/**
	 * Get your contentions
	 * @return the contentions
	 */
	public Hashtable<String,Contention> getContentions() {
		return contentions;
	}

	/**
	 * Set your contentions
	 * @param contentions the contentions to set
	 */
	public void setContentions(Hashtable<String,Contention> contentions) {
		this.contentions = contentions;
	}

	/**
	 * Get your attack types / arsenal 
	 * @return the attackTypes
	 */
	public Hashtable<String,AttackType> getAttackTypes() {
		return AttackTypes;
	}

	/**
	 * Set your attack types / arsenal 
	 * @param attackTypes the attackTypes to set
	 */
	public void setAttackTypes(Hashtable<String,AttackType> attackTypes) {
		AttackTypes = attackTypes;
	}

	/**
	 * @return the agents
	 */
	public Hashtable<String,Agent> getAgents() {
		return agents;
	}

	/**
	 * @param agents the agents to set
	 */
	public void setAgents(Hashtable<String,Agent> agents) {
		this.agents = agents;
	}

	/**
	 * @return the targets
	 */
	public Hashtable<String,Target> getTargets() {
		return targets;
	}

	/**
	 * @param targets the targets to set
	 */
	public void setTargets(Hashtable<String,Target> targets) {
		this.targets = targets;
	}

	public LightConfiguration getLightConfig(String ContentionId, String CampaignId){
		LightConfiguration retVal = new LightConfiguration();
		if (this.getContentions().containsKey(ContentionId)) {
			if(this.getContentions().get(ContentionId).getCampaigns().containsKey(CampaignId)) {
				String agentId = this.getContentions().get(ContentionId).getCampaigns().get(CampaignId).getAgent();
				String targetId = this.getContentions().get(ContentionId).getCampaigns().get(CampaignId).getTarget();
				if (this.getTargets().containsKey(targetId)) {
					Target curTarg = this.getTargets().get(targetId);
					retVal.getTargets().add(curTarg.getIp());
				} else {
					retVal.getTargets().add("0.0.0.0");
				}
				if (this.getAgents().containsKey(agentId)) {
					retVal.setAgent_url(this.getAgents().get(agentId).getUrl());
				} else {
					retVal.setAgent_url("0.0.0.0");
				}
			}
		}
		return retVal;
	}
}
