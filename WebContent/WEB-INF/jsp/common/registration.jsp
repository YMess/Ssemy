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

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="http://vendroid.venmond.com/img/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="http://vendroid.venmond.com/img/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="http://vendroid.venmond.com/img/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="http://vendroid.venmond.com/img/ico/apple-touch-icon-57-precomposed.png">
    <link rel="shortcut icon" href="http://vendroid.venmond.com/img/ico/favicon.png">
    
    
    <!-- CSS -->
       
    <!-- Bootstrap & FontAwesome & Entypo CSS -->
    <link href="css/custom/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="css/custom/font-awesome.css" rel="stylesheet" type="text/css">
    <!--[if IE 7]><link type="text/css" rel="stylesheet" href="css/font-awesome-ie7.min.css"><![endif]-->
    <link href="css/custom/font-entypo.css" rel="stylesheet" type="text/css">    

    <!-- Fonts CSS -->
    <link href="css/custom/fonts.css" rel="stylesheet" type="text/css">
               
    <!-- Plugin CSS -->
    <link href="css/custom/jquery-ui.css" rel="stylesheet" type="text/css">    
    <link href="css/custom/prettyPhoto.css" rel="stylesheet" type="text/css">
    <link href="css/custom/isotope.css" rel="stylesheet" type="text/css">
    <link href="css/custom/jquery.css" media="screen" rel="stylesheet" type="text/css">    
	<link href="css/custom/prettify.css" rel="stylesheet" type="text/css"> 
   
         
    <link href="css/custom/jquery_002.css" rel="stylesheet" type="text/css">
    <link href="css/custom/jquery_003.css" rel="stylesheet" type="text/css">
    <link href="css/custom/bootstrap-switch.css" rel="stylesheet" type="text/css">    
    <link href="css/custom/daterangepicker-bs3.css" rel="stylesheet" type="text/css">    
    <link href="css/custom/bootstrap-timepicker.css" rel="stylesheet" type="text/css">
    <link href="css/custom/colorpicker.css" rel="stylesheet" type="text/css">            

	<!-- Specific CSS -->
	    
     
    <!-- Theme CSS -->
    <link href="css/custom/theme.css" rel="stylesheet" type="text/css">
    <!--[if IE]> <link href="css/ie.css" rel="stylesheet" > <![endif]-->
    <link href="css/custom/chrome.css" rel="stylesheet" type="text/chrome"> <!-- chrome only css -->    
    

        
    <!-- Responsive CSS -->
        	<link href="css/custom/theme-responsive.css" rel="stylesheet" type="text/css"> 

	  
 
 
    <!-- for specific page in style css -->
        
    <!-- for specific page responsive in style css -->
        
    
    <!-- Custom CSS -->
    <link href="css/custom/custom.css" rel="stylesheet" type="text/css">
    <style type="text/css">
    .switch-wrapper {
	  display: inline-block;
	  position: relative;
	  top: 3px;
	}
    </style>
    <!-- Switch Element -->
    
    
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <!-- Head SCRIPTS -->
    <script src="js/ga.js" async="" type="text/javascript"></script><script type="text/javascript" src="js/modernizr.js"></script> 
    <script type="text/javascript" src="js/mobile-detect.js"></script> 
    <script type="text/javascript" src="js/mobile-detect-modernizr.js"></script> 

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script type="text/javascript" src="js/html5shiv.js"></script>
      <script type="text/javascript" src="js/respond.min.js"></script>     
    <![endif]-->
</head>
<body id="pages" class="full-layout no-nav-left no-nav-right  nav-top-fixed background-login     responsive login-layout   clearfix breakpoint-975" data-active="pages " data-smooth-scrolling="1">     
<div class="vd_body">
<!-- Header Start -->

<!-- Header Ends --> 
<div class="content">
  <div class="container"> 
    <!-- Middle Content Start -->  
    <div style="min-height: 8px;" class="vd_content-wrapper">
      <div style="min-height: 8px;" class="vd_container">
        <div class="vd_content clearfix">
          <div class="vd_content-section clearfix">
            <div class="vd_register-page">
              <div class="heading clearfix">
                <div class="logo">
                  <h2 class="mgbt-xs-5"><img src="js/logo.png" alt="logo"></h2>
                </div>
                <h4 class="text-center font-semibold vd_grey">USER REGISTRATION</h4>
              </div>
              <div class="panel widget">
                <div class="panel-body">
