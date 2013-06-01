var selected_node_id = null;
var tree_data = null;

function postTemplate () {
	this.id =  null;
	this.action =  null;
	this.authtoken =  null;
	this.contention =  null;
	this.campaign =  null;
	this.attacktype =  null;
	this.target =  null;
	this.agent =  null;
	this.type =  null;
	this.title =  null;
	this.reason =  null;
	this.code =  null;
	this.ip =  null;
	this.url =  null;
	this.config =  null;
	this.confpass1 =  null;
	this.confpass2 =  null;
};

/**
 * Perform a login authentication
 */
function login () {
	var pass = document.getElementById("authpass");
	var lm = document.getElementById("loginmsg");
	lm.innerHTML = "";
	var url = "Core";
	var args = new postTemplate();
	args.action = "auth";
	args.authtoken = pass.value;
	$.post(url,args, function(data) {
		if (data != null) {
			if(data.authOK == true) {
				authstate();
				pass.value="";
			} else {
				lm.innerHTML = "Invalid Password";
			}
		}
	});
}
/**
 * Logout a user
 */
function logout() {
	var url = "Core";
	var args = new postTemplate();
	args.action ="logout";
	$.post(url, args, function(data) {
		if (data != null) {
			unauthstate();
		}
	});
}

/**
 * Load the JS Tree
 */
function loadTree() {
	var url = "Core";
	var args = new postTemplate();
	args.action ="gettree";
	$.post(url, args, function(data) {
		if (data != null) {
			if(data!=null) {
				tree_data = data.data;
				populateTree ("menu", tree_data);
			} 
		}
	});
}

/**
 * Hide an element
 * @param id The element ID
 */
function hide(id) {
	var e = document.getElementById(id);
	e.style.display = 'none';
	e.style.visibility = "hidden";
}

/**
 * Show an element
 * @param id The element ID
 */
function show(id) {
	try {
		var e = document.getElementById(id);
		e.style.display = 'block';
		e.style.visibility = "visible";
	} catch (e) {
		alert(id);
	}
}

var b = null;

/**
 * Perform an action on JSTree node selection
 * @param e element object selected
 * @param data data object
 */
