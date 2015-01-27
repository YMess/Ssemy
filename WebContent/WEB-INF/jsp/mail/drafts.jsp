<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
$(document).ready(function(){
    $('#deleteInbox').click(function() {
        var deleteMailIds = new Array();
        var k = 0;
        $('input:checked').each(function(index,value) {
        	deleteMailIds[k] = value.id;
        	alert(value.id);
            k++;
        });        
        alert(deleteMailIds[0]);

        $.ajax({
    		  url:"delete_mails.json",
    		  data:'mailIds='+deleteMailIds,
    		  async:false,
    		  error : function() {
    	        console.log("error");
    	      },
    		  success:function(result){
    			  alert(mailSize+" Mails Deleted Successfully");
    			  location.reload();
      }}); 
    });
});
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
						<input type="button" value="Delete" id="deleteInbox" style="height: 50px;width: 250px;"/>
						</div>
						<div class="pure-menu pure-menu-open">
						 	<ul style="padding-left: 10px;">
								<c:forEach items="${mails}" var="mail">
									<c:set var="mailId" value="${mail.mailId}" />
								 	<li onclick="location.href='get_mail_details.htm?mId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("mailId")).getBytes()))%>'">
									 	<div>
									 		<div style="width: 30%; float: left;">
												<div style="float: left;"><input type="checkbox" name="checkmark" id="${mail.mailId}" value="${mail.mailId}">&nbsp;</div>
											 	<div style="float: left;"><c:out value="${mail.mailFrom }" />&nbsp;</div>
									 		</div>
									 		<div style="width: 45%; float: left;">
												 <div style="float: left;"><b><i><c:out value="${mail.mailSubject }"></c:out></i></b>&nbsp;<c:out value="${mail.mailBody }" /></div>
											</div>
									 		<div style="width: 25%; float: right;">
										 		<div style="float: left;">
												 		<c:if test="${mail.isAttachmentAttached eq true }">
												 			<div>
											  					<img  height="20" width="25" src="images/Attachment_icon.png">		
											 	    		</div>
											 	    	</c:if>
											 		</div>
											 	<div style="float: left;"><%-- <c:out value="${mail.mailSentTimestamp }"/> --%>
											 	
											 	<fmt:formatDate type="date" value="${mail.mailSentTimestamp}" />
											 	
											 	</div>
									 		</div>
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