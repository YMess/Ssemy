
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
<script src="js/jquery-1.10.2.js" type="text/javascript"></script>
  <script src="js/jquery-ui.js" type="text/javascript"></script>
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/style.css">
<LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">
<title>Mail</title>
</head>
<body>
<div class="header">
		<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
	</div>
	
	 <div class="clear"></div>
	<div>
		<div class="userleft"><%@include file="/WEB-INF/jsp/include/left.jsp" %></div>
		<div class="usercenter">
					<!-- <div id="dialog-confirm" align="center" style="display: none">
							<textarea rows="5" cols="40" id="answerTextArea"></textarea>
						<br>
						<button id="postAnswer">Post Answer</button>
					</div> -->
					
										<div id="dialog-confirm" align="center" style="display: none">
	                                        <form:form action="userdashboard.htm" modelAttribute="answer" enctype="multipart/form-data" id="postAnswerForm">
	                                                        <form:hidden path="questionId" />
	                                                        <form:textarea path="answerDescription" />
	                                            <br>
	                                            <br>
	                                             <input id="addImage" type="checkbox" name="isImageAttached"> Add Image
	                                            <br><br>
	                                              <div class="imageDiv"  style="display: none;">
	                                                	<input type="file"  accept="image/*" name="answerImage" id="answerImage">
	                                              </div>
	                                                <form:errors path="answerImage"></form:errors>
	                                       			<br>
	                                                <br>
	                                                <button id="postAnswer">Post Answer</button>
	                                        </form:form>
                                        </div>	
		
				<c:choose>
					<c:when test="${not empty emptyResultSet }">
									No records Found! Please add Questions <a href="user_post_question.htm">Here</a>.
					</c:when>
					<c:otherwise>
						
						<div class="pure-menu pure-menu-open">
						    <a class="pure-menu-heading">Questions</a>
						 	<ul style="padding-left: 10px;">
								<c:forEach items="${questions}" var="question">
								 	
								 	<li>
									 	<c:set var="questionId" value="${question.questionId}"/>
									 	<c:set var="emailId" value="${question.authorEmailId }"></c:set>
									 	
									 	
										<a class="pure-menu-heading" href="user_question_responses.htm?qId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionId")).getBytes()))%>">
										<span><c:out value="${question.questionTitle }"></c:out></span></a>
										
										 <c:if test="${question.isImageAttached eq true }">
							  				<img height="60%" width="50%" src="user_question_image.htm?qId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionId")).getBytes()))%>">
							 				 <br>
							 			 </c:if>
							 			<c:out value="${question.questionDescription }"></c:out>
							 			<br/>
										<span>Posted By :</span> <a class="pure-menu-heading" href="user_view_profile.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("emailId")).getBytes()))%>">
										<c:out value="${question.firstName }"></c:out>&nbsp;<c:out value="${question.lastName }"></c:out></a>
										
										<span>Last Updated :</span> <c:out value="${question.updatedDate }"></c:out>  
										<%-- <button onclick="editQuestionDesc('${question.questionId}')">Edit</button> --%>
										
										<br>
										<h3><c:out value="${question.lastAnswer}"/></h3>
										
										<input type="button" class="btnOpenDialog" value="Add Answer" id="${question.questionId}"/>
									
										<br>
										<br>
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