
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
<title>Downvoters</title>
  <!-- <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css"> -->
  <script src="js/jquery-1.10.2.js" type="text/javascript"></script>
  <script src="js/jquery-ui.js" type="text/javascript"></script>
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/style.css">
   <LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">


</head>
<body>
	


		<div class="header">
				<%@include file="/WEB-INF/jsp/include/header.jsp" %>
		</div>
			<div>
				<div class="userleft">	<%@include file="/WEB-INF/jsp/include/left.jsp" %></div>
				<div class="usercenter">
				
						<div class="pure-menu pure-menu-open">
						    <a class="pure-menu-heading">Downvoters</a>
							<ul>
							
									<c:forEach items="${downvoters}" var="downvoter">
									
									<li>
										 <c:set var="emailId" value="${downvoter.userEmailId}"/>
										<a href="user_view_profile.htm?aId=<%= new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("emailId")).getBytes()))%>"> <p><c:out value="${downvoter.firstName }"></c:out> &nbsp;<c:out value="${downvoter.lastName }"></c:out></p></a>
							<%-- 	<c:if test="${! loggedInUser.toString().equals(emailId) }">
									<input type="button" value="Follow" onclick="followUser('<%= new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("emailId")).getBytes()))%>')"/>
								</c:if> --%>
									</li>
									</c:forEach>
									
						</ul>
						</div>
						
						
				</div>
				<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
			</div>
</body>
</html>