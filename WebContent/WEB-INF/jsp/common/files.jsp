
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
<title>Files</title>

<style>
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
										<form:errors path="fileSize" cssClass="errormessage"></form:errors>
										 <br> <br>
										 
										 <textarea name="topics" placeholder="Topics(Separated by Comma)"></textarea>
										 <form:errors path="topics" cssClass="errormessage"></form:errors>
										 <br>
										 <br>
										 <input type="radio" name="shared"> &nbsp; Share the File with the World!
										 <br><br>
										 <input type="submit" value="Upload File">
									</div>
							</form:form>
						</div>
				<div class="clear"></div>
		</div>
		
		
		<div style="float:left;width:100%;padding-top:30px;">
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
			<c:forEach items="${files}" var="file">
				<tr>
				<c:set var="fileId"  value="${file.fileId }"/>
				<c:set var="author"  value="${file.authorEmailId }"/>
					<td style="padding-left:70px;"> 
						<a href="download_file.htm?fileId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("fileId")).getBytes()))%>&author=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("author")).getBytes()))%>"><c:out value="${file.filename}" /></a>
					</td>
					<td style="padding-left:80px;">
							<c:out value="${file.fileSize}" />
					</td>
					<td style="padding-left:110px;">
						<div style="float:left;"><a href="delete_file.htm?fileId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("fileId")).getBytes()))%>&author=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("author")).getBytes()))%>">Delete</a></div>
						<div style="float:right;padding-right:75px;"><a href="edit_file.htm?fileId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("fileId")).getBytes()))%>&author=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("author")).getBytes()))%>">Edit</a></div>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</fieldset>
</form>
		</div>
		
		</div>
		<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
	</div>


</body>
</html>