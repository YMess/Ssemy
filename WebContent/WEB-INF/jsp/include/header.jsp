<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
	<!-- CSS -->
       
    <!-- Bootstrap & FontAwesome & Entypo CSS -->
    <link href="css/custom/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="css/custom/font-awesome.css" rel="stylesheet" type="text/css">
    <!--[if IE 7]><link type="text/css" rel="stylesheet" href="css/custom/font-awesome-ie7.min.css"><![endif]-->
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
    <!--[if IE]> <link href="css/custom/ie.css" rel="stylesheet" > <![endif]-->
    <link href="css/custom/chrome.css" rel="stylesheet" type="text/chrome"> <!-- chrome only css -->    


        
    <!-- Responsive CSS -->
        	<link href="css/custom/theme-responsive.css" rel="stylesheet" type="text/css"> 

	  
 
 
    <!-- for specific page in style css -->
        
    <!-- for specific page responsive in style css -->
        
    
    <!-- Custom CSS -->
    <link href="css/custom/custom.css" rel="stylesheet" type="text/css">



    <!-- Head SCRIPTS -->
    <script src="js/ga.js" async="" type="text/javascript"></script><script type="text/javascript" src="css/custom/modernizr.js"></script> 
    <script type="text/javascript" src="js/mobile-detect.js"></script> 
    <script type="text/javascript" src="js/mobile-detect-modernizr.js"></script> 
	</head>
	<body>
	
	

<!-- <img alt="Header" src="images/header.jpg" height="15%" width="100%"> -->

<%-- <div style="width:25%;float:left;">
	<img alt="Header" src="images/header.jpg" height="15%" width="100%">
</div>

<form:form modelAttribute="searchParameters" action="search.htm">
<div style="width:40%;float:left;padding-left: 65px;">
	<input id="searchString" type="text" placeholder="Search" name="searchString">
	<form:errors path="searchString"></form:errors>
	<select id="criterion" style="height:30px;" name="criterion">
	    <option value="Questions">Questions</option>
	    <option value="People">People</option>
	    <option value="Files">Files</option>
	</select>
	<input type="submit" value="Search">
</div >
 </form:form>   
  <div style="width:30%;float:left;">
  	<img alt="Header" src="images/header.jpg" height="15%" width="100%">
  </div> 
 --%>
 
  <div class="vd_top-menu-wrapper">
        <div class="container ">
          <div class="vd_top-nav vd_nav-width  ">
          <div class="vd_panel-header">
          	<div class="logo">
            	<a href="http://vendroid.venmond.com/index.php"><img alt="logo" src="css/custom/logo.png"></a>
            </div>
            <!-- logo -->
            <div class="vd_panel-menu  hidden-sm hidden-xs" data-intro="&lt;strong&gt;Minimize Left Navigation&lt;/strong&gt;&lt;br/&gt;Toggle navigation size to medium or small size. You can set both button or one button only. See full option at documentation." data-step="1">
            		            		                   
            </div>
            <div class="vd_panel-menu left-pos visible-sm visible-xs">
                                 
                        <span class="menu" data-action="toggle-navbar-left">
                            <i class="fa fa-ellipsis-v"></i>
                        </span>  
                            
                              
            </div>
            <div class="vd_panel-menu visible-sm visible-xs">
                	<span class="menu visible-xs" data-action="submenu">
	                    <i class="fa fa-bars"></i>
                    </span>        
                          
                        <span class="menu visible-sm visible-xs" data-action="toggle-navbar-right">
                            <i class="fa fa-comments"></i>
                        </span>                   
                   	 
            </div>                                     
            <!-- vd_panel-menu -->
          </div>
          <!-- vd_panel-header -->
            
          </div>    
          <div class="vd_container">
          	<div class="row">
            	<div class="col-sm-5 col-xs-12">
            		
