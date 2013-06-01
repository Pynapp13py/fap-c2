package com.fapddos.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fapddos.configuration.Agent;
import com.fapddos.configuration.AttackType;
import com.fapddos.configuration.Campaign;
import com.fapddos.configuration.Contention;
import com.fapddos.configuration.FAPConfig;
import com.fapddos.configuration.Target;
import com.fapddos.jstree.JSTreeElement;
import com.fapddos.jstree.JSTreeRoot;
import com.fapddos.messages.ActionResponse;
import com.fapddos.messages.JSONResponseObject;
import com.fapddos.utils.Generic;
import com.google.gson.*;
import org.apache.commons.codec.binary.Base64;


/**
 * The core of the unicorn servlet goodness.
 * @author Rev. H. Helix
 */
@WebServlet("/Core")
public class Core extends HttpServlet {
	/**
	 *  Serial Version UID
	 */
	private static final long serialVersionUID = 123L;
	/**
	 * FAP Configuration
	 */
	private static FAPConfig fapConfiguration = null;
	/**
	 * FAP Configuration file
	 */
	private static String fapConfigFile = "/etc/fap.conf";
	/**
	 * Log list
	 */
	private static ArrayList<String> log = new ArrayList<String>();
	/**
	 * Allow logging
	 */
	private static Boolean allowLog = true;
	/**
	 * Debug mode on or off
	 */
	private static Boolean debug = true;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Core() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {

	}

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {

		return null;
	}

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {

		return null; 
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMyBidding(request,response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMyBidding(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMyBidding(request,response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMyBidding(request,response);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMyBidding(request,response);
	}

	/**
	 * @see HttpServlet#doHead(HttpServletRequest, HttpServletResponse)
	 */
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMyBidding(request,response);
	}

	/**
	 * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMyBidding(request,response);
	}

	/**
	 * @see HttpServlet#doTrace(HttpServletRequest, HttpServletResponse)
	 */
	protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMyBidding(request,response);
	}

	/**
	 * Do My Bidding Slaves!!!
	 * @param request The Request Object
	 * @param response The Response Object
	 * @throws ServletException Stuff went wrong
	 * @throws IOException Issues with IO
	 */
	protected void doMyBidding(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (Core.fapConfiguration == null) {
				//try to load configuration from file if it exists
				Core.restoreFapConfig();
			}
			HttpSession session = request.getSession(true);
			response.setContentType("application/json");
			JSONResponseObject jr = new JSONResponseObject();

			boolean auth = false;
			//Get session items
			if (session != null) {
				Enumeration<String> sessionProps = session.getAttributeNames();
				if(sessionProps != null) {
					while (sessionProps.hasMoreElements()) {
						String prop = sessionProps.nextElement();
						if(prop.equals("auth")) {
							try {
								Boolean si = (Boolean) session.getAttribute("auth");
								if (si) { 
									auth = true;
								}
							} catch (Exception e) {
								auth = false;
							}
						} 
					}
				}
			}

			String action = Core.getRequestItem("action", request);
			String type = Core.getRequestItem("type", request);
			String target = Core.getRequestItem("target", request);
			String contention = Core.getRequestItem("contention", request);
			String campaign = Core.getRequestItem("campaign", request);
			String attackType = Core.getRequestItem("attacktype", request);
			String agent = Core.getRequestItem("agent", request);

			if(Core.getDebug()) {
				System.out.println("* Request Attributes *");
				System.out.println("Is Auth: " + auth);
				System.out.println("Action: " + action);
				System.out.println("Type: " + type);
				System.out.println("Contention: " + contention);
				System.out.println("Campaign: " + campaign);
				System.out.println("AttackType: " + attackType);
				System.out.println("Agent: " + agent);
				System.out.println("Target: " + target);
				System.out.println("");
			}


			if (auth) {
				//Default to failed state.
				this.setFail(jr,"Invalid action of "+action+".");
				if (action != null) {
					if (action.equals("write_config")) { //Push configuration to disk
						if (this.writeFapConfig().getActionOK()) {
							this.setOK(jr);
						} else {
							this.setFail(jr);
						}
					} else if (action.equals("chadminpass")) { //Change the password
						ActionResponse changed = new ActionResponse(false);			
						String confpass1 = Core.getRequestItem("confpass1", request);
						String confpass2 = Core.getRequestItem("confpass2", request);
						changed = this.changePassword(confpass1, confpass2);
						if (changed.getActionOK()) { 
							this.setOK(jr);
						} else {
							this.setFail(jr,"Unable to change password." + changed.getReason());
						}
					} else if (action.equals("gettree")) { //Get the tree got the UI
						ActionResponse tree = this.getTree();
						if (tree.getActionOK()) {
							this.setOK(jr,tree.getData());
						} else {
							this.setFail(jr,tree.getReason());
						}
					} else if (action.equals("add")) { //Add request handlers
						if (type.equals("campaign")) {
							ActionResponse res = new ActionResponse(false);
							try {
								String title = Core.getRequestItem("title", request);
								res =  Core.addCampaign(title, contention, target, attackType, agent);
							} catch (Exception e) {
								res.setReason(e.getMessage());
							}
							if (res.getActionOK()) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						} else if (type.equals("contention")) {
							String title = Core.getRequestItem("title", request);
							String reason = Core.getRequestItem("reason", request);
							ActionResponse added = new ActionResponse(false); 
							if (!title.equals("") && !reason.equals("")) {
								added = Core.addContention(title, reason);
							}
							if (added.getActionOK()) {
								this.setOK(jr);
							} else {
								this.setFail(jr,added.getReason());
							}
						} else if (type.equals("attacktype")) {
							String title = Core.getRequestItem("title", request);
							String code = Core.getRequestItem("code", request);
							ActionResponse res = Core.addAttackType(title, code) ;
							if (res.getActionOK() ) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						} else if (type.equals("target")) {
							String title = Core.getRequestItem("title", request);
							String code = Core.getRequestItem("ip", request);
							ActionResponse res = Core.addTarget(title, code) ;
							if (res.getActionOK() ) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						} else if (type.equals("agent")) {
							String title = Core.getRequestItem("title", request);
							String url = Core.getRequestItem("url", request);
							ActionResponse res = Core.addAgent( title,url);
							if (res.getActionOK() ) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						}
					} else if (action.equals("del")) { //Delete handlers
						if (type.equals("campaign") && campaign != null && contention != null) {
							ActionResponse res = new ActionResponse(false);
							try {
								Campaign rm = Core.fapConfiguration.getContentions().get(contention).getCampaigns().remove(campaign);
								if (rm == null) {
									throw new Exception("Doesn't exist");
								}
								res.setActionOK(true);
							} catch (Exception e) {
								this.setFail(jr,"Unable to remove Campaign because it doesn' exist.");
							}
							if (res.getActionOK()) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}

						} else if (type.equals("contention")&& contention != null) {
							if (Core.fapConfiguration.getContentions().containsKey(contention)) {
								Contention rm = Core.fapConfiguration.getContentions().remove(contention);
								if (rm != null) {
									this.setOK(jr);
								} else {
									this.setFail(jr, "Contention doesn't exist.");
								}
								this.setOK(jr);
							} else {
								this.setFail(jr,"Unable to remove Contention because it doesn' exist.");
							}

						} else if (type.equals("attacktype")&& attackType != null) {
							ActionResponse res = new ActionResponse(false);
							try {
								AttackType rm = Core.fapConfiguration.getAttackTypes().remove(attackType);
								if (rm != null) {
									res.setActionOK(true);
								} else {
									res.setReason("Attack Type doesn't exist");
								}
							} catch (Exception e) {
								this.setFail(jr,"Unable to remove Attack Type because it doesn' exist.");
							}
							if (res.getActionOK()) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						} else if (type.equals("agent")&& agent != null) {
							ActionResponse res = new ActionResponse(false);

							try {
								Agent rm = Core.fapConfiguration.getAgents().remove(agent);
								if (rm != null) {
									res.setActionOK(true);
								} else {
									res.setReason("Agent doesn't exist");
								}
							} catch (Exception e) {
								this.setFail(jr,"Unable to remove Agent because it doesn' exist.");
							}
							if (res.getActionOK()) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						} else if (type.equals("target")&& agent != null) {
							ActionResponse res = new ActionResponse(false);

							try {
								Target rm = Core.fapConfiguration.getTargets().remove(target);
								if (rm != null) {
									res.setActionOK(true);
								} else {
									res.setReason("Target doesn't exist");
								}
							} catch (Exception e) {
								this.setFail(jr,"Unable to remove Agent because it doesn' exist.");
							}
							if (res.getActionOK()) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						} else {
							this.setFail(jr,"Invalid Type: " + type);
						}
					} else if (action.equals("update")) { //Update handlers
						if (type.equals("campaign") && campaign != null) {
							ActionResponse res = new ActionResponse(false);
							try {
								String title = Core.getRequestItem("title", request);
								res =  Core.editCampaign(campaign,title, contention, target, attackType, agent);
							} catch (Exception e) {
								res.setReason(e.getMessage());
							}
							if (res.getActionOK()) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						} else if (type.equals("contention")&& contention != null) {
							String title = Core.getRequestItem("title", request);
							String reason = Core.getRequestItem("reason", request);
							ActionResponse ed = Core.editContention(contention, title, reason);
							if (ed.getActionOK()) {
								this.setOK(jr);
							} else {
								this.setFail(jr,ed.getReason());
							}
						} else if (type.equals("attacktype")&& attackType != null) {

							String title = Core.getRequestItem("title", request);
							String code = Core.getRequestItem("code", request);
							ActionResponse res = Core.editAttackType(attackType,title, code) ;
							if (res.getActionOK() ) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						} else if (type.equals("agent")&& agent != null) {
							String title = Core.getRequestItem("title", request);
							String url = Core.getRequestItem("url", request);
							ActionResponse res = Core.editAgent(agent, title,url);
							if (res.getActionOK() ) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						} else if (type.equals("target")&& target != null) {
							String title = Core.getRequestItem("title", request);
							String ip = Core.getRequestItem("ip", request);
							ActionResponse res = Core.editTarget(target, title,ip);
							if (res.getActionOK() ) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						}
					} else if (action.equals("activate")) { //Activate handlers that may not be implemented yet
						if (type.equals("Campaign") && campaign != null) {
							ActionResponse res = new ActionResponse(false);
							try {

							} catch (Exception e) {
								this.setFail(jr,"Unable to activate campaign.");
							}
							if (res.getActionOK()) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						} else if (type.equals("contention")&& contention != null) {
							ActionResponse res = new ActionResponse(false);
							try {

							} catch (Exception e) {
								this.setFail(jr,"Unable to activate contention.");
							}
							if (res.getActionOK()) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						} else if (type.equals("attacktype")&& attackType != null) {
							ActionResponse res = new ActionResponse(false);
							try {

							} catch (Exception e) {
								this.setFail(jr,"Unable to activate attack type.");
							}
							if (res.getActionOK()) {
								this.setOK(jr);
							} else {
								this.setFail(jr,res.getReason());
							}
						}
					} else if (action.equals("log")) { //get the log
						this.setOK(jr, Core.log, null);
					} else if (action.equals("logout")) { //logout
						this.setOK(jr, "AUTH INVALIDATED");
						session.setAttribute("auth", new Boolean(false));
					} else if (action.equals("push_configuration")) { //push a new configuration
						String config = Core.getRequestItem("config", request);
						ActionResponse ok = pushConfiguration(config);
						if (ok.getActionOK()) {
							this.setOK(jr);
						} else {
							this.setFail(jr, "Invalid configuration.");
						}
					} else if (action.equals("get_configuration")) { //get the configuration
						this.setOK(jr,Core.fapConfiguration ,null);
					}else if (action.equals("auth")) { //authenticate (again)
						ActionResponse res = this.authUser (request, session, jr);
						if (!res.getActionOK()) {
							this.setFail(jr);
							jr.setAuthOK(false);
						}
					}
				}
			} else {
				//Default to failed state.
				this.setFail(jr, "Invalid access.");
				if (action.equals("auth")) { //Auth the users
					this.authUser (request, session, jr);
				}
			}
			response.getWriter().write(jr.jsonify());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Change the password
	 * @param pass1 Password 1
	 * @param pass2 Password 2
	 * @return True if the password has been changed
	 */

	public ActionResponse changePassword(String pass1,String pass2) {
		ActionResponse retVal = new ActionResponse(false);
		try {
			if (pass1 != null && pass2 != null) {
				if(pass1.equals(pass2)) {
					String passhash = this.md5(pass1);
					Core.fapConfiguration.setAdminPassword(passhash);
					retVal = new ActionResponse(true);
				}else {
					retVal = new ActionResponse(false,"Passwords do not match.");
				}
			} else {
				retVal = new ActionResponse(false,"Password can not be null.");
			}
		} catch (Exception e) {
			retVal = new ActionResponse(false,e.getMessage());
		}
		return retVal;
	}

	/**
	 * Get the JSTree
	 * @return The JSTree
	 */
	public ActionResponse getTree() {
		ActionResponse retVal = new ActionResponse(false);
		try {
			JSTreeRoot tree = new JSTreeRoot();
			//Do contentions
			JSTreeElement cont = new JSTreeElement();
			cont.addAttr("id", "42b38a02-24e3-4a9e-96cb-4729ae84ad3c");
			cont.addData("id", "42b38a02-24e3-4a9e-96cb-4729ae84ad3c");
			cont.addAttr("type", "contentions");
			cont.addData("title", "Contentions");
			Hashtable<String,Object> contProps = new Hashtable<String,Object>();
			contProps.put("href", "#");
			cont.addData("attr", contProps);
			cont.addData("parent","root");
			cont.addAttr("parent","root");
			cont.setIcon("img/dot.png");

			for (String conKey: Core.fapConfiguration.getContentions().keySet()) {
				Contention cur = Core.fapConfiguration.getContentions().get(conKey);
				JSTreeElement myCont = new JSTreeElement();

				myCont.addAttr("id", cur.getId());
				myCont.addData("id", cur.getId());
				myCont.addAttr("type", "contentions");
				myCont.addData("type", "contentions");
				myCont.addAttr("title", cur.getTitle());
				myCont.addData("title", cur.getTitle());
				myCont.addAttr("reason", cur.getReason());
				myCont.addData("reason", cur.getReason());
				myCont.addAttr("parent", "42b38a02-24e3-4a9e-96cb-4729ae84ad3c");
				myCont.addData("parent", "42b38a02-24e3-4a9e-96cb-4729ae84ad3c");


				Hashtable<String,Object> contProp = new Hashtable<String,Object>();
				contProp.put("href", "#");
				myCont.addData("attr", contProp);

				//Put campaigns
				for (String campKey : Core.fapConfiguration.getContentions().get(conKey).getCampaigns().keySet()) {
					Campaign curCamp = Core.fapConfiguration.getContentions().get(conKey).getCampaigns().get(campKey);
					JSTreeElement myCamp = new JSTreeElement();
					
					myCamp.addAttr("id", curCamp.getId());
					myCamp.addData("id", curCamp.getId());
					myCamp.addAttr("parent", cur.getId());
					myCamp.addData("parent", cur.getId());
					myCamp.addAttr("type", "campaign");
					myCamp.addData("type", "campaign");
					myCamp.addAttr("title", curCamp.getTitle());
					myCamp.addData("title", curCamp.getTitle());
					myCamp.addAttr("target",curCamp.getTarget());
					myCamp.addData("target", curCamp.getTarget());
					myCamp.addAttr("agent",curCamp.getAgent());
					myCamp.addData("agent", curCamp.getAgent());
					myCamp.addAttr("attacktype",curCamp.getAttacktype());
					myCamp.addData("attacktype",curCamp.getAttacktype());
					myCont.addChild(myCamp);
				}
				
				
				
				//Add to tree
				cont.addChild(myCont);
			}


			//Do attack Types
			JSTreeElement attack = new JSTreeElement();
			attack.addAttr("id", "42b38a02-24e3-4a9e-96cb-4829ae84ad3c");
			attack.addAttr("type", "attacktypes");
			attack.addData("title", "Attack Types");
			Hashtable<String,Object> attackProps = new Hashtable<String,Object>();
			attackProps.put("href", "#");
			attack.addData("attr", attackProps);
			attack.setIcon("img/atty.png");
			attack.addData("parent","root");
			attack.addAttr("parent","root");
			for (String attackKey: Core.fapConfiguration.getAttackTypes().keySet()) {
				AttackType cur = Core.fapConfiguration.getAttackTypes().get(attackKey);
				JSTreeElement myAttackType = new JSTreeElement();

				myAttackType.addAttr("id", cur.getId());
				myAttackType.addData("id", cur.getId());
				myAttackType.addAttr("type", "attacktype");
				myAttackType.addData("type", "attacktype");
				myAttackType.addAttr("title", cur.getTitle());
				myAttackType.addData("title", cur.getTitle());
				myAttackType.addAttr("default",cur.getAttacks().get("default"));
				myAttackType.addData("default", cur.getAttacks().get("default"));
				myAttackType.addAttr("parent", "42b38a02-24e3-4a9e-96cb-4829ae84ad3c");
				myAttackType.addData("parent", "42b38a02-24e3-4a9e-96cb-4829ae84ad3c");

				Hashtable<String,Object> attackProp = new Hashtable<String,Object>();
				attackProp.put("href", "#");
				myAttackType.addData("attr", attackProp);

				//Add to tree
				attack.addChild(myAttackType);
			}


			//Do targets
			JSTreeElement targets = new JSTreeElement();
			targets.addAttr("id", "42b38a02-24e3-4a9e-96cb-4729ae85ad3c");
			targets.addAttr("type", "targets");
			targets.addData("title", "Targets");
			Hashtable<String,Object> targetsProps = new Hashtable<String,Object>();
			targetsProps.put("href", "#");
			targets.addData("attr", targetsProps);
			targets.setIcon("img/dot.png");
			targets.addData("parent","root");
			targets.addAttr("parent","root");

			for (String targ : Core.fapConfiguration.getTargets().keySet()){
				Target cur = Core.fapConfiguration.getTargets().get(targ);
				JSTreeElement myTarget = new JSTreeElement();

				myTarget.addAttr("id", cur.getId());
				myTarget.addData("id", cur.getId());
				myTarget.addAttr("type", "target");
				myTarget.addData("type", "target");
				myTarget.addAttr("title", cur.getTitle());
				myTarget.addData("title", cur.getTitle());
				myTarget.addAttr("ip",cur.getIp());
				myTarget.addData("ip", cur.getIp());
				myTarget.addAttr("parent", "42b38a02-24e3-4a9e-96cb-4729ae85ad3c");
				myTarget.addData("parent", "42b38a02-24e3-4a9e-96cb-4729ae85ad3c");

				Hashtable<String,Object> agentProp = new Hashtable<String,Object>();
				agentProp.put("href", "#");
				myTarget.addData("attr", agentProp);

				targets.addChild(myTarget);
			}

			//Do Agents
			JSTreeElement agents = new JSTreeElement();
			agents.addAttr("id", "42b38a02-24a3-4a9e-96cb-4729ae85ad3c");
			agents.addAttr("type", "agents");
			agents.addData("title", "Agents");
			Hashtable<String,Object> agentsProps = new Hashtable<String,Object>();
			agentsProps.put("href", "#");
			agents.addData("attr", agentsProps);
			agents.setIcon("img/dot.png");
			agents.addData("parent","root");
			agents.addAttr("parent","root");
			for (String agentKey: Core.fapConfiguration.getAgents().keySet()) {
				Agent cur = Core.fapConfiguration.getAgents().get(agentKey);
				JSTreeElement myAgent = new JSTreeElement();

				myAgent.addAttr("id", cur.getId());
				myAgent.addData("id", cur.getId());
				myAgent.addAttr("type", "agent");
				myAgent.addData("type", "agent");
				myAgent.addAttr("title", cur.getTitle());
				myAgent.addData("title", cur.getTitle());
				myAgent.addAttr("url", URLEncoder.encode(cur.getUrl(),"UTF-8"));
				myAgent.addData("url", URLEncoder.encode(cur.getUrl(),"UTF-8"));
				myAgent.addAttr("parent", "42b38a02-24a3-4a9e-96cb-4729ae85ad3c");
				myAgent.addData("parent", "42b38a02-24a3-4a9e-96cb-4729ae85ad3c");

				Hashtable<String,Object> agentProp = new Hashtable<String,Object>();
				agentProp.put("href", "#");
				myAgent.addData("attr", agentProp);

				//Add to tree
				agents.addChild(myAgent);
			}


			//Do configuration
			JSTreeElement config = new JSTreeElement();
			config.addAttr("id", "42b38a02-24e3-4a9e-96cb-4729ae85a44c");
			config.addAttr("type", "configuration");
			config.addData("title", "Configuration");
			Hashtable<String,Object> configProps = new Hashtable<String,Object>();
			configProps.put("href", "#");
			config.addData("attr", configProps);
			config.setIcon("img/dot.png");
			config.addData("parent","root");
			config.addAttr("parent","root");

			tree.add(cont);
			tree.add(attack);
			tree.add(targets);
			tree.add(agents);
			tree.add(config);
			retVal = new ActionResponse(true,null,tree);
		} catch (Exception e) {
			e.printStackTrace();
			retVal = new ActionResponse(false,e.getMessage());
		}
		return retVal;
	}
	/**
	 * Add a Target
	 * @param title The name of the agent
	 * @param ip The IP
	 * @return Creamy Goodness
	 */
	public static ActionResponse addTarget (String title, String ip) {
		return editTarget(null,title,  ip);
	}


	/**
	 * Edit a Target
	 * @param ID The GUID of an agent (null for new)
	 * @param title The name of the agent
	 * @param ip The IP
	 * @return Creamy Goodness
	 */
	public static ActionResponse editTarget (String ID, String title, String ip) {
		ActionResponse retVal = new ActionResponse(false);		
		try {
			if (ID != null) {
				//Update
				if (Core.getFapConfiguration().getTargets().containsKey(ID)) {
					Core.getFapConfiguration().getTargets().get(ID).setTitle(title);
					Core.getFapConfiguration().getTargets().get(ID).setIp(ip);
					retVal.setActionOK(true);
				} else {
					retVal.setReason("Target does not exist");
				}
			} else {
				//New
				Target cur = new Target();
				cur.setActive(true);
				cur.setId(Generic.createGUID());
				cur.setTitle(title);
				cur.setIp(ip);
				Core.fapConfiguration.getTargets().put(cur.getId(), cur);
				retVal.setActionOK(true);
				retVal.setNew_id(cur.getId());
			}
		} catch (Exception e) {
			retVal.setReason(e.getMessage());
		}
		return retVal;
	}



	/**
	 * Add a campaign
	 * @param title The Title
	 * @param targetId The target ID
	 * @param attackId The Attack ID
	 * @return A response
	 */
	public static ActionResponse addCampaign (String title, String contentionId,String targetId, String attackId, String agentId) {
		return editCampaign(null,title, contentionId, targetId, attackId,agentId);
	}

	/**
	 * Edit a campaign
	 * @param id The Campaign ID
	 * @param title The Title
	 * @param targetId The target ID
	 * @param attackId The Attack ID
	 * @return A response
	 */
	public static ActionResponse editCampaign (String id, String title, String contentionId,String targetId, String attackId,String agentId) {
		ActionResponse retVal = new ActionResponse(false);
		try {
			boolean add = true;
			if (targetId != null && targetId != "" && targetId != "null") {
				if(!Core.fapConfiguration.getTargets().containsKey(targetId)) {
					add = false;
					retVal.setReason("Target doesn't exist");
				}
			} else {
				add = false;
				retVal.setReason("Target doesn't exist");
			}

			if (attackId != null && attackId != "" && attackId != "null" && add) {
				if(!Core.fapConfiguration.getAttackTypes().containsKey(attackId)) {
					add = false;
					retVal.setReason("Attack Type does't exist");
				}
			} else if (add)  {
				add = false;
				retVal.setReason("Attack Type doesn't exist");
			}
			if (agentId != null && agentId != "" && agentId != "null" && add) {
				if(!Core.fapConfiguration.getAgents().containsKey(agentId)) {
					add = false;
					retVal.setReason("Agent doesn't exist");
				}
			} else if (add)  {
				add  = false;
				retVal.setReason("Agent ID not specified");
			}
			//Does the contention exist
			if (contentionId != null && contentionId!="" && contentionId!="null"){
				if (!Core.fapConfiguration.getContentions().containsKey(contentionId)) {
					add = false;
					retVal.setReason("Contention doesn't exist");
					System.out.println("Fails : "+contentionId);
				}
			} else if (add)  {
				add = false;
				retVal.setReason("Contention ID not specified");
			}
			if (add) {
				//Is this supposed to be new?
				if (id == null || id=="null") {
					Campaign cur = new Campaign();
					cur.setId(Generic.createGUID());
					cur.setAttacktype(attackId);
					cur.setTarget(targetId);
					cur.setAgent(agentId);
					cur.setTitle(title);
					if (add) {
						Core.fapConfiguration.getContentions().get(contentionId).getCampaigns().put(cur.getId(), cur);
						retVal.setActionOK(true);
						retVal.setNew_id(cur.getId());
					}
				} else {
					//We are going to perform an update and we know it.
					if (Core.fapConfiguration.getContentions().get(contentionId).getCampaigns().containsKey(id)) {
						if(add) {
							Core.fapConfiguration.getContentions().get(contentionId).getCampaigns().get(id).setAttacktype(attackId);
							Core.fapConfiguration.getContentions().get(contentionId).getCampaigns().get(id).setTarget(targetId);
							Core.fapConfiguration.getContentions().get(contentionId).getCampaigns().get(id).setAgent(agentId);
							Core.fapConfiguration.getContentions().get(contentionId).getCampaigns().get(id).setTitle(title);
							retVal.setActionOK(true);
						}
					} else {
						retVal.setReason("The campaign doesn't exist");
					}
				}
			}
		} catch (Exception e) {
			retVal.setReason(e.getMessage());
		}
		return retVal;
	}


	/**
	 * Add an agent
	 * @param title The name of the agent
	 * @param url The URL of the agent
	 * @return Creamy Goodness
	 */
	public static ActionResponse addAgent (String title, String url) {
		return editAgent(null,title,  url);
	}

	/**
	 * Edit  an agent
	 * @param ID The GUID of an agent (null for new)
	 * @param title The name of the agent
	 * @param url The URL of the agent
	 * @return Creamy Goodness
	 */
	public static ActionResponse editAgent (String ID, String title, String url) {
		ActionResponse retVal = new ActionResponse(false);		
		try {
			if (ID != null) {
				//Update
				if (Core.getFapConfiguration().getAgents().containsKey(ID)) {
					Core.getFapConfiguration().getAgents().get(ID).setTitle(title);
					Core.getFapConfiguration().getAgents().get(ID).setUrl(url);
					retVal.setActionOK(true);
				} else {
					retVal.setReason("Agent does not exist");
				}
			} else {
				//New
				Agent cur = new Agent();
				cur.setActive(true);
				cur.setId(Generic.createGUID());
				cur.setTitle(title);
				cur.setUrl(url);
				Core.fapConfiguration.getAgents().put(cur.getId(), cur);
				retVal.setActionOK(true);
				retVal.setNew_id(cur.getId());
			}
		} catch (Exception e) {
			retVal.setReason(e.getMessage());
		}
		return retVal;
	}

	/**
	 * Add an attack type
	 * @param title The name of the attack type
	 * @param code The code for the attack type
	 * @return Object of goodness
	 */
	public static ActionResponse addAttackType (String title, String code) {
		return editAttackType(null, title,code);
	}

	/**
	 * Edits (or adds) an Attack Type
	 * @param ID The ID of an attack type (Null if new!)
	 * @param title The name of the attack type
	 * @param code The code for the attack type
	 * @return Goodness upon success
	 */
	public static ActionResponse editAttackType (String ID, String title, String code) {
		ActionResponse retVal = new ActionResponse(false);
		if (ID == null) {
			boolean ok = true;
			for (String curKey : Core.fapConfiguration.getAttackTypes().keySet()) {
				if (ok) {
					if (title.equals(Core.fapConfiguration.getAttackTypes().get(curKey).getTitle())) {
						ok = false;
						retVal = new ActionResponse(false,"Attack Type already exists.");
					}
				}
			}
			if (ok) {
				AttackType cur = new AttackType();
				cur.setActive(true);
				cur.setTitle(title);
				Hashtable<String,String> attacks = new Hashtable<String,String>();
				attacks.put("default", code);
				cur.setAttacks(attacks);
				cur.setId(Generic.createGUID());
				Core.fapConfiguration.getAttackTypes().put(cur.getId(), cur);
				System.out.println("ADDED");
				retVal.setActionOK(true);
				retVal.setNew_id(cur.getId());
			}
		} else {
			if (Core.fapConfiguration.getAttackTypes().containsKey(ID)) {
				AttackType cur = Core.fapConfiguration.getAttackTypes().get(ID);
				cur.setTitle(title);
				Hashtable<String,String> attacks = new Hashtable<String,String>();
				attacks.put("default", code);
				cur.setAttacks(attacks);
				retVal.setActionOK(true);
			} else {
				retVal.setActionOK(false);
				retVal.setReason("Attack Type Is Not Defined.");
			}
		}
		return retVal;
	}
	/**
	 * Applies a new configuration
	 * @param config The new configuration
	 * @return Goodness upon success
	 */
	public ActionResponse pushConfiguration(String config) {
		//String config = this.base64Decode(configuration);
		ActionResponse retVal = new ActionResponse(false);
		FAPConfig conf = null;
		try {
			Gson g = new Gson();
			try {
				conf = g.fromJson(config, FAPConfig.class);
			} catch (Exception e) {
				conf = null;
			}
			if (conf != null) {
				//lets do some basic checks for sanity
				if (conf.getAdminPassword() != null) {
					retVal = new ActionResponse(true);
				} else {
					retVal = new ActionResponse(false,"Password can not be null.");
				}
				if (retVal.getActionOK() && conf.getAttackTypes() == null) {
					retVal = new ActionResponse(false,"No Attack Types defined.");
				} 
				if (retVal.getActionOK() && conf.getContentions() == null) {
					retVal = new ActionResponse(false,"No Contentions defined.");
				}
				Core.setFapConfiguration(conf);
				retVal = new ActionResponse(true);
			} else {
				retVal = new ActionResponse(false,"Invalid configuration given.");
			}

		} catch (Exception e) {
			retVal = new ActionResponse(false,e.getMessage());
		}
		return retVal;
	}

	/**
	 * Set the JSONReponse Object to goodness
	 * @param jro The Response Object
	 */
	public void setOK (JSONResponseObject jro) {
		this.setOK(jro, null,null);
	}

	/**
	 * Set the JSONReponse Object to goodness
	 * @param jro The JSONReponse Object
	 * @param data The data to send back
	 */
	public void setOK (JSONResponseObject jro,Object data) {
		this.setOK(jro, data,null);
	}

	/**
	 * Set the JSONReponse Object to goodness
	 * @param jro The JSONReponse Object
	 * @param reason A message
	 */
	public void setOK (JSONResponseObject jro,String reason) {
		this.setOK(jro, null,reason);
	}

	/**
	 * Set the JSONReponse Object to goodness
	 * @param jro The JSONReponse Object
	 * @param data The data to send back
	 * @param reason A message
	 */
	public void setOK (JSONResponseObject jro, Object data, String reason) {
		jro.setActionOK(true);
		jro.setAuthOK(true);
		jro.setError(false);
		jro.setData(data);
		jro.setReason(reason);
		jro.setLog(true);
	}

	/**
	 * Set the JSONReponse Object to unhappy
	 * @param jro The JSONReponse Object
	 */
	public void setFail (JSONResponseObject jro) {
		this.setFail(jro, null, null);
	}
	/**
	 * Set the JSONReponse Object to unhappy
	 * @param jro The JSONReponse Object
	 * @param data The data to send back
	 */
	public void setFail (JSONResponseObject jro,Object data) {
		this.setFail(jro, data,null);
	}
	/**
	 * Set the JSONReponse Object to unhappy
	 * @param jro The JSONReponse Object
	 * @param reason A message
	 */
	public void setFail (JSONResponseObject jro,  String reason) {
		this.setFail(jro, null, reason);
	}
	/**
	 * Set the JSONReponse Object to unhappy
	 * @param jro The JSONReponse Object
	 * @param data The data to send back
	 * @param reason A message
	 */
	public void setFail (JSONResponseObject jro, Object data, String reason) {
		jro.setActionOK(false);
		jro.setAuthOK(true);
		jro.setError(true);
		jro.setData(data);
		jro.setReason(reason);
		jro.setLog(true);
	}
	/**
	 * Get the Post or Get parameter
	 * @param label The parameter name
	 * @param request The request object
	 * @return The value of the parameter
	 */
	public static String getRequestItem(String label, HttpServletRequest request) {
		String retVal = null;
		if (request.getAttribute(label) != null) {
			try {
				retVal = (String) request.getAttribute(label);
			} catch (Exception e) {
				retVal = null;
			}
		} else {
			try {
				retVal = request.getParameter(label);
			} catch (Exception e) {
				retVal = null;
			}
		}
		return retVal;
	}
	/**Get the FAP Configuration
	 * @return The Configuration
	 */
	public static FAPConfig getFapConfiguration() {
		if (fapConfiguration == null) {
			Core.restoreFapConfig();
		}
		return fapConfiguration;
	}
	/**
	 * Set the FAP Configuration
	 * @param fapConfiguration A configuration
	 */
	public static void setFapConfiguration(FAPConfig fapConfiguration) {
		Core.fapConfiguration = fapConfiguration;
	}

	/**
	 * Get the FAP Configuration file
	 * @return the fapConfigFile
	 */
	public static String getFapConfigFile() {
		return fapConfigFile;
	}

	/**
	 * Set the FAP config file
	 * @param fapConfigFile the fapConfigFile to set
	 */
	public static void setFapConfigFile(String fapConfigFile) {
		Core.fapConfigFile = fapConfigFile;
	}
	/**
	 * Write the configuration to a file.
	 * @return Goodness upon unicorns having sexii's
	 */
	public ActionResponse writeFapConfig () {
		ActionResponse retVal = new ActionResponse(false);
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(Core.fapConfigFile));
			Gson g = new Gson();
			out.write(g.toJson(getFapConfiguration()));
			out.close();
			retVal = new ActionResponse(true);
		} catch (Exception e) {
			retVal = new ActionResponse(false);
		}
		return retVal;
	}


	/**
	 * Add a contention
	 * @param title Title of contention
	 * @param reason Your issues
	 * @return Goodness upon pedobear
	 */
	public static ActionResponse addContention (String title, String reason) {
		return editContention(null,title,reason);
	}
	/**
	 * Edit (or add) a contention
	 * @param id The ID of a contention (set to null for a new one!)
	 * @param title The title of your issues
	 * @param reason Your b33f
	 * @return Unicorn cuddles
	 */
	public static ActionResponse editContention (String id, String title, String reason) {
		ActionResponse retVal = new ActionResponse(false);
		try {
			if (id == null) { //New
				String myID = UUID.randomUUID().toString();
				Contention aNew = new Contention();
				aNew.setActive(true);
				aNew.setTitle(title);
				aNew.setReason(reason);
				aNew.setId(myID);
				Core.fapConfiguration.getContentions().put(myID, aNew);
				retVal.setNew_id(myID);
			} else { //Edit
				Core.fapConfiguration.getContentions().get(id).setTitle(title);
				Core.fapConfiguration.getContentions().get(id).setReason(reason);
			}
			
			retVal = new ActionResponse(true);
		} catch (Exception e) {
			e.printStackTrace();
			retVal.setReason(e.getMessage());
		}
		return retVal;
	}

	/**
	 * Authenticate the user and set the session if goodness
	 * @param request The request object
	 * @param session The session object
	 * @param jr The JSONReponse Object
	 * @return Goodness upon candy.
	 */
	public ActionResponse authUser (HttpServletRequest request,HttpSession session,JSONResponseObject jr) {
		ActionResponse retVal = new ActionResponse(false,"AUTH FAIL");
		String pass = Core.getRequestItem("authtoken", request);
		//Start in fail state
		this.setFail(jr,"AUTH FAIL");
		session.setAttribute("auth", new Boolean(false));
		//session.putValue("auth", new Boolean(false));
		if (Core.fapConfiguration != null && pass != null) {
			String passhash = this.md5(pass);
			if(passhash.equals(Core.fapConfiguration.getAdminPassword())) {
				this.setOK(jr,"AUTH OK");
				retVal = new ActionResponse(true,"AUTH OK");
				session.setAttribute("auth", new Boolean(true));
				Core.addToLog("Admin logged in");
			}  else {
				this.setFail(jr);
				jr.setAuthOK(false);
			}
		}
		
		return retVal;
	}

	/**
	 * Restore the configuration, if no config is available use a default one.
	 * @return Goodness upon goodness
	 */
	public synchronized static ActionResponse restoreFapConfig () {
		ActionResponse retVal = null;
		File fc = new File(Core.fapConfigFile);
		try {
			if (fc.exists()) {
				if (fc.isFile() ) {
					FileInputStream stream = new FileInputStream(fc);
					try {
						FileChannel fch = stream.getChannel();
						MappedByteBuffer bb = fch.map(FileChannel.MapMode.READ_ONLY, 0, fch.size());
						/* Instead of using default, pass in a decoder. */
						String confStr = Charset.defaultCharset().decode(bb).toString();
						Gson g = new Gson();
						FAPConfig fco = g.fromJson(confStr, FAPConfig.class);
						Core.setFapConfiguration(fco);
					}
					finally {
						stream.close();
					}
				}
				retVal = new ActionResponse(true);
			} else {
				fapConfiguration = new FAPConfig();
				retVal = new ActionResponse(false,"Configuration file not found.");
			}
		} catch (Exception e) {
			fapConfiguration = new FAPConfig();
			retVal = new ActionResponse(false,e.getMessage());
		}
		return retVal;
	}

	/**
	 * Get the log "array"
	 * @return the log
	 */
	public static ArrayList<String> getLog() {
		return log;
	}

	/**
	 * Set the log "array"
	 * @param log the log to set
	 */
	public static void setLog(ArrayList<String> log) {
		Core.log = log;
	}

	/**
	 * Add an item to the log
	 * @param logline The log line
	 */
	public static void addToLog(String logline) {
		if (Core.getAllowLog()) {
			String msg = "[" + Calendar.getInstance().getTimeInMillis() + "] " + logline;
			Core.log.add(0,msg);
			try {
				if (Core.log.size() > 100) {
					while ( Core.log.size() > 100 ) {
						Core.log.remove(100);
					}
				}
			} catch (Exception e) {
				Core.log.add(0,"Log pruning failure.");
			}
		}
	}

	/**
	 * Do we allow logging?
	 * @return the allowLog
	 */
	public static Boolean getAllowLog() {
		return allowLog;
	}

	/**
	 * Set to true to allow logging
	 * @param allowLog the allowLog to set
	 */
	public static void setAllowLog(Boolean allowLog) {
		Core.allowLog = allowLog;
	}

	/**
	 * Get if we are in debug mode
	 * @return the debug
	 */
	public static Boolean getDebug() {
		return debug;
	}

	/**
	 * Set the debug level
	 * @param debug the debug to set
	 */
	public static void setDebug(Boolean debug) {
		Core.debug = debug;
	}

	public String base64Decode (String code) {
		Base64 decoder = new Base64();
		byte[] decodedBytes = decoder.decode("YWJjZGVmZw==");
		String retVal = new String(decodedBytes) ;
		return retVal;
	}
	
	/**
	 * Get a MD5 hash for a password
	 * @param password The password in question
	 * @return A hash
	 */
	public String md5 (String password) {
		String retVal = null;
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(password.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			retVal = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(retVal.length() < 32 ){
				retVal = "0"+retVal;
			}
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		return retVal;
	}

}
