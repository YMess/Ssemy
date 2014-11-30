package com.ymess.yticket.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.cassandra.core.CassandraTemplate;

import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.ymess.yticket.dao.interfaces.IYTicketDAO;
import com.ymess.yticket.pojos.Ticket;

public class JDBCYTicketDAO implements IYTicketDAO{

	private CassandraTemplate cassandraTemplate;
	
	private Logger logger = Logger.getLogger(getClass());
	
	/***
	 * Gets all the Tickets Posted By User
	 * @param loggedInUserEmail
	 * @return List<Ticket>(tickets)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> getUserTickets(String loggedInUserEmail){
		List<Ticket> userTickets = new ArrayList<Ticket>();

		Set<Long> ticketIds = new HashSet<Long>();
		final String GET_USER_TICKETS = "select tickets from user_tickets where email_id='"+loggedInUserEmail+"'";
		try{
			ticketIds = cassandraTemplate.queryForObject(GET_USER_TICKETS,Set.class);
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			logger.error("Empty Result Set in GET_USER_TICKETS "+emptyRS.getStackTrace().toString());
		}
		
		StringBuilder ticketsSB = new StringBuilder();
		if(null != ticketIds && !ticketIds.isEmpty())
		{
			List<Long> ticketsList = new ArrayList<Long>(ticketIds);
			Collections.reverse(ticketsList);
			for (Long ticketId : ticketsList) {
				ticketsSB.append(ticketId).append(",");
			}
			String ticketIdsStr = "";
			
			if(ticketsSB.length() > 0)
				ticketIdsStr = ticketsSB.substring(0,ticketsSB.lastIndexOf(","));
		
			if(ticketIdsStr != null && ticketIdsStr.length() > 0)
			{
				final String GET_TICKET_DETAILS = "select * from ticket_details where ticket_id in ("+ticketIdsStr+")";
				try{
					userTickets = cassandraTemplate.select(GET_TICKET_DETAILS,Ticket.class);
				}
				catch(EmptyResultDataAccessException emptyRS)
				{
					logger.error("Empty Result Set in GET_TICKET_DETAILS " +emptyRS.getStackTrace().toString());
				}
			}
		}
		return userTickets == null ? new ArrayList<Ticket>() : userTickets ;
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
		
		final String GET_USER_TICKET_COUNT = "select count(*) from user_tickets where email_id=?";
		
		Select selectQuery = QueryBuilder.select().countAll().from("user_tickets");
		selectQuery.where(QueryBuilder.eq("email_id", "'"+ticketDetails.getTicketPostedBy()+"'"));
		
		
		Long ticketCount = cassandraTemplate.queryForObject(GET_USER_TICKET_COUNT,Long.class);
		
		try{
		if(ticketCount > 0){
			final String ADD_TICKET_TO_USER_ACCOUNT = "update user_tickets set tickets = tickets + {"+ticketId+"} where email_id='"+ticketDetails.getTicketPostedBy()+"'";
			//getJdbcTemplate().update(ADD_TICKET_TO_USER_ACCOUNT,ticketDetails.getTicketPostedBy());
			
			cassandraTemplate.execute(ADD_TICKET_TO_USER_ACCOUNT);
		}
		else{
			Set<Long> ticketEntry = new HashSet<Long>();
			ticketEntry.add(ticketId);
			
			final String ADD_TICKET_DETAILS_IN_USER = "insert into user_tickets(email_id,tickets) values (?,?)";
			
			Insert insert = QueryBuilder.insertInto("user_tickets").values(
					new String[]{"email_id","tickets"},
					new Object[]{ticketDetails.getTicketPostedBy(),"{"+ticketDetails.getTicketId()+"}"});
			
			cassandraTemplate.execute(insert);
			
		}
		/*
		final String ADD_TICKET_DETAILS = "insert into ticket_details(ticket_id,ticket_subject,ticket_body,ticket_posted_by,ticket_posted_on,ticket_status) values (?,?,?,?,?,?) ";
		getJdbcTemplate().update(ADD_TICKET_DETAILS,
				new Object[]{
				ticketId,
				ticketDetails.getTicketSubject(),
				ticketDetails.getTicketBody(),
				ticketDetails.getTicketPostedBy(),
				new Date(),
				YTicketStringConstants.TICKET_STATUS_OPEN},
				new int[]{Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR}
				);
		
		if(ticketDetails.getIsAttachmentAttached() != null && ticketDetails.getIsAttachmentAttached())
		{
			
			for (MultipartFile file : ticketDetails.getAttachments()) {
				
				final String INSERT_TICKET_ATTACHMENTS = "insert into ticket_attachments (ticket_id,file_name,file_data,file_mime_type) values (?,?,?,?)";
				getJdbcTemplate().update(INSERT_TICKET_ATTACHMENTS,
						new Object[]{ticketId,file.getOriginalFilename(),file.getBytes(),file.getContentType()},
						new int[]{Types.BIGINT,Types.VARCHAR,Types.BINARY,Types.VARCHAR});
			}
		}
		*/
		
		flag = true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error(ex.getStackTrace().toString());
		}
		
		return flag;
	}

	private Long getTicketId() {
		Long ticketId = 1L;
		/*final String GET_TICKET_ID = "select ticket_id from ticket_counter";
		try{
			ticketId = getJdbcTemplate().queryForObject(GET_TICKET_ID, Long.class);
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			logger.error(emptyRS.getStackTrace().toString());
		}*/
		return ticketId;
	}
	
	Boolean incrementTicketId()
	{
		Boolean flag = false;
		/*final String INCREMENT_TICKET_ID = "update ticket_counter set ticket_id = ticket_id + 1 where master_value=1";
		try{
			getJdbcTemplate().update(INCREMENT_TICKET_ID);
			flag = true;
		}
		catch(Exception ex)
		{
			logger.error(ex.getStackTrace().toString());
		}*/
		return flag;
	}

	public CassandraTemplate getCassandraTemplate() {
		return cassandraTemplate;
	}

	public void setCassandraTemplate(CassandraTemplate cassandraTemplate) {
		this.cassandraTemplate = cassandraTemplate;
	}

}