<div class="vd_menu-search">
  <form id="search-box" method="post" action="#">
    <input name="search" class="vd_menu-search-text width-60" placeholder="Search" type="text">
    <div class="vd_menu-search-category"> <span data-action="click-trigger"> <span class="separator"></span> <span class="text">Category</span> <span class="icon"> <i class="fa fa-caret-down"></i></span> </span>
      <div style="display: none;" class="vd_mega-menu-content width-xs-2 center-xs-2 right-sm" data-action="click-target">
        <div class="child-menu">
          <div class="content-list content-menu content">
            <ul class="list-wrapper">
              <li>
                <label>
                  <input value="all-files" type="checkbox">
                  <span>All Files</span></label>
              </li>
              <li>
                <label>
                  <input value="photos" type="checkbox">
                  <span>Photos</span></label>
              </li>
              <li>
                <label>
                  <input value="illustrations" type="checkbox">
                  <span>Illustrations</span></label>
              </li>
              <li>
                <label>
                  <input value="video" type="checkbox">
                  <span>Video</span></label>
              </li>
              <li>
                <label>
                  <input value="audio" type="checkbox">
                  <span>Audio</span></label>
              </li>
              <li>
                <label>
                  <input value="flash" type="checkbox">
                  <span>Flash</span></label>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <span class="vd_menu-search-submit"><i class="fa fa-search"></i> </span>
  </form>
