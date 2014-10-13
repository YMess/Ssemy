<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- <img alt="Header" src="images/header.jpg" height="15%" width="100%"> -->

<div style="width:25%;float:left;">
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
