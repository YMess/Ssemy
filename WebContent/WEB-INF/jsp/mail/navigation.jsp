<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <link href="css/custom/modal.css" rel="stylesheet" type="text/css"/>
  <script type='text/javascript' src='js/jquery.simplemodal.js'></script>
  <script type="text/javascript">
    $(document).ready(function() {
    	// Load dialog on click
    	$('#basic-modal .basic').click(function (e) {
        	
    		$('#basic-modal-content').modal();

    		return false;
    	});
    });
    </script>
    
<div class="vd_navbar vd_nav-width vd_navbar-email vd_bg-black-80 vd_navbar-left vd_navbar-style-2 " style="width: 15%;">
	<div class="navbar-tabs-menu clearfix">
			<span class="expand-menu" data-action="expand-navbar-tabs-menu">
            	<span class="menu-icon menu-icon-left">
            		<i class="fa fa-ellipsis-h"></i>
                    <span class="badge vd_bg-red">
                        20
                    </span>                    
                </span>
            	<span class="menu-icon menu-icon-right">
            		<i class="fa fa-ellipsis-h"></i>
                    <span class="badge vd_bg-red">
                        20
                    </span>                    
                </span>                
            </span>  
            <div class="menu-container">               
        		 <div class="navbar-search-wrapper">
    <div class="navbar-search vd_bg-black-30">
        <span class="append-icon"><i class="fa fa-search"></i></span>
        <input type="text" placeholder="Search" class="vd_menu-search-text no-bg no-bd vd_white width-70" name="search">
        <div class="pull-right search-config">                                
            <a data-toggle="dropdown" href="javascript:void(0);" class="dropdown-toggle"><span class="prepend-icon vd_grey"><i class="fa fa-cog"></i></span></a>
            <ul role="menu" class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li><a href="#">Separated link</a></li>
              </ul>                                    
        </div>
    </div>
</div>  
            </div>        
                                                 
    </div>
	<div class="navbar-menu clearfix">
    	<h3 class="menu-title hide-nav-medium hide-nav-small"><a href="compose_mail.htm" class="btn vd_btn vd_bg-red"><span class="append-icon"><i class="icon-feather"></i></span>Compose Email</a></h3>
        <div class="vd_menu">
        	<ul>
	<li class="line vd_bd-grey">
    </li>
 	<li>
    	<a href="inbox.htm">
        	<span class="menu-icon entypo-icon"><i class="icon-mail"></i></span> 
            <span class="menu-text">Inbox</span>  
            <span class="menu-badge"><span class="badge vd_bg-red">78+</span></span>
       	</a> 
    </li> 
    <li>
    	<a href="drafts.htm">
        	<span class="menu-icon"><i class="fa fa-archive"></i></span> 
            <span class="menu-text">Drafts</span>  
            <span class="menu-badge"><span class="badge vd_bg-red">5</span></span>            
       	</a>
    </li>          
    <li>
    	<a href="sent.htm">
        	<span class="menu-icon entypo-icon"><i class="icon-paperplane"></i></span> 
            <span class="menu-text">Sent</span>  
       	</a>
    </li>     
    <li>
    	<a href="important.htm">
        	<span class="menu-icon entypo-icon"><i class="icon-paperplane"></i></span> 
            <span class="menu-text">Important</span>  
       	</a>
    </li>     
    <li>
    	<a href="spam.htm">
        	<span class="menu-icon"><i class="fa fa-shield"></i></span> 
            <span class="menu-text">Spam</span>
            <span class="menu-badge"><span class="badge vd_bg-red">99+</span></span>               
       	</a>
    </li>          
    <li>
    	<a href="trash.htm">
        	<span class="menu-icon entypo-icon"><i class="icon-trash"></i></span> 
            <span class="menu-text">Trash</span>  
       	</a>
    </li>          
    <li>
    	<a href="javascript:void(0);" data-action="click-trigger">
        	<span class="menu-icon entypo-icon"><i class="icon-folder"></i></span> 
            <span class="menu-text">Folders</span>
       	</a> 
     	<div class="child-menu" data-action="click-target">
            <ul>
                <li id="basic-modal">
                    <a href="" class="basic">
                        <span class="menu-text">New Folder</span>  
                    </a>
                </li>              
                <li>
                    <a href="http://vendroid.venmond.com/index-ecommerce.php">
                        <span class="menu-text">Edit</span>  
                    </a>
                </li>                                                                                                  
            </ul>   
      	</div>
    </li>             
	<li class="line vd_bd-grey">
    </li>
    <li>
    	<a href="javascript:void(0);">
        	<span class="menu-icon entypo-icon"><i class="icon-user"></i></span> 
            <span class="menu-text">Messenger</span>  
       	</a>
    </li>
    <li>
    	<a href="javascript:void(0);">
        	<span class="menu-icon entypo-icon"><i class="icon-calendar"></i></span> 
            <span class="menu-text">Calendar</span>  
       	</a>
    </li>  
    <li>
    	<a href="javascript:void(0);">
        	<span class="menu-icon"><i class="glyphicon glyphicon-book"></i></span> 
            <span class="menu-text">Contacts</span>  
       	</a>
    </li>  
    <li>
    	<a href="javascript:void(0);">
        	<span class="menu-icon"><i class="glyphicon glyphicon-file"></i></span> 
            <span class="menu-text">Notepad</span>  
       	</a>
    </li> 
	<li class="line vd_bd-grey">
    </li>
    <li>
    	<a href="index.php">
        	<span class="menu-icon"><i class="fa fa-desktop"></i></span> 
            <span class="menu-text">Back  Home</span>  
       	</a>
    </li>                             
