package com.ymess.ymail.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.pojos.Question;
import com.ymess.pojos.User;
import com.ymess.util.MessageConstants;
import com.ymess.ymail.dao.interfaces.YMailDao;
import com.ymess.ymail.pojos.Mail;

public class JdbcYMailDao extends JdbcDaoSupport implements YMailDao {

	
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
		
		String SEND_MAIL = "insert into mail(mail_id,mail_from,mail_to,mail_cc,mail_bcc,mail_subject,mail_body,mail_sent,mail_sent_timestamp,user_first_name,user_last_name) values(?,?,?,?,?,?,?,?,?,?,?)";
		getJdbcTemplate().update(SEND_MAIL,
				new Object[]{newMailId,mail.getMailFrom(),mail.getMailTo(),mail.getMailCC(),mail.getMailBCC(),mail.getMailSubject(),mail.getMailBody(),true,currentTime,userDetails.getFirstName(),userDetails.getLastName()},
				new int[]{Types.BIGINT,Types.VARCHAR,Types.ARRAY,Types.ARRAY,Types.ARRAY,Types.VARCHAR,Types.VARCHAR,Types.BOOLEAN,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR}
				);
		
		for (String toEmailId : mail.getMailTo()) {
		
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
		
	    List<Mail> mail = new ArrayList<Mail>();
		
		try {
			//mail = getJdbcTemplate().query(GET_INBOX_MAILS,new QuestionMapper(),userEmailId);
		} 
		catch (EmptyResultDataAccessException  emptyEx) {
			logger.warn(MessageConstants.EMPTY_RESULT_SET);
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		return null;
	}

}
