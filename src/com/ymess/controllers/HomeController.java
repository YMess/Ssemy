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

import com.ymess.util.YMessJSPMappings;
import com.ymess.util.YMessLoggerConstants;
import com.ymess.util.YMessURLMappings;

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
	@RequestMapping(value=YMessURLMappings.HOME_PAGE,method=RequestMethod.GET)
	public String showHomePage(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		logger.info(YMessLoggerConstants.HOME_PAGE);
		return YMessJSPMappings.HOME_PAGE;
	}

}