function nodeSelect (e,data) {
	var type = data.rslt.obj[0].type;
	b = data.rslt.obj[0];
	var id = data.rslt.obj[0].attributes.getNamedItem("id").nodeValue;
	var parent = data.rslt.obj[0].attributes.getNamedItem("parent").nodeValue;
	var sparent = document.getElementById("selected_parent");
	sparent.value = parent;
	if (id != selected_node_id) {
		node_id_selected = id;
		clearAll();
		switch (type) {
		case "contentions":
			resetButtons();
			hideAll();
			switch (id) {
			case "42b38a02-24e3-4a9e-96cb-4729ae84ad3c":
				show("contention");
				break;
			default:
				show("editcontention");
			var title = data.rslt.obj[0].attributes.getNamedItem("title").nodeValue;
			var reason = data.rslt.obj[0].attributes.getNamedItem("reason").nodeValue;
			var fId = document.getElementById("contention_id");
			fId.value = id;
			var fTitle = document.getElementById("contention_title");
			fTitle.value=title;
			var fReason = document.getElementById("contention_reason");
			fReason.value = reason;
			setButton ("con_btn","Update Contention");
			}
			break;
		case "campaigns":
		case "campaign":
			resetButtons();
			hideAll();
			switch (parent) {
			case "42b38a02-24e3-4a9e-96cb-4729ae84ad3c":
				show("campaign");
				break;
			default:
				show("editCampaign");
			var id  = document.getElementById("campaignId");
			id.value = data.rslt.obj[0].attributes.getNamedItem("id").nodeValue;
			var parent = document.getElementById("campaignParent");
			var title = document.getElementById("campaignName");
			var agent = data.rslt.obj[0].attributes.getNamedItem("agent").nodeValue;
			var attack = data.rslt.obj[0].attributes.getNamedItem("attacktype").nodeValue;
			var target = data.rslt.obj[0].attributes.getNamedItem("target").nodeValue;
			title.value =  data.rslt.obj[0].attributes.getNamedItem("title").nodeValue;
			parent.value =  data.rslt.obj[0].attributes.getNamedItem("parent").nodeValue;
			for (var i = 0; i < tree_data.data.length;i++) {
				if (tree_data.data[i].attr.type == "targets") {
					var ops = document.getElementById("campaignTarget");
					for (var x = 0; x < tree_data.data[i].children.length;x++) {
						var sel = false;
						var title = tree_data.data[i].children[x].attr.title;
						var id = tree_data.data[i].children[x].attr.id;
						if (target == id) {
							sel = true;
						}
						addOption(ops,title,id,sel);
					}	
				}
				if (tree_data.data[i].attr.type == "attacktypes") {
					var ops = document.getElementById("campaignAttackType");
					for (var x = 0; x < tree_data.data[i].children.length;x++) {
						var sel = false;
						var title = tree_data.data[i].children[x].attr.title;
						var id = tree_data.data[i].children[x].attr.id;
						if (attack == id) {
							sel = true;
						}
						addOption(ops,title,id,sel);
					}	
				}
				if (tree_data.data[i].attr.type == "agents") {
					var ops = document.getElementById("campaignAgent");
					addOption(ops,"All Agents","");
					for (var x = 0; x < tree_data.data[i].children.length;x++) {
						var sel = false;
						var title = tree_data.data[i].children[x].attr.title;
						var id = tree_data.data[i].children[x].attr.id;
						if (agent == id) {
							sel = true;
						}
						addOption(ops,title,id,sel);
					}	
				}
			}
			//parent, id
			var msg = "The URL to utilize this campaign: <br />" + getJSUrl(parent.value,data.rslt.obj[0].attributes.getNamedItem("id").nodeValue) ;
			var campUrl = document.getElementById("campaignUrl");
			campaignUrl.innerHTML=msg;
			setButton ("camp_btn","Update Campaign");
			}
			break;
		case "attacktypes":
		case "attacktype":
			resetButtons();
			hideAll();
			switch (id) {
			case "42b38a02-24e3-4a9e-96cb-4829ae84ad3c":
				show("attacktype");
				break;
			default:
				show("editattacktype");
			var title = data.rslt.obj[0].attributes.getNamedItem("title").nodeValue;
			var def = unescape(data.rslt.obj[0].attributes.getNamedItem("default").nodeValue);
			var fId = document.getElementById("attackId");
			fId.value = id;

			var fTitle = document.getElementById("attackName");
			fTitle.value=(title);

			var fDefinition = document.getElementById("attackDefinition");
			fDefinition.value = (def);

			setButton ("atty_btn","Update Attack Type");
			}
			break;
		case "targets":
		case "target":
			resetButtons();
			hideAll();
			switch (id) {
			case "42b38a02-24e3-4a9e-96cb-4729ae85ad3c":
				show("target");
				break;
			default:
				show("edittarget");
			var title = data.rslt.obj[0].attributes.getNamedItem("title").nodeValue;
			var ip = data.rslt.obj[0].attributes.getNamedItem("ip").nodeValue;
			var id = data.rslt.obj[0].attributes.getNamedItem("id").nodeValue;
			var fId = document.getElementById("targetId");
			var fIp = document.getElementById("targetIp");
			var fTitle = document.getElementById("targetName");
			fId.value = id;
			fIp.value = ip;
			fTitle.value = title;

			setButton ("targets_btn","Update Target");
			}
			break;
		case "configuration":
			resetButtons();
			hideAll();
			show("config");
			getconfig();
			break;
		case "agent":
		case "agents":
			resetButtons();
			hideAll();
			switch (id) {
			case "42b38a02-24a3-4a9e-96cb-4729ae85ad3c":
				show("agents");
				break;
			default:
				show("editagents");
			setButton ("agent_btn","Update Agent");
			var url = unescape(data.rslt.obj[0].attributes.getNamedItem("url").nodeValue);
			var title = data.rslt.obj[0].attributes.getNamedItem("title").nodeValue;
			var fId = document.getElementById("agentId");
			var fUrl = (document.getElementById("agentURL"));
			var fTitle = (document.getElementById("agentName"));
			fId.value = id;
			fUrl.value = url;
			fTitle.value = title;
			}
			break;
		default:
			resetButtons();
		hideAll();
		}
	}
}

