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
<title>Inbox</title>
  <script src="js/jquery-1.10.2.js" type="text/javascript"></script>
  <script src="js/jquery-ui.js" type="text/javascript"></script>
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/style.css">
<LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">
<script type="text/javascript">
function deleteMails()
{
  
}
</script>
</head>
<body>
     <c:if test="${ not empty successfullyMailSend }">
				<script type="text/javascript">
						alert("Mail Sent Successfully");
				</script>
				</c:if>
<div class="header">
		<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
	</div>
	
	 <div class="clear"></div>
	<div>
		<div class="userleft"><%@include file="/WEB-INF/jsp/include/mail_navigation.jsp" %></div>
		<div class="usercenter">

				<c:choose>
					<c:when test="${not empty emptyResultSet }">
									No mails Found! 
					</c:when>
					<c:otherwise>
						<div>
						<input type="button" value="Delete" onclick="deleteMails()" style="height: 50px;width: 250px;"/>
						</div>
						<div class="pure-menu pure-menu-open">
						 	<ul style="padding-left: 10px;">
								<c:forEach items="${mails}" var="mail">
								 	<li>
								 	<div>
								 	<div><input type="checkbox" name="checkmark" id="${mail.mailId}"></div>
								 	<div><input type="checkbox" name="impotant"></div>
								 	<div><c:out value="${mail.mailFrom }"></c:out></div>
								 	<div><c:out value="${mail.mailSubject }"></c:out>&nbsp;<c:out value="${mail.mailBody }"></c:out></div>
								 	<div><c:out value="${mail.mailSentTimestamp }"></c:out></div>
								 	<c:if test="${mail.isAttachmentAttached eq true }">
								 	<div>
							  		<img  src="/images/Attachment_icon.png">		
							 	    </div>
							 	    </c:if>
								 	</div>
								 	</li>
								</c:forEach>
							</ul>
							</div>
					</c:otherwise>
			</c:choose>
		</div>
		<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
	</div>
</body>
</html>