</div>
                </div>
                <div class="col-sm-7 col-xs-12">
              		<div class="vd_mega-menu-wrapper">
                    	<div class="vd_mega-menu pull-right">
            				<ul class="mega-ul">
    <li id="top-menu-1" class="one-icon mega-li"> 
      <a href="http://vendroid.venmond.com/index.php" class="mega-link" data-action="click-trigger">
    	<span class="mega-icon"><i class="fa fa-users"></i></span> 
		<span class="badge vd_bg-red">1</span>        
      </a>
      <div style="display: none;" class="vd_mega-menu-content width-xs-3 width-sm-4 width-md-5 right-xs left-sm" data-action="click-target">
        <div class="child-menu">  
           <div class="title"> 
           	   Friend Requests
               <div class="vd_panel-menu">
                     <span data-original-title="Find User" data-toggle="tooltip" data-placement="bottom" class="menu">
                        <i class="fa fa-search"></i>
                    </span>                 
                     <span data-original-title="Message Setting" data-toggle="tooltip" data-placement="bottom" class="menu">
                        <i class="fa fa-cog"></i>
                    </span>                                                                              
                </div>
           </div>                 
		   <div class="content-grid column-xs-2 column-sm-3 height-xs-4">	
           <div style="overflow: hidden;" class="mCustomScrollbar _mCS_1" data-rel="scroll"><div class="mCustomScrollBox mCS-light" id="mCSB_1" style="position: relative; height: 100%; overflow: hidden; max-width: 100%; max-height: 400px;"><div class="mCSB_container mCS_no_scrollbar" style="position: relative; top: 0px;">
               <ul class="list-wrapper">
                    <li> <a href="#"> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar.jpg"></div> 
                         </a>
                        <div class="menu-text"> Gabriella Montagna
                            <div class="menu-info">
                                <div class="menu-date">San Diego</div>                                                                         
                                <div class="menu-action">
                                    <span class="menu-action-icon vd_green vd_bd-green" data-original-title="Approve" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-check"></i>
                                    </span> 
                                    <span class="menu-action-icon vd_red vd_bd-red" data-original-title="Reject" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-times"></i>
                                    </span>                                                                                                                   
                                </div>                                
                            </div>
                        </div> 
                     </li>
                    <li class="warning"> 
                    		<a href="#"> 
                    			<div class="menu-icon"><img alt="example image" src="css/custom/avatar-2.jpg"></div>  
                            </a>                            
                            <div class="menu-text">  Jonathan Fuzzy
                            	<div class="menu-info">
                                    <div class="menu-date">Seattle</div>                                                                         
                                    <div class="menu-action">
                                        <span class="menu-action-icon vd_green vd_bd-green" data-original-title="Approve" data-toggle="tooltip" data-placement="bottom">
                                            <i class="fa fa-check"></i>
                                        </span> 
                                        <span class="menu-action-icon vd_red vd_bd-red" data-original-title="Reject" data-toggle="tooltip" data-placement="bottom">
                                            <i class="fa fa-times"></i>
                                        </span>                                                                            
                                    </div>                                
                            	</div>                            
                            </div> 
                     </li>    
                    <li> <a href="#"> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-3.jpg"></div> 
                         </a>    
                        <div class="menu-text">  Sakura Hinata
                            <div class="menu-info">
                                <div class="menu-date">Hawaii</div>                                                                         
                                <div class="menu-action">
                                    <span class="menu-action-icon vd_green vd_bd-green" data-original-title="Approve" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-check"></i>
                                    </span> 
                                    <span class="menu-action-icon vd_red vd_bd-red" data-original-title="Reject" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-times"></i>
                                    </span>                                                                             
                                </div>                                
                            </div>                                                     
                        </div> 
                    </li>                                     
                    <li> <a href="#"> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-4.jpg"></div> 
                         </a>    
                        <div class="menu-text">  Rikudou Sennin
                            <div class="menu-info">
                                <div class="menu-date">Las Vegas</div>                                                                         
                                <div class="menu-action">
                                    <span class="menu-action-icon vd_green vd_bd-green" data-original-title="Approve" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-check"></i>
                                    </span> 
                                    <span class="menu-action-icon vd_red vd_bd-red" data-original-title="Reject" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-times"></i>
                                    </span>                                                                              
                                </div>                                
                            </div>                                                        
                        </div> 
                    </li> 
                    <li> <a href="#"> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-5.jpg"></div>  
                         </a>   
                        <div class="menu-text">  Kim Kardiosun
                            <div class="menu-info">
                                <div class="menu-date">New York</div>                                                                         
                                <div class="menu-action">
                                    <span class="menu-action-icon vd_green vd_bd-green" data-original-title="Approve" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-check"></i>
                                    </span> 
                                    <span class="menu-action-icon vd_red vd_bd-red" data-original-title="Reject" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-times"></i>
                                    </span>                                                                              
                                </div>                                
                            </div>                                                          
                        </div> 
                     </li>
                    <li> <a href="#"> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-6.jpg"></div>
                         </a>    
                        <div class="menu-text">   Brad Pita
                            <div class="menu-info">
                                <div class="menu-date">Seattle</div>                                                                         
                                <div class="menu-action">
                                    <span class="menu-action-icon vd_green vd_bd-green" data-original-title="Approve" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-check"></i>
                                    </span> 
                                    <span class="menu-action-icon vd_red vd_bd-red" data-original-title="Reject" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-times"></i>
                                    </span>                                                                              
                                </div>                                
                            </div>                             
                        </div> 
                    </li>                                     
                    <li> <a href="#"> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-7.jpg"></div> 
                         </a>   
                        <div class="menu-text">  Celline Dior
                            <div class="menu-info">
                                <div class="menu-date">Los Angeles</div>                                                                         
                                <div class="menu-action">
                                    <span class="menu-action-icon vd_green vd_bd-green" data-original-title="Approve" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-check"></i>
                                    </span> 
                                    <span class="menu-action-icon vd_red vd_bd-red" data-original-title="Reject" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-times"></i>
                                    </span>                                                                             
                                </div>                                
                            </div>                              
                        </div> 
                    </li> 
                    <li> <a href="#"> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-8.jpg"></div>
                         </a>    
                        <div class="menu-text">  Goerge Bruno Marz
                            <div class="menu-info">
                                <div class="menu-date">Las Vegas</div>                                                                         
                                <div class="menu-action">
                                    <span class="menu-action-icon vd_green vd_bd-green" data-original-title="Approve" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-check"></i>
                                    </span> 
                                    <span class="menu-action-icon vd_red vd_bd-red" data-original-title="Reject" data-toggle="tooltip" data-placement="bottom">
                                        <i class="fa fa-times"></i>
                                    </span>                                                                              
                                </div>                                
                            </div>                              
                        </div> 
                    </li>                                                                                
                    
               </ul>
               </div><div class="mCSB_scrollTools" style="position: absolute; display: none;"><div class="mCSB_draggerContainer"><div class="mCSB_dragger" style="position: absolute; top: 0px;" oncontextmenu="return false;"><div class="mCSB_dragger_bar" style="position:relative;"></div></div><div class="mCSB_draggerRail"></div></div></div></div></div>
               <div class="closing text-center" style="">
               		<a href="#">See All Requests <i class="fa fa-angle-double-right"></i></a>
               </div>                                                                       
           </div>                              
        </div> <!-- child-menu -->                      
      </div>   <!-- vd_mega-menu-content --> 
    </li>
    <li id="top-menu-2" class="one-icon mega-li"> 
      <a href="http://vendroid.venmond.com/index.php" class="mega-link" data-action="click-trigger">
    	<span class="mega-icon"><i class="fa fa-envelope"></i></span> 
		<span class="badge vd_bg-red">10</span>        
      </a>
      <div style="display: none;" class="vd_mega-menu-content width-xs-3 width-sm-4 width-md-5 width-lg-4 right-xs left-sm" data-action="click-target">
        <div class="child-menu">  
           <div class="title"> 
           	   Messages
               <div class="vd_panel-menu">
                     <span data-original-title="Message Setting" data-toggle="tooltip" data-placement="bottom" class="menu">
                        <i class="fa fa-cog"></i>
                    </span>                                                                              
                </div>
           </div>                 
		   <div class="content-list content-image">
           	   <div style="overflow: hidden;" class="mCustomScrollbar _mCS_2" data-rel="scroll"><div class="mCustomScrollBox mCS-light" id="mCSB_2" style="position: relative; height: 100%; overflow: hidden; max-width: 100%; max-height: 400px;"><div class="mCSB_container mCS_no_scrollbar" style="position: relative; top: 0px;">	
               <ul class="list-wrapper pd-lr-10">
                    <li> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar.jpg"></div> 
                            <div class="menu-text"> Do you play or follow any sports?
                            	<div class="menu-info">
                                    <span class="menu-date">12 Minutes Ago </span>                                                                         
                                    <span class="menu-action">
                                        <span class="menu-action-icon" data-original-title="Mark as Unread" data-toggle="tooltip" data-placement="bottom">
                                            <i class="fa fa-eye"></i>
                                        </span>                                                                            
                                    </span>                                
                            	</div>
                            </div> 
                    </li>
                    <li class="warning"> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-2.jpg"></div>  
                            <div class="menu-text">  Good job mate !
                            	<div class="menu-info">
                                    <span class="menu-date">1 Hour 20 Minutes Ago </span>                                                                         
                                    <span class="menu-action">
                                        <span class="menu-action-icon" data-original-title="Mark as Read" data-toggle="tooltip" data-placement="bottom">
                                            <i class="fa fa-eye-slash"></i>
                                        </span>                                                                            
                                    </span>                                
                            	</div>                            
                            </div> 
                     </li>    
                    <li> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-3.jpg"></div> 
                            <div class="menu-text">  Just calm down babe, everything will work out.
                            	<div class="menu-info">
                                    <span class="menu-date">12 Days Ago</span>                                                                         
                                    <span class="menu-action">
                                        <span class="menu-action-icon" data-original-title="Mark as Unread" data-toggle="tooltip" data-placement="bottom">
                                            <i class="fa fa-eye"></i>
                                        </span>                                                                            
                                    </span>                                
                            	</div>                                                     
                            </div> 
                    </li>                                     
                    <li>
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-4.jpg"></div> 
                            <div class="menu-text">  Euuh so gross....
                            	<div class="menu-info">
                                    <span class="menu-date">19 Days Ago</span>                                                                         
                                    <span class="menu-action">
                                        <span class="menu-action-icon" data-original-title="Mark as Unread" data-toggle="tooltip" data-placement="bottom">
                                            <i class="fa fa-eye"></i>
                                        </span>                                                                            
                                    </span>                                
                            	</div>                                                        
                            </div> 
                    </li> 
                    <li> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-5.jpg"></div>  
                            <div class="menu-text">  That's the way.. I like it :D
                            	<div class="menu-info">
                                    <span class="menu-date">20 Days Ago</span>                                                                         
                                    <span class="menu-action">
                                        <span class="menu-action-icon" data-original-title="Mark as Unread" data-toggle="tooltip" data-placement="bottom">
                                            <i class="fa fa-eye"></i>
                                        </span>                                                                            
                                    </span>                                
                            	</div>                                                          
                            </div> 
                     </li>
                    <li> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-6.jpg"></div> 
                            <div class="menu-text">  Oooh don't be shy ;P
                            	<div class="menu-info">
                                    <span class="menu-date">21 Days Ago</span>                                                                         
                                    <span class="menu-action">
                                        <span class="menu-action-icon" data-original-title="Mark as Unread" data-toggle="tooltip" data-placement="bottom">
                                            <i class="fa fa-eye"></i>
                                        </span>                                                                            
                                    </span>                                
                            	</div>                             
                            </div> 
                     </li>                                     
                    <li> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-7.jpg"></div> 
                            <div class="menu-text">  Hello, please call my number..
                            	<div class="menu-info">
                                    <span class="menu-date">24 Days Ago</span>                                                                         
                                    <span class="menu-action">
                                        <span class="menu-action-icon" data-original-title="Mark as Unread" data-toggle="tooltip" data-placement="bottom">
                                            <i class="fa fa-eye"></i>
                                        </span>                                                                            
                                    </span>                                
                            	</div>                              
                            </div> 
                    </li> 
                    <li> 
                    		<div class="menu-icon"><img alt="example image" src="css/custom/avatar-8.jpg"></div> 
                            <div class="menu-text">  Don't go anywhere, i will be coming soon
                            	<div class="menu-info">
                                    <span class="menu-date">1 Month 2 days Ago</span>                                                                         
                                    <span class="menu-action">
                                        <span class="menu-action-icon" data-original-title="Mark as Unread" data-toggle="tooltip" data-placement="bottom">
                                            <i class="fa fa-eye"></i>
                                        </span>                                                                            
                                    </span>                                
                            	</div>                              
                            </div> 
                     </li>                                                                                
                    
               </ul>
               </div><div class="mCSB_scrollTools" style="position: absolute; display: none;"><div class="mCSB_draggerContainer"><div class="mCSB_dragger" style="position: absolute; top: 0px;" oncontextmenu="return false;"><div class="mCSB_dragger_bar" style="position:relative;"></div></div><div class="mCSB_draggerRail"></div></div></div></div></div>
               <div class="closing text-center" style="">
               		<a href="#">See All Notifications <i class="fa fa-angle-double-right"></i></a>
               </div>                                                                       
           </div>                              
        </div> <!-- child-menu -->                      
      </div>   <!-- vd_mega-menu-content --> 
    </li>  
    <li id="top-menu-3" class="one-icon mega-li"> 
      <a href="http://vendroid.venmond.com/index.php" class="mega-link" data-action="click-trigger">
    	<span class="mega-icon"><i class="fa fa-globe"></i></span> 
		<span class="badge vd_bg-red">51</span>        
      </a>
      <div style="display: none;" class="vd_mega-menu-content  width-xs-3 width-sm-4  center-xs-3 left-sm" data-action="click-target">
        <div class="child-menu">  
           <div class="title"> 
           		Notifications 
               <div class="vd_panel-menu">
                     <span data-original-title="Notification Setting" data-toggle="tooltip" data-placement="bottom" class="menu">
                        <i class="fa fa-cog"></i>
                    </span>                   
