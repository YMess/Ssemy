<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" import="org.springframework.security.core.Authentication, org.springframework.security.core.context.SecurityContextHolder" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Compose</title>
  <script src="js/jquery-1.10.2.js" type="text/javascript"></script>
  <script src="js/jquery-ui.js" type="text/javascript"></script>
  <LINK REL=Stylesheet TYPE ="text/css" HREF="css/style.css">
<LINK REL=Stylesheet TYPE ="text/css" HREF="css/pure-min.css">
<script>
$(document).ready(function() {
    //add more file components if Add is clicked
    $('#addFile').click(function() {
        var fileIndex = $('#fileTable tr').children().length ;
        $('#fileTable').append(
                '<tr><td>'+
                '   <input type="file" name="mailAttachment['+ fileIndex +']" />'+
                '</td></tr>');
    });  
});
</script>
 <script type="text/javascript">
  $().ready(function(){
	  
	   $('input[type="checkbox"]').click(function(){
              $(".imageDiv").toggle();
      });
	});
  </script>
  
</head>
<body>
<div class="header">
		<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
	</div>
	
	 <div class="clear"></div>
	<div>
		<div class="userleft"><%@include file="/WEB-INF/jsp/include/mail_navigation.jsp" %></div>
		<div class="usercenter">
				<div class="pure-menu pure-menu-open">
		        <a class="pure-menu-heading">Compose Mail</a>
		<form:form action="compose_mail.htm" modelAttribute="mail" enctype="multipart/form-data" id="sendMail">
		
		
		<input id="addImage" type="checkbox" name="isAttachmentAttached"> Add Attachments


		<div class="imageDiv"  style="display: none;">
		<p>Select files to upload. Press Add button to add more file inputs.</p>
 
    	<input id="addFile" type="button" value="Add File" />
   		<table id="fileTable">
       	<tr>
           <td><input name="mailAttachment[0]" type="file" /></td>
        </tr>
    	</table>
					 </div>	
		
				    <form:textarea style="width: 70%;height: 30px;" path="mailTo" placeholder="To"></form:textarea>
			        <br/>	
			        <form:errors path="mailTo"></form:errors>
			        <br/>
			        <form:textarea style="width: 70%;height: 30px;" path="mailCC" placeholder="CC"></form:textarea>
			        <br/>	
			        <form:errors path="mailCC"></form:errors>
			        <br/>
			        <form:textarea style="width: 70%;height: 30px;" path="mailBCC" placeholder="BCC"></form:textarea>
			        <br/>	
			        <form:errors path="mailBCC"></form:errors>
			        <br/>
					<br/>
					<div>

					</div>
					<form:textarea style="width: 70%;height: 30px;" path="mailSubject" placeholder="Subject"></form:textarea>
			        <br/>	
			        <form:errors path="mailSubject"></form:errors>
			        <br/>
					<br/>
					
					<form:textarea style="width: 70%;height: 200px;" path="mailBody" placeholder="Body"></form:textarea>
					<br>
					<br>
					<form:errors path="mailBody"></form:errors>
					<br>
					<br>
				 
		
					<input type="submit" value="Send" name="send" style="height: 50px;width: 250px;"/>
				    <input type="submit" value="Save" name="save" style="height: 50px;width: 250px;"/>
				</form:form>
					
				</div>
		</div>
		<div class="userright"><%@include file="/WEB-INF/jsp/include/right.jsp" %></div>
	</div>
</body>
</html> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page language="java" import="org.springframework.security.core.Authentication, org.springframework.security.core.context.SecurityContextHolder" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inbox</title>
<script src="js/jquery-2.1.3.min.js" type="text/javascript"></script>
 <script src="js/jquery-ui.js" type="text/javascript"></script>
 <script type="text/javascript">
$(document).ready(function() {
    //add more file components if Add is clicked
    $('#addFile').click(function() {
        var fileIndex = $('#fileTable tr').children().length ;
        $('#fileTable').append(
                '<tr><td>'+
                '   <input type="file" name="mailAttachment['+ fileIndex +']" />'+
                '</td></tr>');
    });  
});
</script>
 <script type="text/javascript">
  $().ready(function(){
	   $("#addImage").change(function(){
		   alert("hi"); 
              $(".imageDiv").toggle();
      });
	});
  </script>
</head>
<body id="email" class="full-layout    nav-top-fixed   nav-right-small   responsive    clearfix breakpoint-975" data-active="email " data-smooth-scrolling="1">

<div class="vd_body">
<header>
   <%@ include file="/WEB-INF/jsp/include/header.jsp" %>
