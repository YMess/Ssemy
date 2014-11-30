package com.ymess.ymail.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.cassandra.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.exceptions.DriverException;
import com.datastax.driver.core.querybuilder.Insert;
import com.ymess.pojos.User;
import com.ymess.util.MessageConstants;
import com.ymess.util.YMessCommonUtility;
import com.ymess.ymail.dao.interfaces.YMailDao;
import com.ymess.ymail.pojos.Mail;
import com.ymess.ymail.util.YMailMailStatus;

public class JdbcYMailDao  implements YMailDao {

	
	Logger logger = Logger.getLogger(getClass());
	
	CassandraTemplate cassandraTemplate;
	
	
	private static final String GET_MAIL_IDS = "select mail_id from mail";
	private static final String GET_INBOX_MAILS = "select mail_id,mail_from,mail_subject,mail_body,_mail_sent_timestamp where mail_id in ";
	
	
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
			
	    /*	String SEND_MAIL_WITH_ATTACHMENTS = "insert into mail(mail_id,mail_from,mail_to,mail_cc,mail_bcc,mail_subject,mail_body,is_mail_attachment_attached,mail_status,mail_sent_timestamp,user_first_name,user_last_name) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().update(SEND_MAIL_WITH_ATTACHMENTS,
				,
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
					Types.TIMESTAMP,
					Types.VARCHAR,
					Types.VARCHAR
					});	*/
			Insert insertIntoMail = YMessCommonUtility.getFormattedInsertQuery("mail", "mail_id,mail_from,mail_to,mail_cc,"
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
					userDetails.getLastName()});
			
			cassandraTemplate.insert(insertIntoMail);
			
			
			for (MultipartFile attachmentFile : attachments) {
				
				try {
					
					Insert insertAttachments = YMessCommonUtility.getFormattedInsertQuery("mail_attachments", "mail_id,mail_file_name,mail_attachment", new Object[]{
							newMailId,
                            attachmentFile.getOriginalFilename(),
                            attachmentFile.getBytes()});
					
					cassandraTemplate.insert(insertAttachments);
					
					/*String SEND_MAIL_ATTACHMENTS = "insert into mail_attachments(mail_id,mail_file_name,mail_attachment) values(?,?,?)";
					getJdbcTemplate().update(SEND_MAIL_ATTACHMENTS,
							new Object[]{
							newMailId,
                            attachmentFile.getOriginalFilename(),
                            attachmentFile.getBytes()},
							new int[]{
							Types.BIGINT,
							Types.VARCHAR,
							Types.BINARY,
							});	*/
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else	
		{
			String SEND_MAIL_WITHOUT_ATTACHMENTS = "insert into mail(mail_id,mail_from,mail_to,mail_cc,mail_bcc,mail_subject,mail_body,mail_status,mail_sent_timestamp,user_first_name,user_last_name) values(?,?,?,?,?,?,?,?,?,?,?)";
			Insert insertMailWithoutAttachments = YMessCommonUtility.getFormattedInsertQuery("mail", "mail_id,mail_from,mail_to,mail_cc"
					+ ",mail_bcc,mail_subject,mail_body,mail_status,"
					+ "mail_sent_timestamp,user_first_name,user_last_name", 
					new Object[]{newMailId,mail.getMailFrom(),mail.getMailTo(),mail.getMailCC(),mail.getMailBCC(),mail.getMailSubject(),mail.getMailBody(),YMailMailStatus.MAIL_SENT,currentTime,userDetails.getFirstName(),userDetails.getLastName()});
			
			
			/*	getJdbcTemplate().update(SEND_MAIL_WITHOUT_ATTACHMENTS,
					new Object[]{newMailId,mail.getMailFrom(),mail.getMailTo(),mail.getMailCC(),mail.getMailBCC(),mail.getMailSubject(),mail.getMailBody(),YMailMailStatus.MAIL_SENT,currentTime,userDetails.getFirstName(),userDetails.getLastName()},
					new int[]{Types.BIGINT,Types.VARCHAR,Types.ARRAY,Types.ARRAY,Types.ARRAY,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR}
					);*/
		}

	
		
/*		for (String toEmailId : mail.getMailTo()) {
		
			String SEND_MAIL_TO_DETAILS = "insert into mail_attributes(mail_id,mail_from,mail_to,user_first_name,user_last_name) values(?,?,?,?,?)";
			getJdbcTemplate().update(SEND_MAIL_TO_DETAILS,
					new Object[]{newMailId,mail.getMailFrom(),toEmailId,userDetails.getFirstName(),userDetails.getLastName()},
					new int[]{Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
					);
		}
		
		for (String  ccEmailId : mail.getMailCC()) {
			
			String SEND_MAIL_CC_DETAILS = "insert into mail_attributes(mail_id,mail_from,mail_cc,user_first_name,user_last_name) values(?,?,?,?,?)";
			getJdbcTemplate().update(SEND_MAIL_CC_DETAILS,
					new Object[]{newMailId,mail.getMailFrom(),ccEmailId,userDetails.getFirstName(),userDetails.getLastName()},
					new int[]{Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
					);	
		}
		
		for (String  bccEmailId : mail.getMailBCC()) {
			
			String SEND_MAIL_BCC_DETAILS = "insert into mail_attributes(mail_id,mail_from,mail_bcc,user_first_name,user_last_name) values(?,?,?,?,?)";
			getJdbcTemplate().update(SEND_MAIL_BCC_DETAILS,
					new Object[]{newMailId,mail.getMailFrom(),bccEmailId,userDetails.getFirstName(),userDetails.getLastName()},
					new int[]{Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
					);
		}*/
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
		
		List<String> mailIds = cassandraTemplate.queryForList(GET_MAIL_IDS,String.class);
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
		
	    List<Mail> mail = new ArrayList<Mail>();
		
		try {
			//mail = getJdbcTemplate().query(GET_INBOX_MAILS,new QuestionMapper(),userEmailId);
		} 
		catch (EmptyResultDataAccessException  emptyEx) {
			logger.warn(MessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		return null;
	}

	public CassandraTemplate getCassandraTemplate() {
		return cassandraTemplate;
	}

	public void setCassandraTemplate(CassandraTemplate cassandraTemplate) {
		this.cassandraTemplate = cassandraTemplate;
	}

}
