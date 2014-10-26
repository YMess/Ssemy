
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
			<span>Questions Fetched : </span><c:out value="${questionCount }"/>
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
			<span>Users Fetched : </span><c:out value="${userCount }"/>
			<br><br>
				<c:forEach var="user" items="${users}"> 
					<c:set var="userId" value="${user.value.userEmailId }" />
					<li style="height:100px;">
						<div>
						
							<div style="float:left;">
								<img alt="User Image" src="user_view_profile_image.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("userId")).getBytes())) %>" height="75px;" width="75px;">
							</div>
							
							<div style="float:left;padding=left:20px;"><a href="user_view_profile.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("userId")).getBytes())) %>"><c:out value="${user.value.firstName }"/>&nbsp;<c:out value="${user.value.lastName }"/></a></div>
							<div><c:out value="${user.value.designation }"/></div>
							<div><c:out value="${user.value.organization }" /></div>
						
						</div>
						<div class="clear"></div>
					</li>
				
				</c:forEach>
			
			</c:if>
			
			<c:if test="${not empty files }">
			<span>Files Fetched : </span><c:out value="${fileCount }"/>
			<br>
				<c:forEach var="file" items="${files}"> 
				<c:set var="authorEmailId" value="${file.value.authorEmailId}" />
					<li style="padding-top:20px;padding-bottom: 20px;">
						<span style="float:left;"> Description : </span><div><c:out value="${file.value.fileDescription }"/></div><br><br>
						<span style="float:left;">Name : </span><div style="padding-left: 10px;"><c:out value="${file.value.filename }"/></div>
						<span style="float:left;">Time : </span><div><c:out value="${file.value.uploadedTime }"/></div>
						<span style="float:left;">Author : </span><div><a href="user_view_profile.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("authorEmailId")).getBytes())) %>"><c:out value="${file.value.authorFirstName }"/>&nbsp;<c:out value="${file.value.authorLastName }"/></a></div>
						<span style="float:left;"> Topics : </span><div><c:out value="${file.value.topics}"/></div>
					</li>
				
				</c:forEach>
			
			</c:if>
		</ul>
		
		</div>
		<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
	</div>


</body>
</html>