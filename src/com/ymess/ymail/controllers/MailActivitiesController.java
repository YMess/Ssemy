package com.ymess.ymail.controllers;

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

import com.ymess.util.JSPMappings;
import com.ymess.util.LoggerConstants;
import com.ymess.util.URLMappings;
import com.ymess.ymail.pojos.Mail;
import com.ymess.ymail.service.interfaces.YMailService;

@Controller
public class MailActivitiesController {

	Logger logger = Logger.getLogger(getClass());

	@Autowired
	YMailService yMailService;



	/**
	 * Displays the Inbox Page
	 * @author rvishwakarma
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return InboxPage
	 */
	@RequestMapping(value=URLMappings.INBOX_PAGE,method=RequestMethod.GET)
	public String showInboxPage(Model model)
	{

		logger.info(LoggerConstants.INBOX_PAGE);
		return JSPMappings.INBOX_PAGE;
	}


	/**
	 * Displays the Compose Mail Page
	 * @author rvishwakarma
	 * @param Models
	 * @return ComposeMailPage
	 */
	@RequestMapping(value=URLMappings.COMPOSE_MAIL_PAGE,method=RequestMethod.GET)
	public String showComposeMailPage(Model model)
	{
		Mail mail = new Mail();


		model.addAttribute("mail", mail);
		logger.info(LoggerConstants.COMPOSE_MAIL_PAGE);
		return JSPMappings.COMPOSE_MAIL_PAGE;
	}

	@RequestMapping(value =URLMappings.COMPOSE_MAIL_PAGE,method = RequestMethod.POST)
	public Boolean sendMail(@ModelAttribute("mail") Mail mail)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();
		Boolean successFlag = false;

		mail.setMailFrom(loggedInUserEmailId);

		try
		{
			yMailService.sendMail(mail);
			logger.info(LoggerConstants.USER_SEND_MAIL+" "+loggedInUserEmailId);
		}
		catch(Exception ex)
		{
		logger.error(ex.getLocalizedMessage());
		}
		return successFlag;
	}
}
