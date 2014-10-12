/**
 * 
 */
package com.ymess.controllers;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ymess.pojos.User;
import com.ymess.service.interfaces.YMessService;
import com.ymess.util.JSPMappings;
import com.ymess.util.LoggerConstants;
import com.ymess.util.URLMappings;
import com.ymess.validators.UserValidator;

/**
 * Contains all the methods of User registration
 * @author balaji i
 */
@Controller
public class RegistrationController {
	
	@Autowired
	YMessService yMessService;
	
	private final Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Displays the Registration Page
	 * @author balaji i
	 * @param model
	 * @return (registrationPage)
	 */
	@RequestMapping(value=URLMappings.REGISTRATION_PAGE,method=RequestMethod.GET)
	public String showRegistrationPage(Model model)
	{
		model.addAttribute("user", new User());
		logger.info(LoggerConstants.SHOW_REGISTRATION_PAGE);
		return JSPMappings.REGISTRATION_PAGE;
	}

	
	
	/**
	 * Submits the User Data and checks for Validity of Data,else returns.
	 * @author balaji i
	 * @param user
	 * @param result
	 * @return (loginPage)
	 */
	@RequestMapping(value=URLMappings.REGISTRATION_PAGE,method=RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult result,Model model)
	{
		/** Rejecting the EMail Address if already registered */
		if(yMessService.checkIfUserEmailExists(user.getUserEmailId()))
		{
			result.rejectValue("userEmailId", "This email has already been registered","This email has already been registered");
		}
		
		if(result.hasErrors())
		{
				return JSPMappings.REGISTRATION_PAGE;
		}
		
		yMessService.addUser(user);
		logger.info(LoggerConstants.USER_REGISTRATION);
		
		return URLMappings.REDIRECT_SUCCESS_USER_REGISTRATION;
	}

}