<!--                     <span class="text-menu" data-original-title="Settings" data-toggle="tooltip" data-placement="bottom">
                        Settings
                    </span> -->                                                              
                </div>
           </div>                 
		   <div class="content-list">	
           	   <div style="overflow: hidden;" class="mCustomScrollbar _mCS_3" data-rel="scroll"><div class="mCustomScrollBox mCS-light" id="mCSB_3" style="position: relative; height: 100%; overflow: hidden; max-width: 100%; max-height: 400px;"><div class="mCSB_container mCS_no_scrollbar" style="position: relative; top: 0px;">	
               <ul class="list-wrapper pd-lr-10">
                    <li> <a href="#"> 
                    		<div class="menu-icon vd_yellow"><i class="fa fa-suitcase"></i></div> 
                            <div class="menu-text"> Someone has give you a surprise 
                            	<div class="menu-info"><span class="menu-date">12 Minutes Ago</span></div>
                            </div> 
                    </a> </li>
                    <li> <a href="#"> 
                    		<div class="menu-icon vd_blue"><i class=" fa fa-user"></i></div> 
                            <div class="menu-text">  Change your user profile details
                            	<div class="menu-info"><span class="menu-date">1 Hour 20 Minutes Ago</span></div>
                            </div> 
                    </a> </li>    
                    <li> <a href="#"> 
                    		<div class="menu-icon vd_red"><i class=" fa fa-cogs"></i></div> 
                            <div class="menu-text">  Your setting is updated
                            	<div class="menu-info"><span class="menu-date">12 Days Ago</span></div>                            
                            </div> 
                    </a> </li>                                     
                    <li> <a href="#"> 
                    		<div class="menu-icon vd_green"><i class=" fa fa-book"></i></div> 
                            <div class="menu-text">  Added new article
                            	<div class="menu-info"><span class="menu-date">19 Days Ago</span></div>                              
                            </div> 
                    </a> </li> 
                    <li> <a href="#"> 
                    		<div class="menu-icon vd_green"><img alt="example image" src="css/custom/avatar.jpg"></div> 
                            <div class="menu-text">  Change Profile Pic
                            	<div class="menu-info"><span class="menu-date">20 Days Ago</span></div>                              
                            </div> 
                    </a> </li>
                    <li> <a href="#"> 
                    		<div class="menu-icon vd_red"><i class=" fa fa-cogs"></i></div> 
                            <div class="menu-text">  Your setting is updated
                            	<div class="menu-info"><span class="menu-date">12 Days Ago</span></div>                            
                            </div> 
                    </a> </li>                                     
                    <li> <a href="#"> 
                    		<div class="menu-icon vd_green"><i class=" fa fa-book"></i></div> 
                            <div class="menu-text">  Added new article
                            	<div class="menu-info"><span class="menu-date">19 Days Ago</span></div>                              
                            </div> 
                    </a> </li> 
                    <li> <a href="#"> 
                    		<div class="menu-icon vd_green"><img alt="example image" src="css/custom/avatar.jpg"></div> 
                            <div class="menu-text">  Change Profile Pic
                            	<div class="menu-info"><span class="menu-date">20 Days Ago</span></div>                              
                            </div> 
                    </a> </li>                                                                                
                    
               </ul>
               </div><div class="mCSB_scrollTools" style="position: absolute; display: none;"><div class="mCSB_draggerContainer"><div class="mCSB_dragger" style="position: absolute; top: 0px;" oncontextmenu="return false;"><div class="mCSB_dragger_bar" style="position:relative;"></div></div><div class="mCSB_draggerRail"></div></div></div></div></div>
               <div class="closing text-center" style="">
               		<a href="#">See All Notifications <i class="fa fa-angle-double-right"></i></a>
               </div>                                                                       
           </div>                              
        </div> <!-- child-menu -->                      
      </div>   <!-- vd_mega-menu-content -->         
    </li>  
     
    <li id="top-menu-profile" class="profile mega-li"> 
        <a href="#" class="mega-link" data-action="click-trigger"> 
            <span class="mega-image">
                <img src="css/custom/avatar.jpg" alt="example image">               
            </span>
            <span class="mega-name">
                Caroline <i class="fa fa-caret-down fa-fw"></i> 
            </span>
        </a> 
      <div style="display: none;" class="vd_mega-menu-content  width-xs-2  left-xs left-sm" data-action="click-target">
        <div class="child-menu"> 
        	<div class="content-list content-menu">
                <ul class="list-wrapper pd-lr-10">
                    <li> <a href="#"> <div class="menu-icon"><i class=" fa fa-user"></i></div> <div class="menu-text">Edit Profile</div> </a> </li>
                    <li> <a href="#"> <div class="menu-icon"><i class=" fa fa-trophy"></i></div> <div class="menu-text">My Achievements</div> </a> </li>
                    <li> <a href="#"> <div class="menu-icon"><i class=" fa fa-envelope"></i></div> <div class="menu-text">Messages</div><div class="menu-badge"><div class="badge vd_bg-red">10</div></div> </a>  </li>
                    <li> <a href="#"> <div class="menu-icon"><i class=" fa fa-tasks
