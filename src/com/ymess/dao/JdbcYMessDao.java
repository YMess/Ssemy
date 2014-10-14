/**
 * 
 */
package com.ymess.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.ymess.dao.interfaces.YMessDao;
import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.pojos.Answer;
import com.ymess.pojos.File;
import com.ymess.pojos.Keyword;
import com.ymess.pojos.Question;
import com.ymess.pojos.TimeLine;
import com.ymess.pojos.User;
import com.ymess.util.ActivityConstants;
import com.ymess.util.MessageConstants;
import com.ymess.util.YMessCommonUtility;

/**
 * Contains all the methods of DB insertion
 * @author balaji i
 */
public class JdbcYMessDao extends JdbcDaoSupport implements YMessDao {

	private final Logger logger = Logger.getLogger(getClass());
	
	/** Query Section Starts */
	
	private static final String ADD_USER_DATA = "insert into users_data(email_id,first_name,last_name,password,authority,registered_time) values (?,?,?,?,?,?) ";
	
	private static final String ADD_USER = "insert into users(username,password,authority,enabled) values (?,?,?,?) ";
	
	private static final String INSERT_QUESTION = "insert into questions (question_id,author_email_id,question_title,question_desc,created_date,updated_date,keywords,author_first_name,author_last_name,topics) values (?,?,?,?,?,?,?,?,?,?)";
	
	private static final String GET_USER_QUESTIONS = "select question_id,question_title,question_desc,updated_date,last_answer,author_email_id,author_first_name,author_last_name,is_image_attached,topics from questions where author_email_id=?";
	
	private static final String CHECK_IF_KEYWORD_EXISTS = "select count(*) from question_keywords where keyword = ?";
	
	private static final String GET_KEYWORD_PARAMETERS = "select keyword,contributor_count,contributors,question_ids,question_count from question_keywords where keyword = ?";
	
	private static final String UPDATE_KEYWORDS = "insert into question_keywords(keyword,contributor_count,contributors,question_ids,question_count) values (?,?,?,?,?)";
	
	private static final String INSERT_KEYWORDS = "insert into question_keywords(keyword,contributor_count,contributors,question_ids,question_count,created_date) values (?,?,?,?,?,?)";
	
	private static final String GET_QUESTIONS_DASHBOARD = "select question_id from questions where author_email_id=?";
	
	private static final String GET_QUESTION_IDS = "select question_id from questions";
	
	private static final String GET_ANSWER_IDS = "select answer_id from answers";
	
	private static final String ADD_ANSWER = "insert into answers (question_id,answer_id,answered_time,answer_desc,author_email_id,author_first_name,author_last_name) values(?,?,?,?,?,?,?)";
	
	private static final String ADD_USER_TIMELINE = "insert into user_timeline(user_email_id, user_timestamp, activity, user_first_name, user_last_name, joined_joining_date, is_joined) values(?,?,?,?,?,?,?)";
	
	private static final String UPDATE_USER_PROFILE_TIMELINE = "insert into user_timeline(user_email_id, user_timestamp, activity, user_first_name, user_last_name, profile_updated_first_name, profile_updated_last_name, profile_updated_organization, profile_updated_designation, profile_updated_previous_organizations, profile_updated_interests, is_updated_profile) values(?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String ADD_QUESTION_TIMELINE = "insert into user_timeline(user_email_id, user_timestamp, activity, user_first_name, user_last_name,question_posted_id,question_posted_title ,question_posted_desc,question_is_image_attached, question_topics, question_updated_date, is_posted_question) values(?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String ADD_ANSWER_TIMELINE = "insert into user_timeline(user_email_id, user_timestamp, activity, user_first_name, user_last_name, answered_question_id, answered_question_desc, answered_question_email_id, answered_answered_id, answered_answered_desc, is_answered_question) values(?,?,?,?,?,?,?,?,?,?,?)";	
	
	private static final String ADD_FOLLOWING_TIMELINE = "insert into user_timeline(user_email_id, user_timestamp, activity, user_first_name, user_last_name, following_first_name, following_last_name, following_email_id, is_following) values(?,?,?,?,?,?,?,?,?)";	
	
	/** Query Section Ends*/
	
	
	/**
	 * Adds a new User during registration.
	 * @author balaji i
	 * @param user
	 * @return boolean(successFlag)
	 */
	@Override
	public boolean addUser(User user) {
		boolean added = false;
		String hashedPassword = YMessCommonUtility.getMD5HashedPassword(user.getPassword());
		try{
			getJdbcTemplate().update(ADD_USER,
					new Object[]{user.getUserEmailId(),hashedPassword,YMessCommonUtility.ROLE_REGISTERED,YMessCommonUtility.ENABLE_USER_FLAG},
					new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.BOOLEAN} 
					);
		getJdbcTemplate().update(ADD_USER_DATA,
				new Object[]{user.getUserEmailId(),user.getFirstName(),user.getLastName(),hashedPassword,YMessCommonUtility.ROLE_REGISTERED,new Date()},
				new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP} 
		);
		getJdbcTemplate().update(ADD_USER_TIMELINE, new Object[]{user.getUserEmailId(),new Date(),ActivityConstants.USER_JOINED,user.getFirstName(),user.getLastName(), new Date(),  YMessCommonUtility.IS_JOINED},
				new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.TIMESTAMP,Types.BOOLEAN}
		);
		
