<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  <script type="text/javascript">
    $(function(){
        $("#picture1").hide();
        $("#picture2").hide();
        $("#picture3").hide();
        $("#picture4").hide();
        $("#picture5").hide();
        
  		$("#button1").bind("click", function() {
  			$("#picture1").show();
  			$("#picture2").hide();
  			$("#picture3").hide();
  			$("#picture4").hide();
  			$("#picture5").hide();
			});
			
		$("#button2").bind("click", function() {
  			$("#picture1").hide();
  			$("#picture2").show();
  			$("#picture3").hide();
  			$("#picture4").hide();
  			$("#picture5").hide();
			});
		$("#button3").bind("click", function() {
  			$("#picture1").hide();
  			$("#picture2").hide();
  			$("#picture3").show();
  			$("#picture4").hide();
  			$("#picture5").hide();
			});
		$("#button4").bind("click", function() {
  			$("#picture1").hide();
  			$("#picture2").hide();
  			$("#picture3").hide();
  			$("#picture4").show();
  			$("#picture5").hide();
			});
		$("#button5").bind("click", function() {
  			$("#picture1").hide();
  			$("#picture2").hide();
  			$("#picture3").hide();
  			$("#picture4").hide();
  			$("#picture5").show();
			});
  	
  	});
  </script>
  <body>
 	<br>
	    	<input type="button" value="Convoy L5 Zone Plan" id="button3" />
	    	<input type="button" value="Convoy L7 Zone Plan" id="button4" />
	    	<input type="button" value="Convoy L15 Zone Plan" id="button1" />
	    	<input type="button" value="Convoy L40 Zone Plan" id="button2" />
	    	<input type="button" value="CP3 L17 Zone Plan" id="button5" />
	<br> 	
	<br> 	
	    	<div id="picture1"><img src="photofiles/Convoy L15 Zone Plan.png" /></div>
	    	<div id="picture2"><img src="photofiles/Convoy L40 Zone Plan.png" /></div>
	    	<div id="picture3"><img src="photofiles/Convoy L5 Zone Plan.png" /></div>
	    	<div id="picture4"><img src="photofiles/Convoy L7 Zone Plan.png" /></div>
	    	<div id="picture5"><img src="photofiles/CP3 L17 Zone Plan.png" /></div>
    	
  </body>
</html>