"></i></div> <div class="menu-text"> Tasks</div><div class="menu-badge"><div class="badge vd_bg-red">5</div></div> </a> </li> 
                    <li class="line"></li>                
                    <li> <a href="#"> <div class="menu-icon"><i class=" fa fa-lock
"></i></div> <div class="menu-text">Privacy</div> </a> </li>
                    <li> <a href="#"> <div class="menu-icon"><i class=" fa fa-cogs"></i></div> <div class="menu-text">Settings</div> </a> </li>
                    <li> <a href="#"> <div class="menu-icon"><i class="  fa fa-key"></i></div> <div class="menu-text">Lock</div> </a> </li>
                    <li> <a href="#"> <div class="menu-icon"><i class=" fa fa-sign-out"></i></div>  <div class="menu-text">Sign Out</div> </a> </li>
                    <li class="line"></li>                
                    <li> <a href="#"> <div class="menu-icon"><i class=" fa fa-question-circle"></i></div> <div class="menu-text">Help</div> </a> </li>
                    <li> <a href="#"> <div class="menu-icon"><i class=" glyphicon glyphicon-bullhorn"></i></div> <div class="menu-text">Report a Problem</div> </a> </li>              
                </ul>
            </div> 
        </div> 
      </div>     
  
    </li>               
       
    <li id="top-menu-settings" class="one-big-icon hidden-xs hidden-sm mega-li" data-intro="&lt;strong&gt;Toggle Right Navigation &lt;/strong&gt;&lt;br/&gt;On smaller device such as tablet or phone you can click on the middle content to close the right or left navigation." data-step="2" data-position="left"> 
    	<a href="#" class="mega-link" data-action="toggle-navbar-right"> 
           <span class="mega-icon">
                <i class="fa fa-comments"></i> 
            </span> 
