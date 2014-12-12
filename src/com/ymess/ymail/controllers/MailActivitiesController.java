package com.ymess.ymail.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.ymail.pojos.Mail;
import com.ymess.ymail.service.interfaces.YMailService;
import com.ymess.ymail.util.YMailJSPMappings;
import com.ymess.ymail.util.YMailLoggerConstants;
import com.ymess.ymail.util.YMailURLMappings;

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
	@RequestMapping(value=YMailURLMappings.INBOX_PAGE,method=RequestMethod.GET)
	public String showInboxPage(Model model) throws EmptyResultSetException
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmailId = authentication.getName();
		
		List<Mail> mail = new ArrayList<Mail>();
		
		try {
			mail = yMailService.getInboxMails(userEmailId);
			logger.info(YMailLoggerConstants.INBOX_PAGE +" "+ userEmailId);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		
		model.addAttribute("mail",mail);
		return YMailJSPMappings.INBOX_PAGE;
	}
	
	/**
	 * Displays the Sent Page
	 * @author rvishwakarma
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return SentPage
	 */
	@RequestMapping(value=YMailURLMappings.SENT_PAGE,method=RequestMethod.GET)
	public String showSentPage(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmailId = authentication.getName();
		
		List<Mail> mail = new ArrayList<Mail>();
		
		try {
			mail = yMailService.getSentMails(userEmailId);
			logger.info(YMailLoggerConstants.SENT_PAGE +" "+ userEmailId);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		
		model.addAttribute("mail",mail);
		return YMailJSPMappings.SENT_PAGE;
	}


	/**
	 * Displays the Compose Mail Page
	 * @author rvishwakarma
	 * @param Models
	 * @return ComposeMailPage
	 */
	@RequestMapping(value=YMailURLMappings.COMPOSE_MAIL_PAGE,method=RequestMethod.GET)
	public String showComposeMailPage(Model model)
	{
		Mail mail = new Mail();


		model.addAttribute("mail", mail);
		logger.info(YMailLoggerConstants.COMPOSE_MAIL_PAGE);
		return YMailJSPMappings.COMPOSE_MAIL_PAGE;
	}

	@RequestMapping(value =YMailURLMappings.COMPOSE_MAIL_PAGE,method = RequestMethod.POST)
	public String sendMail(@ModelAttribute("mail") Mail mail,RedirectAttributes redirectAttributes)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();
		Boolean successFlag = false;

		mail.setMailFrom(loggedInUserEmailId);

		try
		{
			yMailService.sendMail(mail);
			logger.info(YMailLoggerConstants.USER_SEND_MAIL+" "+loggedInUserEmailId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error(ex.getStackTrace().toString());
		}
		redirectAttributes.addFlashAttribute("successfullyMailSend","Mail Send Successfully");
		return YMailURLMappings.REDIRECT_SUCCESS_MAIL_SEND;
	}
	
	
	
}
