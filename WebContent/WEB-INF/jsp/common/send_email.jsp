<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java"
	import="org.springframework.security.core.Authentication, org.springframework.security.core.context.SecurityContextHolder"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html >
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Email with Spring MVC</title>
   </head>
   <body>
      <center>
         <h1>Sending e-mail with Spring MVC</h1>
         <form:form modelAttribute="emailParameters" method="post" action="sendEmail.htm">
            <table border="0" width="80%">
               <tr>
                  <td>To:</td>
                  <td><input type="text" name="mailTo" size="65" /></td>
               </tr>
               <tr>
                  <td>Subject:</td>
                  <td><input type="text" name="mailSubject" size="65" /></td>
               </tr>
               <tr>
                  <td>Message:</td>
                  <td><textarea cols="50" rows="10" name="mailBody"></textarea></td>
               </tr>
               <tr>
                  <td colspan="2" align="center">
                     <input type="submit" value="Send E-mail" />
                  </td>
               </tr>
            </table>
         </form:form>
      </center>
   </body>
</html>