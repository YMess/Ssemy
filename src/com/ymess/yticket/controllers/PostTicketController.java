/**
 * 
 */
package com.ymess.yticket.controllers;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ymess.yticket.pojos.Ticket;
import com.ymess.yticket.service.interfaces.IYTicket;
import com.ymess.yticket.util.YTicketJSPMappings;
import com.ymess.yticket.util.YTicketLoggerConstants;
import com.ymess.yticket.util.YTicketStringConstants;
import com.ymess.yticket.util.YTicketURLMappings;

/**
 * Used to post tickets by Users
 * @author balaji i
 *
 */
@Controller
public class PostTicketController {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	IYTicket yTicketManager;
	
	@RequestMapping(value = YTicketURLMappings.LOAD_TICKETS)
	String loadTicketsPage(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		logger.info(YTicketLoggerConstants.LOADED_TICKETS + " For "+ loggedInUserEmail);
		
		List<Ticket> tickets = yTicketManager.getUserTickets(loggedInUserEmail);
		
		if(null == tickets || tickets.size() == 0 )
			model.addAttribute("emptyResultSet",true);
		else
			model.addAttribute("tickets",tickets);
		
		return YTicketJSPMappings.LOAD_TICKETS;
	}
	
	@RequestMapping(value = YTicketURLMappings.ADD_TICKET,method = RequestMethod.POST)
	String addTicket(@ModelAttribute("ticket") Ticket ticketDetails)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		ticketDetails.setTicketPostedBy(loggedInUserEmail);
		ticketDetails.setTicketPostedOn(new Date());
		ticketDetails.setTicketStatus(YTicketStringConstants.TICKET_STATUS_OPEN);
		
		logger.info(YTicketLoggerConstants.ADDED_TICKET + " By "+ loggedInUserEmail);
		
		Boolean flag = yTicketManager.addTicket(ticketDetails);
		
		return "redirect:"+YTicketURLMappings.LOAD_TICKETS;
	}
	
	@RequestMapping(value = YTicketURLMappings.ADD_TICKET,method = RequestMethod.GET)
	String loadPostTicketPage(Model model)
	{
		model.addAttribute("ticket",new Ticket());
		return YTicketJSPMappings.ADD_TICKET;
	}
}
