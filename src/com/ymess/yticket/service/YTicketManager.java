package com.ymess.yticket.service;

import java.util.List;

import com.ymess.yticket.dao.interfaces.IYTicketDAO;
import com.ymess.yticket.pojos.Ticket;
import com.ymess.yticket.service.interfaces.IYTicket;

public class YTicketManager  implements IYTicket{

	IYTicketDAO yTicketDao;

	public IYTicketDAO getyTicketDao() {
		return yTicketDao;
	}

	public void setyTicketDao(IYTicketDAO yTicketDao) {
		this.yTicketDao = yTicketDao;
	}

	/***
	 * Gets all the Tickets Posted By User
	 * @param loggedInUserEmail
	 * @return List<Ticket>(tickets)
	 */
	@Override
	public List<Ticket> getUserTickets(String loggedInUserEmail) {
		return yTicketDao.getUserTickets(loggedInUserEmail);
	}

	
	
	
}