/**
 * Populate a JS Tree div
 * @param divName The div that will contain the JSTree
 * @param tree The tree data structure
 */
function populateTree (divName, tree) {
	$(function () {
		$("#"+divName).jstree(
				{ 
					"json_data" :tree,
					"plugins" : [ "themes", "json_data", "ui","contextmenu" ], 
					contextmenu: {items: customMenu,width:"400px" }
				}
		).bind("select_node.jstree", function (e, data) { nodeSelect(e,data); });
	});
}

/**
 * Set to an authenticated state
 */
function authstate () {
	hide("login");
	loadTree();
	getLog();
	show("main");
	show("topRight");
}

/**
 * Set to an unauthenticated state
 */
function unauthstate () {
	resetButtons();
	hideAll();
	clearAll();
	show("login");
	hide("main");
	hide("topRight");
}

/**
 * Show only the one form, hide the rest
 * @param divName The div to show
 */
function showOnly (divName) {
	resetButtons();
	hideAll();
	show(divName);
}

/**
 * Hide all other forms
 */
function hideAll() {
	hide("contention");
	hide("Campaign");
	hide("attacktype");
	hide("target");
	hide("agents");
	hide("editcontention");
	hide("editCampaign");
	hide("editattacktype");
	hide("edittarget");
	hide("editagents");
	hide("config");
	hide("bear");
}

/**
 * Get the log and place it on the screen
 */
function getLog() {
	var url = "Core";
	var args = new postTemplate();
	args.action ="log";
	$.post(url, args, function(data) {
		if (data != null) {
			if(data.actionOK == true) {
				var size = data.data.length;
				var logconsole = document.getElementById("consolelog");
				logconsole.innerHTML="";
				for (var x = 0; x < size;x++){
					logconsole.innerHTML = logconsole.innerHTML + data.data[x] + "<br />";
				}
			} 
		}
	});
}

/**
 * Update the data objects
 */
function windowUpdate () {
	//getLog();
}

/**
 * The window onload function
 */
window.onload = function() {
	//Disable spell check in certain input boxes
	$('#confconfigjson').attr('spellcheck', 'false');
	$('#attackDefinition').attr('spellcheck', 'false');
	$('#agentURL').attr('spellcheck', 'false');

	setInterval(windowUpdate, 30000);
};

/**
 * Submit a contention from the form
 */
function submitContention () {
	var args = new postTemplate();
	args.action ="add";
	args.type ="contention";
	var uri = "Core";
	var newC = true;
	var title =  document.getElementById("contention_title").value;
	args.title = title;
	var reason =  (document.getElementById("contention_reason").value);
	args.reason = reason;
	var id = document.getElementById("contention_id").value;
	if (id!=null) {
		if (id!="") {
			//Update
			args.contention = id;
			args.action="update";
			newC = false;
		}
	} 
	$.post(uri,args, function(data) {
		if (data != null) {
			if(data.actionOK == true) {
				clearAll();
				resetButtons();
				hideAll();
				loadTree();
				if (newC) {
					jAlert( "Contention Added",'Added');	
				} else {
					jAlert( "Contention Updated",'Updated');
				}
			} else {
				if (newC){
					jAlert( data.reason,'Unable to add contention');
				} else {
					jAlert( data.reason,'Unable to update contention');
				}
			}
		}
	});
}
/**
 * Submit an attack type
 */