			added = true;
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return added;
	}

	/**
	 * Adds a Question in the database
	 * @author balaji i
	 * @param question
	 */
	@Override
	public void addQuestion(Question question) {
		Long lastQuestionId = getLastInsertedQuestionId();
		Long currentQuestionId = lastQuestionId + 1;
		
		Date currentTime = new Date();
		question.setQuestionId(currentQuestionId);
		
		User userDetails = getUserDetailsByEmailId(question.getAuthorEmailId());

		/** Question Along with Image */
		if(question.getIsImageAttached() != null && question.getIsImageAttached())
		{
			try {
				byte[] originalImage = question.getQuestionImage().getBytes();
				byte[] compressedImage = YMessCommonUtility.returnCompressedImage(originalImage);
				
				final String INSERT_QUESTION_WITH_IMAGE = "insert into questions (question_id,author_email_id,question_title,question_desc,created_date,updated_date,keywords,author_first_name,author_last_name,is_image_attached,image_data,image_name,topics) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
						getJdbcTemplate().update(INSERT_QUESTION_WITH_IMAGE,
								new Object[]{
								currentQuestionId,
								question.getAuthorEmailId(),
								question.getQuestionTitle(),
								question.getQuestionDescription(),
								currentTime,
								currentTime,
								question.getKeywords(),
								userDetails.getFirstName(),
								userDetails.getLastName(),
								true,
								compressedImage,
								question.getQuestionImage().getOriginalFilename(),
								question.getTopics()
							},
								new int[]{
								Types.BIGINT,
								Types.VARCHAR,
								Types.VARCHAR,
								Types.VARCHAR,
								Types.TIMESTAMP,
								Types.TIMESTAMP,
								Types.ARRAY,
								Types.VARCHAR,
								Types.VARCHAR,
								Types.BOOLEAN,
								Types.BINARY,
								Types.VARCHAR,
								Types.ARRAY
								
						});
						
						getJdbcTemplate().update(ADD_QUESTION_TIMELINE, new Object[]{question.getAuthorEmailId(),new Date(),ActivityConstants.POSTED_QUESTION,userDetails.getFirstName(),userDetails.getLastName(),currentQuestionId,question.getQuestionTitle(),question.getQuestionDescription(),true,question.getTopics(),currentTime ,YMessCommonUtility.IS_POSTED_QUESTIONS},
								new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.BOOLEAN,Types.ARRAY,Types.TIMESTAMP,Types.BOOLEAN}
						);
						
			} catch (IOException e) {
				logger.error(e.getStackTrace());
			}
			
		}
		else /** Only Text Question */
		{
			getJdbcTemplate().update(INSERT_QUESTION,
					new Object[]{currentQuestionId,question.getAuthorEmailId(),question.getQuestionTitle(),question.getQuestionDescription(),
					currentTime,currentTime,question.getKeywords(),userDetails.getFirstName(),userDetails.getLastName(),question.getTopics()},
					new int[]{Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP,Types.TIMESTAMP,Types.ARRAY,Types.VARCHAR,Types.VARCHAR,Types.ARRAY}
					);
			getJdbcTemplate().update(ADD_QUESTION_TIMELINE, new Object[]{question.getAuthorEmailId(),new Date(),ActivityConstants.POSTED_QUESTION,userDetails.getFirstName(),userDetails.getLastName(),currentQuestionId,question.getQuestionTitle(),question.getQuestionDescription(),false,question.getTopics(),currentTime ,YMessCommonUtility.IS_POSTED_QUESTIONS},
					new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.BOOLEAN,Types.ARRAY,Types.TIMESTAMP,Types.BOOLEAN}
			);
		
		}
		addQuestionToKeywords(question);
		
		addQuestionTopics(question.getTopics(),currentQuestionId);
		
		
		
	}

	private void addQuestionTopics(Set<String> topics,Long questionId) {

		for (String topic : topics) {
			
			if(topic.trim().length() > 0)
			{
				topic = topic.trim().toLowerCase();
			
			String CHECK_IF_TOPIC_EXISTS = "select count(1) from topics where topic=?";
			long topicCount = getJdbcTemplate().queryForLong(CHECK_IF_TOPIC_EXISTS,topic);
			
			if(topicCount != 0)
			{
				long questionCount = getJdbcTemplate().queryForLong("select question_count from topics where topic=?",topic);
				
				String UPDATE_TOPIC = "update topics set question_count="+(questionCount+1)+",question_ids=question_ids + {"+questionId+"} where topic=?";
				try{
					getJdbcTemplate().update(UPDATE_TOPIC,topic);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
			else
			{
				String INSERT_NEW_TOPIC = "insert into topics (topic,question_ids,question_count) values ('"+topic+"',"+ "{"+ questionId +"},1)";
				try{
					getJdbcTemplate().update(INSERT_NEW_TOPIC);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
		}
		
		}
		
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
	 * Gets the User Details based on Email Id
	 * @author balaji i
	 * @param authorEmailId
	 * @return User(userDetails)
	 */
	private User getUserDetailsByEmailId(String authorEmailId) {
		String GET_USER_DETAILS = "select first_name,last_name from users_data where email_id=?";
		User user = new User();
		try{
		user = getJdbcTemplate().queryForObject(GET_USER_DETAILS, new UserDetailsMapper(),authorEmailId);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return user;
	}

	private class KeywordMapper implements ParameterizedRowMapper<Keyword>
	{
		@SuppressWarnings("unchecked")
		public Keyword mapRow(ResultSet rs, int arg1) throws SQLException {
			
			Keyword keyword = new Keyword();
			keyword.setKeywordName(rs.getString("keyword"));
			keyword.setContributorCount(rs.getLong("contributor_count"));
			keyword.setContributors((List<String>) rs.getObject("contributors"));
			keyword.setQuestionIds((List<Long>) rs.getObject("question_ids"));
			keyword.setQuestionCount(rs.getLong("question_count"));
			
			return keyword;
		}
	}
	
	/**
	 * Adds Question Details to Keyword table ( One Question is a set of keywords )
	 * @author balaji i
	 * @param question
	 */
	private void addQuestionToKeywords(Question question) {
		
		for (String keyword : question.getKeywords()) 
		{
			int count = getJdbcTemplate().queryForInt(CHECK_IF_KEYWORD_EXISTS,keyword);
			
			if(count != 0)
			{
				/** The Concerned Keyword already exists , so update the keyword with newer records */
				Keyword oldKeywordDetails = null;
				Keyword newKeywordDetails = new Keyword();
				
				try{
					oldKeywordDetails = getJdbcTemplate().queryForObject(GET_KEYWORD_PARAMETERS,new KeywordMapper(),keyword);
				}
				catch(Exception ex)
				{
					logger.error(ex.getLocalizedMessage());
				}
					newKeywordDetails.setKeywordName(oldKeywordDetails.getKeywordName());
					newKeywordDetails.setContributorCount(oldKeywordDetails.getContributorCount() + 1);
					
					List<String> newestAuthorsFirst = new ArrayList<String>();
					newestAuthorsFirst.add(question.getAuthorEmailId());
					newestAuthorsFirst.addAll(oldKeywordDetails.getContributors());
					
					newKeywordDetails.setContributors(newestAuthorsFirst);

					newKeywordDetails.setQuestionCount(oldKeywordDetails.getQuestionCount() + 1);
					
					List<Long> newestQuestionFirst = new ArrayList<Long>();
					newestQuestionFirst.add(question.getQuestionId());
					
					newestQuestionFirst.addAll(oldKeywordDetails.getQuestionIds());
					
					newKeywordDetails.setQuestionIds(newestQuestionFirst);
					
					updateKeywordsInDB(newKeywordDetails);
			}
			else
			{	
				/** Keyword isnt present already, so insert a record for the keyword */
				insertKeywordInDB(keyword, question);
			}
		}
	}
	
	/**
	 * Updates the keyword details.
	 * @author balaji i
	 * @param keywordDetails
	 */
	private void updateKeywordsInDB(Keyword keywordDetails) {

		try
		{
			getJdbcTemplate().update(UPDATE_KEYWORDS,
					new Object[]{keywordDetails.getKeywordName(),keywordDetails.getContributorCount(),keywordDetails.getContributors(),
					keywordDetails.getQuestionIds(),keywordDetails.getQuestionCount()},
					new int[]{Types.VARCHAR,Types.BIGINT,Types.ARRAY,Types.ARRAY,Types.BIGINT});
			
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		
	}

	/**
	 *  Inserts the keyword in DB along with the question detail mapping.
	 * @param keywordName
	 * @param questionDetails
	 * @return Boolean (successFlag)
	 */
	boolean insertKeywordInDB(String keywordName , Question questionDetails)
	{
		boolean successFlag = false;
		try
		{
			List<String> contributors = new ArrayList<String>();
			contributors.add(questionDetails.getAuthorEmailId());
			
			List<Long> questionIds = new ArrayList<Long>();
			questionIds.add(questionDetails.getQuestionId());
			
			getJdbcTemplate().update(INSERT_KEYWORDS,
					new Object[]{keywordName,1,contributors,questionIds,1,new Date()},
					new int[]{Types.VARCHAR,Types.BIGINT,Types.ARRAY,Types.ARRAY,Types.BIGINT,Types.TIMESTAMP});
			successFlag = true;
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return successFlag;
	}
	
	
	
	/**
	 * 
	 * This class is used to Map the ResultSet values(student) to Domain/Value Objects(Student)
	 *
	 */
	private static class QuestionMapper
			implements
				ParameterizedRowMapper<Question> {
		@Override
		public Question mapRow(ResultSet rs, int rowCount) throws SQLException {
			Question question = new Question();
			question.setQuestionId(rs.getLong("question_id"));
			question.setQuestionTitle(rs.getString("question_title"));
			question.setQuestionDescription(rs.getString("question_desc"));
			question.setUpdatedDate(rs.getDate("updated_date"));
			question.setLastAnswer(rs.getString("last_answer"));
			question.setAuthorEmailId(rs.getString("author_email_id"));
			question.setFirstName(rs.getString("author_first_name"));
			question.setLastName(rs.getString("author_last_name"));
			question.setIsImageAttached(rs.getBoolean("is_image_attached"));
			//question.setTopics((Set<String>) rs.getObject("topics"));
			
			return question;
		}
	}
	
	/**
	 * Retrieves All the Questions posted by an User
	 * @author balaji i
	 * @param userEmailId
	 * @return questions(List<Question>)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<Question> getUserQuestions(String userEmailId) throws EmptyResultSetException {
		List<Question> questions = new ArrayList<Question>();
		
		try {
			questions = getJdbcTemplate().query(GET_USER_QUESTIONS,new QuestionMapper(),userEmailId);
		} 
		catch (EmptyResultDataAccessException  emptyEx) {
			logger.warn(MessageConstants.EMPTY_RESULT_SET);
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		return questions;
	}

	/**
	 * Retrieves the Jobs for an User based on his activities(category and keywords)
	 * @author balaji i
	 * @param userEmailId
	 * @return questions(List<Question>)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<Question> getDashboardQuestions(String userEmailId) throws EmptyResultSetException {
		
		List<Long> allQuestionIds = getAllQuestionIds();
		List<Long> userQuestionIds = getUserQuestionIds(userEmailId);
		
		if(!userQuestionIds.isEmpty())
		{
			allQuestionIds.removeAll(userQuestionIds);
		}
		StringBuilder finalQuestionIdsSB = new StringBuilder();
			for (Long finalQuestionId : allQuestionIds) {
				finalQuestionIdsSB = finalQuestionIdsSB.append(finalQuestionId).append(",");
			}
		
		String finalQuestionIds = "";	
		if(finalQuestionIdsSB.length() > 0)
			finalQuestionIds = finalQuestionIdsSB.substring(0,finalQuestionIdsSB.lastIndexOf(","));
		
		List<Question> questions = new ArrayList<Question>();
		
		if(finalQuestionIds.length() > 0)
		{
			questions = getQuestionDetails(finalQuestionIds);
		}
		return questions;
	}
	
	/**
	 * Gets All the Question Ids
	 * @author balaji i
	 * @return questionIds(List<String>)
	 */
	private List<Long> getAllQuestionIds()
	{
		List<Long> allQuestionIds = getJdbcTemplate().queryForList(GET_QUESTION_IDS,Long.class);
		return allQuestionIds;
	}
	
	private List<Question> getQuestionDetails(String finalQuestionIds) throws EmptyResultSetException
	{
		List<Question> questions = new ArrayList<Question>();
		try
		{
			final String GET_DASHBOARD_QUESTIONS = "select question_id,question_title,question_desc,author_email_id,last_answer,updated_date,author_first_name,author_last_name,is_image_attached from questions where question_id in ("+finalQuestionIds+") allow filtering";
			questions = getJdbcTemplate().query(GET_DASHBOARD_QUESTIONS,new QuestionMapper());
		}
		catch(EmptyResultDataAccessException emptyResultSet)
		{
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return questions;
	}
	
	
	/**
	 * Gets the Questions of an User
	 * @author balaji i
	 * @param userEmailId
	 * @return userQuestions (List<String>)
	 */
	private List<Long> getUserQuestionIds(String userEmailId)
	{
		List<Long> userQuestionIds = getJdbcTemplate().queryForList(GET_QUESTIONS_DASHBOARD,Long.class,userEmailId);
		return userQuestionIds;
	}

	/**
	 * Adds an answer posted by a User to a Question
	 * @author balaji i
	 * @param questionId
	 * @param answer
	 */
	@Override
	public void addAnswer(String questionId, String answer,String loggedInUserEmailId) {
		try{
			
			long lastInsertedAnswerId = Long.parseLong(getLastInsertedAnswerId());
			long newAnswerId = lastInsertedAnswerId + 1;
			
			Long questionIdLong = Long.parseLong(questionId);
			User userDetails = getUserDetailsByEmailId(loggedInUserEmailId);

			
			getJdbcTemplate().update(ADD_ANSWER,
				new Object[]{questionIdLong,newAnswerId,new Date(),answer,loggedInUserEmailId,userDetails.getFirstName(),userDetails.getLastName()},
				new int[]{Types.BIGINT,Types.BIGINT,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
				);
			
			final String GET_QUESTION_AUTHOR = "select author_email_id from questions where question_id="+questionIdLong+" allow filtering";
			String questionAuthorEmailId = getJdbcTemplate().queryForObject(GET_QUESTION_AUTHOR, String.class);
			
			final String GET_QUESTION_DESC = "select question_desc from questions where question_id="+questionIdLong+" allow filtering";
			String questionDesc = getJdbcTemplate().queryForObject(GET_QUESTION_DESC, String.class);
			
			final String UPDATE_QUESTION_LAST_ANSWER = "update questions set last_answer=? where question_id="+questionIdLong+" and author_email_id=?";
			getJdbcTemplate().update(UPDATE_QUESTION_LAST_ANSWER,answer,questionAuthorEmailId);
		
			getJdbcTemplate().update(ADD_ANSWER_TIMELINE, new Object[]{loggedInUserEmailId, new Date(), ActivityConstants.ANSWERED_QUESTION, userDetails.getFirstName(),userDetails.getLastName(),questionIdLong, questionDesc, GET_QUESTION_AUTHOR, newAnswerId, answer,  YMessCommonUtility.IS_ANSWERED_QUESTIONS},
					new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.BIGINT,Types.VARCHAR,Types.BOOLEAN}
			);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		
	}

	private class AnswerMapper implements ParameterizedRowMapper<Answer>
	{
		@SuppressWarnings("unchecked")
		@Override
		public Answer mapRow(ResultSet rs, int arg1) throws SQLException {
			Answer answer = new Answer();
			answer.setQuestionId(rs.getLong("question_id"));
			answer.setAnswerDescription(rs.getString("answer_desc"));
			answer.setAnsweredTime(rs.getTimestamp("answered_time"));
			answer.setAuthorEmailId(rs.getString("author_email_id"));
			answer.setDownvoteCount(rs.getLong("downvote_count"));
			answer.setUpvoteCount(rs.getLong("upvote_count"));
			answer.setAnswerId(rs.getLong("answer_id"));
			answer.setFirstName(rs.getString("author_first_name"));
			answer.setLastName(rs.getString("author_last_name"));
			answer.setUpvotedUsers((Set<String>)rs.getObject("upvoted_users"));
			answer.setDownvotedUsers((Set<String>)rs.getObject("downvoted_users"));
			
			return answer;
		}
		
	}
	
	/**
	 * Fetches all the Answers of a Question in Descending Order (Latest First)
	 * @param loggedInUserEmailId
	 * @param decodedQuestionId
	 * @return answers(List<Answer>)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<Answer> getUserQuestionResponses(String loggedInUserEmailId,
			String decodedQuestionId) throws EmptyResultSetException {
		
		List<Answer> answers = new ArrayList<Answer>();
		
		long questionId = Long.parseLong(decodedQuestionId);
		try {
			final String FETCH_USER_ANSWERS_FOR_QUESTION ="select question_id,answer_id,answered_time,answer_desc,author_email_id,downvote_count,upvote_count,author_first_name,author_last_name,upvoted_users,downvoted_users from answers where question_id="+questionId+" order by answer_id desc";
			answers = getJdbcTemplate().query(FETCH_USER_ANSWERS_FOR_QUESTION, new AnswerMapper());
		}
		catch(EmptyResultDataAccessException emptyResultSet)
		{
			logger.error(emptyResultSet.getLocalizedMessage());
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		
		return answers;
	}
	
	/**
	 * Retrieves the last inserted question Id.
	 * @author balaji i
	 * @return lastInsertedQuestionId(String)
	 */
	private Long getLastInsertedQuestionId() 
	{
		long maxQuestionId = 0;
		
		List<Long> questionIds = getJdbcTemplate().queryForList(GET_QUESTION_IDS,Long.class);
			try
			{
				if(!questionIds.isEmpty())
					maxQuestionId = Collections.max(questionIds);
			}
			catch(EmptyResultDataAccessException emptyRS)
			{
				logger.error(emptyRS.getLocalizedMessage());
			}
		return maxQuestionId;
	}
	
	/**
	 * Retrieves the last inserted Answer Id.
	 * @author balaji i
	 * @return lastInsertedQuestionId(String)
	 */
	private String getLastInsertedAnswerId() 
	{
		long maxAnswerId = 0;
		
		List<String> answerIds = getJdbcTemplate().queryForList(GET_ANSWER_IDS,String.class);
			try
			{
				if(!answerIds.isEmpty())
					maxAnswerId = Long.parseLong(Collections.max(answerIds));
			}
			catch(EmptyResultDataAccessException emptyRS)
			{
				logger.error(emptyRS.getLocalizedMessage());
			}
		return String.valueOf(maxAnswerId);
	}

	private class UserMapper implements ParameterizedRowMapper<User>{
		@Override
		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User();
			user.setUserEmailId(rs.getString("email_id"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			
			return user;
		}
	}
	/**
	 * Retrieves Upvoted Users for an Answer
	 * @param questionId
	 * @param answerId
	 * @return List<Users> upvotedUsers
	 * @throws EmptyResultSetException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAnswerUpvoters(String questionId, String answerId) throws EmptyResultSetException {

		Set<String> upvotedUserEmailIds = new HashSet<String>();
		try{
			Long answerIdLong = Long.parseLong(answerId);
			Long questionIdLong = Long.parseLong(questionId);
			
			final String GET_UPVOTED_USER_EMAIL_IDS = "select upvoted_users from answers where answer_id="+answerIdLong +" and question_id="+questionIdLong;
			upvotedUserEmailIds = getJdbcTemplate().queryForObject(GET_UPVOTED_USER_EMAIL_IDS,HashSet.class);
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		String emailIds = "";
		if(upvotedUserEmailIds != null)
			emailIds = YMessCommonUtility.getEmailIdListAsString(upvotedUserEmailIds);
		
		final String GET_USER_DETAILS = "select email_id,first_name,last_name from users_data where email_id in ("+emailIds+")";
		List<User> users = new ArrayList<User>();
		try{
			users = getJdbcTemplate().query(GET_USER_DETAILS, new UserMapper());
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		return users;
	}

	private class UserDetailMapper implements ParameterizedRowMapper<User>
	{
		@SuppressWarnings("unchecked")
		@Override
		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User();
			
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setUserEmailId(rs.getString("email_id"));
			user.setOrganization(rs.getString("organization"));
			user.setPreviousOrganizations((Set<String>)rs.getObject("previous_organizations"));
			user.setInterests((Set<String>)rs.getObject("interests"));
			user.setDesignation(rs.getString("designation"));
			user.setFollowingUsers((Map<String,Date>)rs.getObject("following_users"));
			
			return user;
		}
		
	}
	/**
	 * Fetches the User Details based on emailId
	 * @author rvishwakarma
	 * @param loggedInUserEmail
	 * @return User(userDetails)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public User getUserDetails(String loggedInUserEmail) throws EmptyResultSetException {
		final String GET_USER_DETAILS = "select email_id,first_name,last_name,designation,organization,previous_organizations,interests,following_users from users_data where email_id=?";
		
		User user = new User();
		try{
			user = getJdbcTemplate().queryForObject(GET_USER_DETAILS, new UserDetailMapper(),loggedInUserEmail);
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return user;
	}

	/**
	 * Updates User Details in DB
	 * @author rvishwakarma
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateUserProfile(User user) {

		final String GET_USER_PREVIOUS_INTERESTS = "select interests from users_data where email_id=?";
		Set<String> previousInterests = (Set<String>) getJdbcTemplate().queryForObject(GET_USER_PREVIOUS_INTERESTS, Set.class,user.getUserEmailId());
		
		Set<String> previousOrganizations = new HashSet<String>();
		Set<String> interests = new HashSet<String>();
		
		if(user.getPreviousOrganizations() != null && ! user.getPreviousOrganizations().isEmpty()) 
		{
 			previousOrganizations.addAll(user.getPreviousOrganizations());
		}
		else
		{
			previousOrganizations.add(" ");
		}
		
		if(user.getInterests() != null && ! user.getInterests().isEmpty())
		{
			interests.addAll(user.getInterests()); 
			interests = YMessCommonUtility.removeNullAndEmptyElements(interests);
		}
		
		final String UPDATE_USER_PROFILE = "insert into users_data(email_id,first_name,last_name,designation,organization,previous_organizations,interests) values(?,?,?,?,?,?,?)  ";

		try{
			getJdbcTemplate().update(UPDATE_USER_PROFILE,
				new Object[]{user.getUserEmailId(), user.getFirstName(), user.getLastName(), user.getDesignation(), user.getOrganization(), previousOrganizations ,interests},
		new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.ARRAY,Types.ARRAY}
				);
			getJdbcTemplate().update(UPDATE_USER_PROFILE_TIMELINE, new Object[]{user.getUserEmailId(),new Date(),ActivityConstants.PROFILE_UPDATED,user.getFirstName(),user.getLastName(),user.getFirstName(),user.getLastName(),user.getOrganization(),user.getDesignation(),previousOrganizations, interests,YMessCommonUtility.IS_PROFILE_UPDATED},
					new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.ARRAY,Types.ARRAY,Types.BOOLEAN}
			);
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage());
		}
		
		if(!interests.isEmpty())
		{
			for (String interest : interests) {
				
				interest = interest.toLowerCase();
				
				if(interest.length() > 0)
				{
					final String GET_INTEREST_COUNT = "Select Count(*) from user_interests where interest = ?";
				
				int interestCount	=	getJdbcTemplate().queryForInt(GET_INTEREST_COUNT, interest);
				
				if(interestCount != 0)
				{
					final String UPDATE_INTEREST ="Update user_interests set contributors = contributors + {'"+user.getUserEmailId()+"'} ,contributor_count=? where interest=?";
					getJdbcTemplate().update(UPDATE_INTEREST,
							new Object[]{interestCount+1,interest},
							new int[]{Types.BIGINT,Types.VARCHAR}
							);
				}
				else
				{
					Set<String> emailId = new HashSet<String>();
					emailId.add(user.getUserEmailId());
					
				    final  String INSERT_INTEREST = "Insert into user_interests(interest,contributors,contributor_count,created_date) values(?,?,?,?)";
				     getJdbcTemplate().update(INSERT_INTEREST,
				    		 new Object[]{interest,emailId,1,new Date() },
				    		 new int[]{Types.VARCHAR,Types.ARRAY,Types.BIGINT,Types.TIMESTAMP});
				}
			}
				
			}
			Set<String> newInterests = user.getInterests();
			Set<String> deletedInterests = new HashSet<String>();
			Set<String> addedInterests = new HashSet<String>();
			
			
			if(previousInterests != null)
				previousInterests = YMessCommonUtility.removeNullAndEmptyElements(previousInterests);
			
			if(newInterests != null)
				newInterests = YMessCommonUtility.removeNullAndEmptyElements(newInterests);
			
			Map<String,Set<String>> mergedSets = YMessCommonUtility.compareSetsAndReturnAddedAndDeletedObjects(previousInterests, newInterests);
			
			if(! mergedSets.containsKey(YMessCommonUtility.EQUAL_SET))
			{
				//Changes have been made
				deletedInterests = mergedSets.get(YMessCommonUtility.DELETED_ITEMS);
				addedInterests = mergedSets.get(YMessCommonUtility.ADDED_ITEMS);
				
				if(! deletedInterests.isEmpty())
				{
					updateUserInterestsInTopics(deletedInterests,user.getUserEmailId(),true);
				}
				
				if(! addedInterests.isEmpty())
				{
					updateUserInterestsInTopics(addedInterests,user.getUserEmailId(),false);
				}
			}
		}	
		
	}

	private boolean updateUserInterestsInTopics(Set<String> deletedInterests,String userEmailId,Boolean deletedTopicsFlag) {
		boolean success = false;
		
		for (String deletedInterest : deletedInterests) {
			deletedInterest = deletedInterest.trim().toLowerCase();
			
			/** User Deleted Interests */
			if(deletedTopicsFlag)
			{
				try{
			
				final String GET_USER_COUNT_FOR_TOPIC = "select user_count from topics where topic=?";
				Long userCount = getJdbcTemplate().queryForLong(GET_USER_COUNT_FOR_TOPIC,deletedInterest);	
					
				String REMOVE_USER_FROM_TOPICS = "UPDATE topics SET user_ids = user_ids - {'"+ userEmailId +"'},user_count = "+(userCount-1)+" WHERE topic ='"+deletedInterest+"'";
				getJdbcTemplate().update(REMOVE_USER_FROM_TOPICS);
				success = true;
				}
				catch(Exception ex)
				{
					logger.error(ex.getMessage());
				}
			}
			/** User Added Interests */
			else
			{
				String GET_TOPIC_COUNT = "select count(1) from topics where topic=?";
				Long topicCount = getJdbcTemplate().queryForLong(GET_TOPIC_COUNT,deletedInterest);
				
				if(topicCount > 0)
				{
					try{
						
						final String GET_USER_COUNT_FOR_TOPIC = "select user_count from topics where topic=?";
						Long userCount = getJdbcTemplate().queryForLong(GET_USER_COUNT_FOR_TOPIC,deletedInterest);
						
						String ADD_USER_TO_TOPICS = "UPDATE topics SET user_ids = user_ids + {'"+ userEmailId +"'},user_count="+(userCount+1)+" WHERE topic = '"+deletedInterest+"'";
						getJdbcTemplate().update(ADD_USER_TO_TOPICS);
						success = true;
						}
						catch(Exception ex)
						{
							logger.error(ex.getMessage());
						}
				}
				else
				{
					Set<String> userIds = new HashSet<String>();
					userIds.add(userEmailId);
					
					String ADD_USER_INTEREST = "insert into topics(topic,user_count,user_ids) values(?,?,?)";
					try{
					getJdbcTemplate().update(ADD_USER_INTEREST,
								new Object[]{deletedInterest,1,userIds},
								new int[]{Types.VARCHAR,Types.BIGINT,Types.ARRAY}
							);
					}
					catch(Exception ex)
					{
						logger.error(ex.getMessage());
					}
				}
				
			}
			
		}
		return success;
	}

	
	private class UserImageMapper implements ParameterizedRowMapper<User>
	{
		@Override
		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User();
			user.setUserImageData(rs.getBytes("user_image"));
			user.setImageName(rs.getString("user_image_name"));
			
			return user;
		}
		
	}
	/**
	 * Retrieves the User image based on emailId
	 * @author rvishwakarma
	 * @param loggedInUserEmail
	 * @return byte[] (userImage)
	 */
	@Override
	public User getUserImage(String loggedInUserEmail) {
		
		final String GET_USER_IMAGE = "select user_image,user_image_name from users_data where email_id=?";
		User userImage = getJdbcTemplate().queryForObject(GET_USER_IMAGE, new UserImageMapper(),loggedInUserEmail);
		
		return userImage;
	}

	/**
	 * Uploads the User's Image 
	 * @author balaji i
	 * @param user
	 */
	@Override
	public void uploadUserImage(User user) {
		 
		if(user.getUserImage() != null)
		{
			String imageName = user.getUserImage().getOriginalFilename();
			final String INSERT_IMAGE = "update users_data set user_image_name= ? , user_image = ? where email_id = ? ";
			try {
				byte [] originalImage = user.getUserImage().getBytes();
				byte[] compressedImage = YMessCommonUtility.returnCompressedImage(originalImage);
				
				getJdbcTemplate().update(INSERT_IMAGE,
						new Object[]{imageName,compressedImage,user.getUserEmailId()},
						new int[]{Types.VARCHAR,Types.BINARY,Types.VARCHAR}
						);
			} catch (DataAccessException e) {
				logger.error(e.getLocalizedMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
	}
	/**
	 * Upvotes an answer
	 * @param questionId
	 * @param answerId
	 * @param loggedInUserEmail
	 * @return Boolean(successFlag)
	 */
	@Override
	public Boolean upvoteAnswer(String questionId, String answerId,String loggedInUserEmail) {
		
		Boolean successFlag = false;
		try{
			
			Long questionIdLong = Long.parseLong(questionId);
			Long answerIdLong = Long.parseLong(answerId);
			
			long upvoteCount = 0;
			final String SELECT_UPVOTE_COUNT = "select upvote_count from answers where question_id="+questionIdLong+" and answer_id="+answerIdLong;
			upvoteCount = getJdbcTemplate().queryForLong(SELECT_UPVOTE_COUNT);
			
			
			final String UPVOTE_ANSWER = "update answers set upvoted_users = upvoted_users + {'"+loggedInUserEmail+"'} , upvote_count = ? where question_id="+questionIdLong+" and answer_id="+answerIdLong;
			getJdbcTemplate().update(UPVOTE_ANSWER,
					new Object[]{upvoteCount+1},
					new int[]{Types.BIGINT}
					);
			successFlag = true;
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return successFlag;
	}

	/**
	 * Downvotes an Answer
	 * @param questionId
	 * @param answerId
	 * @param loggedInUserEmail
	 * @return Boolean(successFlag)
	 */
	@Override
	public Boolean downvoteAnswer(String questionId, String answerId,String loggedInUserEmail) {
		
		Boolean successFlag = false;
		try{
			
			long downvoteCount = 0;
			Long answerIdLong = Long.parseLong(answerId);
			Long questionIdLong = Long.parseLong(questionId);
			
			final String SELECT_DOWNVOTE_COUNT = "select downvote_count from answers where question_id="+questionIdLong+" and answer_id="+answerIdLong;
			downvoteCount = getJdbcTemplate().queryForLong(SELECT_DOWNVOTE_COUNT);
			
			
			final String DOWNVOTE_ANSWER = "update answers set downvoted_users = downvoted_users + {'"+loggedInUserEmail+"'} , downvote_count = ? where question_id=? and answer_id=?";
			getJdbcTemplate().update(DOWNVOTE_ANSWER,
					new Object[]{downvoteCount+1,questionId,answerId},
					new int[]{Types.BIGINT,Types.BIGINT,Types.BIGINT}
					);
			successFlag = true;
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return successFlag;
	}

	/**
	 * loggedInUserEmail Following decodedUserEmailId
	 * @param loggedInUserEmail
	 * @param decodedUserEmailId
	 */
	@Override
	public void followUser(String loggedInUserEmail, String decodedUserEmailId) {

		long currentTime = new Date().getTime();
		
		final String USER_FOLLOW = "update users_data set following_users = following_users + {'"+decodedUserEmailId+"':'"+currentTime+"'} where email_id=?";
		getJdbcTemplate().update(USER_FOLLOW,loggedInUserEmail);
		
		final String FOLLOWED_BY = "update users_data set followed_by_users = followed_by_users + {'"+loggedInUserEmail+"':'"+currentTime+"'} where email_id=?";
		getJdbcTemplate().update(FOLLOWED_BY,decodedUserEmailId);
		
		User userDetails = null;
		User decodeUserDetails = null;
		
		try {
			userDetails = getUserDetailsByEmailId(loggedInUserEmail);
			decodeUserDetails = getUserDetails(decodedUserEmailId);
			System.out.println("Hi"+userDetails.getUserEmailId()+"----"+decodeUserDetails.getUserEmailId());
		} catch (EmptyResultSetException ex) {
			
			logger.error(ex.getLocalizedMessage());
		}
		getJdbcTemplate().update(ADD_FOLLOWING_TIMELINE, new Object[]{userDetails.getUserEmailId(),new Date(),ActivityConstants.FOLLOWING,userDetails.getFirstName(),userDetails.getLastName(), decodeUserDetails.getFirstName(), decodeUserDetails.getLastName(),decodeUserDetails.getUserEmailId(),  YMessCommonUtility.IS_FOLLOWING},
				new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN}
		);
		
		
	}

	/**
	 * Gets the Following Users
	 * @param loggedInUserEmail
	 * @return List<User>(followingUsers)
	 * @throws EmptyResultSetException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getFollowingUsers(String loggedInUserEmail) throws EmptyResultSetException {
		
		final String GET_FOLLOWING_USERS = "select following_users from users_data where email_id=?";
		Map<String,Date> followingUsersMap = new HashMap<String, Date>();
		List<User> followingUsers = new ArrayList<User>();
		
		try{
			followingUsersMap = getJdbcTemplate().queryForObject(GET_FOLLOWING_USERS, Map.class,loggedInUserEmail);
		}
		catch(EmptyResultDataAccessException emptyRs)
		{
			logger.error(MessageConstants.EMPTY_RESULT_SET);
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		if(followingUsersMap != null)
		{
			StringBuilder userEmailIdsSB = new StringBuilder();
			String userEmailIds = "";
			
			for (String userEmailId : followingUsersMap.keySet()) {
				userEmailIdsSB = userEmailIdsSB.append("'").append(userEmailId).append("',");
			}
			
			if(userEmailIdsSB.length() > 0)
			{
				userEmailIds = userEmailIdsSB.substring(0,userEmailIdsSB.lastIndexOf(","));
			}
			
			if(userEmailIds.length() > 0)
			{
				final String GET_USERS = "select first_name,last_name,email_id from users_data where email_id in ("+userEmailIds+")";
				followingUsers = getJdbcTemplate().query(GET_USERS,new UserMapper());
			}
		}
		
		
		return followingUsers;
	}

	/**
	 * Gets the Followers of an User
	 * @author balaji i
	 * @param loggedInUserEmail
	 * @return List<User> followers
	 * @throws EmptyResultSetException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getFollowers(String loggedInUserEmail) throws EmptyResultSetException {
		
		final String GET_FOLLOWERS = "select followed_by_users from users_data where email_id=?";
		Map<String,Date> followersMap = new HashMap<String, Date>();
		List<User> followers = new ArrayList<User>();
		
		try{
			followersMap = getJdbcTemplate().queryForObject(GET_FOLLOWERS, Map.class,loggedInUserEmail);
		}
		catch(EmptyResultDataAccessException emptyRs)
		{
			logger.error(MessageConstants.EMPTY_RESULT_SET);
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		if(followersMap != null)
		{
			StringBuilder userEmailIdsSB = new StringBuilder();
			String userEmailIds = "";
			
			for (String userEmailId : followersMap.keySet()) {
				userEmailIdsSB = userEmailIdsSB.append("'").append(userEmailId).append("',");
			}
			
			if(userEmailIdsSB.length() > 0)
			{
				userEmailIds = userEmailIdsSB.substring(0,userEmailIdsSB.lastIndexOf(","));
			}
			
			if(userEmailIds.length() > 0)
			{
				final String GET_USERS = "select first_name,last_name,email_id from users_data where email_id in ("+userEmailIds+")";
				followers = getJdbcTemplate().query(GET_USERS,new UserMapper());
			}
		}
		return followers;
	}
	/**
	 * Gets the Map of Following Users
	 * @param loggedInUserEmailId
	 * @returnMap<String,Date> (followingUsers)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Date> getFollowingUsersMap(String loggedInUserEmailId) {
		String GET_FOLLOWING_USERS_MAP = "select following_users from users_data where email_id=?";
		Map<String,Date> followingUsers = new HashMap<String, Date>();
		try{
			followingUsers = getJdbcTemplate().queryForObject(GET_FOLLOWING_USERS_MAP, Map.class,loggedInUserEmailId);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return followingUsers;
	}

	/**
	 * Unfollows an User
	 * @param loggedInUserEmail
	 * @param decodedUserEmailId
	 */
	@Override
	public void unfollowUser(String loggedInUserEmail, String decodedUserEmailId) {
		String UNFOLLOW_USER ="delete following_users ['"+decodedUserEmailId+"'] from users_data where email_id=?";
		try{
			getJdbcTemplate().update(UNFOLLOW_USER,loggedInUserEmail);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
	}

	/**
	 * Gets the downvoters of an Answer
	 * @param questionId
	 * @param answerId
	 * @return List<User>(downvoters)
	 * @throws EmptyResultSetException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAnswerDownvoters(String questionId, String answerId) throws EmptyResultSetException {

		Set<String> downvotedUserEmailIds = new HashSet<String>();
		try{
			Long answerIdLong = Long.parseLong(answerId);
			Long questionIdLong = Long.parseLong(questionId);
			
			final String GET_DOWNVOTED_USER_EMAIL_IDS = "select downvoted_users from answers where answer_id="+answerIdLong+" and question_id="+questionIdLong;
			downvotedUserEmailIds = getJdbcTemplate().queryForObject(GET_DOWNVOTED_USER_EMAIL_IDS,HashSet.class);
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		String emailIds = "";
		if(downvotedUserEmailIds != null)
			emailIds = YMessCommonUtility.getEmailIdListAsString(downvotedUserEmailIds);
		
		final String GET_USER_DETAILS = "select email_id,first_name,last_name from users_data where email_id in ("+emailIds+")";
		List<User> users = new ArrayList<User>();
		try{
			users = getJdbcTemplate().query(GET_USER_DETAILS, new UserMapper());
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		return users;
	}

	/**
	 * Checks if the new registering user has entered an emailId that has already been registered with
	 * @param userEmailId
	 * @return boolean (registeredFlag)
	 */
	@Override
	public boolean checkIfUserEmailExists(String userEmailId) {
		
		boolean isRegisterd = false;
		try{
			String CHECK_IF_USER_EXISTS = "select count(*) from users where username=?";
			int isRegisteredCount = getJdbcTemplate().queryForInt(CHECK_IF_USER_EXISTS,userEmailId);
			
			if(isRegisteredCount > 0)
				isRegisterd = true;
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return isRegisterd;
	}
	
	private class QuestionImageMapper implements ParameterizedRowMapper<Question>{
		@Override
		public Question mapRow(ResultSet rs, int arg1) throws SQLException {
			Question question = new Question();
			
			question.setQuestionImageName(rs.getString("image_name"));
			question.setQuestionImageDB(rs.getBytes("image_data"));
			question.setIsImageAttached(true);
			return question;
		}
		
	}
	
	/**
	 * Gets the Question Image
	 * @param decodedQuestionId
	 * @return Question(Image Data)
	 */
	@Override
	public Question getQuestionImage(String decodedQuestionId) {

		Long questionId = Long.parseLong(decodedQuestionId);
		final String GET_USER_IMAGE = "select image_name,image_data from questions where question_id="+questionId +" allow filtering";
		Question questionImageDetails = getJdbcTemplate().queryForObject(GET_USER_IMAGE, new QuestionImageMapper());
		
		return questionImageDetails;
	}

	/**
	 * Gets the timeline results of a user
	 * @param loggedInUserEmail
	 * @return List<TimeLine>(loggedInUserEmail)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<TimeLine> getUserTimeLine(String loggedInUserEmail) throws EmptyResultSetException {
		
		final String GET_USER_TIMELINE = "select * from user_timeline where user_email_id=? order by user_timestamp asc";

		List<TimeLine> timeline = new ArrayList<TimeLine>();
		try{
		timeline = getJdbcTemplate().query(GET_USER_TIMELINE,new UserTimeLineMapper(),loggedInUserEmail);
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		return timeline;
	}
	
	private class UserTimeLineMapper implements ParameterizedRowMapper<TimeLine>
	{
		@SuppressWarnings("unchecked")
		@Override
		public TimeLine mapRow(ResultSet rs, int arg1) throws SQLException {
			TimeLine timeLine = new TimeLine();
			timeLine.setUserEmailId(rs.getString("user_email_id"));
			timeLine.setUserTimestamp(rs.getString("user_timestamp"));
			timeLine.setActivity(rs.getString("activity"));
			timeLine.setUserFirstName(rs.getString("user_first_name"));
			timeLine.setUserLastName(rs.getString("user_last_name"));
			
			timeLine.setJoinedJoiningDate(rs.getDate("joined_joining_date"));
			timeLine.setHasJoined(rs.getBoolean("is_joined"));
			
			timeLine.setProfileUpdatedFirstName(rs.getString("profile_updated_first_name"));
			timeLine.setProfileUpdatedLastName(rs.getString("profile_updated_last_name"));
			timeLine.setProfileUpdatedOrganization(rs.getString("profile_updated_organization"));
			timeLine.setProfileUpdatedDesignation(rs.getString("profile_updated_designation"));
			timeLine.setProfileUpdatedPreviousOrganizations((Set<String>) rs.getObject("profile_updated_previous_organizations"));
			timeLine.setProfileUpdatedInterests((Set<String>)rs.getObject("profile_updated_interests"));
			timeLine.setHasUpdatedProfile(rs.getBoolean("is_updated_profile"));
			
			timeLine.setQuestionPostedId(rs.getLong("question_posted_id"));
			timeLine.setQuestionPostedTitle(rs.getString("question_posted_title"));
			timeLine.setQuestionPostedDesc(rs.getString("question_posted_desc"));
			timeLine.setQuestionIsImageAttached(rs.getBoolean("question_is_image_attached"));
			timeLine.setQuestionTopics((Set<String>)rs.getObject("question_topics"));
			timeLine.setQuestionUpdatedDate(rs.getDate("question_updated_date"));
			timeLine.setQuestionLastAnswer(rs.getString("question_last_answer"));
			timeLine.setHasPostedQuestion(rs.getBoolean("is_posted_question"));
			
			timeLine.setAnsweredQuestionId(rs.getLong("answered_question_id"));
			timeLine.setAnsweredQuestionTitle(rs.getString("answered_question_title"));
			timeLine.setAnsweredQuestionDesc(rs.getString("answered_question_desc"));
			timeLine.setAnsweredQuestionEmailId(rs.getString("answered_question_email_id"));
			timeLine.setAnsweredQuestionIsImageAttached(rs.getBoolean("answered_question_is_image_attached"));
			timeLine.setAnsweredQuestionTopics((Set<String>)rs.getObject("answered_question_topics"));
			timeLine.setAnsweredAnsweredId(rs.getLong("answered_answered_id"));
			timeLine.setAnsweredAnsweredDesc(rs.getString("answered_answered_desc"));
			timeLine.setHasAnsweredQuestion(rs.getBoolean("is_answered_question"));
			
			timeLine.setFollowingFirstName(rs.getString("following_first_name"));
			timeLine.setFollowingLastName(rs.getString("following_last_name"));
			timeLine.setFollowingEmailId(rs.getString("following_email_id"));
			timeLine.setHasFollowing(rs.getBoolean("is_following"));
			
			timeLine.setUpvotedQuestionId(rs.getLong("upvoted_question_id"));
			timeLine.setUpvotedQuestionDesc(rs.getString("upvoted_question_desc"));
			timeLine.setUpvotedQuestionEmailId(rs.getString("upvoted_question_email_id"));
			timeLine.setUpvotedAnswerId(rs.getLong("upvoted_answer_id"));
			timeLine.setUpvotedAnswerDesc(rs.getString("upvoted_answer_desc"));
			timeLine.setHasUpvoted(rs.getBoolean("is_upvoted"));
			
			timeLine.setDownvotedQuestionId(rs.getLong("downvoted_question_id"));
			timeLine.setDownvotedQuestionDesc(rs.getString("downvoted_question_desc"));
			timeLine.setDownvotedQuestionEmailId(rs.getString("downvoted_question_email_id"));
			timeLine.setDownvotedAnswerId(rs.getLong("downvoted_answer_id"));
			timeLine.setDownvotedAnswerDesc(rs.getString("downvoted_answer_desc"));
			timeLine.setHasDownvoted(rs.getBoolean("is_downvoted"));
			
			timeLine.setActivity(rs.getString("shared_question_id"));
			timeLine.setActivity(rs.getString("shared_question_desc"));
			timeLine.setActivity(rs.getString("shared_question_email_id"));
			timeLine.setHasShared(rs.getBoolean("is_shared"));
			
			timeLine.setLikedQuestionId(rs.getLong("liked_question_id"));
			timeLine.setLikedQuestionDesc(rs.getString("liked_question_desc"));
			timeLine.setLikedQuestionEmailId(rs.getString("liked_question_email_id"));
			timeLine.setHasLikedQuestion(rs.getBoolean("is_liked_question"));
			
			timeLine.setLikedAnswerId(rs.getString("liked_answer_id"));
			timeLine.setLikedAnswerDesc(rs.getString("liked_answer_desc"));
			timeLine.setLikedAnswerEmailId(rs.getString("liked_answer_email_id"));
			timeLine.setHasLikedAnswer(rs.getBoolean("is_liked_answer"));
			
			return timeLine;
		}
	}
	
	/**
	 * Fetches all the Questions in a Topic
	 * @param topicName
	 * @return  List<Question> (questions in a topic)
	 * @throws EmptyResultSetException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getQuestionsInTopic(String topicName) throws EmptyResultSetException {

		String GET_QUESTION_IDS_IN_TOPIC = "select question_ids from topics where topic=?";
		Set<Long> questionIds = (Set<Long>) getJdbcTemplate().queryForObject(GET_QUESTION_IDS_IN_TOPIC, HashSet.class,topicName);
		
		StringBuilder questionIdsSB = new StringBuilder();
		String questionIdsString = "";
		List<Question> questions = new ArrayList<Question>();
		
		if(null != questionIds && ! questionIds.isEmpty())
		{
			for (Long questionId : questionIds) {
				questionIdsSB = questionIdsSB.append(questionId).append(",");
			}
			if(questionIdsSB.length() > 0)
				questionIdsString = questionIdsSB.substring(0,questionIdsSB.lastIndexOf(","));
		
			if(questionIdsString.length() > 0)
			{
				questions = getQuestionDetailsWithoutTopics(questionIdsString);
			}
		}
		return questions;
	}
	private static class QuestionDetailsMapper
	implements
		ParameterizedRowMapper<Question> {
@Override
public Question mapRow(ResultSet rs, int rowCount) throws SQLException {
	Question question = new Question();
	question.setQuestionId(rs.getLong("question_id"));
	question.setQuestionDescription(rs.getString("question_desc"));
	question.setUpdatedDate(rs.getDate("updated_date"));
	question.setLastAnswer(rs.getString("last_answer"));
	question.setAuthorEmailId(rs.getString("author_email_id"));
	question.setFirstName(rs.getString("author_first_name"));
	question.setLastName(rs.getString("author_last_name"));
	question.setIsImageAttached(rs.getBoolean("is_image_attached"));
	
	return question;
}
}
	private List<Question> getQuestionDetailsWithoutTopics(String finalQuestionIds) throws EmptyResultSetException
	{
		List<Question> questions = new ArrayList<Question>();
		try
		{
			final String GET_DASHBOARD_QUESTIONS = "select question_id,question_desc,author_email_id,last_answer,updated_date,author_first_name,author_last_name,is_image_attached from questions where question_id in ("+finalQuestionIds+") allow filtering";
			questions = getJdbcTemplate().query(GET_DASHBOARD_QUESTIONS,new QuestionDetailsMapper());
		}
		catch(EmptyResultDataAccessException emptyResultSet)
		{
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return questions;
	}

	/**
	 * @author balaji i
	 * Retrieves all the Topics from topics table.
	 * @return topics(List<String>)
	 */
	@Override
	public List<String> getAllTopics() throws EmptyResultSetException {
		List<String> topics = new ArrayList<String>();
		try{
			String GET_TOPIC_NAMES = "select topic from topics";
			topics = getJdbcTemplate().queryForList(GET_TOPIC_NAMES,String.class);
		}
		catch(EmptyResultDataAccessException emptyRs)
		{
			logger.error(MessageConstants.EMPTY_RESULT_SET);
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		return topics;
	}

	/**
	 * Facilitates User File Upload
	 * @param file
	 * @throws IOException 
	 * @throws DataAccessException 
	 */
	@Override
	public void uploadFile(File file) throws DataAccessException, IOException {
		
		Long lastInsertedFileId = getLastFileId();
		Long newFileId = lastInsertedFileId + 1;
		
		Set<String> fileTopics = file.getTopics();
		if(!fileTopics.isEmpty())
			fileTopics = YMessCommonUtility.removeNullAndEmptyElements(fileTopics);
		
		final String UPLOAD_FILE = "insert into files(file_id,user_email_id,file_type,filename,share_flag,topics,filedata,upload_time,file_size) values (?,?,?,?,?,?,?,?,?)";
		getJdbcTemplate().update(UPLOAD_FILE,
				new Object[]{newFileId,file.getAuthorEmailId(),YMessCommonUtility.getFileExtension(file.getFileData().getOriginalFilename()),file.getFileData().getOriginalFilename(),file.getShared(),fileTopics,file.getFileData().getBytes(),new Date(),file.getFileSize()},
				new int[]{Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.BOOLEAN,Types.ARRAY,Types.BINARY,Types.TIMESTAMP,Types.VARCHAR}
				);
		
		
		for (String fileTopic : fileTopics) {
			fileTopic = fileTopic.trim().toLowerCase();
			
			String CHECK_IF_TOPIC_EXISTS = "select count(1) from topics where topic=?";
			Long topicCount = getJdbcTemplate().queryForLong(CHECK_IF_TOPIC_EXISTS,fileTopic);
			
			if(topicCount > 0)
			{
				final String PREVIOUS_FILE_COUNT = "select file_count from topics where topic=?";
				Long previousFileCount = getJdbcTemplate().queryForLong(PREVIOUS_FILE_COUNT,fileTopic);
				
				String UPDATE_TOPIC_WITH_FILE_DETAILS = "update topics set file_ids = file_ids + {"+newFileId+":'"+file.getAuthorEmailId() +"'}, file_count = "+ (previousFileCount+1) +" where topic=?";
				getJdbcTemplate().update(UPDATE_TOPIC_WITH_FILE_DETAILS,fileTopic);
			}
			else
			{
				Map<Long,String> fileIdAndAuthorEmailId = new HashMap<Long, String>();
				fileIdAndAuthorEmailId.put(newFileId,file.getAuthorEmailId());
				
				String INSERT_INTO_TOPICS = "insert into topics(topic,file_ids,file_count) values(?,?,?)";
				getJdbcTemplate().update(INSERT_INTO_TOPICS,
						new Object[]{fileTopic,fileIdAndAuthorEmailId,1},
						new int[]{Types.VARCHAR,Types.OTHER,Types.BIGINT}
						);
			}
		}
		
		
	}

	/**
	 * Fetches the Last Inserted File Id
	 * @return LastInsertedFileId(Long)
	 */
	private Long getLastFileId() {
		
		Long lastInsertedFileId = 0L;
		final String LAST_FILE_ID = "select file_id from files";
		try{
		List<Long> fileIds = getJdbcTemplate().queryForList(LAST_FILE_ID,Long.class);
		if(! fileIds.isEmpty())
			lastInsertedFileId = Collections.max(fileIds);
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			logger.error(emptyRS.getMessage());
		}
		return lastInsertedFileId;
	}
	
	private class FileDetailsMapper implements ParameterizedRowMapper<File>
	{
		@SuppressWarnings("unchecked")
		@Override
		public File mapRow(ResultSet rs, int arg1) throws SQLException {
			File fileDetails = new File();
			fileDetails.setFileId(rs.getLong("file_id"));
			fileDetails.setAuthorEmailId(rs.getString("user_email_id"));
			fileDetails.setFileType(rs.getString("file_type"));
			fileDetails.setFilename(rs.getString("filename"));
			fileDetails.setShared(rs.getBoolean("share_flag"));
			fileDetails.setTopics((Set<String>) rs.getObject("topics"));
			fileDetails.setUploadedTime(rs.getDate("upload_time"));
			fileDetails.setFileSize(rs.getString("file_size"));
			//fileDetails.setFileDataDb(rs.getBytes("filedata"));
			
			return fileDetails;
		}
		
	}
	
	
	/**
	 * Gets all the files that have been shared by Users
	 * @author balaji i
	 * @return  sharedFiles(List<File>)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<File> getAllSharedFiles() throws EmptyResultSetException {
		List<File> files = new ArrayList<File>();
		String GET_SHARED_FILES = "select file_id,user_email_id,file_type,filename,share_flag,topics,filedata,upload_time,file_size from files where share_flag=true"; 
		try{
			files = getJdbcTemplate().query(GET_SHARED_FILES,new FileDetailsMapper());
		}
		catch(EmptyResultDataAccessException emptyRs)
		{
			throw new EmptyResultSetException(MessageConstants.EMPTY_RESULT_SET);
		}
		return files;
	}

	private class FileDownloadMapper implements ParameterizedRowMapper<File>
	{
		@Override
		public File mapRow(ResultSet rs, int arg1) throws SQLException {
			File file = new File();
			file.setFilename(rs.getString("filename"));
			file.setFileDataDb(rs.getBytes("filedata"));
			
			return file;
		}
		
	}
	/**
	 * Downloads the file data
	 * @author balaji i
	 * @param encodedFileId
	 * @param encodedAuthorEmailId
	 * @return File (downloadedFileDetails)
	 */
	@Override
	public File downloadFile(String encodedFileId, String encodedAuthorEmailId) {
		final String GET_FILE_DETAILS = "select filename,filedata from files where file_id="+encodedFileId+" and user_email_id=?";
		File fileDetails = getJdbcTemplate().queryForObject(GET_FILE_DETAILS,new FileDownloadMapper(),encodedAuthorEmailId);
		return fileDetails;
	}
}
