
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" import="org.springframework.security.core.Authentication, org.springframework.security.core.context.SecurityContextHolder" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="org.apache.commons.codec.binary.Base64" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Post Ticket</title>

 <script src="js/jquery-1.10.2.js" type="text/javascript"></script>
  <script src="js/jquery-ui.js" type="text/javascript"></script>
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/style.css">
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">
 
 <script>
$(document).ready(function() {
    //add more file components if Add is clicked
    $('#addFile').click(function() {
        var fileIndex = $('#fileTable tr').children().length ;
        $('#fileTable').append(
                '<tr><td>'+
                '   <input type="file" name="attachments['+ fileIndex +']" />'+
                '</td></tr>');
    });  
});
</script>
  <script type="text/javascript">
  $().ready(function(){
	  
	   $('input[type="checkbox"]').click(function(){
              $(".imageDiv").toggle();
      });
	});
  </script>
 
  
</head>
<body>
	<div class="header">
		<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
	</div>
	
	<div>
		<div class="userleft"><%@include file="/WEB-INF/jsp/include/left.jsp" %></div>
		<div class="usercenter">
				<form:form modelAttribute="ticket" enctype="multipart/form-data">
					<form:input path="ticketSubject"/>
					<form:errors path="ticketSubject"></form:errors>
					<br>
					
					<form:textarea path="ticketBody"/>
					<form:errors path="ticketBody"></form:errors>
					
			<input id="addImage" type="checkbox" name="isAttachmentAttached"> Add File 
				<div class="imageDiv"  style="display: none;">
				<p>Select files to upload. Press Add button to add more file inputs.</p>
		    	<input id="addFile" type="button" value="Add File" />
	   			<table id="fileTable">
	       			<tr>
	           			<td><input name="attachments[0]" type="file" /></td>
	        		</tr>
	    		</table>
				</div>		
				
				<input type="submit" value="Post Ticket" />
				
				</form:form>
		</div>
		<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
	</div>


</body>
</html>