<!--                  <div id="register-success" class="alert alert-success" style="display:none;"><i class="fa fa-exclamation-circle fa-fw"></i> Registration confirmation has been sent to your email </div>
                  <div id="register-passerror" class="alert alert-danger" style="display:none;"><i class="fa fa-exclamation-circle fa-fw"></i> Your password and Confirm password are not same </div>-->
                  <form:form modelAttribute="user" action="registration.htm" method="POST" novalidate="novalidate" class="form-horizontal" role="form" id="register-form">
                  <div class="alert alert-danger vd_hidden">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true"><i class="icon-cross"></i></button>
                    <span class="vd_alert-icon"><i class="fa fa-exclamation-circle vd_red"></i></span><strong>Oh snap!</strong> Change a few things up and try submitting again. </div>
                  <div class="alert alert-warning vd_hidden">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true"><i class="icon-cross"></i></button>
                    <span class="vd_alert-icon"><i class="fa fa-exclamation-circle vd_red"></i></span>Your password and Confirm password are not same </div>                    
                  <div class="alert alert-success vd_hidden">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true"><i class="icon-cross"></i></button>
                    <span class="vd_alert-icon"><i class="fa fa-check-circle vd_green"></i></span>Registration confirmation has been sent to your email. </div>                  
                    
                    <div class="form-group">
                      <div class="col-md-6">
                        <div class="label-wrapper">
                          <label class="control-label">Registration Type <span class="vd_red">*</span></label>
                        </div>
                      </div>
                    </div>
                    <div class="form-group">
                      <div class="col-md-6">
                        <div class="label-wrapper">
                          <label class="control-label">First Name <span class="vd_red">*</span></label>
                        </div>
                        <div class="vd_input-wrapper" id="first-name-input-wrapper"> <span class="menu-icon"> <i class="fa fa-user"></i> </span>
                          <input placeholder="Steve" class="required" required="" name="firstName" id="firstname" type="text">
                        </div>
                      </div>
                      <div class="col-md-6">
                        <div class="label-wrapper">
                          <label class="control-label">Last Name <span class="vd_red">*</span></label>
                        </div>
                        <div class="vd_input-wrapper" id="last-name-input-wrapper"> <span class="menu-icon"> <i class="fa fa-user"></i> </span>
                          <input placeholder="Austin" class="required" required="" name="lastName" id="lastname" type="text">
                        </div>
                      </div>
                    </div>
                    <div class="form-group">
                      <div class="col-md-12">
                        <div class="label-wrapper">
                          <label class="control-label">Company Name <span class="vd_red">*</span></label>
                        </div>
                        <div class="vd_input-wrapper" id="company-input-wrapper"> <span class="menu-icon"> <i class="fa fa-briefcase"></i> </span>
                          <input placeholder="Your Company Co, Ltd." class="required" required="" name="organization" id="company" type="text">
                        </div>
                      </div>
                    </div>
                    <div class="form-group ">
                      <div class="col-md-3">
                        <div class="label-wrapper">
                          <label class="control-label">Phone <span class="vd_red">*</span></label>
                        </div>
                        <div class="vd_input-wrapper" id="country-code-input-wrapper"> <span class="menu-icon"> <i class="fa fa-plus"></i> </span>
                          <input placeholder="Country" class="required" required="" name="country" id="country" type="number">
                        </div>
                      </div>
                      <div class="col-md-9">
                        <div class="label-wrapper">
                          <label class="control-label">&nbsp;</label>
                        </div>
                        <div class="vd_input-wrapper no-icon" id="phone-input-wrapper">
                          <input placeholder="Phone Number" class="required" required="" name="phoneNumber" id="phone" type="number">
                        </div>
                      </div>
                    </div>
                    <div class="form-group">
                      <div class="col-md-12">
                        <div class="label-wrapper">
                          <label class="control-label">Website</label>
                        </div>
                        <div class="vd_input-wrapper" id="website-input-wrapper"> <span class="menu-icon"> <i class="fa fa-globe"></i> </span>
                          <input placeholder="http://www.yourcompany.com" class="" name="website" id="website" type="text">
                        </div>
                      </div>
                    </div>
                    <div class="form-group">
                      <div class="col-md-12">
                        <div class="label-wrapper">
                          <label class="control-label">Email <span class="vd_red">*</span></label>
                        </div>
                        <div class="vd_input-wrapper" id="email-input-wrapper"> <span class="menu-icon"> <i class="fa fa-envelope"></i> </span>
                          <input placeholder="Email" class="required" required="" name="userEmailId" id="email" type="email">
                        </div>
                      </div>
                    </div>
                    <div class="form-group">
                      <div class="col-md-6">
                        <div class="label-wrapper">
                          <label class="control-label">Password <span class="vd_red">*</span></label>
                        </div>
                        <div class="vd_input-wrapper" id="password-input-wrapper"> <span class="menu-icon"> <i class="fa fa-lock"></i> </span>
                          <input placeholder="Password" class="required" required="" name="password" id="password" type="password">
                        </div>
                      </div>
                      <div class="col-md-6">
                        <div class="label-wrapper">
                          <label class="control-label">Confirm Password <span class="vd_red">*</span></label>
                        </div>
                        <div class="vd_input-wrapper" id="confirm-password-input-wrapper"> <span class="menu-icon"> <i class="fa fa-lock"></i> </span>
                          <input placeholder="Password" class="required" required="" name="confirmPassword" id="confirmpass" type="password">
                        </div>
                      </div>
                    </div>
                    <div id="vd_login-error" class="alert alert-danger hidden"><i class="fa fa-exclamation-circle fa-fw"></i> Please fill the necessary field </div>
                    <div class="form-group">
                      <div class="col-md-12 mgbt-xs-10 mgtp-20">
                        <div class="vd_checkbox">
                          <input id="checkbox-1" value="1" type="checkbox">
                          <label for="checkbox-1"> Send me newsletter about the latest update</label>
                        </div>
                        <div class="vd_checkbox">
                          <input id="checkbox-2" value="1" required="" name="checkbox-2" type="checkbox">
                          <label for="checkbox-2"> I agree with <a href="#">terms of service</a></label>
                        </div>
                      </div>
                      <div class="col-md-12 text-center mgbt-xs-5">
                        <button class="btn vd_bg-green vd_white width-100" type="submit" id="submit-register" name="submit-register">Register</button>
                      </div>
                    </div>
                  </form:form>
                </div>
              </div>
              <!-- Panel Widget -->
              <div class="register-panel text-center font-semibold"> Already Have an Account? <br>
                <a href="login.htm">SIGN IN<span class="menu-icon"><i class="fa fa-angle-double-right fa-fw"></i></span></a> </div>
            </div>
            <!-- vd_login-page --> 
            
          </div>
          <!-- .vd_content-section --> 
          
        </div>
        <!-- .vd_content --> 
      </div>
      <!-- .vd_container --> 
    </div>
    <!-- .vd_content-wrapper --> 
    
    <!-- Middle Content End --> 
    
  </div>
  <!-- .container --> 