function submitAttackType () {
	var args = new postTemplate();
	args.action="add";
	args.type = "attacktype";
	var uri = "Core";
	var newAt = true;
	var title =  document.getElementById("attackName").value;
	args.title = title;
	var code =  document.getElementById("attackDefinition").value;
	args.code = code;
	var id = document.getElementById("attackId").value;
	if (id!="") {
		//Update
		args.action = "update";
		args.attacktype = id;
		newAt = false;
	}
	$.post(uri,args, function(data) {
		if (data != null) {
			if(data.actionOK == true) {
				clearAll();
				resetButtons();
				hideAll();
				loadTree();
				if (newAt) {
					jAlert( "Attack Type Added",'Added');
				} else {
					jAlert( "Attack Type Updated",'Updated');
				}
			} else {
				if (newAt) {
					jAlert( data.reason,'Unable to add attack type');
				} else {
					jAlert( data.reason,'Unable to update attack type');
				}
			}
		}
	});
}

/**
 * Submit an attack type
 */
function submitCampaign () {
	var args = new postTemplate();
	args.action="add";
	args.type = "campaign";
	var uri = "Core";
	var newAt = true;
	var title =  document.getElementById("campaignName").value;
	args.title = title;
	var target =  document.getElementById("campaignTarget").value;
	args.target = target;
	var attack =  document.getElementById("campaignAttackType").value;
	args.attacktype = attack;
	var agent =  document.getElementById("campaignAgent").value;
	args.agent = agent;
	var parent =  document.getElementById("campaignParent").value;
	args.contention = parent;
	var id = document.getElementById("campaignId").value;
	
	if (id!="" && id != null) {
		//Update
		args.action = "update";
		args.campaign = id;
		newAt = false;
	} 
	$.post(uri,args, function(data) {
		if (data != null) {
			if(data.actionOK == true) {
				clearAll();
				resetButtons();
				hideAll();
				loadTree();
				if (newAt) {
					jAlert( "Campaign Added",'Added');
				} else {
					jAlert( "Campaign Updated",'Updated');
				}
			} else {
				if (newAt) {
					jAlert( data.reason,'Unable to add campaign');
				} else {
					jAlert( data.reason,'Unable to update campaign');
				}
			}
		}
	});
}

/**
 * IP Add button action
 */
function addTargetIP () {
	alert("Add Target IP");	
}
/**
 * Submit a targer
 */
function submitTarget () {
	var args = new postTemplate();
	args.action="add";
	args.type = "target";
	var newTarget = true;
	var uri = "Core";
	var id =  document.getElementById("targetId").value;
	var title =  document.getElementById("targetName").value;
	args.title = title;
	var ip =  document.getElementById("targetIp").value;
	args.ip = ip;

	if (id != null && id != "" ) {
		args.target = id;
		args.action = "update";
		newTarget = false;
	}

	$.post(uri,args, function(data) {
		if (data != null) {
			if(data.actionOK == true) {
				clearAll();
				resetButtons();
				hideAll();
				loadTree();
				if(newTarget) {
					jAlert( "Target Added",'Added');
				} else {
					jAlert( "Target Updated",'Updated');
				}
			} else {
				if (newTarget) {
					jAlert( data.reason,'Unable to add target');
				} else {
					jAlert( data.reason,'Unable to update target');
				}
			}
		}
	});
}
/**
 * Add / update a user agent
 */
function submitAgent () {
	var args = new postTemplate();
	args.action="add";
	args.type = "agent";
	var uri = "Core";
	var newAgent = true;
	var id =  document.getElementById("agentId").value;
	var title = document.getElementById("agentName").value;
	args.title = title;
	var url =  document.getElementById("agentURL").value;
	args.url = url;
	if (id != null && id != "" ) {
		args.agent = id;
		args.action = "update";
		newAgent = false;
	}
	$.post(uri, args, function(data) {
		if (data != null) {
			if(data.actionOK == true) {
				clearAll();
				resetButtons();
				hideAll();
				loadTree();
				if (newAgent) {
					jAlert( "Agent Added",'Added');	
				} else {
					jAlert( "Agent Updated",'Updated');
				}
			} else {
				if(newAgent) {
					jAlert( data.reason, 'Unable to add agent');
				} else {
					jAlert( data.reason, 'Unable to update agent');
				}
			}
		}
	});

}

