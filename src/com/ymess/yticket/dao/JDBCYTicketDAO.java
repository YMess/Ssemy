package com.ymess.yticket.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.ymess.yticket.dao.interfaces.IYTicketDAO;
import com.ymess.yticket.pojos.Ticket;

public class JDBCYTicketDAO extends JdbcDaoSupport implements IYTicketDAO{

	private class TicketDetailsMapper implements ParameterizedRowMapper<Ticket>
	{
		@SuppressWarnings("unchecked")
		@Override
		public Ticket mapRow(ResultSet rs, int rowCount) throws SQLException {
			Ticket ticketDetails = new Ticket();
			
			ticketDetails.setTicketId(rs.getLong("ticket_id"));
			ticketDetails.setTicketSubject(rs.getString("ticket_subject"));
			ticketDetails.setTicketBody(rs.getString("ticket_body"));
			ticketDetails.setTicketAttachments((Map<String,byte[]>)rs.getObject("ticket_attachments"));
			ticketDetails.setTicketAssignedTo(rs.getString("ticket_assigned_to"));
			ticketDetails.setTicketAssignedBy(rs.getString("ticket_assigned_by"));
			ticketDetails.setTicketPostedOn(rs.getDate("ticket_posted_on"));
			ticketDetails.setTicketPostedBy(rs.getString("ticket_posted_by"));
			ticketDetails.setTicketStatus(rs.getString("ticket_status"));
			
			return ticketDetails;
		}
		
	}
	
	/***
	 * Gets all the Tickets Posted By User
	 * @param loggedInUserEmail
	 * @return List<Ticket>(tickets)
	 */
	@Override
	public List<Ticket> getUserTickets(String loggedInUserEmail){
		List<Ticket> userTickets = new ArrayList<Ticket>();
		Map<Long,Date> tickets = new HashMap<Long, Date>();
		
		final String GET_USER_TICKETS = "select tickets from user_tickets where email_id="+loggedInUserEmail;
		try{
			tickets = getJdbcTemplate().queryForObject(GET_USER_TICKETS,Map.class);
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			logger.error("Empty Result Set in GET_USER_TICKETS "+emptyRS.getStackTrace().toString());
		}
		
		StringBuilder ticketsSB = new StringBuilder();
		if(null != tickets && !tickets.isEmpty())
		{
			List<Long> ticketIds = new ArrayList<Long>(tickets.keySet());
			Collections.reverse(ticketIds);
			for (Long ticketId : ticketIds) {
				ticketsSB.append(ticketId);
			}
			String ticketIdsStr = ticketsSB.toString();
		
			if(ticketIdsStr != null && ticketIdsStr.length() > 0)
			{
				final String GET_TICKET_DETAILS = "select * from ticket_details where ticket_id in ("+ticketIdsStr+")";
				try{
					userTickets = getJdbcTemplate().query(GET_TICKET_DETAILS,new TicketDetailsMapper());
				}
				catch(EmptyResultDataAccessException emptyRS)
				{
					logger.error("Empty Result Set in GET_TICKET_DETAILS " +emptyRS.getStackTrace().toString());
				}
			}
		}
		return userTickets;
	}

}
