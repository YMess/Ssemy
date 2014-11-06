<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" import="org.springframework.security.core.Authentication, org.springframework.security.core.context.SecurityContextHolder" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Compose</title>
  <script src="js/jquery-1.10.2.js" type="text/javascript"></script>
  <script src="js/jquery-ui.js" type="text/javascript"></script>
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/style.css">
<LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">
</head>
<body>
<div class="header">
		<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
	</div>
	
	 <div class="clear"></div>
	<div>
		<div class="userleft"><%@include file="/WEB-INF/jsp/include/mail_navigation.jsp" %></div>
		<div class="usercenter">
				<div class="pure-menu pure-menu-open">
		        <a class="pure-menu-heading">Compose Mail</a>
		
			
				    <form:textarea style="width: 70%;height: 30px;" path="mailTo" placeholder="To"></form:textarea>
			        <br/>	
			        <form:errors path="mailTo"></form:errors>
			        <br/>
			        <form:textarea style="width: 70%;height: 30px;" path="mailCC" placeholder="CC"></form:textarea>
			        <br/>	
			        <form:errors path="mailCC"></form:errors>
			        <br/>
			        <form:textarea style="width: 70%;height: 30px;" path="mailBCC" placeholder="BCC"></form:textarea>
			        <br/>	
			        <form:errors path="mailBCC"></form:errors>
			        <br/>
					<br/>
					<div>

					<br><br>
					 <div class="imageDiv"  style="display: none;">
					 	<input type="file"  accept="image/*" name="questionImage">
					 	<input type="submit" id=uploadQuestionImage value="Upload Photo">
					 </div>
					</div>
					<form:textarea style="width: 70%;height: 30px;" path="mailSubject" placeholder="Subject"></form:textarea>
			        <br/>	
			        <form:errors path="mailSubject"></form:errors>
			        <br/>
					<br/>
					
					<form:textarea style="width: 70%;height: 200px;" path="mailBody" placeholder="Body"></form:textarea>
					<br>
					<br>
					<form:errors path="mailBody"></form:errors>
					<br>
					<br>
				 
		
					<input type="submit" value="Post" style="height: 50px;width: 250px;"/>
				</div>
		</div>
		<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
	</div>
</body>
</html>