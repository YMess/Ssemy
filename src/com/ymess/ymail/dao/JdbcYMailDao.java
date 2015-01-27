package com.ymess.ymail.dao;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.cassandra.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.exceptions.DriverException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;
import com.ymess.pojos.User;
import com.ymess.util.YMessCommonUtility;
import com.ymess.util.YMessMessageConstants;
import com.ymess.ymail.dao.interfaces.YMailDao;
import com.ymess.ymail.pojos.Mail;
import com.ymess.ymail.util.YMailCommonUtility;
import com.ymess.ymail.util.YMailLoggerConstants;
import com.ymess.ymail.util.YMailMailStatus;

public class JdbcYMailDao  implements YMailDao {

	
	Logger logger = Logger.getLogger(getClass());
	
	CassandraTemplate cassandraTemplate;
	
	
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
		
		
		if(mail.getIsAttachmentAttached() != null && mail.getIsAttachmentAttached())
		{
			
	    /*	String SEND_MAIL_WITH_ATTACHMENTS = "insert into mail(mail_id,mail_from,mail_to,mail_cc,mail_bcc,mail_subject,mail_body,is_mail_attachment_attached,mail_status,mail_sent_timestamp,user_first_name,user_last_name) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			cassandraTemplate.update(SEND_MAIL_WITH_ATTACHMENTS,
				,
					false,
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
					});	*/
			Insert insertIntoMail = YMessCommonUtility.getFormattedInsertQuery("mail_details", "mail_id,mail_from,mail_to,mail_cc,"
					+ "mail_bcc,mail_subject,mail_body,is_mail_attachment_attached,"
					+ "mail_status,mail_sent_timestamp,user_first_name,user_last_name",
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
					currentTime,
					userDetails.getFirstName(),
					userDetails.getLastName()
			});
			
