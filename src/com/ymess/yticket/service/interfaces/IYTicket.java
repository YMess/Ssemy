package com.ymess.yticket.service.interfaces;

import java.util.List;

import com.ymess.yticket.pojos.Ticket;

public interface IYTicket {

	/***
	 * Gets all the Tickets Posted By User
	 * @author balaji i
	 * @param loggedInUserEmail
	 * @return List<Ticket>(tickets)
	 */
	List<Ticket> getUserTickets(String loggedInUserEmail);

	/**
	 * Adds a Ticket posted by User
	 * @author balaji i
	 * @param ticketDetails
	 * @return flag(Boolean)
	 */
	Boolean addTicket(Ticket ticketDetails);

}