/**
 * Open the edit contention form
 * @param node The node selected
 */
function addContention (node) {
	resetButtons();
	hideAll();
	clearAll();
	show("editcontention");
}

/**
 * Edit a contention 
 * @param node Node data
 */
function editContention(node) {
	resetButtons();
	hideAll();
	clearAll();
	show("editcontention");
}

/**
 * Delete a contention
 * @param node Node data
 */
function rmContention(node) {
	var id = node[0].attributes.getNamedItem("id").nodeValue;
	var args = new postTemplate();
	args.action="del";
	args.type = "contention";
	args.contention = id;
	var uri = "Core";

	$.post(uri, args, function(data) {
		if (data != null) {
			if(data.actionOK == true) {
				clearAll();
				resetButtons();
				hideAll();
				loadTree();
			} else {
				jAlert( data.reason,'Unable to remove contention');
			}
		}
	});
}

/**
 * Show add campaign form
 * @param node The parent
 */
function addCampaign(node) {
	b = node;
	resetButtons();
	hideAll();
	clearAll();
	show("editCampaign");
	var parent = document.getElementById("campaignParent");
	parent.value = node[0].attributes.getNamedItem("id").nodeValue;
	for (var i = 0; i < tree_data.data.length;i++) {
		if (tree_data.data[i].attr.type == "targets") {
			var ops = document.getElementById("campaignTarget");
			for (var x = 0; x < tree_data.data[i].children.length;x++) {
				var title = tree_data.data[i].children[x].attr.title;
				var id = tree_data.data[i].children[x].attr.id;
				addOption(ops,title,id,false);
			}	
		}
		if (tree_data.data[i].attr.type == "attacktypes") {
			var ops = document.getElementById("campaignAttackType");
			for (var x = 0; x < tree_data.data[i].children.length;x++) {
				var title = tree_data.data[i].children[x].attr.title;
				var id = tree_data.data[i].children[x].attr.id;
				addOption(ops,title,id,false);
			}	
		}
		if (tree_data.data[i].attr.type == "agents") {
			var ops = document.getElementById("campaignAgent");
			addOption(ops,"All Agents","");
			for (var x = 0; x < tree_data.data[i].children.length;x++) {
				var title = tree_data.data[i].children[x].attr.title;
				var id = tree_data.data[i].children[x].attr.id;
				addOption(ops,title,id,false);
			}	
		}
	}
}

/**
 * Remove a campaign
 * @param node Node data
 */
function rmCampaign(node) {
	var id = node[0].attributes.getNamedItem("id").nodeValue;
	var parent = node[0].attributes.getNamedItem("parent").nodeValue;
	var args = new postTemplate();
	args.action="del";
	args.type = "campaign";
	args.contention = parent;
	args.campaign = id;
	var uri = "Core";
	$.post(uri, args, function(data) {
		if (data != null) {
			if(data.actionOK == true) {
				clearAll();
				resetButtons();
				hideAll();
				loadTree();
			} else {
				jAlert( data.reason,'Unable to remove campaign');
			}
		}
	});
}
/**
 * Show add attack type form
 * @param node Parent
 */
function addAttackType(node) {
	resetButtons();
	hideAll();
	clearAll();
	show("editattacktype");
}
/**
 * Show edit attack type form
 * @param node Node data
 */
function editAttackType(node) {
	resetButtons();
	hideAll();
	clearAll();
	show("editattacktype");
}
/**
 * Remove an attack type
 * @param node Node data
 */
