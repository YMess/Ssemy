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
<title>Spam</title>
<script src="js/jquery-2.1.3.min.js" type="text/javascript"></script>
<script src="js/jquery-ui.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
    $('#deleteMail').click(function() {
        var deleteMailIds = new Array();
        var k = 0;
        $('input:checked').each(function(index,value) {
        	deleteMailIds[k] = value.id;
        	alert(value.id);
            k++;
        });        
        alert(deleteMailIds[0]);

        $.ajax({
    		  url:"delete_mails.json",
    		  data:'mailIds='+deleteMailIds,
    		  async:false,
    		  error : function() {
    	        console.log("error");
    	      },
    		  success:function(result){
    			  alert(mailSize+" Mails Deleted Successfully");
    			  location.reload();
      }}); 
    });
});
</script>
</head>
<body id="email" class="full-layout    nav-top-fixed   nav-right-small   responsive    clearfix breakpoint-975" data-active="email " data-smooth-scrolling="1">     
<c:if test="${ not empty successfullyMailSend }">
				<script type="text/javascript">
						alert("Mail Sent Successfully");
				</script>
				</c:if>
<div class="vd_body">
<header>
   <%@ include file="/WEB-INF/jsp/include/header.jsp" %>
</header>
<div class="content">
  <div class="container">
		<%@include file="/WEB-INF/jsp/mail/navigation.jsp" %>
		<%@ include file="/WEB-INF/jsp/include/right.jsp" %>
	<div style="min-height: 1048px;" class="vd_content-wrapper">
      <div style="min-height: 1048px;margin-left: 180px;" class="vd_container">
		<div class="vd_content clearfix">
        
          <div class="vd_head-section clearfix">
            <div class="vd_panel-header">
              <ul class="breadcrumb">
                <li><a href="index.php">Home</a> </li>
                <li class="active">Email</li>
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
              <h1>Email</h1>
              <small class="subtitle">Email templates</small> 
                 
            </div>
          </div>
          <!-- vd_title-section -->
          
          <div class="vd_content-section clearfix" >
            <div class="panel widget light-widget">
            
              <div class="panel-heading no-title">
                      <div class="vd_panel-menu">
  <div data-action="refresh" class="menu entypo-icon smaller-font" data-placement="bottom" data-toggle="tooltip" data-original-title="Refresh"> <i class="icon-cycle"></i> </div>
  <div class="menu entypo-icon smaller-font" data-placement="bottom" data-toggle="tooltip" data-original-title="Config">
    <div data-action="click-trigger" class="menu-trigger"> <i class="icon-cog"></i> </div>
    <div class="vd_mega-menu-content  width-xs-2  left-xs" data-action="click-target">
      <div class="child-menu">
        <div class="content-list content-menu">
          <ul class="list-wrapper pd-lr-10">
            <li> <a href="#"> <div class="menu-icon"><i class=" fa fa-user"></i></div> <div class="menu-text">Panel Menu</div> </a> </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div class="menu entypo-icon" data-placement="bottom" data-toggle="tooltip" data-original-title="Close" data-action="close"> <i class="icon-cross"></i> </div>
</div>
<!-- vd_panel-menu --> 
 
              </div>
              <!-- vd_panel-heading -->
              <div class="panel-body">
                <h2 class="mgtp--10"> Spam</h2>
                <table class="table table-striped table-hover">
                  <thead>
                    <tr>
                      <th> <div class="vd_checkbox">
                          <input type="checkbox" id="checkbox-0">
                          <label for="checkbox-0"></label>
                        </div>
                      </th>
                      <th> </th>
                      <th colspan="3"> 
                      <div class="mgr-20 menu-btn"><a role="button" id="deleteMail"><i class="icon-trash append-icon vd_green"></i> Delete</a></div> 
                      <div class="mgr-20 menu-btn"><a role="button"><i class="fa fa-shield append-icon vd_green"></i> Spam</a></div> 
                      <div class="mgr-20 menu-btn"><a role="button"><i class="icon-folder append-icon vd_green"></i>Move</a></div> 
                      <div class="mgr-20 menu-btn"><a data-toggle="dropdown" role="button"><i class="fa fa-ellipsis-h append-icon vd_green"></i>More</a>
                        <ul class="dropdown-menu pull-right">
                          <li><a href="#">Action</a></li>
                          <li><a href="#">Another action</a></li>
                          <li><a href="#">Something else here</a></li>
                          <li class="divider"></li>
                          <li><a href="#">Separated link</a></li>
                        </ul>
                        </div> </th>
                    </tr>
                  </thead>
                  <c:choose>
					<c:when test="${not empty emptyResultSet }">
									No mails Found! 
					</c:when>
					<c:otherwise>
                  <tbody> 
					<c:forEach items="${mails}" var="mail">
						<c:set var="mailId" value="${mail.mailId}" />
                    <tr>
                      <td style="width:20px"><div class="vd_checkbox">
                          <input type="checkbox" class="checkbox-group" id="${mail.mailId}" value="${mail.mailId}">
                          <label for="checkbox-1"></label>
                        </div></td>
                      <td style="width:40px"><div class="vd_star">
                          <input type="checkbox" id="checkstar-1" id="${mail.mailId}" value="${mail.mailId}">
                          <label for="checkstar-1"></label>
                        </div></td>
                      <td><c:out value="${mail.mailFrom }" /></td>
                      <td><span class="label vd_bg-green append-icon">Work</span> <strong><c:out value="${mail.mailSubject }"/></strong> <span class="prepend-icon append-icon"><i class="icon-dot"></i></span><c:out value="${mail.mailBody }" /></td>
                      <td>
                      <c:if test="${mail.isAttachmentAttached eq true }">
						<div>
						   <img  height="20" width="25" src="images/Attachment_icon.png">		
						</div>
					  </c:if>
					  </td>
                      <td style="width:80px" class="text-right"><strong><fmt:formatDate type="date" value="${mail.mailSentTimestamp}" /></strong></td>
                    </tr>
                    </c:forEach>
                  </tbody>
                  </c:otherwise>
			</c:choose>
                </table>
                <ul class="pagination">
                  <li><a href="#">«</a></li>
                  <li class="active"><a href="#">1</a></li>
                  <li><a href="#">2</a></li>
                  <li><a href="#">3</a></li>
                  <li><a href="#">4</a></li>
                  <li><a href="#">5</a></li>
                  <li><a href="#">»</a></li>
                </ul>
              </div>
              <!-- panel-body  -->
              
            </div>
          <!-- panel --> 
          
        </div>
        <!-- .vd_content-section --> 
        
      </div>
     </div>
    </div>  
   </div>
</div>

<%@include file="/WEB-INF/jsp/include/footer.jsp" %>
<a id="back-top" href="#" data-action="backtop" class="vd_back-top"> <i class="fa  fa-angle-up"> </i> </a>
</div>
</body>
</html>