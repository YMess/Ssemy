
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" import="org.springframework.security.core.Authentication, org.springframework.security.core.context.SecurityContextHolder" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Timeline</title>
<script src="js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="js/jquery-ui.js" type="text/javascript"></script>
<LINK REL=Stylesheet TYPE ="text/css" HREF="css/style.css">
<LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">
<style>
.inline
{
width:100%;
height: 20%;
display: inline-block;"
}
</style>

</head>
<body>

	<div class="header">
		<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
	</div>
	
	<div>
		<div class="userleft"><%@include file="/WEB-INF/jsp/include/left.jsp" %></div>
		<div class="usercenter">
		
						
						<div class="pure-menu pure-menu-open">
						    <a class="pure-menu-heading">Timeline</a>
						 	<ul style="padding-left: 10px;">
								<c:forEach items="${userTimeline}" var="userTimeline">
								
								  <c:if test="${userTimeline.hasJoined eq true}">
                                  <!-- Joined Start -->
                                  <li class="inline">
                                  <div>
                                        <c:set var="userEmailId" value="${userTimeline.userEmailId }"></c:set>
                                    	<div style="float: left;padding-top: 20px;">
                                    		<img height="100px"  width="100px" src="user_view_profile_image.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("userEmailId")).getBytes()))%>">
                                    	</div>
                                    	<div style="float: left;padding-left: 20px;padding-top: 20px;">
                                    		<div style="height: 30% ;">
 												     <a href='user_view_profile.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("userEmailId")).getBytes()))%>'>
														<c:out value="${userTimeline.userFirstName }" />&nbsp;<c:out value="${userTimeline.userLastName }" /></a>                              		
                                    		</div>
                                    		<div>
												Joined on <c:out value="${userTimeline.joinedJoiningDate }"/>
                                    		</div>
                                    		<div>
                                    		   
                                    		</div>
                                    	
                                    	</div>
                                  </div>
                                  </li>
                                  <!-- Joined End -->
                                  
                                  </c:if>
                                  
                                 <c:if test="${userTimeline.hasUpdatedProfile eq true}">
                                 <!-- ProfileUpdate Start -->
                                 <li class="inline">
                                  <div>
                                        <c:set var="userEmailId" value="${userTimeline.userEmailId }"></c:set>
                                    	<div>
                                    	<div style="float: left;padding-top: 20px;">
                                    		<img height="100px"  width="100px" src="user_view_profile_image.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("userEmailId")).getBytes()))%>">
                                    	</div>
                                    	<div style="float: left;padding-left: 20px;padding-top: 20px;">
                                    		<div style="height: 30% ;">
 												     <a href='user_view_profile.htm?aId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("userEmailId")).getBytes()))%>'>
														<c:out value="${userTimeline.userFirstName }" />&nbsp;<c:out value="${userTimeline.userLastName }" /></a>                              		
                                    		</div>
                                    		<div>
												Designation,Current Organization 
                                    		</div>
                                    		<div>
                                    		    Topics
                                    		</div>
                                    	
                                    	</div>
                                    	<div>
                                    		Updated Profile
                                    	</div>
                                    	</div>
                                    	<div>
                                    	  <div style="float: left; height: 50%;width: 100%">

                            <div >
	                            <p>Personal Details</p>
								Name : <c:out value="${userTimeline.profileUpdatedFirstName  }"/> <c:out value="${userTimeline.profileUpdatedLastName  }" />
								<br>
								Email Id : <c:out value="${userTimeline.userEmailId  }" />
                            </div>
                            
							<div>
	                            <p>Professional Details</p>
								Current Organization : <c:out value="${userTimeline.profileUpdatedOrganization }" />
								<br>
								Designation : <c:out  value="${userTimeline.profileUpdatedDesignation }" />
	                            <br>
								Previous Organizations : <c:out value="${userTimeline.profileUpdatedPreviousOrganizations }" />
							</div>
					
							<div>
								Interests : <c:out value="${userTimeline.profileUpdatedInterests }"/>
							</div>
					</div>
					<div>
						Last Update <c:out value="${userTimeline.userTimestamp }"/>
					</div>
                                    	  
                                    	</div>
                                  </div>
                                  </li> 
                                 <!-- ProfileUpdate End --> 
                                 </c:if> 
                                  
                                  <c:if test="${userTimeline.hasUpdatedProfile eq true}">
                                 <!-- Questions Start -->
                                 <li class="inline">
                                     <div>
                                     	<c:set var="questionId" value="${userTimeline.questionPostedId}"/>
					 <a href="user_question_responses.htm?qId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionId")).getBytes()))%>"><c:out value="${userTimeline.questionPostedDesc }"></c:out></a>	
						  <c:if test="${userTimeline.questionIsImageAttached eq true }">
						  	<img height="60%" width="50%" src="user_question_image.htm?qId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionId")).getBytes()))%>">
						 <br>
						  </c:if>
						  
						  
						 
								<!-- <ul style="display: inline;margin-left: 20px;"> -->
								
								<c:forEach items="${userTimeline.questionTopics }" var="topic">
								<c:set var="topic" value="${topic }"></c:set>
								<!-- <li style="float: left;"> --><a href="view_topic_questions.htm?topic=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("topic")).getBytes()))%>"><c:out value="${ topic}" /></a><!-- </li> -->
						  		<%-- <c:out value="${ topic}" />&nbsp; --%>
						 		 </c:forEach>
						
						  
						  
						  
						  
						  
						  
						    <a class="pure-menu-heading">Updated Date :  <c:out value="${userTimeline.questionUpdatedDate }"></c:out></a>  
						<button onclick="editQuestionDesc('${userTimeline.questionPostedId}')">Edit</button>
						<br>
						    <a class="pure-menu-heading"><c:out value="${userTimeline.questionLastAnswer}"/></a>
						<br>
						<br>
                                     </div>
                                 </li>
                                  <!-- Questions End -->
                                 </c:if>
                       </c:forEach> 
						            
                                    <%-- <li><c:out value="${userTimeline.userEmailId }"/></li>
                                    <li><c:out value="${userTimeline.userTimestamp }"/></li>
                                    <li><c:out value="${userTimeline.activity }"/></li>
                                    <li><c:out value="${userTimeline.userFirstName }"/></li>
                                    <li><c:out value="${userTimeline.userLastName }"/></li>               
                                  </c:if>
                                  
                                  <c:if test="${userTimeline.hasPostedQuestion eq true}">
                                    <li>
					<c:set var="questionId" value="${userTimeline.questionPostedId}"/>
					 <a href="user_question_responses.htm?qId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionId")).getBytes()))%>"><c:out value="${userTimeline.questionPostedDesc }"></c:out></a>	
						  <c:if test="${question.questionIsImageAttached eq true }">
						  	<img height="60%" width="50%" src="user_question_image.htm?qId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionId")).getBytes()))%>">
						 <br>
						  </c:if>
						  
						  
						 
								<!-- <ul style="display: inline;margin-left: 20px;"> -->
								
								<c:forEach items="${userTimeline.questionTopics }" var="topic">
								<c:set var="topic" value="${topic }"></c:set>
								<!-- <li style="float: left;"> --><a href="view_topic_questions.htm?topic=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionTopics")).getBytes()))%>"><c:out value="${ topic}" /></a><!-- </li> -->
						  		<c:out value="${ topic}" />&nbsp; --%>
						 		
						  
						  
						  
						  
						  
						  
						   <%--  <a class="pure-menu-heading">Updated Date :  <c:out value="${question.updatedDate }"></c:out></a>  
						<button onclick="editQuestionDesc('${question.questionId}')">Edit</button>
						<br>
						    <a class="pure-menu-heading"><c:out value="${question.lastAnswer}"/></a>
						<br> --%>
					<%-- 	<br>
						</li>
					
                                  </c:if>
                                  
                                  <c:if test="${userTimeline.hasAnsweredQuestion eq true}">
                                    <li><c:out value="${userTimeline.userEmailId }"/></li>
                                    <li><c:out value="${userTimeline.userTimestamp }"/></li>
                                    <li><c:out value="${userTimeline.activity }"/></li>
                                    <li><c:out value="${userTimeline.userFirstName }"/></li>
                                    <li><c:out value="${userTimeline.userLastName }"/></li>
                                  </c:if>
                                  
                                  <c:if test="${userTimeline.hasFollowing eq true}">
                                    <li><c:out value="${userTimeline.userEmailId }"/></li>
                                    <li><c:out value="${userTimeline.userTimestamp }"/></li>
                                    <li><c:out value="${userTimeline.activity }"/></li>
                                    <li><c:out value="${userTimeline.userFirstName }"/></li>
                                    <li><c:out value="${userTimeline.userLastName }"/></li>
                                  </c:if>
                                  
                                  <c:if test="${userTimeline.hasUpvoted eq true}">
                                    <li><c:out value="${userTimeline.userEmailId }"/></li>
                                    <li><c:out value="${userTimeline.userTimestamp }"/></li>
                                    <li><c:out value="${userTimeline.activity }"/></li>
                                    <li><c:out value="${userTimeline.userFirstName }"/></li>
                                    <li><c:out value="${userTimeline.userLastName }"/></li>
                                  </c:if>
                                  
                                   <c:if test="${userTimeline.hasDownvoted eq true}">
                                    <li><c:out value="${userTimeline.userEmailId }"/></li>
                                    <li><c:out value="${userTimeline.userTimestamp }"/></li>
                                    <li><c:out value="${userTimeline.activity }"/></li>
                                    <li><c:out value="${userTimeline.userFirstName }"/></li>
                                    <li><c:out value="${userTimeline.userLastName }"/></li>
                                  </c:if>
                                  
                                   <c:if test="${userTimeline.hasShared eq true}">
                                    <li><c:out value="${userTimeline.userEmailId }"/></li>
                                    <li><c:out value="${userTimeline.userTimestamp }"/></li>
                                    <li><c:out value="${userTimeline.activity }"/></li>
                                    <li><c:out value="${userTimeline.userFirstName }"/></li>
                                    <li><c:out value="${userTimeline.userLastName }"/></li>
                                  </c:if>
                                  
                                  <c:if test="${userTimeline.hasLikedQuestion eq true}">
                                    <li><c:out value="${userTimeline.userEmailId }"/></li>
                                    <li><c:out value="${userTimeline.userTimestamp }"/></li>
                                    <li><c:out value="${userTimeline.activity }"/></li>
                                    <li><c:out value="${userTimeline.userFirstName }"/></li>
                                    <li><c:out value="${userTimeline.userLastName }"/></li>
                                  </c:if>
                                  
                                   <c:if test="${userTimeline.hasLikedAnswer eq true}">
                                    <li><c:out value="${userTimeline.userEmailId }"/></li>
                                    <li><c:out value="${userTimeline.userTimestamp }"/></li>
                                    <li><c:out value="${userTimeline.activity }"/></li>
                                    <li><c:out value="${userTimeline.userFirstName }"/></li>
                                    <li><c:out value="${userTimeline.userLastName }"/></li>
                                  </c:if>
								 	 --%>
							<%-- 	</c:forEach> --%>
							</ul>
							</div>
		</div>
		<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
	
	</div>
</body>
</html>