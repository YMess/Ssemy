
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
<title>Post Question</title>
 <script src="js/jquery-1.10.2.js" type="text/javascript"></script>
  <script src="js/jquery-ui.js" type="text/javascript"></script>
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/style.css">
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">
  <script type="text/javascript">
	function uploadQuestionImage(qId)
	{
		alert("Question Id : "+qId);

	
		location.href="question_image_upload.htm?qId="+qId;
	/*  $.ajax({url:"demo_test.txt",success:function(result){
		    $("#div1").html(result);
		  }}); */
	}
</script>
  <script type="text/javascript">
  $().ready(function(){
	  
	   $('input[type="checkbox"]').click(function(){
              $(".imageDiv").toggle();
      });
	});
  </script>
  
  <script>
    function addTopic(){
    
 
var who = $("#topics"); 
  
    
if(who.val().length > 0)    
who.val(who.val() + " , "+$("#topic").val())  ;
else
 who.val($("#topic").val()) ; 
   $("#topic").val("");
    }
</script>
</head>
<body>
	<div class="header">
		<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
	</div>
	<div>
		<div class="userleft"><%@include file="/WEB-INF/jsp/include/left.jsp" %></div>
		<div class="usercenter">
			<div class="pure-menu pure-menu-open">
						    <a class="pure-menu-heading">Post Question</a>
		
			<%-- <form:form action="question_image_upload.htm" method="POST" modelAttribute="user" enctype="multipart/form-data">
		       
			   <div>
			   <c:set var="questionIds" value="${question.questionId}"/>
					<c:if test="${question.isImageAttached eq true }">
						  	<img height="60%" width="50%" src="user_question_image.htm?qId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionIds")).getBytes()))%>">
				    </c:if>
					</div>
					
					<input id="addImage" type="checkbox" name="isImageAttached"> Change Image
					<div class="imageDiv"  style="display: none;">							
					<input type="file"  accept="image/*" name="questionImage">
					 <!--  <input type="submit" id=uploadQuestionImage value="Upload Photo">-->
					<button onclick="uploadQuestionImage('${question.questionId}')">Upload</button>
					</div>
				</form:form> --%>
		
				<form:form action="user_post_question.htm" modelAttribute="question" enctype="multipart/form-data" id="postQuestionForm">
				
				
				<c:if test="${not empty param.qId}">
					<form:hidden path="questionId" value="${param.qId }"/>
				</c:if>
				    <form:textarea style="width: 70%;height: 30px;" path="questionTitle" placeholder="Your Question Title?"></form:textarea>
			        <br/>
			        <br/>	
			        <form:errors path="questionTitle"></form:errors>
			        <br/>
			        <br/>
					<form:textarea style="width: 70%;height: 200px;" path="questionDescription" placeholder="Your Question Description?"></form:textarea>
					<br>
					<br>
					<form:errors path="questionDescription"></form:errors>
					<br>
					<br>
					<c:set var="questionId" value="${question.questionId}"/>
					<c:if test="${question.isImageAttached eq true }">
						  	<img height="60%" width="50%" src="user_question_image.htm?qId=<%=new String(Base64.encodeBase64(String.valueOf(pageContext.getAttribute("questionId")).getBytes()))%>">
				    </c:if>
				    <br>
				    
					<input id="addImage" type="checkbox" name="isImageAttached"> Change Image
					<br><br>
					 <div class="imageDiv"  style="display: none;">
					 	<input type="file"  accept="image/*" name="questionImage">
					 	<input type="submit" id=uploadQuestionImage value="Upload Photo">
					 </div>

					 	<form:errors path="questionImage"></form:errors>
					<br>
					
					<p>Question Topics(Add Topics Separated by Commas)</p>
					 <textarea id="topics" name="topics"></textarea>
					 <br>
					 <br>
					 <form:errors path="topics"></form:errors>
					 
   						 <br>
      					 <br>
   				 <!--  <span>Type a Topic and Add</span>
    				<input type="text" id="topic" onchange="">
       				<input type="button" value="Add Topic" onclick="addTopic()">
       				
 -->      				<!-- <span>Question Content</span>

					<input type="radio" name="questionContentType" value="SuitableForAll"> Suitable For All <br>
					<input type="radio" name="questionContentType" value="Adult"> Suitable only for Adults<br><br>	 -->		
					<input type="submit" value="Post" style="height: 50px;width: 250px;"/>
				</form:form>
				</div>
				
		</div>
		<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
	</div>
</body>
</html>