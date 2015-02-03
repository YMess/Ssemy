package com.ymess.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CustomLogoutHandler extends SimpleUrlLogoutSuccessHandler {

		/**
		 * This method is used to get the logout time of a user and redirect it to the home page on logout
		 */
		@Override
		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, 
				Authentication authentication) throws IOException, ServletException {
	       
		// Redirect user to the home page
			response.sendRedirect("home.htm");
		}
}
