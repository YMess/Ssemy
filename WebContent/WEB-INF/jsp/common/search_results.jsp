
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
<title>Search Results</title>

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
		<ul>
			<c:if test="${not empty questions }">
			Questions Fetched : <c:out value="${questionCount }"/>
			<br>
				<c:forEach var="question" items="${questions}"> 
				
					<li>
						<div><c:out value="${question.value.questionTitle }"/></div>
						<div><c:out value="${question.value.questionDescription }"/></div>
						<div><c:out value="${question.value.authorEmailId }" /></div>
					</li>
				
				</c:forEach>
			
			</c:if>
			
			<c:if test="${not empty users }">
					<li>
					</li>
			</c:if>
			
			<c:if test="${not empty files }">
					<li>
					</li>
			</c:if>
		</ul>
		
		</div>
		<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
	</div>


</body>
</html>