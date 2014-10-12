
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
<title>Question Responses</title>
  <!-- <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css"> -->
  <script src="js/jquery-1.10.2.js" type="text/javascript"></script>
  <script src="js/jquery-ui.js" type="text/javascript"></script>
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/style.css">
   <LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">
 

<script type="text/javascript">
$().ready(function(){

	
	
});
</script>

<script type="text/javascript">
function upvote(questionId,answerId)
{
	$.ajax({
	    type: "GET",
	    url: "user_upvote_answer.htm",
	    data: 'qId='+questionId+'&aId='+answerId,
	    async:false,
	    contentType: "application/json; charset=utf-8",
	    success: function(result){
	        alert("Successfully Upvoted the Answer");
	        location.reload();
	        console.log(result);
	    }
	});
}
</script>
<script type="text/javascript">
function downvote(questionId,answerId)
{

	$.ajax({
	    type: "GET",
	    url: "user_downvote_answer.htm",
	    data: 'qId='+questionId+'&aId='+answerId,
	    async:false,
	    contentType: "application/json; charset=utf-8",
	    success: function(result){
	        alert("Successfully Downvoted the Answer");
	        console.log(result);
	        location.reload();
	    }
	});
}
</script>

</head>
<body>
	


		<div class="header">
				<%@include file="/WEB-INF/jsp/include/header.jsp" %>
		</div>
			<div>
				<div class="userleft">	<%@include file="/WEB-INF/jsp/include/left.jsp" %></div>
				<div class="usercenter">
					<c:choose>
						<c:when test="${not empty emptyResultSet }">
										No records Found! Please Go Back <a href="userdashboard.htm">Here</a>.
						</c:when>
						<c:otherwise>
						<div class="pure-menu pure-menu-open">
						    <a class="pure-menu-heading">Responses</a>
							
							<ul>
									<c:forEach items="${answers}" var="answer">
									
									<li>
									 <c:set var="questionId" value="${answer.questionId}"/>
									 <c:set var="answerId" value="${answer.answerId }"></c:set>
									 <c:set var="authorEmailId" value="${answer.authorEmailId}"></c:set>
									 
									 <p><c:out value="${answer.questionDescription }"></c:out></p>
									Answer: <p><c:out value="${answer.answerDescription }"></c:out></p>
									Answered By :<a href="user_view_profile.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("authorEmailId")).getBytes()))%>"> <p><c:out value="${answer.firstName}"></c:out>&nbsp;<c:out value="${answer.lastName}"></c:out></p></a>
									Answered Time :	<legend> <c:out value="${answer.answeredTime }"></c:out>  </legend>
									Upvoters : <c:choose><c:when test="${answer.upvoteCount != 0 }"><a href="user_answer_upvoters.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("answerId")).getBytes()))%>&qId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionId")).getBytes()))%>"><c:out value="${answer.upvoteCount }"></c:out></a></c:when> 
										<c:otherwise><span>0</span></c:otherwise>
										</c:choose>
										
										<c:if test="${! loggedInUser.equals(authorEmailId)}">
											<c:choose>
												<c:when test="${answer.upvotedUsers.contains(authorEmailId)}">
													<p>Already Upvoted</p>
												</c:when>
												<c:otherwise>
													<input type="button" style="padding-left: 20px;" onclick="upvote('${questionId}','${answerId }')" value="Upvote"/>
												</c:otherwise>
											</c:choose>
										</c:if>
										Downvoters : <c:choose><c:when test="${answer.downvoteCount != 0 }"><a href="user_answer_downvoters.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("answerId")).getBytes()))%>&qId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionId")).getBytes()))%>"><c:out value="${answer.downvoteCount }"></c:out></a></c:when> 
										<c:otherwise><span>0</span></c:otherwise>
										</c:choose>
										<c:if test="${! loggedInUser.equals(authorEmailId)}">
											<c:choose>
												<c:when test="${answer.downvotedUsers.contains(authorEmailId)}">
													<p>Already Downvoted</p>
												</c:when>
												<c:otherwise>
													<input type="button" style="padding-left: 20px;" onclick="downvote('${questionId}','${answerId }')" value="Downvote"/>
												</c:otherwise>
											</c:choose>
										</c:if>
										
										
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