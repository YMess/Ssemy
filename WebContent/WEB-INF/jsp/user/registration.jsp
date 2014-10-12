<%@ page language="java" import="org.springframework.security.core.Authentication, org.springframework.security.core.context.SecurityContextHolder" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="A layout example that shows off a responsive product landing page.">

    <title>YMess-Your Online Digital Collection &ndash; </title>

    


<link rel="stylesheet" href="http://yui.yahooapis.com/pure//pure.css">

<link rel="stylesheet" href="css/pure-min.css">

<!--[if lte IE 8]>
  
 <!--    <link rel="stylesheet" href="http://yui.yahooapis.com/pure//grids-responsive-old-ie.css">
   
<![endif]-->
<!--[if gt IE 8]><!-->
  
  <!--   <link rel="stylesheet" href="http://yui.yahooapis.com/pure//grids-responsive.css"> -->
  
<!--<![endif]-->



  
    <!--[if lte IE 8]>
        <link rel="stylesheet" href="css/layouts/marketing-old-ie.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
        <link rel="stylesheet" href="css/layouts/marketing.css">
    <!--<![endif]-->
  



<link rel="stylesheet" href="css/font-awesome.css">

<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
    
<script type="text/javascript">
$().ready(function(){
	
	$("#registrationForm").validate({
		   rules: {
		     // no quoting necessary
		     firstName: "required",
		     // quoting necessary!
		    lastName:"required",
		    password:"required",
		    confirmPassword:"required",
		    userEmailId:"required"
		   },
		   messages:
			   {
				   firstName : 'Please enter your First Name',
				   lastName : 'Please Enter your Last Name',
				   userEmailId : 'Please Enter your email Id',
				   password:'Please Enter a Password'
			   }
		 });
}); 



/* 

new FormValidator('registrationForm', [ {
    name: 'firstName',
    rules: 'required'
}, 
{
    name: 'lastName',
    rules: 'required'
},
{
    name: 'password',
    rules: 'required'
}, {
    name: 'confirmPassword',
    display: 'password confirmation',
    rules: 'required|matches[password]'
}, {
    name: 'userEmailId',
    rules: 'valid_email'
}, {
    name: 'minlength',
    display: 'min length',
    rules: 'min_length[8]'
}], function(errors, evt) {

    /*
     * DO NOT COPY AND PASTE THIS CALLBACK. THIS IS CONFIGURED TO WORK ON THE DOCUMENTATION PAGE ONLY.
     * YOU MUST CUSTOMIZE YOUR CALLBACK TO WORK UNDER YOUR ENVIRONMENT.
     */
/* 
    var SELECTOR_ERRORS = $('.error_box'),
        SELECTOR_SUCCESS = $('.success_box');

    if (errors.length > 0) {
        SELECTOR_ERRORS.empty();

        for (var i = 0, errorLength = errors.length; i < errorLength; i++) {
            SELECTOR_ERRORS.append(errors[i].message + '<br />');
        }

        SELECTOR_SUCCESS.css({ display: 'none' });
        SELECTOR_ERRORS.fadeIn(200);
    } else {
        SELECTOR_ERRORS.css({ display: 'none' });
        SELECTOR_SUCCESS.fadeIn(200);
    }

    if (evt && evt.preventDefault) {
        evt.preventDefault();
    } else if (event) {
        event.returnValue = false;
    }
}); */ 



/* var validator = new FormValidator('registrationForm', [, {
    name: 'firstName',
    rules: 'required'
}, 
{
    name: 'lastName',
    rules: 'required'
},
{
    name: 'password',
    rules: 'required'
}, {
    name: 'confirmPassword',
    display: 'password confirmation',
    rules: 'required|matches[password]'
}, {
    name: 'userEmailId',
    rules: 'valid_email',
    depends: function() {
        return Math.random() > .5;
    }
}, {
    name: 'minlength',
    display: 'min length',
    rules: 'min_length[8]'
}], function(errors, event) {
    if (errors.length > 0) {
    	 var errorString = '';

         for (var i = 0, errorLength = errors.length; i < errorLength; i++) {
             errorString += errors[i].message + '<br />';
         }

         el.innerHTML = errorString;
        // Show the errors
    }
}); */
    
</script>

</head>
<body>









<div class="header">
    <div class="home-menu pure-menu pure-menu-open pure-menu-horizontal pure-menu-fixed">
        <a class="pure-menu-heading" href="">YMess</a>

        <ul>
            <li><a href="home.htm">Home</a></li>
            <li><a href="login.htm">Login</a></li>
            <li class="pure-menu-selected"><a href="#">Sign Up</a></li>
        </ul>
    </div>
</div>

<div class="splash-container">
    <div class="splash">
        <h1 class="splash-head">Your Digital Collection</h1>
        <p class="splash-subhead">
            All Questions have an Answer!
        </p>
        <p>
            <a href="registration.htm" class="pure-button pure-button-primary">Get Started</a>
        </p>
    </div>
</div>

<div class="content-wrapper">
    <div class="content">
        <h2 class="content-head is-center">
        Enter the World of Awesomeness! :)
        </h2>
		
		<div class="error_box" style="display: block;"></div>
		<form:form modelAttribute="user" action="registration.htm" method="POST" cssClass="pure-form pure-form-aligned" name="registrationForm" id="registrationForm">
		<%-- <form class="pure-form pure-form-aligned"> --%>
    <fieldset>
        <div class="pure-control-group">
            <label for="firstName">First Name</label>
            <input id="firstName" type="text" placeholder="Username" name="firstName" required>
            <form:errors path="firstName"></form:errors>
        </div>

		<div class="pure-control-group">
            <label for="lastName">Last Name</label>
            <input id="lastName" type="text" placeholder="Username" name="lastName" required>
             <form:errors path="lastName"></form:errors>
        </div>
        <div class="pure-control-group">
            <label for="password">Password</label>
            <input id="password" type="password" placeholder="Password" name="password" required onchange="form.confirmPassword.pattern = this.value;">
             <form:errors path="password"></form:errors>
        </div>

 		<div class="pure-control-group">
            <label for="confirmPassword">Confirm Password</label>
            <input id="confirmPassword" type="password" placeholder="Confirm Password" name="confirmPassword" required>
             <form:errors path="confirmPassword"></form:errors>
        </div>
        
        <div class="pure-control-group">
            <label for="userEmailId">Email Address</label>
            <input id="userEmailId" type="email" placeholder="Email Address" name="userEmailId" required>
             <form:errors path="userEmailId"></form:errors>
        </div>

        <div class="pure-controls">
            <!-- <label for="cb" class="pure-checkbox">
                <input id="cb" type="checkbox"> I've read the terms and conditions
            </label> -->

            <button type="submit" class="pure-button pure-button-primary">Sign Up</button>
        </div>
    </fieldset>
</form:form>
		
		
		
    </div>

   
   

    <div class="footer l-box is-center">
      YMess Registered 2014.
    </div>

</div>






</body>
</html>