<!--            <span  class="mega-image">
                <img src="img/avatar/avatar.jpg" alt="example image" />               
            </span> -->           
			<span class="badge vd_bg-red">8</span>               
        </a>              
       
    </li>
	</ul>
<!-- Head menu search form ends -->                         
                        </div>
                    </div>
                </div>

            </div>
          </div>
        </div>
        <!-- container --> 
      </div>
      <!-- vd_primary-menu-wrapper --> 
      
      <!-- Javascript =============================================== --> 
<!-- Placed at the end of the document so the pages load faster --> 
<script type="text/javascript" src="js/jquery_007.js"></script> 
<!--[if lt IE 9]>
  <script type="text/javascript" src="js/excanvas.js"></script>      
<![endif]-->
<script type="text/javascript" src="js/bootstrap.js"></script> 
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>



<script type="text/javascript" src="js/breakpoints.js"></script>
<script type="text/javascript" src="js/jquery_003.js"></script>
<script type="text/javascript" src="js/jquery_004.js"></script> 

<script type="text/javascript" src="js/theme.js"></script>
<script type="text/javascript" src="js/custom.js"></script>
 
<!-- Specific Page Scripts Put Here -->
<script type="text/javascript" src="js/wysihtml5-0.js"></script>
<script type="text/javascript" src="js/bootstrap-wysihtml5-0.js"></script>
      </body>
</html>