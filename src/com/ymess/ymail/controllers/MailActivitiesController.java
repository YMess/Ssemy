package com.ymess.ymail.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.util.YMessCommonUtility;
import com.ymess.util.YMessLoggerConstants;
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
		
		List<Mail> mails = new ArrayList<Mail>();
		
		try {
			mails = yMailService.getInboxMails(userEmailId);
			logger.info(YMailLoggerConstants.INBOX_PAGE +" "+ userEmailId);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		
		model.addAttribute("mails",mails);
		return YMailJSPMappings.INBOX_PAGE;
	}
	
	@RequestMapping(value = YMailURLMappings.DRAFTS_PAGE,method = RequestMethod.GET)
	public String showDraftPage(Model model) throws EmptyResultSetException
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmailId = authentication.getName();
		
		List<Mail> mails = new ArrayList<Mail>();
		
		//temporary for testing new inbox JSP
		try {
			mails = yMailService.getDraftMails(userEmailId);
			logger.info(YMailLoggerConstants.DRAFTS_PAGE +" "+ userEmailId);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		
		model.addAttribute("mails",mails);
		return YMailJSPMappings.DRAFTS_PAGE;
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
		
		List<Mail> mails = new ArrayList<Mail>();
		
		try {
			mails = yMailService.getSentMails(userEmailId);
			logger.info(YMailLoggerConstants.SENT_PAGE +" "+ userEmailId);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		
		model.addAttribute("mails",mails);
		return YMailJSPMappings.SENT_PAGE;
	}


	/**
	 * Displays Spam Page
	 * @author RAJ@param httpServletRequest
	 * @param httpServletResponse* @param model
	 * @return Spam Page
	 */
	@RequestMapping(value=YMailURLMappings.SPAM_PAGE,method=RequestMethod.GET)
	public String showSpamPage(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmailId = authentication.getName(); 
		
		List<Mail> mails = new ArrayList<Mail>();
		
		try {
			mails = yMailService.getSpamMails(userEmailId);	
			logger.info(YMailLoggerConstants.SPAM_PAGE+" "+userEmailId);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		model.addAttribute("mails", mails);
		return YMailJSPMappings.SPAM_PAGE;
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
		model.addAttribute("mail",  new Mail());
		logger.info(YMailLoggerConstants.COMPOSE_MAIL_PAGE);
		return YMailJSPMappings.COMPOSE_MAIL_PAGE;
	}

	@RequestMapping(value =YMailURLMappings.COMPOSE_MAIL_PAGE,method = RequestMethod.POST,params="send")
	public String sendMail(@ModelAttribute("mail") Mail mail,RedirectAttributes redirectAttributes)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();

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
	
	
	@RequestMapping(value= YMailURLMappings.IMPORTANT_PAGE)
	public String loadImportantMails(Model model)
	{
		List<Mail> importantMails = new ArrayList<Mail>();
		try {
			importantMails = yMailService.getImportantMails(SecurityContextHolder.getContext().getAuthentication().getName());
			logger.info(YMailLoggerConstants.IMPORTANT_PAGE+" "+SecurityContextHolder.getContext().getAuthentication().getName());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getStackTrace().toString());
		}
		model.addAttribute("importantMails",importantMails);
		return YMailJSPMappings.IMPORTANT_PAGE;
	}
	
	@RequestMapping(value= YMailURLMappings.TRASH_PAGE)
	public String showTrashPage(Model model)
	{
		List<Mail> trashMails = new ArrayList<Mail>();
		try {
			trashMails = yMailService.getTrashMails(SecurityContextHolder.getContext().getAuthentication().getName());
			logger.info(YMailLoggerConstants.TRASH_PAGE+" "+SecurityContextHolder.getContext().getAuthentication().getName());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getStackTrace().toString());
		}
		model.addAttribute("trashMails",trashMails);
		return YMailJSPMappings.TRASH_PAGE;
	}
	
	@RequestMapping(value = YMailURLMappings.DELETE_MAIlS)
	@ResponseBody
	public Boolean deleteMails(@RequestParam("mailIds") Long[] deleteMailIds)
	{
		Boolean successFlag = false;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		try {
			yMailService.deleteMails(deleteMailIds,loggedInUserEmail);
			successFlag = true;
		} catch (Exception ex) {
			logger.error(ex.getStackTrace().toString());
		}
		logger.info(loggedInUserEmail+" "+YMessLoggerConstants.DELETED_MAILS);
		return successFlag;
	}
	
	
	@RequestMapping(value = YMailURLMappings.MAIL_DETAILS)
	String getMailDetails(@RequestParam("mId") String mailId, Model model)
	{
		String decodedMailId = YMessCommonUtility.decodeEncodedParameter(mailId);
		
		Mail mailDetails = yMailService.getMailDetails(decodedMailId);
		model.addAttribute("mailDetails",mailDetails);
		return YMailJSPMappings.MAIL_DETAILS;

	}

	
	@RequestMapping(value =YMailURLMappings.COMPOSE_MAIL_PAGE,method = RequestMethod.POST,params="save")
	public String saveMail(@ModelAttribute("mail") Mail mail,RedirectAttributes redirectAttributes)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();

		mail.setMailFrom(loggedInUserEmailId);

		try
		{
			yMailService.saveMail(mail);
			logger.info(YMailLoggerConstants.USER_SAVED_MAIL+" "+loggedInUserEmailId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error(ex.getStackTrace().toString());
		}
		redirectAttributes.addFlashAttribute("successfullyMailSaved","Mail Saved to Drafts Successfully");
		return YMailURLMappings.REDIRECT_SUCCESS_MAIL_SAVED;
	}
	
}
