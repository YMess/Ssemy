
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java"
	import="org.springframework.security.core.Authentication, org.springframework.security.core.context.SecurityContextHolder"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Profile</title>
<!-- <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css"> -->
<script src="js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="js/jquery-ui.js" type="text/javascript"></script>
<LINK REL=Stylesheet TYPE="text/css" HREF="css/style.css">
<LINK REL=Stylesheet TYPE="text/css" HREF="css/pure-min.css">
  
<script type="text/javascript">
	$().ready(function() {
		 $("#suggest").on('change keyup paste',function(){
		  		suggestTopics();
		 	}); 
		  
		$('.btnOpenDialog').click(fnOpenNormalDialog);
		function fnOpenNormalDialog() {

			localStorage.setItem("clickedQuestionId", $(this).attr('id'));

			// Define the Dialog and its properties.
			$("#dialog-confirm").dialog({
				resizable : false,
				modal : true,
				title : "Add Answer",
				height : 250,
				width : 400,

			});

		}

		$("button#postAnswer").click(function() {
			var qId = localStorage.getItem("clickedQuestionId");
			var answer = $("#answerTextArea").val();

			var successFlag = false;
			$.ajax({
				url : "user_post_answer.htm",
				data : 'qId=' + qId + "&answer=" + answer,
				success : function(result) {
					successFlag = true;
				}
			});

			if (successFlag) {
				$(this).dialog('close');
				window.reload();
				successFlag = false;
			}
		});

	});
</script>
<script type="text/javascript">
function suggestTopics()
{
	alert("Suggested Topics");
	var topic = $("#suggest").val();
	alert(topic);
	var successFlag = false;
	$.ajax({
		url : "get_related_topics.htm",
		data : 'topic=' + topic,
		async:false,
		cache:false,
		success : function(result) {
			alert(result);
			successFlag = true;
		},
    error:function(data,status,er) { 
        alert("error: "+data+" status: "+status+" er:"+er);
    }
	});
	
}
</script>

</head>
<body>
	<c:if test="${ not empty successfullyPostedQuestion }">
		<script type="text/javascript">
			alert("Your Question has been posted Successfully");
		</script>
	</c:if>

	<div class="header">
		<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
	</div>


	<div>
		<div class="userleft"><%@include
				file="/WEB-INF/jsp/include/left.jsp"%></div>
		<div class="usercenter">
			<div id="dialog-confirm" align="center" style="display: none">
				<textarea rows="5" cols="40" id="answerTextArea"></textarea>
				<br>
				<button id="postAnswer">Post Answer</button>
			</div>




			<c:choose>
				<c:when test="${not empty emptyResultSet }">
									No Details Found! Please Fill Profile Details!
					</c:when>
				<c:otherwise>

					<div style="height: 100%; width: 100%;">
						
						<div style="height: 25%; width: 100%;">
							<form:form action="user_profile_image_upload.htm" method="POST"
								modelAttribute="user" enctype="multipart/form-data">
								<div style="float: left; height: 15%; width: 30%;">
									<img alt="User Image" src="user_profile_image.htm"
										style="height: 150px; width: 200px;">
								</div>
									<div
										style="float: left; padding-top: 20px; padding-left: 20px;">
										<input type="file" name="userImage"> <br> <br>
										<input type="submit" value="Upload Photo">
									</div>
							</form:form>
						</div>
						<div class="clear"></div>
						<div style="height: 50%; width: 100%;">
							<form:form cssClass="pure-form pure-form-stacked"
								action="user_profile.htm" method="POST" modelAttribute="user">
								<fieldset>
									<legend>Personal Details</legend>

									<div style="float: left; width: 50%;">
										<div class="pure-u-1 pure-u-md-1-3">
											<label for="first-name">First Name</label> <input
												id="first-name" class="width80 ptop20" type="text"
												name="firstName" value="${userDetails.firstName }">
										</div>


										<div class="pure-u-1 pure-u-md-1-3">
											<label for="tagline">Tagline</label> <input id="tagline"
												class="width80 ptop20" type="text" name="tagline"
												value="${userDetails.tagline }">
										</div>




										<div class="pure-u-1 pure-u-md-1-3">
											<label for="organization">Organization</label> <input
												id="organization" class="width80 ptop20" type="text"
												name="organization" value="${userDetails.organization }">
										</div>

									</div>
									<div style="float: left; width: 50%;">

										<div class="pure-u-1 pure-u-md-1-3">
											<label for="last-name">Last Name</label> <input
												id="last-name" class="width80 ptop20" type="text"
												name="lastName" value="${userDetails.lastName }">
										</div>

										<div class="pure-u-1 pure-u-md-1-3">
											<label for="email">E-Mail</label> <input id="email"
												class="width80 ptop20" type="email" required
												name="userEmailId" value="${userDetails.userEmailId }"
												readonly>
										</div>


										<div class="pure-u-1 pure-u-md-1-3">
											<label for="designation">Designation</label> <input
												id="designation" class="width80 ptop20" type="text"
												name="designation" value="${userDetails.designation}">
										</div>

										<div class="pure-u-1 pure-u-md-1-3">
											<label for="previousOrganizations">Previous
												Organizations</label> <input id="previousOrganizations"
												class="width80 ptop20" type="text"
												name="previousOrganizations"
												value="${userPreviousOrganizations }">
										</div>


									</div>

									<div style="float: left; width: 50%;">
										<div class="pure-u-1 pure-u-md-1-3">
											<label for="interests">Interests</label> <input
												id="interests" type="text" class="width80 ptop20"
												name="interests" value="${userInterests}">
												
												
												<input type="text" id="suggest">
												<!-- <label for="tags">Tag programming languages: </label>
  												<input id="tags" size="50"> -->
										</div>
									</div>

								<!-- 	<div style="float: left; width: 50%;">

										<div class="pure-u-1 pure-u-md-1-3">
											<label for="interest">Search Interests</label> <input
												id="interest" class="width80 ptop20"> <br> <input
												type="button" value="Add Interest"> <br>
										</div>
									</div> -->

									<div style="float: left; width: 100%;">
										<button type="submit" class="pure-button pure-button-primary">Update Details</button>
									</div>

								</fieldset>
							</form:form>
						</div>
					</div>

				</c:otherwise>
			</c:choose>
		</div>
		<div style="float: left; height: 30%"></div>



	</div>
	<div class="userright"><%@include
			file="/WEB-INF/jsp/include/right.jsp"%>\
	</div>
</body>
</html>