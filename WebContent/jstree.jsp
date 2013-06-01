<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/fap.css">
<script src="js/jquery.js"></script>
<script type="text/javascript" src="js/_lib/jquery.cookie.js"></script>
<script type="text/javascript" src="js/_lib/jquery.hotkeys.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script src="js/fap.js"></script>
</head>
<body>

<div id="demo1">
        </div>

<script>

var jtree = {
		"data" : [
					{ 
						"attr" : { "id" : "li.node.id1" }, 
						"data" : { 
							"title" : "Long format demo", 
							"attr" : { "href" : "#" } 
						} 
					}
				]
			} ;

function populateTree (divName, tree) {
$(function () {
	    $("#"+divName).jstree(
	    		{ 
	    			"json_data" :tree,
	    			"plugins" : [ "themes", "json_data", "ui" ]
	    		}
	    		).bind("select_node.jstree", function (e, data) { alert(data.rslt.obj.data("id")); });
	});
}

populateTree("demo1",jtree);
</script>

</body>
</html>