</div>
<!-- .content -->

<!-- Footer Start -->
  <footer class="footer-2" id="footer">      
    <div class="vd_bottom ">
        <div class="container">
            <div class="row">
              <div class=" col-xs-12">
                <div class="copyright text-center">
                  	Copyright ©2014 Venmond Inc. All Rights Reserved 
                </div>
              </div>
            </div><!-- row -->
        </div><!-- container -->
    </div>
  </footer>
<!-- Footer END -->

</div>

<!-- .vd_body END  -->
<a id="back-top" href="#" data-action="backtop" class="vd_back-top"> <i class="fa  fa-angle-up"> </i> </a>
<!--
<a class="back-top" href="#" id="back-top"> <i class="icon-chevron-up icon-white"> </i> </a> -->

<!-- Javascript =============================================== --> 
<!-- Placed at the end of the document so the pages load faster --> 
<script type="text/javascript" src="js/jquery_007.js"></script> 
<!--[if lt IE 9]>
  <script type="text/javascript" src="js/excanvas.js"></script>      
<![endif]-->
<script type="text/javascript" src="js/bootstrap.js"></script> 
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>

<script type="text/javascript" src="js/caroufredsel.js"></script> 
<script type="text/javascript" src="js/plugins.js"></script>

<script type="text/javascript" src="js/breakpoints.js"></script>
<script type="text/javascript" src="js/jquery_002.js"></script>
<script type="text/javascript" src="js/jquery_005.js"></script> 

