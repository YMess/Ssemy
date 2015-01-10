package com.ymess.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler  {

	@Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();

        String successfulLogin = "";
        if(role.contains("ROLE_REGISTERED")) {
            successfulLogin = "/userdashboard.htm";
        }
        if(role.contains("ROLE_ADMIN")) {
            successfulLogin = "admin_dashboard.htm";
        }
        return successfulLogin;
    }
}
