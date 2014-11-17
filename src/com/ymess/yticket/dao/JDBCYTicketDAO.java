package com.ymess.yticket.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
import com.ymess.yticket.util.YTicketStringConstants;

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

	/**
	 * Adds a Ticket posted by User
	 * @author balaji i
	 * @param ticketDetails
	 * @return flag(Boolean)
	 */
	@Override
	public Boolean addTicket(Ticket ticketDetails) {
		Boolean flag = false;
		Long ticketId = getTicketId();
		incrementTicketId();
		
		final String GET_USER_TICKET_COUNT = "select count(*) from user_ticket where email_id=?";
		Long ticketCount = getJdbcTemplate().queryForObject(GET_USER_TICKET_COUNT,Long.class,ticketDetails.getTicketPostedBy());
		
		try{
		if(ticketCount > 0){
			final String ADD_TICKET_TO_USER_ACCOUNT = "update user_ticket set"
					+ " tickets = tickets + {"+ticketId +":" +new Date().getTime()+"} where email_id=?";
			getJdbcTemplate().update(ADD_TICKET_TO_USER_ACCOUNT,ticketDetails.getTicketPostedBy());
		}
		else{
			Map<Long,Long> ticketEntry = new HashMap<Long, Long>();
			ticketEntry.put(ticketId, new Date().getTime());
			
			final String ADD_TICKET_DETAILS_IN_USER = "insert into user_ticket(email_id,tickets) values (?,?)";
			getJdbcTemplate().update(ADD_TICKET_DETAILS_IN_USER,
					new Object[]{ticketDetails.getTicketPostedBy(),ticketEntry},
					new int[]{Types.VARCHAR,Types.OTHER});
			
		}
		final String ADD_TICKET_DETAILS = "insert into ticket_details(ticket_id,ticket_subject,ticket_body,ticket_posted_by,ticket_posted_on,ticket_status) values (?,?,?,?,?,?) ";
		getJdbcTemplate().update(ADD_TICKET_DETAILS,
				new Object[]{
				ticketId,
				ticketDetails.getTicketSubject(),
				ticketDetails.getTicketBody(),
				ticketDetails.getTicketPostedBy(),
				new Date().getTime(),
				YTicketStringConstants.TICKET_STATUS_OPEN},
				new int[]{Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR}
				);
		flag = true;
		}
		catch(Exception ex)
		{
			logger.error(ex.getStackTrace().toString());
		}
		
		return flag;
	}

	private Long getTicketId() {
		Long ticketId = 1L;
		final String GET_TICKET_ID = "select ticket_id from ticket_counter";
		try{
			ticketId = getJdbcTemplate().queryForObject(GET_TICKET_ID, Long.class);
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			logger.error(emptyRS.getStackTrace().toString());
		}
		return ticketId;
	}
	
	Boolean incrementTicketId()
	{
		Boolean flag = false;
		final String INCREMENT_TICKET_ID = "update ticket_counter set ticket_id = ticket_id + 1 where master_value=1";
		try{
			getJdbcTemplate().update(INCREMENT_TICKET_ID);
			flag = true;
		}
		catch(Exception ex)
		{
			logger.error(ex.getStackTrace().toString());
		}
		return flag;
	}

}
