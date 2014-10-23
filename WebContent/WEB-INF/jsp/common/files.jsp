
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
									<div style="float: left;">
										<input type="file" name="fileData">
										
										
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
										
										
										Uploaded File : <c:out value="${filename }"/>
										
										<form:errors path="fileSize" cssClass="errormessage"></form:errors>
										 <br> <br>
										 
										 <c:choose>
											 <c:when test="${not empty topics}">
											 	 <textarea name="topics"><c:out value="${topics}" /></textarea>
											 </c:when>
											 <c:otherwise>
											  <textarea name="topics" placeholder="Enter Topics(Separated By Comma)"></textarea>
											  <form:errors path="fileSize"></form:errors>
											 </c:otherwise>
										 </c:choose>
										
										 <br>
										 <br>
										 
										  <c:choose>
											 <c:when test="${not empty shared}">
											 	  <input type="radio" name="shared" checked="checked"> &nbsp;Already Shared
											 </c:when>
											 <c:otherwise>
											  <input type="radio" name="shared"> &nbsp; Share the File with the World!
											 </c:otherwise>
										 </c:choose>
										 
										
										 <br><br>
										 <input type="submit" value="Upload File">
									</div>
							</form:form>
						</div>
				<div class="clear"></div>
		</div>
		
		<div style="float:left;width:100%;padding-top:30px;">
						<p style="float:left;padding-left: 45%;">	MyFiles</p>
		<form>
	<fieldset>
		<table class="width100">
			<thead>
			
				<tr>
					<th style="width:35%;">FileName</th>
					<th style="width:20%;">FileSize</th>
					<th style="width:25%;">Actions</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${userFiles}" var="userFile">
				<tr>
				<c:set var="fileId"  value="${userFile.fileId }"/>
				<c:set var="author"  value="${userFile.authorEmailId }"/>
					<td style="padding-left:70px;"> 
						<a href="download_file.htm?fileId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("fileId")).getBytes()))%>&author=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("author")).getBytes()))%>"><c:out value="${userFile.filename}" /></a>
					</td>
					<td style="padding-left:80px;">
							<c:out value="${userFile.fileSize}" />
					</td>
					<td style="padding-left:110px;">
						<div style="float:left;"><a href="delete_file.htm?fId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("fileId")).getBytes()))%>&author=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("author")).getBytes()))%>">Delete</a></div>
						<div style="float:right;padding-right:75px;"><a href="edit_file.htm?fId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("fileId")).getBytes()))%>&author=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("author")).getBytes()))%>">Edit</a></div>
						
						<c:if test="${userFile.shared eq false}">
							<div style="float:right;padding-right:75px;"><a href="share_file.htm?fId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("fileId")).getBytes()))%>&author=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("author")).getBytes()))%>">Share</a></div>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</fieldset>
</form>
		</div>
		<div class="clear"></div>
		<div style="width:100%;float:left;">
				<p style="padding-left: 45%;padding-top: 20px;">	Popular Topics</p>
				<div id="navcontainer">
					<ul id="navlist">
					
					<c:forEach var="popularFile" items="${popularFiles}">
					
					<li id="active" class="main"><a href="#" id="current"><c:out value="${popularFile.key }"/></a>
					   <ul>
					  
					   <c:forEach var="popularFileDetails" items="${popularFile.value}">
					  
					       <li>
					       	<a href="view_file_details.htm"><c:out value="${popularFileDetails.filename}"/></a><br>
					       	<c:out value="${popularFileDetails.fileSize }"/>
					       </li>
					    </c:forEach>    
					    
					    </ul>
					    
					    </li>
				 </c:forEach>	    
					</ul>
				</div>
									
				
		</div>
		
		</div>
		<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
	</div>


</body>
</html>