</header>
<div class="content">
  <div class="container">
		<%@include file="/WEB-INF/jsp/mail/navigation.jsp" %>

	<div style="min-height: 1048px;" class="vd_content-wrapper">
      <div style="min-height: 1048px;margin-left: 14%;" class="vd_container">
<!-- Compose Content -->
 <div class="vd_content clearfix">
          <div class="vd_head-section clearfix">
            <div class="vd_panel-header">
              <ul class="breadcrumb">
                <li><a href="index.php">Home</a> </li>
                <li><a href="email.php">Email</a></li>
                <li class="active">Email Compose</li>
              </ul>
              <div class="vd_panel-menu hidden-sm hidden-xs" data-intro="<strong>Expand Control</strong><br/>To expand content page horizontally, vertically, or Both. If you just need one button just simply remove the other button code." data-step="5" data-position="left">
    <div data-action="remove-navbar" data-original-title="Remove Navigation Bar Toggle" data-toggle="tooltip" data-placement="bottom" class="remove-navbar-button menu"> <i class="fa fa-arrows-h"></i> </div>
      <div data-action="remove-header" data-original-title="Remove Top Menu Toggle" data-toggle="tooltip" data-placement="bottom" class="remove-header-button menu"> <i class="fa fa-arrows-v"></i> </div>
      <div data-action="fullscreen" data-original-title="Remove Navigation Bar and Top Menu Toggle" data-toggle="tooltip" data-placement="bottom" class="fullscreen-button menu"> <i class="glyphicon glyphicon-fullscreen"></i> </div>
      
