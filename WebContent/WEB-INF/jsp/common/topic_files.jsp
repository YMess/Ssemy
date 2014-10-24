
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" import="org.springframework.security.core.Authentication, org.springframework.security.core.context.SecurityContextHolder" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<%@page import="org.apache.commons.codec.binary.Base64" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Topic Files</title>

<style>

li.main
{
	display: inline;
	list-style-type: none;
	padding-left:20px;
	float:left;
}
.height30{
float:left;
height:30%;
width:100%;
}
.errormessage{
color:red;
}
.width100{
width: 100%;
border: medium;
}
</style>

 <script src="js/jquery-1.10.2.js" type="text/javascript"></script>
  <script src="js/jquery-ui.js" type="text/javascript"></script>
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/style.css">
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">
 
  
</head>

<body>
	<div class="header">
		<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
	</div>
	
	<div>
		<div class="userleft"><%@include file="/WEB-INF/jsp/include/left.jsp" %></div>
		<div class="usercenter">
		
		
		<div style="float:left;width:100%;padding-top:30px;">
						<p style="padding-left: 45%;"></p>
						<ul>
						<c:forEach items="${filesInTopic}" var="userFile">
						<c:set var="fileId"  value="${userFile.fileId }"/>
						<c:set var="author"  value="${userFile.authorEmailId }"/>
							<li class="grid">
								<div><c:out value="${userFile.fileDescription }" /></div>
								<div><a href="download_file.htm?fileId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("fileId")).getBytes()))%>&author=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("author")).getBytes()))%>"><c:out value="${userFile.filename}" /></a>
								</div><div><c:out value="${userFile.fileSize}" /></div>
							</li>
						</c:forEach>
						</ul>
						
	
		</div>
		<div class="clear"></div>
		
		</div>
		<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
	</div>


</body>
</html>