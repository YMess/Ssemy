package com.ymess.yticket.dao.interfaces;

import java.util.List;

import com.ymess.yticket.pojos.Ticket;

public interface IYTicketDAO {

	/***
	 * Gets all the Tickets Posted By User
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