</div>
            </div>
            <!-- vd_panel-header --> 
          </div>
          <!-- vd_panel-head-section -->
          
          <div class="vd_title-section clearfix">
            <div class="vd_panel-header">
              <h1>Email Compose</h1>
              <small class="subtitle">Compose email page with address book filter</small>
                          </div>
          </div>
          <!-- vd_title-section -->
          
          <div class="vd_content-section clearfix" >
            <div class="row">
              <div class="col-md-9">
                <div class="panel widget light-widget">
                  <div class="panel-heading no-title"> </div>
                  <!-- vd_panel-heading -->
                  
                  <div class="panel-body">
                    <h2 class="mgtp--10"><i class="icon-feather mgr-10 vd_green"></i> Compose New Email </h2>
                    <br>
                    <form:form action="compose_mail.htm" modelAttribute="mail" enctype="multipart/form-data" id="sendMail">
                      <div class="form-group clearfix">
                        <label class="col-sm-2 control-label">To</label>
                        <div class="col-sm-10 controls">
                          <input id="email-input" type="text" class="input-border-btm" name="mailTo" placeholder="someone@example.com">
                        </div>
                      </div>
                       <div class="form-group clearfix">
                        <label class="col-sm-2 control-label">Cc</label>
                        <div class="col-sm-10 controls">
                          <input id="email-input" type="text" class="input-border-btm" name="mailCC" placeholder="someone@example.com">
                        </div>
                      </div>
                       <div class="form-group clearfix">
                        <label class="col-sm-2 control-label">Bcc</label>
                        <div class="col-sm-10 controls">
                          <input id="email-input" type="text" class="input-border-btm" name="mailBCC" placeholder="someone@example.com">
                        </div>
                      </div>
                      <div class="form-group  clearfix">
                        <label class="col-sm-2 control-label">Subject</label>
                        <div class="col-sm-10 controls">
                          <input type="text" id="subject-input" class="input-border-btm" name="mailSubject" placeholder="Subject Title">
                        </div>
                      </div>
                    <div>  
                    <input id="addImage" type="checkbox" name="isAttachmentAttached"> Add Attachments

					<div class="imageDiv"  style="display: none;">
					<p>Select files to upload. Press Add button to add more file inputs.</p>
			 
			    	<input id="addFile" type="button" value="Add File" />
			   		<table id="fileTable">
			       	<tr>
			           <td><input name="mailAttachment[0]" type="file" /></td>
			        </tr>
			    	</table>
					 </div>
					 </div>
                      <br>
                      <div class="form-group  clearfix">
                        <label class="col-sm-12 control-label sr-only">Message</label>
                        <div class="col-sm-12 controls">
                          <ul class="wysihtml5-toolbar">
                          <li class="dropdown"><a class="btn btn-default dropdown-toggle" data-toggle="dropdown" href="#">
                          <i class="fa fa-font"></i>&nbsp;<span class="current-font">Normal text</span>&nbsp;<b class="caret"></b></a><ul class="dropdown-menu"><li><a data-wysihtml5-command="formatBlock" data-wysihtml5-command-value="div" href="javascript:;" unselectable="on">Normal text</a></li><li><a data-wysihtml5-command="formatBlock" data-wysihtml5-command-value="h1" href="javascript:;" unselectable="on">Heading 1</a></li><li><a data-wysihtml5-command="formatBlock" data-wysihtml5-command-value="h2" href="javascript:;" unselectable="on">Heading 2</a></li><li><a data-wysihtml5-command="formatBlock" data-wysihtml5-command-value="h3" href="javascript:;" unselectable="on">Heading 3</a></li></ul></li><li><div class="btn-group"><a class="btn btn-default" data-wysihtml5-command="bold" title="CTRL+B" href="javascript:;" unselectable="on">Bold</a><a class="btn btn-default" data-wysihtml5-command="italic" title="CTRL+I" href="javascript:;" unselectable="on">Italic</a><a class="btn btn-default" data-wysihtml5-command="underline" title="CTRL+U" href="javascript:;" unselectable="on">Underline</a></div></li><li><div class="btn-group"><a class="btn btn-default" data-wysihtml5-command="insertUnorderedList" title="Unordered list" href="javascript:;" unselectable="on"><i class="fa fa-list"></i></a><a class="btn btn-default" data-wysihtml5-command="insertOrderedList" title="Ordered list" href="javascript:;" unselectable="on"><i class="fa fa-th-list"></i></a><a class="btn btn-default" data-wysihtml5-command="Outdent" title="Outdent" href="javascript:;" unselectable="on"><i class="fa fa-dedent"></i></a><a class="btn btn-default" data-wysihtml5-command="Indent" title="Indent" href="javascript:;" unselectable="on"><i class="fa fa-indent"></i></a></div></li><li><div class="bootstrap-wysihtml5-insert-link-modal modal  fade"><div class="modal-dialog"> <div class="modal-content"><div class="modal-header vd_bg-green vd_white"><button aria-hidden="true" data-dismiss="modal" class="close" type="button"><i class="fa fa-times"></i></button><h4 id="myModalLabel" class="modal-title">Insert link</h4></div><div class="modal-body"><input value="http://" class="bootstrap-wysihtml5-insert-link-url input-border-btm input-lg width-100"></div><div class="modal-footer background-login"><a href="#" class="btn" data-dismiss="modal">Cancel</a><a href="#" class="btn vd_btn vd_bg-blue" data-dismiss="modal">Insert link</a></div></div> </div></div><a class="btn btn-default" data-wysihtml5-command="createLink" title="Insert link" href="javascript:;" unselectable="on"><i class="fa fa-share"></i></a></li><li><div class="bootstrap-wysihtml5-insert-image-modal modal fade"><div class="modal-dialog"> <div class="modal-content"><div class="modal-header vd_bg-green vd_white"><button aria-hidden="true" data-dismiss="modal" class="close" type="button"><i class="fa fa-times"></i></button><h4 id="myModalLabel" class="modal-title">Insert image</h4></div><div class="modal-body"><input value="http://" class="bootstrap-wysihtml5-insert-image-url input-border-btm input-lg width-100"></div><div class="modal-footer background-login"><a href="#" class="btn" data-dismiss="modal">Cancel</a><a href="#" class="btn vd_btn vd_bg-blue" data-dismiss="modal">Insert image</a></div></div> </div></div><a class="btn btn-default" data-wysihtml5-command="insertImage" title="Insert image" href="javascript:;" unselectable="on"><i class="fa fa-picture-o"></i></a></li></ul>    
                       </div>
                      </div>
                     
                      <div class="form-group clearfix">
                       <textarea id="message" class="width-100 form-control" name="mailBody" rows="15" placeholder="Write your message here" ></textarea>
                        <input type="hidden" name="_wysihtml5_mode" value="1">
                        <div style="display: none;"><iframe class="wysihtml5-sandbox" security="restricted" allowtransparency="true" frameborder="0" width="0" height="0" marginwidth="0" marginheight="0" style="border-collapse: separate; border: 1px solid rgb(204, 204, 204); clear: none; display: block; float: none; margin: 0px; outline: rgb(85, 85, 85) none 0px; outline-offset: 0px; padding: 6px 12px; position: static; top: auto; left: auto; right: auto; bottom: auto; z-index: auto; vertical-align: baseline; text-align: start; -webkit-box-shadow: rgba(0, 0, 0, 0.0745098) 0px 1px 1px 0px inset; box-shadow: rgba(0, 0, 0, 0.0745098) 0px 1px 1px 0px inset; border-radius: 4px; width: 689.75px; height: 314px; background-color: rgb(255, 255, 255);"></iframe></div>
                       </div>
                      <div class="form-group form-actions">
                        <div class="col-sm-12">
                          <button type="submit" class="btn vd_btn vd_bg-green vd_white" name="send" ><i class="fa fa-envelope append-icon"></i> SEND</button>
                          <button type="submit" class="btn vd_btn vd_bg-yellow vd_white"  name="save"><i class="fa fa-archive append-icon"></i> SAVE TO DRAFT</button>
                        </div>
                      </div>
                    </form:form>
                  </div>
                  <!-- panel-body  --> 
                  
                </div>
                <!-- panel --> 
              </div>
              <!-- col-md-8 -->
              
              <div class="col-md-3">
                <div class="panel widget">
                  <div class="panel-heading vd_bg-yellow">
                    <h3 class="panel-title"> <span class="menu-icon"> <i class="glyphicon glyphicon-book"></i> </span> Address Book </h3>
                  </div>
                  <!-- vd_panel-heading -->
                  
                  <div class="panel-body" style="width: 256px; ">
                    <div class="form-group clearfix mgtp-10">
                      <div class="vd_input-wrapper light-theme"> <span class="menu-icon"> <i class="fa fa-filter"></i> </span>
                        <input type="text" id="filter-text" placeholder="Name Filter">
                      </div>
                    </div>
                    <br>
                    <form:form class="form-horizontal" role="form" name="form2" action="#">


                           
                          <a href="#" id="check-all">Check All</a> <span class="mgl-10 mgr-10">/</span> <a href="#" id="uncheck-all">Uncheck All</a>  
     
                          <hr class="mgtp-5">                   
                          <div class="form-group clearfix" style="height: 250px; overflow-y:scroll;">
                              <div class="col-md-12">
                                <div class="content-list content-menu" id="email-list">
                                  <div class="list-wrapper wrap-25 isotope" style="position: relative; height: 616px;">
                                    <div class="email-item" style="position: absolute; left: 0px; top: 0px;">
                                      <div class="vd_checkbox checkbox-success">
                                        <input type="checkbox" id="checkbox-1" value="brad@pitt.com">
                                        <label class="filter-name" for="checkbox-1"> Brad Pitt - <em class="font-normal">brad@pitt.com</em> </label>
                                      </div>
                                    </div>
                                    <div class="email-item" style="position: absolute; left: 0px; top: 56px;">
                                      <div class="vd_checkbox checkbox-success">
                                        <input type="checkbox" id="checkbox-2" value="angelina@jolie.com">
                                        <label class="filter-name" for="checkbox-2"> Angelina Jolie - <em class="font-normal">angelina@jolie.com</em> </label>
                                      </div>
                                    </div>
                                    <div class="email-item" style="position: absolute; left: 0px; top: 112px;">
                                      <div class="vd_checkbox checkbox-success"> <input type="checkbox" id="checkbox-3" value="adam@sandler.com">
                                  
                                        <label class="filter-name" for="checkbox-3"> Adam Sandler - <em class="font-normal">adam@sandler.com</em> </label>
                                      </div>
                                    </div>
                                    <div class="email-item" style="position: absolute; left: 0px; top: 168px;">
                                      <div class="vd_checkbox checkbox-success">
                                        <input type="checkbox" id="checkbox-4" value="christina@aguilera.com">
                                        <label class="filter-name" for="checkbox-4"> Chirstina Aguilera - <em class="font-normal">christina@aguilera.com</em> </label>
                                      </div>
                                    </div>
                                    <div class="email-item" style="position: absolute; left: 0px; top: 224px;">
                                      <div class="vd_checkbox checkbox-success">
                                        <input type="checkbox" id="checkbox-5" value="tom@cruise.com">
                                        <label class="filter-name" for="checkbox-5"> Tom Cruise - <em class="font-normal">tom@cruise.com</em> </label>
                                      </div>
                                    </div>
                                    <div class="email-item" style="position: absolute; left: 0px; top: 280px;">
                                      <div class="vd_checkbox checkbox-success">
                                        <input type="checkbox" id="checkbox-6" value="dominicus@soddley.com">
                                        <label class="filter-name" for="checkbox-6"> Dominicus Soddley - <em class="font-normal">dominicus@soddley.com</em> </label>
                                      </div>
                                    </div>
                                    <div class="email-item" style="position: absolute; left: 0px; top: 336px;">
                                      <div class="vd_checkbox checkbox-success">
                                        <input type="checkbox" id="checkbox-7" value="web@designer.com">
                                        <label class="filter-name" for="checkbox-7"> Web Designer - <em class="font-normal">web@designer.com</em> </label>
                                      </div>
                                    </div>
                                    <div class="email-item" style="position: absolute; left: 0px; top: 392px;">
                                      <div class="vd_checkbox checkbox-success">
                                        <input type="checkbox" id="checkbox-8" value="web@templatecompany.com">
                                        <label class="filter-name" for="checkbox-8"> Web Template Company - <em class="font-normal">web@templatecompany.com</em> </label>
                                      </div>
                                    </div>
                                    <div class="email-item" style="position: absolute; left: 0px; top: 448px;">
                                      <div class="vd_checkbox checkbox-success">
                                        <input type="checkbox" id="checkbox-9" value="round@live.com">
                                        <label class="filter-name" for="checkbox-9"> Round Live - <em class="font-normal">round@live.com</em> </label>
                                      </div>
                                    </div>
                                    <div class="email-item" style="position: absolute; left: 0px; top: 504px;">
                                      <div class="vd_checkbox checkbox-success">
                                        <input type="checkbox" id="checkbox-10" value="chrisitan@bautista.com">
                                        <label class="filter-name" for="checkbox-10"> Chrisitan Bautista - <em class="font-normal">chrisitan@bautista.com</em> </label>
                                      </div>
                                    </div>
                                    <div class="email-item" style="position: absolute; left: 0px; top: 560px;">
                                      <div class="vd_checkbox checkbox-success">
                                        <input type="checkbox" id="checkbox-11" value="admin@template.com">
                                        <label class="filter-name" for="checkbox-11"> Admin Template - <em class="font-normal">admin@template.com</em> </label>
                                      </div>
                                    </div>
                                  </div>
                                  <!-- list-wrapper --> 
                                </div>
                                <!-- content-list --> 
                              </div>
                              <!-- col-md-12 --> 
                            </div>
                            <!-- form-group -->
                            
                        
                      <hr>
                      <div class="form-group form-actions">
                        <div class="col-sm-12">
                          <button type="button" id="insert-email-btn" class="btn vd_btn vd_bg-blue vd_white"><i class="fa fa-angle-double-left append-icon"></i> INSERT ADDRESS</button>
                          <button type="button" class="btn vd_btn vd_bg-grey vd_white"><i class="fa fa-plus append-icon"></i> ADD NEW</button>
                        </div>
                      </div>
                    </form:form>
                  </div>
                  <!-- panel-body  --> 
                  
                </div>
                <!-- panel --> 
              </div>
              <!-- col-md-8 --> 
            </div>
            <!-- row --> 
            
          </div>
          <!-- .vd_content-section --> 	
        </div>
        </div>
       </div>
      </div>
     </div>