</ul>
<!-- Head menu search form ends -->         </div>  

            
    </div>
    <div class="navbar-spacing clearfix">
    </div>
<!--     <div id="basic-modal-content">
			<h3>Basic Modal Dialog</h3>
			<div class='contact-content'>
		<div class='contact-loading' style='display:none'></div>
		<div class='contact-message' style='display:none'></div>
		<form action='#'>
			<label for='folder-name'>*Folder:</label>
			<input type='text' id='folder-name' class='folder-input' name='folder' tabindex='1001' /><br/>
			<label for='folder-name'>*From:</label>
			<select>
			<option value="xyx@gmail.com">XYZ@gmail.com</option>
			<option value="abc@gmail.com">abc@gmail.com</option>
			</select>
			<br/>
			<label for='folder-name'>*To:</label>
			<select>
			<option value="xyx@gmail.com">XYZ@gmail.com</option>
			<option value="abc@gmail.com">abc@gmail.com</option>
			</select>
			<br/>
			
			
			<br/>
			<label>&nbsp;</label>
			<button type='submit' class='contact-send contact-button' tabindex='1006'>Send</button>
			<button type='submit' class='contact-cancel contact-button simplemodal-close' tabindex='1007'>Cancel</button>
			<br/>
			<input type='hidden' name='token' value='" . smcf_token($to) . "'/>
		</form>
	  </div>
	</div> -->
	                <div id="basic-modal-content">
                    <h2 class="mgtp--10"><i class="icon-feather mgr-10 vd_green"></i> Create Folder</h2>
                    <br>
                    <form:form action="create_folder.json" modelAttribute="folder" enctype="multipart/form-data" id="createFolder" method="post">
                      <div class="form-group clearfix">
                        <label class="col-sm-2 control-label">Folder</label>
                        <div class="col-sm-10 controls">
                          <input id="email-input" type="text" class="input-border-btm" name="folderName" placeholder="Folder name">
                        </div>
                      </div>
                      <div class="form-group clearfix">
                        <label class="col-sm-2 control-label">From</label>
                        <div class="col-sm-10 controls">
                          <input id="email-input" type="text" class="input-border-btm" name="ruleFrom" placeholder="someone@example.com">
                        </div>
                        </div>
                        <div class="form-group clearfix">
                        <label class="col-sm-2 control-label">To</label>
                        <div class="col-sm-10 controls">
                          <input id="email-input" type="text" class="input-border-btm" name="ruleTo" placeholder="someone@example.com">
                        </div>
                        </div>
                       <div class="form-group clearfix">
                        <label class="col-sm-2 control-label">Cc</label>
                        <div class="col-sm-10 controls">
                          <input id="email-input" type="text" class="input-border-btm" name="ruleCC" placeholder="someone@example.com">
                        </div>
                      </div>
                       <div class="form-group clearfix">
                        <label class="col-sm-2 control-label">Bcc</label>
                        <div class="col-sm-10 controls">
                          <input id="email-input" type="text" class="input-border-btm" name="ruleBCC" placeholder="someone@example.com">
                        </div>
                      </div>
                      <div class="form-group clearfix">
                        <label class="col-sm-2 control-label">Subject</label>
                        <div class="col-sm-10 controls">
                          <input id="email-input" type="text" class="input-border-btm" name="ruleSubject" placeholder="subject">
                        </div>
                      </div>
                      <div class="form-group  clearfix">
                        <label class="col-sm-2 control-label">Exclude Inbox</label>
                        <div class="col-sm-10 controls">
                          <input id="excludeInbox" type="checkbox" name="isExcludeInbox">
                        </div>
                      </div>
                      <div class="form-group form-actions">
                        <div class="col-sm-12">
                          <button type="submit" class="btn vd_btn vd_bg-green vd_white" name="save" ><i class="fa fa-envelope append-icon"></i> SAVE</button>
                          <button type="submit" class="btn vd_btn vd_bg-yellow vd_white"  name="cancel"><i class="fa fa-archive append-icon"></i> CANCEl</button>
                        </div>
                      </div>
                    </form:form>
                  </div>
                  <!-- preload the images -->
		         <div style='display:none'>
			         <img src='images/x.png' alt='' />
		         </div>
</div>