			cassandraTemplate.execute(insertIntoMail);
			
			
			for (MultipartFile attachmentFile : attachments) {
				
				try {
					ByteBuffer byteBuffer = null;
					
					if(null != attachmentFile.getBytes() )
					{
						byteBuffer = ByteBuffer.wrap(attachmentFile.getBytes());
					}
					Insert insertAttachments = YMessCommonUtility.getFormattedInsertQuery("mail_attachments", "mail_id,mail_file_name,mail_attachment,attachment_mime_type", new Object[]{
							newMailId,
                            attachmentFile.getOriginalFilename(),
                            byteBuffer,
                            attachmentFile.getContentType()
					});
					
					cassandraTemplate.execute(insertAttachments);
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else	
		{
			//String SEND_MAIL_WITHOUT_ATTACHMENTS = "insert into mail_details(mail_id,mail_from,mail_to,mail_cc,mail_bcc,mail_subject,mail_body,mail_status,mail_sent_timestamp,user_first_name,user_last_name) values(?,?,?,?,?,?,?,?,?,?,?)";
			Insert insertMailWithoutAttachments = YMessCommonUtility.getFormattedInsertQuery("mail_details", "mail_id,mail_from,mail_to,mail_cc"
					+ ",mail_bcc,mail_subject,mail_body,mail_status,"
					+ "mail_sent_timestamp,user_first_name,user_last_name", 
					new Object[]{newMailId,mail.getMailFrom(),mail.getMailTo(),mail.getMailCC(),mail.getMailBCC(),mail.getMailSubject(),mail.getMailBody(),YMailMailStatus.MAIL_SENT,currentTime,userDetails.getFirstName(),userDetails.getLastName()});
			
			
			cassandraTemplate.execute(insertMailWithoutAttachments);
			
			/*	cassandraTemplate.update(SEND_MAIL_WITHOUT_ATTACHMENTS,
					new Object[]{newMailId,mail.getMailFrom(),mail.getMailTo(),mail.getMailCC(),mail.getMailBCC(),mail.getMailSubject(),mail.getMailBody(),YMailMailStatus.MAIL_SENT,currentTime,userDetails.getFirstName(),userDetails.getLastName()},
					new int[]{Types.BIGINT,Types.VARCHAR,Types.ARRAY,Types.ARRAY,Types.ARRAY,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR}
					);*/
		}
		
		//map TO in MailUserMapper
		for (String mailIdTo : mail.getMailTo()) {
		
			String CHECK_IF_USER_EXISTS = "select count(1) from mail_user_mapper where user_email_id='"+mailIdTo+"'";
			Long userCountTo = cassandraTemplate.queryForObject(CHECK_IF_USER_EXISTS,Long.class);
			
			if(userCountTo != 0)
			{
				String UPDATE_EXISTING_USER_INBOX = "update mail_user_mapper set mail_inbox=mail_inbox + {"+newMailId+"} where user_email_id='"+mailIdTo+"'";
				try{
					cassandraTemplate.execute(UPDATE_EXISTING_USER_INBOX);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
			else	
			{
				String INSERT_NEW_USER_INBOX = "insert into mail_user_mapper (user_email_id,mail_inbox ,user_first_name,user_last_name) values ('"+mailIdTo+"',"+ "{"+ newMailId +"},'"+userDetails.getFirstName()+"','"+userDetails.getLastName()+"')";
				try {
					cassandraTemplate.execute(INSERT_NEW_USER_INBOX);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
		}
		
		//map CC in MailUserMapper
		for (String mailIdCC : mail.getMailCC()) {
			String CHECK_IF_USER_EXISTS = "select count(1) from mail_user_mapper where user_email_id='"+mailIdCC+"'";
			long userCountCC = cassandraTemplate.queryForObject(CHECK_IF_USER_EXISTS,Long.class);
			
			if(userCountCC != 0)
			{
				String UPDATE_EXISTING_USER_INBOX = "update mail_user_mapper set mail_inbox=mail_inbox + {"+newMailId+"} where user_email_id='"+mailIdCC+"'";
				try{
					cassandraTemplate.execute(UPDATE_EXISTING_USER_INBOX);
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
					cassandraTemplate.execute(INSERT_NEW_USER_INBOX);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
		}
		
		//map BCC in MailUserMapper
		for (String mailIdBCC : mail.getMailBCC()) {
			String CHECK_IF_USER_EXISTS = "select count(1) from mail_user_mapper where user_email_id='"+mailIdBCC+"'";
			long userCountBCC = cassandraTemplate.queryForObject(CHECK_IF_USER_EXISTS,Long.class);
			
			if(userCountBCC != 0)
			{
				String UPDATE_EXISTING_USER_INBOX = "update mail_user_mapper set mail_inbox=mail_inbox + {"+newMailId+"} where user_email_id='"+mailIdBCC+"'";
				try{
					cassandraTemplate.execute(UPDATE_EXISTING_USER_INBOX);
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
					cassandraTemplate.execute(INSERT_NEW_USER_INBOX);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
		}
		
		//map SENT in MailUserMapper
		String CHECK_IF_USER_EXISTS = "select count(1) from mail_user_mapper where user_email_id='"+mail.getMailFrom()+"'";
		long userCount = cassandraTemplate.queryForObject(CHECK_IF_USER_EXISTS,Long.class);
		
		if(userCount != 0)
		{
			String UPDATE_EXISTING_USER = "update mail_user_mapper set mail_sent=mail_sent + {"+newMailId+"} where user_email_id='"+mail.getMailFrom()+"'";
			try{
				cassandraTemplate.execute(UPDATE_EXISTING_USER);
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
				cassandraTemplate.execute(INSERT_NEW_USER);
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
		
		String GET_USER_DETAILS = "select first_name,last_name from users_data where email_id='"+mailFrom+"'";
		User user = new User();
		try{
		user = cassandraTemplate.queryForObject(GET_USER_DETAILS, new UserDetailsMapper());
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return user;
	}

	private class UserDetailsMapper implements RowMapper<User>
	{
		@Override
		public User mapRow(Row rs, int arg1) throws DriverException {
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
		
		List<Long> mailIds = cassandraTemplate.queryForList(GET_MAIL_IDS,Long.class);
			try
			{
				if(!mailIds.isEmpty())
				{
					maxMailId = (Collections.max(mailIds));
				}
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
	    Set<Long> inboxMailIds = new HashSet<Long>();
		
		try {
			String GET_INBOX_MAIL_IDS = "select mail_inbox from mail_user_mapper where user_email_id='"+userEmailId+"'";
			inboxMailIds = cassandraTemplate.queryForObject(GET_INBOX_MAIL_IDS,Set.class);
			
			
			StringBuilder inboxMailIdsSB = new StringBuilder();
			
			if(null != inboxMailIds && inboxMailIds.size() > 0)
			{
				for (Long inboxMailId : inboxMailIds) {
					inboxMailIdsSB.append(inboxMailId).append(",");
				}
				String inboxMailIdsStr = "";
				if(inboxMailIdsSB.length() > 0)
					inboxMailIdsStr = inboxMailIdsSB.substring(0,inboxMailIdsSB.lastIndexOf(","));
					
				String GET_INBOX_MAILS = "Select * from mail_details where mail_id in ("+inboxMailIdsStr+")";
				inboxMails = cassandraTemplate.query(GET_INBOX_MAILS,new MailDetailsMapper());
			}
		} 
		catch (EmptyResultDataAccessException  | NullPointerException emptyEx) {
			logger.warn(YMessMessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		return inboxMails== null ? new ArrayList<Mail>(): inboxMails;
	}

	public CassandraTemplate getCassandraTemplate() {
		return cassandraTemplate;
	}

	public void setCassandraTemplate(CassandraTemplate cassandraTemplate) {
		this.cassandraTemplate = cassandraTemplate;
	}
	
	/**
	 * 
	 * This class is used to Map the ResultSet values(mail) to Domain/Value Objects(Mail)
	 *
	 */
	private class MailDetailsMapper implements RowMapper<Mail> {
		@Override
		public Mail mapRow(Row rs, int arg1) throws DriverException {
			Mail mail = new Mail();
			mail.setMailId(rs.getLong("mail_id"));
			mail.setMailFrom(rs.getString("mail_from"));
			mail.setMailCC((Set<String>) rs.getSet("mail_cc",String.class));
			mail.setMailBCC((Set<String>) rs.getSet("mail_bcc",String.class));
			mail.setMailSubject(rs.getString("mail_subject"));
			mail.setMailBody(rs.getString("mail_body"));
			
			if(rs.getDate("mail_saved_timestamp") != null)
				mail.setMailSavedTimestamp(rs.getDate("mail_saved_timestamp"));
			
			if(rs.getDate("mail_sent_timestamp") != null)	
				mail.setMailSentTimestamp(rs.getDate("mail_sent_timestamp"));
			
			mail.setIsAttachmentAttached(rs.getBool("is_mail_attachment_attached"));
			mail.setIsPictureAttached(rs.getBool("is_mail_picture_attached"));
			mail.setMailStatus(rs.getString("mail_status"));
			mail.setMailRead(rs.getBool("mail_read"));
			mail.setUserFirstName(rs.getString("user_first_name"));
			mail.setUserLastName(rs.getString("user_last_name"));
			
			return mail;
		}
	}

	@Override
	public List<Mail> getSentMails(String userEmailId) {
		Set<Long> sentMailIds = new HashSet<Long>();
		List<Mail> sentMails = new ArrayList<Mail>();
		
		try {
			String GET_SENT_MAIL_IDS = "select mail_sent from mail_user_mapper where user_email_id='"+userEmailId+"'";
			sentMailIds = cassandraTemplate.queryForObject(GET_SENT_MAIL_IDS,Set.class);
			
			if(null != sentMailIds && sentMailIds.size() > 0)
			{
				String sentMailIdsStr = "";
				sentMailIdsStr = YMailCommonUtility.getCassandraInQuery(sentMailIds);
				
				sentMails = getMailDetailsByMailIds(sentMailIdsStr);
			}
		} 
		catch (Exception emptyEx ) {
			sentMailIds = new HashSet<Long>();
			logger.error(YMessMessageConstants.EMPTY_RESULT_SET);
		}
		return sentMails;
	}

	
	
	private List<Mail> getMailDetailsByMailIds(String mailIds)
	{
		List<Mail> mails = new ArrayList<Mail>();
		String GET_SENT_MAILS = "Select * from mail_details where mail_id in ("+mailIds+")";
		try {
			mails = cassandraTemplate.query(GET_SENT_MAILS,new MailDetailsMapper());
		}
		catch(NullPointerException | IllegalArgumentException ex){
			mails = new ArrayList<Mail>();
		}
		return mails == null ? new ArrayList<Mail>() : mails;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Mail> getImportantMails(String userEmailId) {
		
		Set<Long> importantMailIds = new HashSet<Long>();
		List<Mail> importantMails = new  ArrayList<Mail>();
		String importantMailIdsStr = "";
		
		Select selectImportantMails = QueryBuilder.select("mail_starred").from("mail_user_mapper");
		selectImportantMails.where(QueryBuilder.eq("user_email_id", userEmailId));
		
		try {
			importantMailIds =  cassandraTemplate.queryForObject(selectImportantMails, Set.class);
		} catch (NullPointerException | IllegalArgumentException e) {
			importantMailIds = new HashSet<Long>();
		}
		
		if(null != importantMailIds){
			importantMailIdsStr = YMailCommonUtility.getCassandraInQuery(importantMailIds);
		}
	
		if(null != importantMailIdsStr && importantMailIdsStr.trim().length() > 0){
			importantMails = getMailDetailsByMailIds(importantMailIdsStr);
		}
		return importantMails;
	}


	@Override
	public void deleteMails(Long[] deleteMailIds,String userEmailId) {
		
			Update deleteMails = QueryBuilder.update("mail_user_mapper");
			deleteMails.with(QueryBuilder.removeAll("mail_inbox", new HashSet<Long>(Arrays.asList(deleteMailIds))));
			
			deleteMails.where(QueryBuilder.eq("user_email_id", userEmailId));
			
			Update trashMails = QueryBuilder.update("mail_user_mapper");
			trashMails.with(QueryBuilder.appendAll("mail_trash",Arrays.asList(deleteMailIds)));
			
			trashMails.where(QueryBuilder.eq("user_email_id", userEmailId));
			try {
				cassandraTemplate.execute(deleteMails);
				cassandraTemplate.execute(trashMails);
			}
			catch(IllegalArgumentException | NoHostAvailableException ex){
				logger.error(YMailLoggerConstants.NO_HOST_ERROR + "in deleteMails");
			}
	}

    /**
     * @author RVishwakarma
     * @param userEmailId
     * @return List<Mail>
     */
	@Override
	public List<Mail> getTrashMails(String userEmailId) {
		Set<Long> trashMailIds = new HashSet<Long>();
		List<Mail> trashMails = new  ArrayList<Mail>();
		String trashMailIdsStr = "";
		
		Select selectTrashMails = QueryBuilder.select("mail_starred").from("mail_user_mapper");
		selectTrashMails.where(QueryBuilder.eq("user_email_id", userEmailId));
		
		try {
			trashMailIds =  cassandraTemplate.queryForObject(selectTrashMails, Set.class);
		} catch (NullPointerException | IllegalArgumentException e) {
			trashMailIds = new HashSet<Long>();
		}
		
		if(null != trashMailIds){
			trashMailIdsStr = YMailCommonUtility.getCassandraInQuery(trashMailIds);
		}
	
		if(null != trashMailIdsStr && trashMailIdsStr.trim().length() > 0){
			trashMails = getMailDetailsByMailIds(trashMailIdsStr);
		}
		return trashMails;
	}


	@Override
	public Mail getMailDetails(String decodedMailId) {
		
		Mail mail = new Mail();
		Long mailId = Long.valueOf(decodedMailId);
		
		Select select = QueryBuilder.select().from("mail_details");
		select.where(QueryBuilder.eq("mail_id", mailId));

		try {
			mail = cassandraTemplate.queryForObject( select,new MailDetailsMapper());
			
		} catch (IllegalArgumentException | NullPointerException ex ) {
			 mail = new Mail();
		}
		return mail;
	}


	@Override
	public void saveMail(Mail mail) {
		long lastInsertedMailId = Long.parseLong(getLastInsertedMailId());
		long newMailId = lastInsertedMailId + 1;
		
		Date currentTime = new Date();
		mail.setMailId(newMailId);
		
		User userDetails = getUserDetailsByEmailId(mail.getMailFrom());
		
		List<MultipartFile> attachments = mail.getMailAttachment();
		
		if(mail.getIsAttachmentAttached() != null && mail.getIsAttachmentAttached())
		{
			Insert insertIntoMail = YMessCommonUtility.getFormattedInsertQuery("mail_details", "mail_id,mail_from,mail_to,mail_cc,"
					+ "mail_bcc,mail_subject,mail_body,is_mail_attachment_attached,"
					+ "mail_status,mail_sent_timestamp,user_first_name,user_last_name",
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
					currentTime,
					userDetails.getFirstName(),
					userDetails.getLastName()
			});
			cassandraTemplate.execute(insertIntoMail);
			
			for (MultipartFile attachmentFile : attachments) {
				try {
					ByteBuffer byteBuffer = null;
					
					if(null != attachmentFile.getBytes() )
					{
						byteBuffer = ByteBuffer.wrap(attachmentFile.getBytes());
					}
					Insert insertAttachments = YMessCommonUtility.getFormattedInsertQuery("mail_attachments", "mail_id,mail_file_name,mail_attachment,attachment_mime_type", new Object[]{
							newMailId,
                            attachmentFile.getOriginalFilename(),
                            byteBuffer,
                            attachmentFile.getContentType()
					});
					cassandraTemplate.execute(insertAttachments);
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else	
		{
			Insert insertMailWithoutAttachments = YMessCommonUtility.getFormattedInsertQuery("mail_details", "mail_id,mail_from,mail_to,mail_cc"
					+ ",mail_bcc,mail_subject,mail_body,mail_status,"
					+ "mail_sent_timestamp,user_first_name,user_last_name", 
					new Object[]{newMailId,mail.getMailFrom(),mail.getMailTo(),mail.getMailCC(),mail.getMailBCC(),mail.getMailSubject(),mail.getMailBody(),YMailMailStatus.MAIL_SENT,currentTime,userDetails.getFirstName(),userDetails.getLastName()});
			
			
			cassandraTemplate.execute(insertMailWithoutAttachments);
		}
		
		
			String CHECK_IF_USER_EXISTS = "select count(1) from mail_user_mapper where user_email_id='"+newMailId+"'";
			Long userCountTo = cassandraTemplate.queryForObject(CHECK_IF_USER_EXISTS,Long.class);

			if(userCountTo != 0)
			{
				String UPDATE_EXISTING_USER_DRAFT = "update mail_user_mapper set mail_drafts=mail_drafts + {"+newMailId+"} where user_email_id='"+mail.getMailFrom()+"'";
				try{
					cassandraTemplate.execute(UPDATE_EXISTING_USER_DRAFT);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
			else	
			{
				String INSERT_NEW_USER_DRAFT = "insert into mail_user_mapper (user_email_id,mail_drafts ,user_first_name,user_last_name) values ('"+mail.getMailFrom()+"',"+ "{"+ newMailId +"},'"+userDetails.getFirstName()+"','"+userDetails.getLastName()+"')";
				try {
					cassandraTemplate.execute(INSERT_NEW_USER_DRAFT);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}	
	}


	@Override
	public List<Mail> getDraftMails(String userEmailId) {
		List<Mail> draftMails = new ArrayList<Mail>();
	    Set<Long> draftMailIds = new HashSet<Long>();
		
		try {
			String GET_DRAFT_MAIL_IDS = "select mail_drafts from mail_user_mapper where user_email_id='"+userEmailId+"'";
			draftMailIds = cassandraTemplate.queryForObject(GET_DRAFT_MAIL_IDS,Set.class);
			
			
			StringBuilder draftMailIdsSB = new StringBuilder();
			
			if(null != draftMailIds && draftMailIds.size() > 0)
			{
				for (Long draftMailId : draftMailIds) {
					draftMailIdsSB.append(draftMailId).append(",");
				}
				String draftMailIdsStr = "";
				if(draftMailIdsSB.length() > 0)
					draftMailIdsStr = draftMailIdsSB.substring(0,draftMailIdsSB.lastIndexOf(","));
					
				String GET_DRAFT_MAILS = "Select * from mail_details where mail_id in ("+draftMailIdsStr+")";
				draftMails = cassandraTemplate.query(GET_DRAFT_MAILS,new MailDetailsMapper());
			}
		} 
		catch (EmptyResultDataAccessException  | NullPointerException emptyEx) {
			logger.warn(YMessMessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		return draftMails== null ? new ArrayList<Mail>(): draftMails;
	}
}
