
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
<title>Files</title>

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
		
		<div class="height30">
						<div style="height: 25%; width: 100%;">
						<form:form action="file_upload.htm" method="POST" modelAttribute="file" enctype="multipart/form-data">
										<!-- Editing an Already Uploaded File -->
										<form:hidden path="fileId"/>
										<form:hidden path="authorEmailId"/>
										
										<spring:bind path="filename">
											<c:set value="${status.value }" var="filename" />
										</spring:bind>
										<spring:bind path="topics">
											<c:set value="${status.value }" var="topics" />
										</spring:bind>
										<spring:bind path="shared">
											<c:set value="${status.value }" var="shared" />
										</spring:bind>
										<spring:bind path="fileDescription">
											<c:set value="${status.value }" var="fileDescription" />
										</spring:bind>
										
										
						<!-- Left Section -->
							<div style="width:40%;float:left;">
								<input type="file" name="fileData">
								<c:out value="${filename }"/>
									<form:errors path="fileSize" cssClass="errormessage"></form:errors>
								<br>
								<br>	  
										  <c:choose>
											 <c:when test="${not empty fileDescription}">
											 	 <textarea name="fileDescription"><c:out value="${fileDescription}" /></textarea>
											 </c:when>
											 <c:otherwise>
											  <textarea name="fileDescription" placeholder="Enter File Description"></textarea>
											  <form:errors path="fileDescription"></form:errors>
											 </c:otherwise>
										 </c:choose>
										
							</div>
						<!-- Left Section -->
						
						<!-- Right Section -->
							<div style="width:50%;float:left;"> 
								 		 <c:choose>
											 <c:when test="${not empty shared}">
											 	  <input type="checkbox" name="shared" checked="checked"> &nbsp;<span> Already Shared </span>
											 </c:when>
											 <c:otherwise>
											  <input type="checkbox" name="shared"> &nbsp;<span> Share the File with the World! </span>
											 </c:otherwise>
										 </c:choose>
									<br>
									<br>	
										  <c:choose>
											 <c:when test="${not empty topics}">
											 	 <textarea name="topics"><c:out value="${topics}" /></textarea>
											 </c:when>
											 <c:otherwise>
											  <textarea name="topics" placeholder="Enter Topics(Separated By Comma)"></textarea>
											  <form:errors path="fileSize"></form:errors>
											 </c:otherwise>
										 </c:choose>
										 
							
							</div>	
						<!-- Right Section -->
						
						 <br><br>
						<input type="submit" value="Upload File">	
						</form:form>	
							
						</div>
				<div class="clear"></div>
		</div>
		
		<div style="float:left;width:100%;padding-top:30px;">
						<p style="padding-left: 45%;">	My Files </p>
						<ul>
						<c:forEach items="${userFiles}" var="userFile">
						<c:set var="fileId"  value="${userFile.fileId }"/>
						<c:set var="author"  value="${userFile.authorEmailId }"/>
							<li class="grid">
								<div><c:out value="${userFile.fileDescription }" /></div>
								<div><a href="download_file.htm?fileId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("fileId")).getBytes()))%>&author=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("author")).getBytes()))%>"><c:out value="${userFile.filename}" /></a>
								</div><div><c:out value="${userFile.fileSize}" /></div>
								<div style="padding-left:25%;float: left;">
									<a href="edit_file.htm?fId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("fileId")).getBytes()))%>&author=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("author")).getBytes()))%>">
										<img height="40px" width="40px" src="images/Edit_Icon.png">
									</a>
								</div>
								<div style="padding-left:10px;float: left;">
								<a href="delete_file.htm?fId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("fileId")).getBytes()))%>&author=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("author")).getBytes()))%>">
									<img height="40px" width="40px" src="images/Delete_Icon.png">
								</a>
								</div>
							</li>
						</c:forEach>
						</ul>
						
	
		</div>
		<div class="clear"></div>
		<div style="width:100%;float:left;">
					<p style="padding-left: 45%;">	Popular Files </p>
						<ul>
					<c:forEach var="popularFile" items="${popularFiles}">
							<li class="gridsmall">
							<c:set var="topic" value="${popularFile.key}" />
								<div style="padding-left: 25%;word-break: break-all;"><a href="view_files_topic.htm?t=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("topic")).getBytes()))%>" id="current"><c:out value="${popularFile.key}"/></a></div>
								<div>
									<ul>
						   				<c:forEach var="popularFileDetails" items="${popularFile.value}">
						      				 <li>
										       	<a href="view_file_details.htm"><c:out value="${popularFileDetails.filename}"/></a><br>
										       	<c:out value="${popularFileDetails.fileSize }"/>
						      				 </li>
						   			    </c:forEach>    
					    			</ul>
								</div>
							</li>
						</c:forEach>
						</ul>
		</div>
		
		</div>
		<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
	</div>


</body>
</html>