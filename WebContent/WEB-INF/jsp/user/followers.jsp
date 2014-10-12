
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java"
	import="org.springframework.security.core.Authentication, org.springframework.security.core.context.SecurityContextHolder"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Followers</title>
<!-- <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css"> -->
<script src="js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="js/jquery-ui.js" type="text/javascript"></script>
<LINK REL=Stylesheet TYPE="text/css" HREF="css/style.css">
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">
</head>
<body>

	<div class="header">
		<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
	</div>

	<div>
		<div class="userleft">
			<%@include file="/WEB-INF/jsp/include/left.jsp"%>
		</div>
			<div class="usercenter">
				<c:choose>
					<c:when test="${not empty emptyResultSet }">
										No Followers!
						</c:when>
					<c:otherwise>
						<div class="pure-menu pure-menu-open">
						    <a class="pure-menu-heading">People Following Me</a>
							<ul>
								<c:forEach items="${followers}" var="follower">
								<c:set var="userEmailId" value="${follower.userEmailId }"></c:set>
									<li style="height:100px;">
									<div style="float:left;width:10%;">
										<img height="75px" width="75px" alt="User Image" src="user_view_profile_image.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("userEmailId")).getBytes()))%>" >
									</div>
									
							<div style="float:left;width:90%;">	
								<a href="user_view_profile.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("userEmailId")).getBytes()))%>"><c:out value="${follower.firstName }"/>&nbsp; <c:out value="${follower.lastName }"/></a>
							</div>
									</li>
									
								</c:forEach>
							</ul>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
					<div style="float: left; height: 30%"></div>


			
		</div>
		<div class="userright">
			<%@include file="/WEB-INF/jsp/include/right.jsp"%>
		</div>
</body>
</html>