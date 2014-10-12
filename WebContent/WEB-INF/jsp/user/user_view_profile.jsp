
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
<title>User View Profile</title>
<!-- <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css"> -->
<script src="js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="js/jquery-ui.js" type="text/javascript"></script>
<LINK REL=Stylesheet TYPE="text/css" HREF="css/style.css">
   <LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">

<script type="text/javascript">

function followUser(userEmailId)
{

	  $.ajax({
		  url:"user_follow.htm",
		  data:'eId='+userEmailId,
		  async:false,
		  success:function(result){
			  alert("Successfully Followed User");
			  location.reload();
  }}); 
}

function unfollowUser(userEmailId)
{
			  $.ajax({
				  url:"user_unfollow.htm",
				  data:'eId='+userEmailId,
				  async:false,
				  success:function(result){
					  alert("Successfully UnFollowed User");
					  location.reload();
		  }}); 
}
</script>



</head>
<body>

	<div class="header">
		<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
	</div>

	<div>
		<div class="userleft">
			<%@include file="/WEB-INF/jsp/include/left.jsp"%></div>
		<div class="usercenter">

			<c:choose>
				<c:when test="${not empty emptyResultSet }">
									No Details Found! Please Fill Profile Details!
					</c:when>
				<c:otherwise>

					<div style="float: left; height: 15%">
						<img alt="User Image" src="user_view_profile_image.htm?aId=<%=request.getParameter("aId") %>" style="height:150px;width:200px;" >
						<br>
							<div class="pure-menu pure-menu-open">
						    <a class="pure-menu-heading">User Profile</a>
						
						<c:if test="${ empty selfProfile}">
						
						<c:choose>
							<c:when test="${empty alreadyFollowed}">
								<%-- <input type="button" value="Follow" onclick="followUser('<%=request.getParameter("aId")%>')"/> --%>
								<button class="pure-button pure-button-active" onclick="followUser('<%=request.getParameter("aId")%>')">Follow</button>
							</c:when>
							<c:otherwise>
							<button class="pure-button pure-button-disabled">Follow</button>
							<button class="pure-button pure-button-active" onclick="unfollowUser('<%=request.getParameter("aId")%>')">Un-Follow</button>
							
								<!-- <input type="button" value="Un-Follow" /> -->
							</c:otherwise>
						</c:choose>
						
						</c:if>
						</div>
					</div>
					
					<div style="float: left; height: 50%;width: 100%">

                            <div >
	                            <p>Personal Details</p>
								Name : <c:out value="${userDetails.firstName }"/> <c:out value="${userDetails.lastName }" />
								<br>
								Email Id : <c:out value="${userDetails.userEmailId }" />
                            </div>
                            
							<div>
	                            <p>Professional Details</p>
								Current Organization : <c:out value="${userDetails.organization }" />
								<br>
								Designation : <c:out  value="${userDetails.designation }" />
	                            <br>
								Previous Organizations : <c:out value="${userPreviousOrganizations }" />
							</div>
					
							<div>
								Interests : <c:out value="${userInterests }"/>
							</div>
					</div>

	</c:otherwise>
			</c:choose>
					</div>
					<div style="float: left; height: 30%"></div>


			
		</div>
		<div class="userright">
			<%@include file="/WEB-INF/jsp/include/right.jsp"%>
		</div>
</body>
</html>