function rmAttackType(node) {
	var id = node[0].attributes.getNamedItem("id").nodeValue;
	var args = new postTemplate();
	args.action="del";
	args.type = "attacktype";
	args.attacktype = id;
	var uri = "Core";
	$.post(uri, args, function(data) {
		if (data != null) {
			if(data.actionOK == true) {
				clearAll();
				resetButtons();
				hideAll();
				loadTree();
			} else {
				jAlert( data.reason,'Unable to remove attack type');
			}
		}
	});
}
/**
 * Show add target form
 * @param node Parent
 */
function addTarget(node) {
	resetButtons();
	hideAll();
	clearAll();
	show("edittarget");
}

/**
 * Show edit target form
 * @param node Node data
 */
function editTarget(node) {
	resetButtons();
	hideAll();
	clearAll();
	show("edittarget");
}

/**
 * Remove a target
 * @param node Node data
 */
function rmTarget(node) {
	var id = node[0].attributes.getNamedItem("id").nodeValue;
	var args = new postTemplate();
	args.action="del";
	args.type = "target";
	args.target = id;
	var uri = "Core";
	$.post(uri, args,function(data) {
		if (data != null) {
			if(data.actionOK == true) {
				clearAll();
				resetButtons();
				hideAll();
				loadTree();
			} else {
				jAlert( data.reason,'Unable to remove target');
			}
		}
	});
}
/**
 * Show add agent form
 * @param node Parent
 */
function addAgent(node) {
	resetButtons();
	hideAll();
	clearAll();
	show("editagents");
}

/**
 * Show edit agent form
 * @param node Node data
 */
function editAgent(node) {
	resetButtons();
	hideAll();
	clearAll();
	show("editagents");
}
/**
 * Remove an agent
 * @param node Node data
 */
function rmAgent(node) {
	var id = node[0].attributes.getNamedItem("id").nodeValue;
	var args = new postTemplate();
	args.action="del";
	args.type = "agent";
	args.agent = id;
	var uri = "Core";
	$.getJSON(uri, function(data) {
		if (data != null) {
			if(data.actionOK == true) {
				clearAll();
				resetButtons();
				hideAll();
				loadTree();
			} else {
				jAlert( data.reason,'Unable to remove agent');
			}
		}
	});
}

/**
 * Get the proper menu on the JS Tree
 * @param node The node selected
 * @returns {___anonymous5879_7352} No Idea
 */
