<%@ page language="java" import="org.springframework.security.core.Authentication, org.springframework.security.core.context.SecurityContextHolder" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
  
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure//grids-responsive-old-ie.css">
  
<![endif]-->
<!--[if gt IE 8]><!-->
  
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure//grids-responsive.css">
  
<!--<![endif]-->



  
    <!--[if lte IE 8]>
        <link rel="stylesheet" href="css/layouts/marketing-old-ie.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
        <link rel="stylesheet" href="css/layouts/marketing.css">
    <!--<![endif]-->
  



<link rel="stylesheet" href="css/font-awesome.css">


    

    

    

</head>
<body>









<div class="header">
    <div class="home-menu pure-menu pure-menu-open pure-menu-horizontal pure-menu-fixed">
        <a class="pure-menu-heading" href="">YMess</a>

        <ul>
            <li><a href="home.htm">Home</a></li>
            <li class="pure-menu-selected"><a href="#">Login</a></li>
            <li><a href="registration.htm">Sign Up</a></li>
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
        Log into the World of Awesomeness! :)
        </h2>


	<form class="pure-form" action="<c:url value='j_spring_security_check' />" method="post" id="submit">
   		 <fieldset>
       		 <legend>Login</legend>
		
		        <input type="email" placeholder="Email" name="username">
		        <input type="password" placeholder="Password" name="password">
		
		      <!--   <label for="remember">
		            <input id="remember" type="checkbox"> Remember me
		        </label> -->
		
		        <button type="submit" class="pure-button pure-button-primary">Sign in</button>
   			 </fieldset>
		</form>
		
    </div>

   
   

    <div class="footer l-box is-center">
      YMess Registered 2014.
    </div>

</div>






</body>
</html>