<!-- Compose Content -->
	<%@include file="/WEB-INF/jsp/include/footer.jsp" %>
<!-- .vd_body END  -->
<a id="back-top" href="#" data-action="backtop" class="vd_back-top"> <i class="fa  fa-angle-up"> </i> </a>
</div>
<script type="text/javascript">

$(function () {
	"use strict";
	
	$('#message').wysihtml5();
	


	  // init Isotope
	  var $container = $('.isotope').isotope({
		itemSelector: '.isotope .email-item',
		layoutMode: 'vertical'
	  });

		
	// User types in search box - call our search function and supply lower-case keyword as argument
	$('#filter-text').bind('keyup', function() {

		var filterValue = this.value.toLowerCase();
		isotopeFilter();

	});
	
	var filterFns = function() {		 
	  var kwd = $('#filter-text').val().toLowerCase();
	  var re = new RegExp(kwd, "gi");
      var name = $(this).find('.filter-name').text();
      return name.match( re );		  
	}
	
	function isotopeFilter(){

		  $container.isotope({ filter: filterFns });	
	}
	
	
	$('#check-all').click(function() {
        $('.email-item input').prop('checked', true);
	});
	$('#uncheck-all').click(function() {
        $('.email-item input').prop('checked', false);
	});	


		
	$('#insert-email-btn').click(function(e) {
          e.preventDefault();
		  var emails='';
		  emails=$('.email-item input:checked').map(function(n){  //map all the checked value to tempValue with `,` seperated
            	return  this.value;
   		  }).get().join(' , ');
		  var comma = $('#email-input').val() ? ' , ' : '';		
		  if (emails)  {
		  	$('#email-input').val($('#email-input').val() + comma + emails);
		  }
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