package com.ymess.ymail.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.web.multipart.MultipartFile;

import com.ymess.pojos.Question;
import com.ymess.pojos.User;
import com.ymess.util.MessageConstants;
import com.ymess.ymail.dao.interfaces.YMailDao;
import com.ymess.ymail.pojos.Mail;
import com.ymess.ymail.util.YMailMailStatus;

public class JdbcYMailDao extends JdbcDaoSupport implements YMailDao {

	
	private static final String GET_MAIL_IDS = "select mail_id from mail_details";
	
	
	/**
	 * Send Email to respected users
	 * @author RVishwakarma
	 * @param mail
	 */
	@Override
	public void sendMail(Mail mail) {
		
		long lastInsertedMailId = Long.parseLong(getLastInsertedMailId());
		long newMailId = lastInsertedMailId + 1;
		
		Date currentTime = new Date();
		mail.setMailId(newMailId);
		
		User userDetails = getUserDetailsByEmailId(mail.getMailFrom());
		
		List<MultipartFile> attachments = mail.getMailAttachment();
		
		
		if(mail.getIsAttachmentAttached())
		{
			
	    	String SEND_MAIL_WITH_ATTACHMENTS = "insert into mail_details(mail_id,mail_from,mail_to,mail_cc,mail_bcc,mail_subject,mail_body,is_mail_attachment_attached,mail_status,mail_read,mail_sent_timestamp,user_first_name,user_last_name) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().update(SEND_MAIL_WITH_ATTACHMENTS,
					new Object[]{
					newMailId,
					mail.getMailFrom(),
					mail.getMailTo(),
					mail.getMailCC(),
					mail.getMailBCC(),
					mail.getMailSubject(),
					mail.getMailBody(),
					true,
					YMailMailStatus.MAIL_SENT,
					false,
					currentTime,
					userDetails.getFirstName(),
					userDetails.getLastName()},
					new int[]{
					Types.BIGINT,
					Types.VARCHAR,
					Types.ARRAY,
					Types.ARRAY,
					Types.ARRAY,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.BOOLEAN,
					Types.VARCHAR,
					Types.BOOLEAN,
					Types.TIMESTAMP,
					Types.VARCHAR,
					Types.VARCHAR
					});	
			
			for (MultipartFile attachmentFile : attachments) {
				try {
					String SEND_MAIL_ATTACHMENTS = "insert into mail_attachments(mail_id,mail_file_name,mail_attachment) values(?,?,?)";
					getJdbcTemplate().update(SEND_MAIL_ATTACHMENTS,
							new Object[]{
							newMailId,
                            attachmentFile.getName(),
                            attachmentFile.getBytes()},
							new int[]{
							Types.BIGINT,
							Types.VARCHAR,
							Types.BINARY,
							});	
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else	
		{
			String SEND_MAIL_WITHOUT_ATTACHMENTS = "insert into mail_details(mail_id,mail_from,mail_to,mail_cc,mail_bcc,mail_subject,mail_body,mail_status,mail_sent_timestamp,user_first_name,user_last_name) values(?,?,?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().update(SEND_MAIL_WITHOUT_ATTACHMENTS,
					new Object[]{newMailId,mail.getMailFrom(),mail.getMailTo(),mail.getMailCC(),mail.getMailBCC(),mail.getMailSubject(),mail.getMailBody(),YMailMailStatus.MAIL_SENT,currentTime,userDetails.getFirstName(),userDetails.getLastName()},
					new int[]{Types.BIGINT,Types.VARCHAR,Types.ARRAY,Types.ARRAY,Types.ARRAY,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR}
					);
		}
		
		String CHECK_IF_USER_EXISTS = "select count(1) from mail_user_mapper where user_email_id=?";
		
		for (String mailIdTo : mail.getMailTo()) {
			long userCountTo = getJdbcTemplate().queryForLong(CHECK_IF_USER_EXISTS,mailIdTo);
			
			if(userCountTo != 0)
			{
				String UPDATE_EXISTING_USER_INBOX = "update mail_user_mapper set mail_inbox=mail_inbox + {"+newMailId+"} where user_email_id=?";
				try{
					getJdbcTemplate().update(UPDATE_EXISTING_USER_INBOX,mailIdTo);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
			else	
			{
				String INSERT_NEW_USER_INBOX = "insert into mail_user_mapper (user_email_id,mail_inbox ,user_first_name,user_last_name) values ('"+mailIdTo+"',"+ "{"+ newMailId +"},'"+userDetails.getFirstName()+"','"+userDetails.getLastName()+"')";
				try{
					getJdbcTemplate().update(INSERT_NEW_USER_INBOX);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
		}
		
		
		for (String mailIdCC : mail.getMailCC()) {
			long userCountCC = getJdbcTemplate().queryForLong(CHECK_IF_USER_EXISTS,mailIdCC);
			
			if(userCountCC != 0)
			{
				String UPDATE_EXISTING_USER_INBOX = "update mail_user_mapper set mail_inbox=mail_inbox + {"+newMailId+"} where user_email_id=?";
				try{
					getJdbcTemplate().update(UPDATE_EXISTING_USER_INBOX,mailIdCC);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
			else	
			{
				String INSERT_NEW_USER_INBOX = "insert into mail_user_mapper (user_email_id,mail_inbox ,user_first_name,user_last_name) values ('"+mailIdCC+"',"+ "{"+ newMailId +"},'"+userDetails.getFirstName()+"','"+userDetails.getLastName()+"')";
				try{
					getJdbcTemplate().update(INSERT_NEW_USER_INBOX);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
		}
		
		for (String mailIdBCC : mail.getMailBCC()) {
			long userCountBCC = getJdbcTemplate().queryForLong(CHECK_IF_USER_EXISTS,mailIdBCC);
			
			if(userCountBCC != 0)
			{
				String UPDATE_EXISTING_USER_INBOX = "update mail_user_mapper set mail_inbox=mail_inbox + {"+newMailId+"} where user_email_id=?";
				try{
					getJdbcTemplate().update(UPDATE_EXISTING_USER_INBOX,mailIdBCC);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
			else	
			{
				String INSERT_NEW_USER_INBOX = "insert into mail_user_mapper (user_email_id,mail_inbox ,user_first_name,user_last_name) values ('"+mailIdBCC+"',"+ "{"+ newMailId +"},'"+userDetails.getFirstName()+"','"+userDetails.getLastName()+"')";
				try{
					getJdbcTemplate().update(INSERT_NEW_USER_INBOX);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
		}
		
		

		long userCount = getJdbcTemplate().queryForLong(CHECK_IF_USER_EXISTS,mail.getMailFrom());
		
		if(userCount != 0)
		{
			String UPDATE_EXISTING_USER = "update mail_user_mapper set mail_sent=mail_sent + {"+newMailId+"} where user_email_id=?";
			try{
				getJdbcTemplate().update(UPDATE_EXISTING_USER,mail.getMailFrom());
			}
			catch(Exception ex)
			{
				logger.error(ex.getStackTrace());
			}
		}
		else	
		{
			String INSERT_NEW_USER = "insert into mail_user_mapper (user_email_id,mail_sent ,user_first_name,user_last_name) values ('"+mail.getMailFrom()+"',"+ "{"+ newMailId +"},'"+userDetails.getFirstName()+"','"+userDetails.getLastName()+"')";
			try{
				getJdbcTemplate().update(INSERT_NEW_USER);
			}
			catch(Exception ex)
			{
				logger.error(ex.getStackTrace());
			}
		}
	}
	
	/**
	 * Gets the User Details based on Email Id
	 * @author RVishwakarma
	 * @param mailFrom
	 * @return User(userDetails)
	 */
	private User getUserDetailsByEmailId(String mailFrom) {
		
		String GET_USER_DETAILS = "select first_name,last_name from users_data where email_id=?";
		User user = new User();
		try{
		user = getJdbcTemplate().queryForObject(GET_USER_DETAILS, new UserDetailsMapper(),mailFrom);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return user;
	}

	private class UserDetailsMapper implements ParameterizedRowMapper<User>
	{
		@Override
		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User();
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			
			return user;
		}
		
	}

	/**
	 * Retrieves the last inserted Mail Id.
	 * @author RVishwakarma
	 * @return lastInsertedMailId(String)
	 */
	private String getLastInsertedMailId() {
		
		long maxMailId = 0;
		
		List<String> mailIds = getJdbcTemplate().queryForList(GET_MAIL_IDS,String.class);
			try
			{
				if(!mailIds.isEmpty())
					maxMailId = Long.parseLong(Collections.max(mailIds));
			}
			catch(EmptyResultDataAccessException emptyRS)
			{
				logger.error(emptyRS.getLocalizedMessage());
			}
		return String.valueOf(maxMailId);
	}

	@Override
	public List<Mail> getInboxMails(String userEmailId) {
		
	    List<Mail> inboxMails = new ArrayList<Mail>();
		
		try {
			String GET_INBOX_MAIL_IDS = "select mail_inbox from mail_user_mapper where user_email_id='"+userEmailId+"'";
			Set<Long> inboxMailIds = getJdbcTemplate().queryForObject(GET_INBOX_MAIL_IDS,Set.class);
			
			
			StringBuilder inboxMailIdsSB = new StringBuilder();
			for (Long inboxMailId : inboxMailIds) {
				inboxMailIdsSB.append(inboxMailId).append(",");
			}
			String inboxMailIdsStr = "";
			if(inboxMailIdsSB.length() > 0)
				inboxMailIdsStr = inboxMailIdsSB.substring(0,inboxMailIdsSB.lastIndexOf(","));
				
			
			String GET_INBOX_MAILS = "Select * from mail_details where mail_id in ("+inboxMailIdsStr+")";
			inboxMails = getJdbcTemplate().query(GET_INBOX_MAILS,new MailDetailsMapper());
		} 
		catch (EmptyResultDataAccessException  emptyEx) {
			logger.warn(MessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		return inboxMails;
	}

	/**
	 * 
	 * This class is used to Map the ResultSet values(mail) to Domain/Value Objects(Mail)
	 *
	 */
	private static class MailDetailsMapper
			implements
				ParameterizedRowMapper<Mail> {
		@Override
		public Mail mapRow(ResultSet rs, int rowCount) throws SQLException {
			Mail mail = new Mail();
			mail.setMailId(rs.getLong("mail_id"));
			mail.setMailFrom(rs.getString("mail_from"));
			mail.setMailCC((Set<String>) rs.getObject("mail_cc"));
			mail.setMailBCC((Set<String>) rs.getObject("mail_bcc"));
			mail.setMailSubject(rs.getString("mail_subject"));
			mail.setMailBody(rs.getString("mail_body"));
			
			/*if(rs.getDate("mail_saved_timestamp") != null)
				mail.setMailSavedTimestamp(rs.getDate("mail_saved_timestamp "));
			
			if(rs.getDate("mail_sent_timestamp") != null)	
				mail.setMailSentTimestamp(rs.getDate("mail_sent_timestamp "));*/
			
			//mail.setIsAttachmentAttached(rs.getBoolean("is_mail_attachment_attached "));
			//mail.setIsPictureAttached(rs.getBoolean("is_mail_picture_attached "));
			mail.setMailStatus(rs.getString("mail_status"));
			mail.setMailRead(rs.getBoolean("mail_read"));
			mail.setUserFirstName(rs.getString("user_first_name"));
			mail.setUserLastName(rs.getString("user_last_name"));
			
			return mail;
		}
	}

	@Override
	public List<Mail> getSentMails(String userEmailId) {
		
		List<Mail> sentMails = new ArrayList<Mail>();
		try {
			String GET_SENT_MAIL_IDS = "select mail_sent from mail_user_mapper where user_email_id='"+userEmailId+"'";
			Set<Long> sentMailIds = getJdbcTemplate().queryForObject(GET_SENT_MAIL_IDS,Set.class);
			
			
			StringBuilder sentMailIdsSB = new StringBuilder();
			for (Long sentMailId : sentMailIds) {
				sentMailIdsSB.append(sentMailId).append(",");
			}
			String sentMailIdsStr = "";
			if(sentMailIdsSB.length() > 0)
				sentMailIdsStr = sentMailIdsSB.substring(0,sentMailIdsSB.lastIndexOf(","));
				
			
			String GET_SENT_MAILS = "Select * from mail_details where mail_id in ("+sentMailIdsStr+")";
			sentMails = getJdbcTemplate().query(GET_SENT_MAILS,new MailDetailsMapper());
		} 
		catch (EmptyResultDataAccessException  emptyEx) {
			logger.warn(MessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		return sentMails;
	}
	
}