<script type="text/javascript" src="js/jquery_004.js"></script>
<script type="text/javascript" src="js/jquery_008.js"></script>
<script type="text/javascript" src="js/bootstrap-switch.js"></script>
<script type="text/javascript" src="js/jquery_006.js"></script>
<script type="text/javascript" src="js/jquery_003.js"></script>

<script type="text/javascript" src="js/theme.js"></script>
<script type="text/javascript" src="js/custom.js"></script>
 
<!-- Specific Page Scripts Put Here -->
<script type="text/javascript">
$(document).ready(function() {
	
	"use strict";
	
	var form_register_2 = $('#register-form');
	var error_register_2 = $('.alert-danger', form_register_2);
	var success_register_2 = $('.alert-success', form_register_2);
	var warning_register_2 = $('.alert-warning', form_register_2);
		
			
	var options = { 
		type: "POST",
		url:  $("#register-form").attr('action'),
		dataType: "json",
		success: function(data) {
			if (data.response == "success") {		
/*				$("#register-form").fadeOut(500, function(){
					$('#register-passerror').fadeOut(500);					
					$('#register-success').fadeIn(500);
				});	*/
				
				setTimeout(function(){
					$('#submit-register .fa-spinner').remove()	;
					$('#submit-register').addClass('disabled');					
					success_register_2.fadeIn(500);
					error_register_2.fadeOut(500);
					warning_register_2.fadeOut(500);							
				},1500);
												
			} else if (data.response == "passerror") {	
				setTimeout(function(){
					error_register_2.fadeOut(500);			
					warning_register_2.fadeIn(500);	
					$('#submit-register .fa-spinner').remove()	;
					form_register_2.find('#submit-register').removeClass('disabled');	
					scrollTo(form_register_2,-100);					
				},1500);									
					
				
			} else if (data.response == "empty") {
					
			} else if (data.response == "unexpected") {
						
			}	


									
		},
		error: function() {

		}
	}; 


        form_register_2.validate({
            errorElement: 'div', //default input error message container
            errorClass: 'vd_red', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                firstname: {
                    minlength: 3,
                    required: true
                },
                lastname: {
                    minlength: 3,
                    required: true
                },				
                email: {
                    required: true,
                    email: true
                },
                website: {
                    required: true,
                    url: true
                },
                company: {
                    minlength: 3,					
                    required: true
                },
                country: {					
                    required: true
                },				
                phone: {					
                    required: true
                },					
                password: {
                    required: true
                },
                confirmpass: {
                    required: true
                },				
            },
			
			errorPlacement: function(error, element) {
				if (element.parent().hasClass("vd_checkbox") || element.parent().hasClass("vd_radio")){
					element.parent().append(error);
				} else if (element.parent().hasClass("vd_input-wrapper")){
					error.insertAfter(element.parent());
				}else {
					error.insertAfter(element);
				}
			}, 
			
            invalidHandler: function (event, validator) { //display error alert on form submit              
                success_register_2.hide();
                error_register_2.show();
				scrollTo(form_register_2,-100);

            },

            highlight: function (element) { // hightlight error inputs
		
				$(element).addClass('vd_bd-red');
				$(element).parent().siblings('.help-inline').removeClass('help-inline hidden');
				if ($(element).parent().hasClass("vd_checkbox") || $(element).parent().hasClass("vd_radio")) {
					$(element).siblings('.help-inline').removeClass('help-inline hidden');
				}

            },

            unhighlight: function (element) { // revert the change dony by hightlight
                $(element)
                    .closest('.control-group').removeClass('error'); // set error class to the control group
            },

            success: function (label, element) {
                label
                    .addClass('valid').addClass('help-inline hidden') // mark the current input as valid and display OK icon
                	.closest('.control-group').removeClass('error').addClass('success'); // set success class to the control group
				$(element).removeClass('vd_bd-red');
            },

           
        });		

	
	
});
</script>
<!-- Specific Page Scripts END -->




<!-- Google Analytics: Change UA-XXXXX-X to be your site's ID. Go to http://www.google.com/analytics/ for more information. -->

<script>
    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-43329142-3']);
    _gaq.push(['_trackPageview']);

    (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
    })();
</script>

</body>
</html>
