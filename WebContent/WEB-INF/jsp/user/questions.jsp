
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
<title>Questions</title>

 <script src="js/jquery-1.10.2.js" type="text/javascript"></script>
  <script src="js/jquery-ui.js" type="text/javascript"></script>
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/style.css">
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">
 
  
</head>

<script type="text/javascript">
function editQuestionDesc(questionId)
{
	alert("Question Id : "+questionId);

	/*  $.ajax({url:"demo_test.txt",success:function(result){
		    $("#div1").html(result);
		  }}); */
}
</script>

<body>
	<div class="header">
		<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
	</div>
	
	<div>
		<div class="userleft"><%@include file="/WEB-INF/jsp/include/left.jsp" %></div>
		<div class="usercenter">
			<c:choose>
	<c:when test="${not empty emptyResultSet }">
					No records Found! Please add Questions <a href="user_post_question.htm">Here</a>.
	</c:when>
	<c:otherwise>
	<div class="pure-menu pure-menu-open">
						    <a class="pure-menu-heading">My Questions</a>
			<ul>
				<c:forEach items="${questions}" var="question">
					<li>
					<c:set var="questionId" value="${question.questionId}"/>
					 <a href="user_question_responses.htm?qId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionId")).getBytes()))%>"><c:out value="${question.questionDescription }"></c:out></a>	
						  <c:if test="${question.isImageAttached eq true }">
						  	<img height="60%" width="50%" src="user_question_image.htm?qId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionId")).getBytes()))%>">
						 <br>
						  </c:if>
						  
						  
						 
								<!-- <ul style="display: inline;margin-left: 20px;"> -->
								
								<c:forEach items="${question.topics }" var="topic">
								<c:set var="topic" value="${topic }"></c:set>
								<!-- <li style="float: left;"> --><a href="view_topic_questions.htm?topic=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("topic")).getBytes()))%>"><c:out value="${ topic}" /></a><!-- </li> -->
						  		<%-- <c:out value="${ topic}" />&nbsp; --%>
						 		 </c:forEach>
						
						  
						  
						  
						  
						  
						  
						    <a class="pure-menu-heading">Updated Date :  <c:out value="${question.updatedDate }"></c:out></a>  
						<button onclick="editQuestionDesc('${question.questionId}')">Edit</button>
						<br>
						    <a class="pure-menu-heading"><c:out value="${question.lastAnswer}"/></a>
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