function customMenu(node) {
	// The default set of all items
	var type = node[0].attributes.type.nodeValue;
	var id = node[0].attributes.id.nodeValue;
	var parent = node[0].attributes.parent.nodeValue;
	var items = {
			addContention: { 
				label: "Add Contention",
				action: function () {addContention(node);}
			},
			addAttackType: { 
				label: "Add Attack Type",
				action: function () {addAttackType(node);}
			},
			addTarget: { 
				label: "Add Target",
				action: function () {addTarget(node);}
			},
			addAgent: { 
				label: "Add Agent",
				action: function () {addAgent(node);}
			},
			addCampaign: { 
				label: "Add Campaign",
				action: function () {addCampaign(node);}
			},
			rmContention: { 
				label: "Delete Contention",
				action: function () {rmContention(node);}
			},
			rmAttackType: { 
				label: "Delete Attack Type",
				action: function () {rmAttackType(node);}
			},
			rmTarget: { 
				label: "Delete Target",
				action: function () {rmTarget(node);}
			},
			rmAgent: { 
				label: "Delete Agent",
				action: function () {rmAgent(node);}
			},
			rmCampaign: { 
				label: "Delete Campaign",
				action: function () {rmCampaign(node) ;}
			}
	};

	switch(type) {
	case "contentions":
		if (id == "42b38a02-24e3-4a9e-96cb-4729ae84ad3c") {
			delete items.addTarget;
			delete items.rmTarget;
			delete items.addCampaign;
			delete items.rmCampaign;
			delete items.addAgent;
			delete items.rmAgent;
			delete items.addAttackType;
			delete items.rmAttackType;
			delete items.rmContention;
		} else {
			delete items.addContention;
			delete items.addTarget;
			delete items.rmTarget;
			delete items.rmCampaign;
			delete items.addAgent;
			delete items.rmAgent;
			delete items.addAttackType;
			delete items.rmAttackType;
		}
		break;
	case "Campaign":
	case "campaign":
		if (parent == "root") {
			delete items.addTarget;
			delete items.rmTarget;
			delete items.addAgent;
			delete items.rmAgent;
			delete items.rmCampaign;
			delete items.addAttackType;
			delete items.rmAttackType;
			delete items.addContention;
			delete items.rmContention;
		} else {
			delete items.addCampaign;
			delete items.addTarget;
			delete items.rmTarget;
			delete items.addAgent;
			delete items.rmAgent;
			delete items.addAttackType;
			delete items.rmAttackType;
			delete items.addContention;
			delete items.rmContention;
		}
		break;
	case "attacktypes":
		if (parent == "root") {
			delete items.rmAttackType;
			delete items.addAgent;
			delete items.rmAgent;
			delete items.addTarget;
			delete items.rmTarget;
			delete items.addCampaign;
			delete items.rmCampaign;
			delete items.addContention;
			delete items.rmContention;
		} else {

		}
		break;
	case "attacktype":
		delete items.addAttackType;
		delete items.addAgent;
		delete items.rmAgent;
		delete items.addTarget;
		delete items.rmTarget;
		delete items.addCampaign;
		delete items.rmCampaign;
		delete items.addContention;
		delete items.rmContention;
		break;
	case "targets":
	case "target":
		if (parent == "root") {
			delete items.rmTarget;
			delete items.addCampaign;
			delete items.rmCampaign;
			delete items.addAttackType;
			delete items.rmAttackType;
			delete items.addContention;
			delete items.rmContention;
			delete items.addAgent;
			delete items.rmAgent;
		} else {
			delete items.addTarget;
			delete items.addCampaign;
			delete items.rmCampaign;
			delete items.addAttackType;
			delete items.rmAttackType;
			delete items.addContention;
			delete items.rmContention;
			delete items.addAgent;
			delete items.rmAgent;
		}
		break;
	case "agents":
	case "agent":
		if (parent == "root") {
			delete items.rmAgent;
			delete items.addTarget;
			delete items.rmTarget;
			delete items.addCampaign;
			delete items.rmCampaign;
			delete items.addAttackType;
			delete items.rmAttackType;
			delete items.addContention;
			delete items.rmContention;
		} else {
			delete items.addAgent;
			delete items.addTarget;
			delete items.rmTarget;
			delete items.addCampaign;
			delete items.rmCampaign;
			delete items.addAttackType;
			delete items.rmAttackType;
			delete items.addContention;
			delete items.rmContention;
		}
		break;
	default:
		items = {};
	}


	return items;
}
/**
 * Change the password
 */
function chpass () {
	var p1 = document.getElementById("confpass1").value;
	var p2 = document.getElementById("confpass2").value;
	var args = new postTemplate();
	args.action="chadminpass";
	args.confpass1 = p1;
	args.confpass2 = p2;
	var uri = "Core";
	if (p1 === p2) {
		$.post(uri,args, function(data) {
			if(data.authOK == true) {
				jAlert( "Password Updated",'Updated');
			} else {
				jAlert( data.reason,'Unable to set password');
			}
		});
	} else {
		jAlert( data.reason,'Unable to set password');
	}
}
/**
 * Get the configuration
 */
function getconfig () {
	var url = "Core";
	var args = new postTemplate();
	args.action="get_configuration";
	$.post(url, args, function(data) {
		if(data.authOK == true) {
			var cnf = document.getElementById("confconfigjson");
			cnf.value= JSON.stringify(data.data);
		} else {
			jAlert( data.reason,'Unable to update configuration');
		}
	});
}
/**
 * Push the configuration
 */
