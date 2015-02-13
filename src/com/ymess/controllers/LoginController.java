/**
 * 
 */
package com.ymess.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.pojos.Answer;
import com.ymess.pojos.Question;
import com.ymess.pojos.SearchParameters;
import com.ymess.service.interfaces.YMessService;
import com.ymess.util.YMessJSPMappings;
import com.ymess.util.YMessLoggerConstants;
import com.ymess.util.YMessMessageConstants;
import com.ymess.util.YMessURLMappings;

/**
 * Contains all the methods related to User Login
 * @author balaji i
 *
 */
@Controller
public class LoginController
{
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	YMessService yMessService;
	
	/**
	 * Displays the Login Page on Successful Validation of Credentials
	 * @author balaji i
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return LoginPage
	 */
	@RequestMapping(value=YMessURLMappings.LOGIN_PAGE,method=RequestMethod.GET)
	public String showLoginPage(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		logger.info(YMessLoggerConstants.LOGIN_PAGE);
		return YMessJSPMappings.LOGIN_PAGE;
	}
	
	
	/**
	 * Displays the Login Failed Page on Invalid Credentials
	 * @author balaji i
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return LoginPage
	 */
	@RequestMapping(value=YMessURLMappings.LOGIN_FAILED_PAGE,method=RequestMethod.GET)
	public String showLoginFailedPage(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		logger.info(YMessLoggerConstants.LOGIN_FAILED);
		return YMessJSPMappings.LOGIN_PAGE;
	}
	
	
	/**
	 * Displays the  Dashboard Page on Proper Credentials
	 * @author balaji i
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return LoginPage
	 */
	@RequestMapping(value=YMessURLMappings.DASHBOARD_PAGE,method=RequestMethod.GET)
	public String showDashboardPage(@ModelAttribute("successfullyPostedQuestion") String successfullyPostedQuestion,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmailId = authentication.getName();
		
		if(successfullyPostedQuestion != null && successfullyPostedQuestion.length() > 0)
		{
			model.addAttribute("successfullyPostedQuestion",successfullyPostedQuestion);
		}
		
		List<Question> questions = new ArrayList<Question>();
		try {
			questions = yMessService.getDashboardQuestions(userEmailId);
		} catch (EmptyResultSetException e) {
			model.addAttribute("emptyResultSet",YMessMessageConstants.EMPTY_RESULT_SET);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		model.addAttribute("searchParameters",new SearchParameters());
		model.addAttribute("questions",questions);
		model.addAttribute("answer",new Answer());
		
		logger.info(YMessLoggerConstants.DASHBOARD_PAGE);
		return YMessJSPMappings.DASHBOARD_PAGE;
	}
	
	
	

}
