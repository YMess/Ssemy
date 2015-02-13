<%@ page language="java" import="org.springframework.security.core.Authentication, org.springframework.security.core.context.SecurityContextHolder" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="demos/switchy.css" rel="stylesheet" />
<link href="demos/bootstrap.min.css" rel="stylesheet" />
<link href="demos/application.css" rel="stylesheet" />
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
<script type="text/javascript">
$(document).ready(function() {
    $("#proceed-register").click(function(){
        alert("button");
    }); 
});
</script>
</script>
</head>
<body>
<h1 align="center" style="margin-top:150px;">Registraton Type</h1>
<br/>
<div class="message">
<p>Please Select the Registration type and then click on proceed.</p>
</div>
<div class="container">
<form:form modelAttribute="user" action="registrationtype.htm" method="POST" novalidate="novalidate" class="form-horizontal" role="form" id="registertype-form">
<div class="row">
<div class="span1 offset4 company"> <img src='demos/company.png' class='register' register='Company' /> </div>
<div class="span2">
<select id='switch-me'>
<option value='Company'>Company</option>
<option value='' selected="selected"></option>
<option value='Individual'>Individual</option>
</select>
</div>
<div class="span1"> <img src='demos/individual.png' class='register' register='Individual' /> </div>
</div>
<div class='row'>
<div class='span12' id="console"> </div>
</div>
<input type="hidden" id="register_type" name="registrationType" value="">
<div class="proceed">
<button class ="proceed-button" type="submit" id="proceed-register" name="proceed-register">Proceed</button>
</div>
</form:form>
</div>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="demos/jquery.animate-color.js"></script> 
<script type="text/javascript" src="demos/jquery.event.drag.js"></script> 
<script type="text/javascript" src="demos/switchy.js"></script> 
<script type="text/javascript" src="demos/application.js"></script> 

</body>
</html>