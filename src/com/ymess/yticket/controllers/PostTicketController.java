/**
 * 
 */
package com.ymess.yticket.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ymess.yticket.pojos.Ticket;
import com.ymess.yticket.service.interfaces.IYTicket;
import com.ymess.yticket.util.YTicketJSPMappings;
import com.ymess.yticket.util.YTicketLoggerConstants;
import com.ymess.yticket.util.YTicketURLMappings;

/**
 * Used to post tickets by Users
 * @author balaji i
 *
 */
@RestController
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
		model.addAttribute("tickets",tickets);
		
		return YTicketJSPMappings.LOAD_TICKETS;
	}
	
	@RequestMapping(value = YTicketURLMappings.ADD_TICKET)
	String addTicket(@ModelAttribute("ticket") Ticket ticketDetails)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		logger.info(YTicketLoggerConstants.ADDED_TICKET + " By "+ loggedInUserEmail);
		
		Boolean flag = yTicketManager.addTicket(ticketDetails);
		
		return YTicketJSPMappings.LOAD_TICKETS;
	}
}
