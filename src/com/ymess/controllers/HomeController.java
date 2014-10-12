/**
 * 
 */
package com.ymess.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ymess.util.JSPMappings;
import com.ymess.util.LoggerConstants;
import com.ymess.util.URLMappings;

/**
 * @author balaji i
 *
 */
@Controller
public class HomeController {
	
private final Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Displays the Home Page
	 * @author balaji i
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return LoginPage
	 */
	@RequestMapping(value=URLMappings.HOME_PAGE,method=RequestMethod.GET)
	public String showHomePage(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		logger.info(LoggerConstants.HOME_PAGE);
		return JSPMappings.HOME_PAGE;
	}

}