function pushconfig () {
	var conf = document.getElementById("confconfigjson").value;
	if (conf != null) {
		var url = "Core";
		var args = new postTemplate();
		args.action="push_configuration";
		args.config = conf;
		$.post(url,args, function(data) {
			if(data.authOK == true) {
				resetButtons();
				hideAll();
				clearAll();
				authstate();
			} else {
				jAlert('Unable to retrieve configuration', data.reason);
			}
		});
	} else {
		jAlert('Configuration is invalid', "Please check your JSON.");
	}
}

/**
 * Save configuration to disk
 */
function savetodisk() {
	var url = "Core";
	var args = new postTemplate();
	args.action="write_config";
	$.post(url,args, function(data) {
		if(data.authOK == true) {
			jAlert('YAY',"Configuration written to disk");
		} else {
			jAlert( data.reason,'Unable to save configuration');
		}
	});
}

/**
 * Remove Values
 * @param elem element to clean out
 */
function rmValue(elem) {
	try {
		var e = document.getElementById(elem);
		e.value="";
	} catch(err) {
		alert("val: " + elem);
	}
}

/**
 * Remove Values
 * @param elem element to clean out
 */
function rmInnerHTML(elem) {
	try {
		var e = document.getElementById(elem);
		e.innerHTML="";
	} catch(err) {
		alert("val: " + elem);
	}
}

/**
 * Remove Values from a select box
 * @param elem element to clean out
 */
function rmSelectValue(elem) {
	try {
		var e = document.getElementById(elem);
		for( var i=e.options.length-1;i>=0;i--) {
			e.remove(i);
		}
	} catch(err) {
		alert("val: " + elem);
	}
}

/**
 * Adds an option to a select box
 * @param selectbox The select box in question
 * @param text The text of the option
 * @param value The value of the option
 */
function addOption(selectbox,text,value, selected ) {
	var optn = document.createElement("OPTION");
	optn.text = text;
	optn.value = value;
	optn.selected = selected;
	selectbox.options.add(optn);
}

/**
 * Clear all values from all forms and input fields
 */
function clearAll() {
	//Contention
	rmValue("contention_id");
	rmValue("contention_title");
	rmValue("contention_reason");

	//Config
	rmValue("confconfigjson");
	rmValue("confpass1");
	rmValue("confpass2");

	//Login
	rmValue("authpass");

	//Agents
	rmValue("agentId");
	rmValue("agentName");
	rmValue("agentURL");

	//Targets
	rmValue("targetId");
	rmValue("targetIp");
	rmValue("targetName");

	//Attack Types
	rmValue("attackId");
	rmValue("attackName");
	rmValue("attackDefinition");

	//Campaigns
	rmValue("campaignId");
	rmValue("campaignName");
	rmSelectValue("campaignTarget");
	rmSelectValue("campaignAttackType");
	rmSelectValue("campaignAgent");
	rmInnerHTML("campaignUrl");
	
	
	rmValue("contention_reason");
	rmValue("contention_reason");
	rmValue("contention_reason");
	rmValue("contention_reason");
	rmValue("contention_reason");
	rmValue("contention_reason");
	rmValue("contention_reason");
}
/**
 * Set a buttons text
 * @param btn_name The button element id
 * @param value The text on the button
 */
function setButton (btn_name,value) {
	try {
		var e = document.getElementById(btn_name);
		e.value=value;
	} catch(err) {
		alert("val: " + btn_name);
	}
}

function resetButtons() {
	//Contentions button
	setButton ("con_btn","Add Contention");

	//Agent
	setButton ("agent_btn","Add Agent");

	//Attack Type
	setButton ("atty_btn","Add Attack Type");

	//Targets
	setButton ("targets_btn","Add Target");

	//Campaign
	setButton ("camp_btn","Add Campaign");
}


function getJSUrl (contention,campaign) {
	return "http[s]://&lt;servername&gt;/FAP-C2/attackjs.jsp?contention="+contention+"&campaign="+campaign;
}
