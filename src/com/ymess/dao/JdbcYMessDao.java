/**
 * 
 */
package com.ymess.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.cassandra.core.RowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.cassandra.core.CassandraTemplate;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.exceptions.DriverException;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;
import com.ymess.dao.interfaces.YMessDao;
import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.pojos.Answer;
import com.ymess.pojos.File;
import com.ymess.pojos.Question;
import com.ymess.pojos.TimeLine;
import com.ymess.pojos.Topic;
import com.ymess.pojos.User;
import com.ymess.util.YMessActivityConstants;
import com.ymess.util.YMessCommonUtility;
import com.ymess.util.YMessMessageConstants;

/**
 * Contains all the methods of DB insertion
 * @author balaji i
 */
public class JdbcYMessDao implements YMessDao {

	private CassandraTemplate cassandraTemplate;
	
	
	private final Logger logger = Logger.getLogger(getClass());
	
	/** Query Section Starts */
	
	
	
	
	
	private static final String INSERT_QUESTION = "insert into questions (question_id,author_email_id,question_title,question_desc,created_date,updated_date,keywords,author_first_name,author_last_name,topics) values (?,?,?,?,?,?,?,?,?,?)";
	
	private static final String GET_USER_QUESTIONS = "select question_id,question_title,question_desc,updated_date,last_answer,author_email_id,author_first_name,author_last_name,is_image_attached,topics from questions where author_email_id=?";
	
	private static final String CHECK_IF_KEYWORD_EXISTS = "select count(*) from question_keywords where keyword = ?";
	
	private static final String GET_KEYWORD_PARAMETERS = "select keyword,contributor_count,contributors,question_ids,question_count from question_keywords where keyword = ?";
	
	//private static final String UPDATE_KEYWORDS = "insert into question_keywords(keyword,contributor_count,contributors,question_ids,question_count) values (?,?,?,?,?)";
	
	//private static final String INSERT_KEYWORDS = "insert into question_keywords(keyword,contributor_count,contributors,question_ids,question_count,created_date) values (?,?,?,?,?,?)";
	
	private static final String GET_QUESTIONS_DASHBOARD = "select question_id from questions where author_email_id=?";
	
	private static final String GET_QUESTION_IDS = "select question_id from questions";
	
	private static final String GET_ANSWER_IDS = "select answer_id from answers";
	
	private static final String ADD_ANSWER = "insert into answers (question_id,answer_id,answered_time,answer_desc,author_email_id,author_first_name,author_last_name) values(?,?,?,?,?,?,?)";
	
	private static final String UPDATE_USER_PROFILE_TIMELINE = "insert into user_timeline(user_email_id, user_timestamp, activity, user_first_name, user_last_name, profile_updated_first_name, profile_updated_last_name, profile_updated_organization, profile_updated_designation, profile_updated_previous_organizations, profile_updated_interests, is_updated_profile) values(?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String ADD_QUESTION_TIMELINE = "insert into user_timeline(user_email_id, user_timestamp, activity, user_first_name, user_last_name,question_posted_id,question_posted_title ,question_posted_desc,question_is_image_attached, question_topics, question_updated_date, is_posted_question) values(?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String ADD_ANSWER_TIMELINE = "insert into user_timeline(user_email_id, user_timestamp, activity, user_first_name, user_last_name, answered_question_id, answered_question_desc, answered_question_email_id, answered_answered_id, answered_answered_desc, is_answered_question) values(?,?,?,?,?,?,?,?,?,?,?)";	
	
	private static final String ADD_FOLLOWING_TIMELINE = "insert into user_timeline(user_email_id, user_timestamp, activity, user_first_name, user_last_name, following_first_name, following_last_name, following_email_id, is_following) values(?,?,?,?,?,?,?,?,?)";	
	
	private static final String GET_USER_IDS = "select user_id from users_data";
	
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
		
		Long newUserId = getLastInsertedUserId() + 1;
		String hashedPassword = YMessCommonUtility.getMD5HashedPassword(user.getPassword());
		try{
			
			// Users
			Insert insertQuery = QueryBuilder.insertInto("users").values(
					new String[]{"username","password","authority","enabled","role"}, 
					new Object[]{user.getUserEmailId(),hashedPassword,YMessCommonUtility.ROLE_REGISTERED,YMessCommonUtility.ENABLE_USER_FLAG,user.getRegistrationType()});
			cassandraTemplate.execute(insertQuery);
			
			// Users Data
			Insert insertIntoUserDetails;
			if(user.getRegistrationType() == YMessCommonUtility.ROLE_COMPANY)
			{
				insertIntoUserDetails = QueryBuilder.insertInto("users_data").values(
						new String[]{"user_id","email_id","first_name","last_name","password","authority","registered_time","phone_code","mobile_phone","role_type","website","is_registered_newsletter","is_agreed_service"},
						new Object[]{newUserId,user.getUserEmailId(),user.getFirstName(),user.getLastName(),hashedPassword,YMessCommonUtility.ROLE_REGISTERED,new Date(),
								user.getPhoneCode(),user.getMobilePhone(),YMessCommonUtility.ROLE_COMPANY_ADMINISTRATOR,user.getWebsite(),user.getIsRegisterNewsLetter(),user.getIsAgreedService()});
				
			}
			else
			{
				insertIntoUserDetails = QueryBuilder.insertInto("users_data").values(
						new String[]{"user_id","email_id","first_name","last_name","password","authority","registered_time","phone_code","mobile_phone",},
						new Object[]{newUserId,user.getUserEmailId(),user.getFirstName(),user.getLastName(),hashedPassword,YMessCommonUtility.ROLE_REGISTERED,new Date(),user.getPhoneCode(),user.getMobilePhone(),});
				
			}
			cassandraTemplate.execute(insertIntoUserDetails);
		
		// User Timeline
		Insert insertIntoUserTimeline = QueryBuilder.insertInto("user_timeline").values(
				new String[]{"user_email_id", "user_timestamp", "activity", "user_first_name", "user_last_name", "joined_joining_date", "is_joined"},
				new Object[]{user.getUserEmailId(),new Date(),YMessActivityConstants.USER_JOINED,user.getFirstName(),user.getLastName(), new Date(),  YMessCommonUtility.IS_JOINED});
		
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
		
		Long questionId = getLastInsertedQuestionId() + 1;
		
		Date currentTime = new Date();
		question.setQuestionId(questionId);
		
		User userDetails = getUserDetailsByEmailId(question.getAuthorEmailId());

		/** Question Along with Image */
		if(question.getIsImageAttached() != null && question.getIsImageAttached())
		{
			try {
				byte[] originalImage = question.getQuestionImage().getBytes();
				byte[] compressedImage = null;
				if(YMessCommonUtility.getFileExtension(question.getQuestionImage().getOriginalFilename()).equalsIgnoreCase("jpg") 
						|| YMessCommonUtility.getFileExtension(question.getQuestionImage().getOriginalFilename()).equalsIgnoreCase("jpeg") )
					compressedImage = YMessCommonUtility.returnCompressedImage(originalImage);
				else
					compressedImage = originalImage;
				
				Insert insertIntoQuestions = YMessCommonUtility.getFormattedInsertQuery("questions", 
						"question_id,author_email_id,question_title,question_desc,created_date,"
						+ "updated_date,keywords,author_first_name,"
						+ "author_last_name,is_image_attached,image_data,image_name,topics",
						new Object[]{
						questionId,
						question.getAuthorEmailId(),
						question.getQuestionTitle(),
						question.getQuestionDescription(),
						currentTime,
						currentTime,
						question.getKeywords(),
						userDetails.getFirstName(),
						userDetails.getLastName(),
						true,
						ByteBuffer.wrap(compressedImage),
						question.getQuestionImage().getOriginalFilename(),
						question.getTopics()
					});
				
				cassandraTemplate.execute(insertIntoQuestions);
				
				
			Insert insertQueryinTimeline = YMessCommonUtility.getFormattedInsertQuery("user_timeline", 
					"user_email_id, user_timestamp, activity, user_first_name, user_last_name,question_posted_id,"
					+ "question_posted_title ,question_posted_desc,question_is_image_attached,"
					+ " question_topics, question_updated_date, is_posted_question",  
					new Object[]{question.getAuthorEmailId(),new Date(),YMessActivityConstants.POSTED_QUESTION,userDetails.getFirstName(),
					userDetails.getLastName(),questionId,question.getQuestionTitle(),question.getQuestionDescription(),true,
					userDetails.getLastName(),questionId,question.getQuestionTitle(),question.getQuestionDescription(),true,
					question.getTopics(),currentTime ,YMessCommonUtility.IS_POSTED_QUESTIONS});	
				
					/*	cassandraTemplate.update(ADD_QUESTION_TIMELINE, new Object[]{question.getAuthorEmailId(),new Date(),ActivityConstants.POSTED_QUESTION,userDetails.getFirstName(),userDetails.getLastName(),currentQuestionId,question.getQuestionTitle(),question.getQuestionDescription(),true,question.getTopics(),currentTime ,YMessCommonUtility.IS_POSTED_QUESTIONS},
								new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.BOOLEAN,Types.ARRAY,Types.TIMESTAMP,Types.BOOLEAN}
						);*/
			
			
			cassandraTemplate.execute(insertQueryinTimeline);
						
			} catch (IOException e) {
				logger.error(e.getStackTrace());
			}
			
		}
		else /** Only Text Question */
		{
			
			Insert insertQuestionWithoutImage = YMessCommonUtility.getFormattedInsertQuery("questions", "question_id,author_email_id,"
					+ "question_title,question_desc,created_date,updated_date,"
					+ "keywords,author_first_name,author_last_name,topics", 
					new Object[]{questionId,question.getAuthorEmailId(),question.getQuestionTitle(),question.getQuestionDescription(),
					currentTime,currentTime,question.getKeywords(),userDetails.getFirstName(),userDetails.getLastName(),question.getTopics()});
			
			cassandraTemplate.execute(insertQuestionWithoutImage);
			
			
		/*	cassandraTemplate.update(INSERT_QUESTION,
					new Object[]{currentQuestionId,question.getAuthorEmailId(),question.getQuestionTitle(),question.getQuestionDescription(),
					currentTime,currentTime,question.getKeywords(),userDetails.getFirstName(),userDetails.getLastName(),question.getTopics()},
					new int[]{Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP,Types.TIMESTAMP,Types.ARRAY,Types.VARCHAR,Types.VARCHAR,Types.ARRAY}
					);*/
			
			Insert insertIntoUserTimeline = YMessCommonUtility.getFormattedInsertQuery("user_timeline",
					"user_email_id, user_timestamp, activity, user_first_name, user_last_name,question_posted_id,"
					+ "question_posted_title ,question_posted_desc,question_is_image_attached, "
					+ "question_topics, question_updated_date, is_posted_question", 
					new Object[]{question.getAuthorEmailId(),new Date(),YMessActivityConstants.POSTED_QUESTION,userDetails.getFirstName(),
					userDetails.getLastName(),questionId,question.getQuestionTitle(),question.getQuestionDescription(),
					false,question.getTopics(),currentTime ,YMessCommonUtility.IS_POSTED_QUESTIONS});
			
			cassandraTemplate.execute(insertIntoUserTimeline);
			
			
			/*cassandraTemplate.update(ADD_QUESTION_TIMELINE, new Object[]{question.getAuthorEmailId(),new Date(),ActivityConstants.POSTED_QUESTION,userDetails.getFirstName(),userDetails.getLastName(),currentQuestionId,question.getQuestionTitle(),question.getQuestionDescription(),false,question.getTopics(),currentTime ,YMessCommonUtility.IS_POSTED_QUESTIONS},
					new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.BOOLEAN,Types.ARRAY,Types.TIMESTAMP,Types.BOOLEAN}
			);*/
		
		}
		//addQuestionToKeywords(question);
		
		addQuestionTopics(question.getTopics(),questionId);
		
		
		
	}

	private void addQuestionTopics(Set<String> topics,Long questionId) {

		for (String topic : topics) {
			
			if(topic.trim().length() > 0)
			{
				topic = YMessCommonUtility.removeExtraneousApostrophe(topic);
				topic = topic.trim().toLowerCase();
			
			Select selectTopicCount = QueryBuilder.select().countAll().from("topics");
			selectTopicCount.where(QueryBuilder.eq("topic", topic));
				
			Long topicCount = cassandraTemplate.queryForObject(selectTopicCount,Long.class);
			if(topicCount != 0)
			{
				Select selectQuestionCount = QueryBuilder.select("question_count").from("topics");
				selectQuestionCount.where(QueryBuilder.eq("topic", topic));
					
				Long questionCount = 0L;
				try {
					questionCount = cassandraTemplate.queryForObject(selectQuestionCount,Long.class);
				}
				catch(NullPointerException npe){
					questionCount = 0L;
				}
				
				try {
					Update update = QueryBuilder.update("topics");
					update.with(QueryBuilder.set("question_count", questionCount+1));
					update.with(QueryBuilder.append("question_ids", questionId));

					update.where(QueryBuilder.eq("topic", topic));
					cassandraTemplate.execute(update);
					
				}
				catch(Exception ex)
				{
					logger.error(ex.getCause());
				}
			}
			else
			{
				try {
					Set<Long> questionIds = new HashSet<Long>();
					questionIds.add(questionId);
					
					Insert insert = QueryBuilder.insertInto("topics");
					insert.value("topic", topic);
					insert.value("question_ids", questionIds);
					insert.value("question_count", 1);

					cassandraTemplate.execute(insert);
					
					//String INSERT_NEW_TOPIC = "insert into topics (topic,question_ids,question_count) values ('"+topic+"',"+ "{"+ questionId +"},1)";
				//	cassandraTemplate.update(INSERT_NEW_TOPIC);
				}
				catch(Exception ex)
				{
					logger.error(ex.getStackTrace());
				}
			}
		}
		
		}
		
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
	 * Gets the User Details based on Email Id
	 * @author balaji i
	 * @param authorEmailId
	 * @return User(userDetails)
	 */
	private User getUserDetailsByEmailId(String authorEmailId) {
		User user = new User();
		try{
			Select selectUserDetails = QueryBuilder.select("first_name","last_name").from("users_data");
			selectUserDetails.where(QueryBuilder.eq("email_id", authorEmailId));
			
			user = cassandraTemplate.queryForObject(selectUserDetails, new UserDetailsMapper());
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return user;
	}
/*
	private class KeywordMapper implements RowMapper<Keyword>
	{
		@Override
		public Keyword mapRow(Row rs, int arg1) throws DriverException {
			Keyword keyword = new Keyword();
			keyword.setKeywordName(rs.getString("keyword"));
			keyword.setContributorCount(rs.getLong("contributor_count"));
			keyword.setContributors(rs.getList("contributors",String.class));
			keyword.setQuestionIds(rs.getList("question_ids",Long.class));
			keyword.setQuestionCount(rs.getLong("question_count"));
			
			return keyword;
		}
	}
	*/
	
	
	
/*	*//**
	 * Adds Question Details to Keyword table ( One Question is a set of keywords )
	 * @author balaji i
	 * @param question
	 *//*
	private void addQuestionToKeywords(Question question) {
		
		for (String keyword : question.getKeywords()) 
		{
			int count = cassandraTemplate.queryForObject(CHECK_IF_KEYWORD_EXISTS,keyword);
			
			if(count != 0)
			{
				*//** The Concerned Keyword already exists , so update the keyword with newer records *//*
				Keyword oldKeywordDetails = null;
				Keyword newKeywordDetails = new Keyword();
				
				try{
					oldKeywordDetails = cassandraTemplate.queryForObject(GET_KEYWORD_PARAMETERS,new KeywordMapper(),keyword);
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
				*//** Keyword isnt present already, so insert a record for the keyword *//*
				insertKeywordInDB(keyword, question);
			}
		}
	}
	*/
/*	*//**
	 * Updates the keyword details.
	 * @author balaji i
	 * @param keywordDetails
	 *//*
	private void updateKeywordsInDB(Keyword keywordDetails) {

		try
		{
			cassandraTemplate.update(UPDATE_KEYWORDS,
					new Object[]{keywordDetails.getKeywordName(),keywordDetails.getContributorCount(),keywordDetails.getContributors(),
					keywordDetails.getQuestionIds(),keywordDetails.getQuestionCount()},
					new int[]{Types.VARCHAR,Types.BIGINT,Types.ARRAY,Types.ARRAY,Types.BIGINT});
			
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		
	}*/

/*	*//**
	 *  Inserts the keyword in DB along with the question detail mapping.
	 * @param keywordName
	 * @param questionDetails
	 * @return Boolean (successFlag)
	 *//*
	boolean insertKeywordInDB(String keywordName , Question questionDetails)
	{
		boolean successFlag = false;
		try
		{
			List<String> contributors = new ArrayList<String>();
			contributors.add(questionDetails.getAuthorEmailId());
			
			List<Long> questionIds = new ArrayList<Long>();
			questionIds.add(questionDetails.getQuestionId());
			
			cassandraTemplate.update(INSERT_KEYWORDS,
					new Object[]{keywordName,1,contributors,questionIds,1,new Date()},
					new int[]{Types.VARCHAR,Types.BIGINT,Types.ARRAY,Types.ARRAY,Types.BIGINT,Types.TIMESTAMP});
			successFlag = true;
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return successFlag;
	}*/
	
	
	
	/**
	 * 
	 * This class is used to Map the ResultSet values(student) to Domain/Value Objects(Student)
	 *
	 */
	private static class QuestionMapper implements RowMapper<Question> {

		@Override
		public Question mapRow(Row rs, int arg1) throws DriverException {
			Question question = new Question();
			question.setQuestionId(rs.getLong("question_id"));
			question.setQuestionTitle(rs.getString("question_title"));
			question.setQuestionDescription(rs.getString("question_desc"));
			question.setUpdatedDate(rs.getDate("updated_date"));
			question.setLastAnswer(rs.getString("last_answer"));
			question.setAuthorEmailId(rs.getString("author_email_id"));
			question.setFirstName(rs.getString("author_first_name"));
			question.setLastName(rs.getString("author_last_name"));
			question.setIsImageAttached(rs.getBool("is_image_attached"));
			question.setTopics(rs.getSet("topics",String.class));
			
			return question;
		}
	}
	
	
	/**
	 * 
	 * This class is used to Map the ResultSet values(student) to Domain/Value Objects(Student)
	 *
	 */
	private static class QuestionDetialsMapper implements RowMapper<Question> {
		@Override
		public Question mapRow(Row rs, int arg1) throws DriverException {
			Question question = new Question();
			question.setQuestionId(rs.getLong("question_id"));
			question.setQuestionTitle(rs.getString("question_title"));
			question.setQuestionDescription(rs.getString("question_desc"));
			question.setUpdatedDate(rs.getDate("updated_date"));
			question.setLastAnswer(rs.getString("last_answer"));
			question.setAuthorEmailId(rs.getString("author_email_id"));
			question.setFirstName(rs.getString("author_first_name"));
			question.setLastName(rs.getString("author_last_name"));
			question.setIsImageAttached(rs.getBool("is_image_attached"));
			question.setTopics((Set<String>) rs.getSet("topics",String.class));
			
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
			
			Select selectUserQuestions = QueryBuilder.select().from("questions");
			selectUserQuestions.where(QueryBuilder.eq("author_email_id", userEmailId));
			
			questions = cassandraTemplate.query(selectUserQuestions, new QuestionDetailsMapper());
		} 
		catch (EmptyResultDataAccessException  emptyEx) {
			logger.warn(YMessMessageConstants.EMPTY_RESULT_SET);
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
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
		
		List<Question> questions = new ArrayList<Question>();
		StringBuilder finalQuestionIdsSB = new StringBuilder();
			for (Long finalQuestionId : allQuestionIds){
				finalQuestionIdsSB = finalQuestionIdsSB.append(finalQuestionId).append(",");
			}
		
		String finalQuestionIds = "";	
		if(finalQuestionIdsSB.length() > 0)
			finalQuestionIds = finalQuestionIdsSB.substring(0,finalQuestionIdsSB.lastIndexOf(","));
		
		if(finalQuestionIds.length() > 0)
		{
			if(null != allQuestionIds && !allQuestionIds.isEmpty())
			{
				questions = getQuestionsWithDetails(allQuestionIds);
			}
		}
		
		
		return questions;
	}
	
	/**
	 * Gets All the Question Ids
	 * @author balaji i
	 * @return questionIds(List<Long>)
	 */
	private List<Long> getAllQuestionIds()
	{
		Select selectQuestionIds = QueryBuilder.select("question_id").from("questions");
		List<Long> allQuestionIds = new ArrayList<Long>();
		try {
			allQuestionIds = cassandraTemplate.queryForList(selectQuestionIds,Long.class);
		}
		catch(NullPointerException | IllegalArgumentException ex){
			allQuestionIds = new ArrayList<Long>();
		}
		
		return allQuestionIds == null ?  new ArrayList<Long>() : allQuestionIds;
	}
	
	private List<Question> getQuestionsWithDetails(List<Long> allQuestionIds) throws EmptyResultSetException
	{
		List<Question> questions = new ArrayList<Question>();
		try
		{
		/*	final String GET_DASHBOARD_QUESTIONS = "select question_id,question_title,question_desc,author_email_id,last_answer,updated_date,author_first_name,author_last_name,is_image_attached from questions where question_id in ("+finalQuestionIds+") allow filtering";
			questions = cassandraTemplate.query(GET_DASHBOARD_QUESTIONS,new QuestionMapper());*/
		

			for (Long questionId : allQuestionIds) {
				
				final String GET_QUESTION_DETAILS = "select * from questions where question_id="+questionId+" allow filtering";
				questions.add(cassandraTemplate.queryForObject(GET_QUESTION_DETAILS, new QuestionMapper()));
			}
		
		}
		catch(EmptyResultDataAccessException emptyResultSet)
		{
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
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
		Select selectUserQuestionIds = QueryBuilder.select("question_id").from("questions");
		selectUserQuestionIds.where(QueryBuilder.eq("author_email_id", userEmailId));
		List<Long> userQuestionIds = new ArrayList<Long>();
		try {
			userQuestionIds = cassandraTemplate.queryForList(selectUserQuestionIds,Long.class);
		}
		catch(NullPointerException | IllegalArgumentException ex){
			userQuestionIds = new ArrayList<Long>();
		}
		return userQuestionIds == null ? new ArrayList<Long>(): userQuestionIds;
	}

	/**
	 * Adds an answer posted by a User to a Question
	 * @author balaji i
	 * @param questionId
	 * @param answer
	 */
	@Override
	public void addAnswer(Answer answer) {
		try{
			
			Long answerId = Long.valueOf(getLastInsertedAnswerId()) + 1;
			
			answer.setAnswerId(answerId);
			
			User userDetails = getUserDetailsByEmailId(answer.getAuthorEmailId());
	        
			/** Question Along with Image */
			if(answer.getIsImageAttached() != null && answer.getIsImageAttached())
			{
				try {
					byte[] originalImage = answer.getAnswerImage().getBytes();
					
					byte[] compressedImage = null;
					
					if(YMessCommonUtility.getFileExtension(answer.getAnswerImage().getOriginalFilename()).equalsIgnoreCase("jpg") 
							|| YMessCommonUtility.getFileExtension(answer.getAnswerImage().getOriginalFilename()).equalsIgnoreCase("jpeg") )
						compressedImage = YMessCommonUtility.returnCompressedImage(originalImage);
					else
						compressedImage = originalImage;
					
					
					Insert insertQuery = YMessCommonUtility.getFormattedInsertQuery("answers", "question_id,answer_id,answered_time,answer_desc,"
							+ "author_email_id,author_first_name,"
							+ "author_last_name,image_data,is_image_attached",
							new Object[]{
							answer.getQuestionId(),answerId,new Date(),answer.getAnswerDescription(),
							answer.getAuthorEmailId(),userDetails.getFirstName(),userDetails.getLastName(),
							ByteBuffer.wrap(compressedImage),answer.getIsImageAttached()});
					
					cassandraTemplate.execute(insertQuery);
					
					/*cassandraTemplate.update(ADD_ANSWER_WITH_IMAGE,
							new Object[]{answer.getQuestionId(),newAnswerId,new Date(),answer.getAnswerDescription(),answer.getAuthorEmailId(),userDetails.getFirstName(),userDetails.getLastName(),compressedImage,answer.getIsImageAttached()},
							new int[]{Types.BIGINT,Types.BIGINT,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.BINARY,Types.BOOLEAN}
							);*/
					
					final String GET_QUESTION_AUTHOR = "select author_email_id from questions where question_id="+answer.getQuestionId()+" allow filtering";
					String questionAuthorEmailId = cassandraTemplate.queryForObject(GET_QUESTION_AUTHOR, String.class);
						
					final String GET_QUESTION_DESC = "select question_desc from questions where question_id="+answer.getQuestionId()+" allow filtering";
					String questionDesc = cassandraTemplate.queryForObject(GET_QUESTION_DESC, String.class);
						
					//final String UPDATE_QUESTION_LAST_ANSWER = "update questions set last_answer=? where question_id="+answer.getQuestionId()+" and author_email_id="+questionAuthorEmailId;
					//cassandraTemplate.update(UPDATE_QUESTION_LAST_ANSWER,new Object[]{answer.getAnswerDescription()},
					//		new int[]{Types.VARCHAR});

					String ADD_ANSWER_TIMELINE="insert into user_timeline(user_email_id, user_timestamp, activity, user_first_name, user_last_name, answered_question_id, answered_question_desc, answered_question_email_id, answered_answered_id, answered_answered_desc, is_answered_question,answered_answered_is_image_attached) values(?,?,?,?,?,?,?,?,?,?,?,?)";
					
					Insert insertUserTimeline = YMessCommonUtility.getFormattedInsertQuery(
							"user_timeline", "user_email_id, user_timestamp, activity, "
							+ "user_first_name, user_last_name, answered_question_id,"
							+ " answered_question_desc, answered_question_email_id, answered_answered_id,"
							+ " answered_answered_desc, is_answered_question,answered_answered_is_image_attached",
							new Object[]{
									answer.getAuthorEmailId(), 
									new Date(),
									YMessActivityConstants.ANSWERED_QUESTION,
									userDetails.getFirstName(),
									userDetails.getLastName(),
									answer.getQuestionId(), 
									questionDesc, GET_QUESTION_AUTHOR, 
									answerId, 
									answer,  
									YMessCommonUtility.IS_ANSWERED_QUESTIONS,
									true});
					cassandraTemplate.execute(insertUserTimeline);
					
					/*cassandraTemplate.update(ADD_ANSWER_TIMELINE, new Object[]{answer.getAuthorEmailId(), new Date(), ActivityConstants.ANSWERED_QUESTION, userDetails.getFirstName(),userDetails.getLastName(),answer.getQuestionId(), questionDesc, GET_QUESTION_AUTHOR, newAnswerId, answer,  YMessCommonUtility.IS_ANSWERED_QUESTIONS,true},
							new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.BIGINT,Types.VARCHAR,Types.BOOLEAN,Types.BOOLEAN}
							);*/
				}
				catch(IOException e)
				{
					logger.error(e.getStackTrace());
				}
			}
			else
			{
				/*cassandraTemplate.update(ADD_ANSWER,
					new Object[]{answer.getQuestionId(),newAnswerId,new Date(),answer.getAnswerDescription(),answer.getAuthorEmailId(),userDetails.getFirstName(),userDetails.getLastName()},
					new int[]{Types.BIGINT,Types.BIGINT,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
					);*/
				
				Insert insertAnswer = YMessCommonUtility.getFormattedInsertQuery("answers", "question_id,answer_id,answered_time,"
						+ "answer_desc,author_email_id,author_first_name,author_last_name",
						new Object[]{
						answer.getQuestionId(),
						answerId,
						new Date(),
						answer.getAnswerDescription(),
						answer.getAuthorEmailId(),
						userDetails.getFirstName(),
						userDetails.getLastName()});
				
				cassandraTemplate.execute(insertAnswer);
				
				final String GET_QUESTION_AUTHOR = "select author_email_id from questions where question_id="+answer.getQuestionId()+" allow filtering";
				String questionAuthorEmailId = cassandraTemplate.queryForObject(GET_QUESTION_AUTHOR, String.class);
					
				final String GET_QUESTION_DESC = "select question_desc from questions where question_id="+answer.getQuestionId()+" allow filtering";
				String questionDesc = cassandraTemplate.queryForObject(GET_QUESTION_DESC, String.class);
					
				//final String UPDATE_QUESTION_LAST_ANSWER = "update questions set last_answer=? where question_id="+answer.getQuestionId()+" and author_email_id="+questionAuthorEmailId;
			    //cassandraTemplate.update(UPDATE_QUESTION_LAST_ANSWER,answer.getAnswerDescription());

			/*	cassandraTemplate.update(ADD_ANSWER_TIMELINE, new Object[]{answer.getAuthorEmailId(), new Date(), ActivityConstants.ANSWERED_QUESTION, userDetails.getFirstName(),userDetails.getLastName(),answer.getQuestionId(), questionDesc, GET_QUESTION_AUTHOR, newAnswerId, answer,  YMessCommonUtility.IS_ANSWERED_QUESTIONS},
					new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.BIGINT,Types.VARCHAR,Types.BOOLEAN}
					);*/
				
				Insert insertAnswerTimeline = YMessCommonUtility.getFormattedInsertQuery("user_timeline", "user_email_id, user_timestamp, activity, "
						+ "user_first_name, user_last_name, answered_question_id, "
						+ "answered_question_desc, answered_question_email_id, "
						+ "answered_answered_id, answered_answered_desc, is_answered_question",
						new Object[]{
						answer.getAuthorEmailId(), 
						new Date(), 
						YMessActivityConstants.ANSWERED_QUESTION, 
						userDetails.getFirstName(),
						userDetails.getLastName(),
						answer.getQuestionId(),
						questionDesc,
						GET_QUESTION_AUTHOR,
						answerId, 
						answer, 
						YMessCommonUtility.IS_ANSWERED_QUESTIONS});
				
				cassandraTemplate.execute(insertAnswerTimeline);
				
			}
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}


	private class AnswerMapper implements RowMapper<Answer>
	{
		@Override
		public Answer mapRow(Row rs, int arg1) throws DriverException {
			Answer answer = new Answer();
			answer.setQuestionId(rs.getLong("question_id"));
			answer.setAnswerDescription(rs.getString("answer_desc"));
			answer.setAnsweredTime(rs.getDate("answered_time"));
			answer.setAuthorEmailId(rs.getString("author_email_id"));
			answer.setDownvoteCount(rs.getLong("downvote_count"));
			answer.setUpvoteCount(rs.getLong("upvote_count"));
			answer.setAnswerId(rs.getLong("answer_id"));
			answer.setFirstName(rs.getString("author_first_name"));
			answer.setLastName(rs.getString("author_last_name"));
			answer.setUpvotedUsers(rs.getSet("upvoted_users",String.class));
			answer.setDownvotedUsers((Set<String>)rs.getSet("downvoted_users",String.class));
			answer.setIsImageAttached(rs.getBool("is_image_attached"));
			
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
		
		//UUID questionId = UUID.fromString(decodedQuestionId);
		
		long questionId = Long.parseLong(decodedQuestionId);
		try {
			final String FETCH_USER_ANSWERS_FOR_QUESTION ="select question_id,answer_id,answered_time,answer_desc,author_email_id,downvote_count,upvote_count,author_first_name,author_last_name,upvoted_users,downvoted_users,is_image_attached from answers where question_id="+questionId+" order by answer_id desc";
			answers = cassandraTemplate.query(FETCH_USER_ANSWERS_FOR_QUESTION, new AnswerMapper());
			
			Collections.sort(answers,new Comparator<Answer>() {
				@Override
				public int compare(Answer o1, Answer o2) {
					int compare = 0;
					
					compare = o2.getAnswerId().compareTo(o1.getAnswerId());
					
					// TODO Auto-generated method stub
					return compare;
				}
			});
			
		}
		catch(EmptyResultDataAccessException emptyResultSet)
		{
			logger.error(emptyResultSet.getLocalizedMessage());
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
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
		
		List<Long> questionIds = cassandraTemplate.queryForList(GET_QUESTION_IDS,Long.class);
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
	 * Retrieves the last inserted user Id.
	 * @author rvishwakarma
	 * @return lastInsertedUserId(String)
	 */
	private Long getLastInsertedUserId() 
	{
		long maxUserId = 0;
		
		List<Long> userIds = cassandraTemplate.queryForList(GET_USER_IDS,Long.class);
			try
			{
				if(!userIds.isEmpty())
					maxUserId = Collections.max(userIds);
			}
			catch(EmptyResultDataAccessException emptyRS)
			{
				logger.error(emptyRS.getLocalizedMessage());
			}
		return maxUserId;
	}
	
	/**
	 * Retrieves the last inserted Answer Id.
	 * @author balaji i
	 * @return lastInsertedQuestionId(String)
	 */
	private Long getLastInsertedAnswerId() 
	{
		Long maxAnswerId = 0L;
		
		List<Long> answerIds = cassandraTemplate.queryForList(GET_ANSWER_IDS,Long.class);
			try
			{
				if(!answerIds.isEmpty())
					maxAnswerId = Collections.max(answerIds);
			}
			catch(EmptyResultDataAccessException emptyRS)
			{
				logger.error(emptyRS.getLocalizedMessage());
			}
		return maxAnswerId == null ? 0 : maxAnswerId;
	}

	private class UserMapper implements RowMapper<User>{
		@Override
		public User mapRow(Row rs, int arg1) throws DriverException {
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
			upvotedUserEmailIds = cassandraTemplate.queryForObject(GET_UPVOTED_USER_EMAIL_IDS,HashSet.class);
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
		}
		String emailIds = "";
		if(upvotedUserEmailIds != null)
			emailIds = YMessCommonUtility.getEmailIdListAsString(upvotedUserEmailIds);
		
		final String GET_USER_DETAILS = "select email_id,first_name,last_name from users_data where email_id in ("+emailIds+")";
		List<User> users = new ArrayList<User>();
		try{
			users = cassandraTemplate.query(GET_USER_DETAILS, new UserMapper());
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
		}
		return users;
	}

	private class UserDetailMapper implements RowMapper<User>
	{
		@Override
		public User mapRow(Row rs, int arg1) throws DriverException {
			User user = new User();
			
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setUserEmailId(rs.getString("email_id"));
			user.setOrganization(rs.getString("organization"));
			user.setPreviousOrganizations(rs.getSet("previous_organizations",String.class));
			user.setInterests(rs.getSet("interests",String.class));
			user.setDesignation(rs.getString("designation"));
			user.setFollowingUsers( rs.getMap("following_users", String.class, Date.class));
			
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
		final String GET_USER_DETAILS = "select * from users_data where email_id='"+loggedInUserEmail+"'";
		
		User user = new User();
		try{
			user = cassandraTemplate.queryForObject(GET_USER_DETAILS, new UserDetailMapper());
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
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

		final String GET_USER_PREVIOUS_INTERESTS = "select interests from users_data where email_id='"+user.getUserEmailId()+"'";
		/** Fetching User's previous Interests to Compare and check if anything has been updated */
		Set<String> previousInterests = new HashSet<String>();
		try {
			previousInterests = cassandraTemplate.queryForObject(GET_USER_PREVIOUS_INTERESTS, Set.class);
		}
		catch(NullPointerException npe){
			previousInterests = new HashSet<String>();
		}
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
		
		try { 
			
			Insert insertUserProfile = YMessCommonUtility.getFormattedInsertQuery("users_data", "email_id,first_name,last_name,designation,"
					+ "organization,previous_organizations,interests", 
					new Object[]{
					user.getUserEmailId(), 
					user.getFirstName(), 
					user.getLastName(), 
					user.getDesignation(),
					user.getOrganization(), 
					previousOrganizations ,
					interests});
			
			cassandraTemplate.execute(insertUserProfile);
			
			Insert insertUserProfileTimeline = YMessCommonUtility.getFormattedInsertQuery("user_timeline",
					"user_email_id, user_timestamp, activity, user_first_name, user_last_name, "
					+ "profile_updated_first_name, profile_updated_last_name, "
					+ "profile_updated_organization, profile_updated_designation, "
					+ "profile_updated_previous_organizations, profile_updated_interests, is_updated_profile",
					 new Object[]{user.getUserEmailId(),new Date(),YMessActivityConstants.PROFILE_UPDATED,
					user.getFirstName(),user.getLastName(),user.getFirstName(),user.getLastName(),
					user.getOrganization(),user.getDesignation(),previousOrganizations, interests,
					YMessCommonUtility.IS_PROFILE_UPDATED});
			
			cassandraTemplate.execute(insertUserProfileTimeline);
			
			/*cassandraTemplate.update(UPDATE_USER_PROFILE_TIMELINE, new Object[]{user.getUserEmailId(),new Date(),ActivityConstants.PROFILE_UPDATED,user.getFirstName(),user.getLastName(),user.getFirstName(),user.getLastName(),user.getOrganization(),user.getDesignation(),previousOrganizations, interests,YMessCommonUtility.IS_PROFILE_UPDATED},
					new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.ARRAY,Types.ARRAY,Types.BOOLEAN}
			);
			*/
			
			
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
					final String GET_INTEREST_COUNT = "Select Count(*) from user_interests where interest = '"+interest+"'";
					Long interestCount = cassandraTemplate.queryForObject(GET_INTEREST_COUNT,Long.class);
				
				if(interestCount != 0)
				{
					final String UPDATE_INTEREST ="Update user_interests set contributors = contributors + {'"+user.getUserEmailId()+"'} ,contributor_count="+ interestCount+1 +" where interest='"+interest+"'";
					cassandraTemplate.execute(UPDATE_INTEREST);
				}
				else
				{
					Set<String> emailId = new HashSet<String>();
					emailId.add(user.getUserEmailId());
					
				    Insert insertIntoUserInterests = YMessCommonUtility.getFormattedInsertQuery("user_interests", 
				    		"interest,contributors,contributor_count,created_date", 
				    		new Object[]{interest,emailId,1,new Date() });

				    cassandraTemplate.execute(insertIntoUserInterests);
				}
			}
				
			}
			/** New Interests Added By User in Form */
			Set<String> newInterests = user.getInterests();
			Set<String> deletedInterests = new HashSet<String>();
			Set<String> addedInterests = new HashSet<String>();
			
			
			if(previousInterests != null)
				previousInterests = YMessCommonUtility.removeNullAndEmptyElements(previousInterests);
			
			if(newInterests != null)
				newInterests = YMessCommonUtility.removeNullAndEmptyElements(newInterests);
			
			Map<String,Set<String>> mergedSets = YMessCommonUtility.compareSetsAndReturnAddedAndDeletedObjects(previousInterests,newInterests);
			
			/** Nothing has been changed in terms of topics */
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
			deletedInterest = YMessCommonUtility.removeExtraneousApostrophe(deletedInterest);
			
			/** User Deleted Interests */
			if(deletedTopicsFlag)
			{
				try{
			
				final String GET_USER_COUNT_FOR_TOPIC = "select user_count from topics where topic='"+deletedInterest+"'";
				Long userCount = cassandraTemplate.queryForObject(GET_USER_COUNT_FOR_TOPIC,Long.class);	
					
				String REMOVE_USER_FROM_TOPICS = "UPDATE topics SET user_ids = user_ids - {'"+ userEmailId +"'},user_count = "+(userCount-1)+" WHERE topic ='"+deletedInterest+"'";
				cassandraTemplate.update(REMOVE_USER_FROM_TOPICS);
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
				String GET_TOPIC_COUNT = "select count(1) from topics where topic='"+deletedInterest+"'";
				Long topicCount = cassandraTemplate.queryForObject(GET_TOPIC_COUNT,Long.class);
				
				if(topicCount > 0)
				{
					try{
						
						final String GET_USER_COUNT_FOR_TOPIC = "select user_count from topics where topic='"+deletedInterest+"'";
						Long userCount = cassandraTemplate.queryForObject(GET_USER_COUNT_FOR_TOPIC,Long.class);
						
						String ADD_USER_TO_TOPICS = "UPDATE topics SET user_ids = user_ids + {'"+ userEmailId +"'},user_count="+(userCount+1)+" WHERE topic = '"+deletedInterest+"'";
						cassandraTemplate.update(ADD_USER_TO_TOPICS);
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
						
					Insert insertintoTopics = YMessCommonUtility.getFormattedInsertQuery("topics", "topic,user_count,user_ids", new Object[]{deletedInterest,1,userIds});	
					cassandraTemplate.insert(insertintoTopics);
					
					/*cassandraTemplate.insert(ADD_USER_INTEREST,
								new Object[]{deletedInterest,1,userIds},
								new int[]{Types.VARCHAR,Types.BIGINT,Types.ARRAY}
							);*/
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

	
	private class UserImageMapper implements RowMapper<User>
	{

		@Override
		public User mapRow(Row rs, int arg1) throws DriverException {
			User user = new User();
			
			if(rs.getBytes("user_image") != null){
				byte[] userImage = new byte[rs.getBytes("user_image").remaining()];
				rs.getBytes("user_image").get(userImage);
				user.setUserImageData(userImage);
			}
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
		
		final String GET_USER_IMAGE = "select user_image,user_image_name from users_data where email_id='"+loggedInUserEmail +"'";
		User userImage = cassandraTemplate.queryForObject(GET_USER_IMAGE, new UserImageMapper());
		return userImage == null ? new User() : userImage;
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
			try {
				byte [] originalImage = user.getUserImage().getBytes();
				
				byte[] compressedImage = null;
				if(YMessCommonUtility.getFileExtension(user.getUserImage().getOriginalFilename()).equalsIgnoreCase("jpg") 
						|| YMessCommonUtility.getFileExtension(user.getUserImage().getOriginalFilename()).equalsIgnoreCase("jpeg") )
					compressedImage = YMessCommonUtility.returnCompressedImage(originalImage);
				else
					compressedImage = originalImage;
				
				//final String INSERT_IMAGE = "update users_data set user_image_name= '"+imageName+"', user_image = "+ByteBuffer.wrap(compressedImage)+" where email_id ='"+user.getUserEmailId()+"'";
				
				Update updateUserImage = QueryBuilder.update("users_data");
				updateUserImage.with(QueryBuilder.set("user_image_name", imageName));
				updateUserImage.with(QueryBuilder.set("user_image", ByteBuffer.wrap(compressedImage)));
				
				updateUserImage.where(QueryBuilder.eq("email_id", user.getUserEmailId()));
				
				cassandraTemplate.execute(updateUserImage);
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
			upvoteCount = cassandraTemplate.queryForObject(SELECT_UPVOTE_COUNT,Long.class);
			
			
			final String UPVOTE_ANSWER = "update answers set upvoted_users = upvoted_users + {'"+loggedInUserEmail+"'} , upvote_count = "+upvoteCount+1+" where question_id="+questionIdLong+" and answer_id="+answerIdLong;
			cassandraTemplate.update(UPVOTE_ANSWER);
			
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
			downvoteCount = cassandraTemplate.queryForObject(SELECT_DOWNVOTE_COUNT,Long.class);
			
			
			final String DOWNVOTE_ANSWER = "update answers set downvoted_users = downvoted_users + {'"+loggedInUserEmail+"'} , downvote_count = "+downvoteCount+1+" where question_id="+questionId+" and answer_id="+answerId;
			cassandraTemplate.update(DOWNVOTE_ANSWER);
			
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
		
		final String USER_FOLLOW = "update users_data set following_users = following_users + {'"+decodedUserEmailId+"':'"+currentTime+"'} where email_id='"+loggedInUserEmail+"'";
		cassandraTemplate.execute(USER_FOLLOW);
		
		final String FOLLOWED_BY = "update users_data set followed_by_users = followed_by_users + {'"+loggedInUserEmail+"':'"+currentTime+"'} where email_id='"+decodedUserEmailId+"'";
		cassandraTemplate.execute(FOLLOWED_BY);
		
		User userDetails = null;
		User decodeUserDetails = null;
		
		try {
			
			userDetails = getUserDetailsByEmailId(loggedInUserEmail);
			decodeUserDetails = getUserDetails(decodedUserEmailId);

		} catch (EmptyResultSetException ex) {
			
			logger.error(ex.getLocalizedMessage());
		}
		/*cassandraTemplate.update(ADD_FOLLOWING_TIMELINE, new Object[]{userDetails.getUserEmailId(),new Date(),ActivityConstants.FOLLOWING,userDetails.getFirstName(),userDetails.getLastName(), decodeUserDetails.getFirstName(), decodeUserDetails.getLastName(),decodeUserDetails.getUserEmailId(),  YMessCommonUtility.IS_FOLLOWING},
				new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN}
		);*/
		
		
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
		
		final String GET_FOLLOWING_USERS = "select following_users from users_data where email_id='"+loggedInUserEmail+"'";
		Map<String,Date> followingUsersMap = new HashMap<String, Date>();
		List<User> followingUsers = new ArrayList<User>();
		
		try{
			followingUsersMap = cassandraTemplate.queryForObject(GET_FOLLOWING_USERS, Map.class);
		}
		catch(EmptyResultDataAccessException emptyRs)
		{
			logger.error(YMessMessageConstants.EMPTY_RESULT_SET);
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
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
				followingUsers = cassandraTemplate.query(GET_USERS,new UserMapper());
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
		
		final String GET_FOLLOWERS = "select followed_by_users from users_data where email_id='"+loggedInUserEmail+"'";
		Map<String,Date> followersMap = new HashMap<String, Date>();
		List<User> followers = new ArrayList<User>();
		
		try{
			followersMap = cassandraTemplate.queryForObject(GET_FOLLOWERS, Map.class);
		}
		catch(EmptyResultDataAccessException emptyRs)
		{
			logger.error(YMessMessageConstants.EMPTY_RESULT_SET);
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
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
				followers = cassandraTemplate.query(GET_USERS,new UserMapper());
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
		String GET_FOLLOWING_USERS_MAP = "select following_users from users_data where email_id='"+loggedInUserEmailId+"'";
		Map<String,Date> followingUsers = new HashMap<String, Date>();
		try{
			followingUsers = cassandraTemplate.queryForObject(GET_FOLLOWING_USERS_MAP, Map.class);
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
		String UNFOLLOW_USER ="delete following_users ['"+decodedUserEmailId+"'] from users_data where email_id='"+loggedInUserEmail+"'";
		try{
			cassandraTemplate.update(UNFOLLOW_USER);
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
			downvotedUserEmailIds = cassandraTemplate.queryForObject(GET_DOWNVOTED_USER_EMAIL_IDS,HashSet.class);
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
		}
		String emailIds = "";
		if(downvotedUserEmailIds != null)
			emailIds = YMessCommonUtility.getEmailIdListAsString(downvotedUserEmailIds);
		
		final String GET_USER_DETAILS = "select email_id,first_name,last_name from users_data where email_id in ("+emailIds+")";
		List<User> users = new ArrayList<User>();
		try{
			users = cassandraTemplate.query(GET_USER_DETAILS, new UserMapper());
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
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
			String CHECK_IF_USER_EXISTS = "select count(*) from users where username='"+userEmailId+"'";
			int isRegisteredCount = cassandraTemplate.queryForObject(CHECK_IF_USER_EXISTS,Integer.class);
			
			if(isRegisteredCount > 0)
				isRegisterd = true;
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return isRegisterd;
	}
	
	private class QuestionImageMapper implements RowMapper<Question>{

		@Override
		public Question mapRow(Row rs, int arg1) throws DriverException {
			Question question = new Question();
			
			question.setQuestionImageName(rs.getString("image_name"));
			
			if(rs.getBytes("image_data") != null){
				byte[] imageData = new byte[rs.getBytes("image_data").remaining()];
				rs.getBytes("image_data").get(imageData);
				question.setQuestionImageDB(imageData);
			}
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
		Question questionImageDetails = cassandraTemplate.queryForObject(GET_USER_IMAGE, new QuestionImageMapper());
		
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
		
		final String GET_USER_TIMELINE = "select * from user_timeline where user_email_id='"+loggedInUserEmail+"' order by user_timestamp asc";

		List<TimeLine> timeline = new ArrayList<TimeLine>();
		try{
		timeline = cassandraTemplate.query(GET_USER_TIMELINE,new UserTimeLineMapper());
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
		}
		return timeline;
	}
	
	private class UserTimeLineMapper implements RowMapper<TimeLine>
	{

		@Override
		public TimeLine mapRow(Row rs, int arg1) throws DriverException {
			TimeLine timeLine = new TimeLine();
			timeLine.setUserEmailId(rs.getString("user_email_id"));
			timeLine.setUserTimestamp(rs.getString("user_timestamp"));
			timeLine.setActivity(rs.getString("activity"));
			timeLine.setUserFirstName(rs.getString("user_first_name"));
			timeLine.setUserLastName(rs.getString("user_last_name"));
			
			timeLine.setJoinedJoiningDate(rs.getDate("joined_joining_date"));
			timeLine.setHasJoined(rs.getBool("is_joined"));
			
			timeLine.setProfileUpdatedFirstName(rs.getString("profile_updated_first_name"));
			timeLine.setProfileUpdatedLastName(rs.getString("profile_updated_last_name"));
			timeLine.setProfileUpdatedOrganization(rs.getString("profile_updated_organization"));
			timeLine.setProfileUpdatedDesignation(rs.getString("profile_updated_designation"));
			timeLine.setProfileUpdatedPreviousOrganizations( rs.getSet("profile_updated_previous_organizations",String.class));
			timeLine.setProfileUpdatedInterests(rs.getSet("profile_updated_interests",String.class));
			timeLine.setHasUpdatedProfile(rs.getBool("is_updated_profile"));
			
			timeLine.setQuestionPostedId(rs.getLong("question_posted_id"));
			timeLine.setQuestionPostedTitle(rs.getString("question_posted_title"));
			timeLine.setQuestionPostedDesc(rs.getString("question_posted_desc"));
			timeLine.setQuestionIsImageAttached(rs.getBool("question_is_image_attached"));
			timeLine.setQuestionTopics((Set<String>)rs.getSet("question_topics",String.class));
			timeLine.setQuestionUpdatedDate(rs.getDate("question_updated_date"));
			timeLine.setQuestionLastAnswer(rs.getString("question_last_answer"));
			timeLine.setHasPostedQuestion(rs.getBool("is_posted_question"));
			
			timeLine.setAnsweredQuestionId(rs.getLong("answered_question_id"));
			timeLine.setAnsweredQuestionTitle(rs.getString("answered_question_title"));
			timeLine.setAnsweredQuestionDesc(rs.getString("answered_question_desc"));
			timeLine.setAnsweredQuestionEmailId(rs.getString("answered_question_email_id"));
			timeLine.setAnsweredQuestionIsImageAttached(rs.getBool("answered_question_is_image_attached"));
			timeLine.setAnsweredQuestionTopics(rs.getSet("answered_question_topics",String.class));
			timeLine.setAnsweredAnsweredId(rs.getLong("answered_answered_id"));
			timeLine.setAnsweredAnsweredDesc(rs.getString("answered_answered_desc"));
			timeLine.setHasAnsweredQuestion(rs.getBool("is_answered_question"));
			
			timeLine.setFollowingFirstName(rs.getString("following_first_name"));
			timeLine.setFollowingLastName(rs.getString("following_last_name"));
			timeLine.setFollowingEmailId(rs.getString("following_email_id"));
			timeLine.setHasFollowing(rs.getBool("is_following"));
			
			timeLine.setUpvotedQuestionId(rs.getLong("upvoted_question_id"));
			timeLine.setUpvotedQuestionDesc(rs.getString("upvoted_question_desc"));
			timeLine.setUpvotedQuestionEmailId(rs.getString("upvoted_question_email_id"));
			timeLine.setUpvotedAnswerId(rs.getLong("upvoted_answer_id"));
			timeLine.setUpvotedAnswerDesc(rs.getString("upvoted_answer_desc"));
			timeLine.setHasUpvoted(rs.getBool("is_upvoted"));
			
			timeLine.setDownvotedQuestionId(rs.getLong("downvoted_question_id"));
			timeLine.setDownvotedQuestionDesc(rs.getString("downvoted_question_desc"));
			timeLine.setDownvotedQuestionEmailId(rs.getString("downvoted_question_email_id"));
			timeLine.setDownvotedAnswerId(rs.getLong("downvoted_answer_id"));
			timeLine.setDownvotedAnswerDesc(rs.getString("downvoted_answer_desc"));
			timeLine.setHasDownvoted(rs.getBool("is_downvoted"));
			
			timeLine.setActivity(rs.getString("shared_question_id"));
			timeLine.setActivity(rs.getString("shared_question_desc"));
			timeLine.setActivity(rs.getString("shared_question_email_id"));
			timeLine.setHasShared(rs.getBool("is_shared"));
			
			timeLine.setLikedQuestionId(rs.getLong("liked_question_id"));
			timeLine.setLikedQuestionDesc(rs.getString("liked_question_desc"));
			timeLine.setLikedQuestionEmailId(rs.getString("liked_question_email_id"));
			timeLine.setHasLikedQuestion(rs.getBool("is_liked_question"));
			
			timeLine.setLikedAnswerId(rs.getString("liked_answer_id"));
			timeLine.setLikedAnswerDesc(rs.getString("liked_answer_desc"));
			timeLine.setLikedAnswerEmailId(rs.getString("liked_answer_email_id"));
			timeLine.setHasLikedAnswer(rs.getBool("is_liked_answer"));
			
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

		topicName = YMessCommonUtility.removeExtraneousApostrophe(topicName);
		topicName = topicName.trim().toLowerCase();
		
		String GET_QUESTION_IDS_IN_TOPIC = "select question_ids from topics where topic='"+topicName+"'";
		Set<Long> questionIds = (Set<Long>) cassandraTemplate.queryForObject(GET_QUESTION_IDS_IN_TOPIC, Set.class);
		
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
	
	private static class QuestionDetailsMapper implements RowMapper<Question> {
		@Override
		public Question mapRow(Row rs, int arg1) throws DriverException {
			Question question = new Question();
			question.setQuestionId(rs.getLong("question_id"));
			question.setQuestionDescription(rs.getString("question_desc")); 
			
			if(rs.getDate("updated_date") != null)
				question.setUpdatedDate(rs.getDate("updated_date"));
			
			question.setLastAnswer(rs.getString("last_answer"));
			question.setAuthorEmailId(rs.getString("author_email_id"));
			question.setFirstName(rs.getString("author_first_name"));
			question.setLastName(rs.getString("author_last_name"));
			question.setIsImageAttached(rs.getBool("is_image_attached"));
			
			question.setTopics(rs.getSet("topics", String.class));
			
			return question;
		}
}
	
	
	private static class QuestionWithoutTopicsMapper implements RowMapper<Question> {
		@Override
		public Question mapRow(Row rs, int arg1) throws DriverException {
			Question question = new Question();
			question.setQuestionId(rs.getLong("question_id"));
			question.setQuestionDescription(rs.getString("question_desc")); 
			
			if(rs.getDate("updated_date") != null)
				question.setUpdatedDate(rs.getDate("updated_date"));
			
			question.setLastAnswer(rs.getString("last_answer"));
			question.setAuthorEmailId(rs.getString("author_email_id"));
			question.setFirstName(rs.getString("author_first_name"));
			question.setLastName(rs.getString("author_last_name"));
			question.setIsImageAttached(rs.getBool("is_image_attached"));
			
			return question;
		}
}
	private List<Question> getQuestionDetailsWithoutTopics(String finalQuestionIds) throws EmptyResultSetException
	{
		List<Question> questions = new ArrayList<Question>();
		try
		{
			final String GET_DASHBOARD_QUESTIONS = "select question_id,question_desc,author_email_id,last_answer,updated_date,author_first_name,author_last_name,is_image_attached from questions where question_id in ("+finalQuestionIds+") allow filtering";
			questions = cassandraTemplate.query(GET_DASHBOARD_QUESTIONS,new QuestionWithoutTopicsMapper());
		}
		catch(EmptyResultDataAccessException emptyResultSet)
		{
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
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
			topics = cassandraTemplate.queryForList(GET_TOPIC_NAMES,String.class);
		}
		catch(EmptyResultDataAccessException emptyRs)
		{
			logger.error(YMessMessageConstants.EMPTY_RESULT_SET);
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
		}
		return topics;
	}

	
	private boolean updateTopicsInFiles(Set<String> deletedTopics,Long fileId,Boolean deletedTopicsFlag,String authorEmaild) {
		boolean success = false;
		
		for (String deletedInterest : deletedTopics) {
			deletedInterest = deletedInterest.trim().toLowerCase();
			deletedInterest = YMessCommonUtility.removeExtraneousApostrophe(deletedInterest);
			
			/** User Deleted Interests */
			if(deletedTopicsFlag)
			{
				try{
			
				final String GET_FILE_COUNT_FOR_TOPIC = "select file_count from topics where topic='"+deletedInterest+"'";
				Long oldFileCount = cassandraTemplate.queryForObject(GET_FILE_COUNT_FOR_TOPIC,Long.class);	
					
				final String REMOVE_USER_FROM_TOPICS = "Delete file_ids["+fileId+"] from topics where topic ='"+deletedInterest+"'";
				cassandraTemplate.update(REMOVE_USER_FROM_TOPICS);
				
				final String UPDATE_FILE_COUNT = "update topics set file_count = "+(oldFileCount - 1)+" where topic ='"+deletedInterest+"'";
				cassandraTemplate.update(UPDATE_FILE_COUNT);
				
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
				String GET_TOPIC_COUNT = "select count(1) from topics where topic='"+deletedInterest+"'";
				Long topicCount = cassandraTemplate.queryForObject(GET_TOPIC_COUNT,Long.class);
				
				if(topicCount > 0)
				{
					try{
						
						final String GET_FILE_COUNT_FOR_TOPIC = "select file_count from topics where topic='"+deletedInterest+"'";
						Long oldFileCount = cassandraTemplate.queryForObject(GET_FILE_COUNT_FOR_TOPIC,Long.class);
						
						String ADD_FILE_TO_TOPICS = "UPDATE topics SET file_ids = file_ids + {"+ fileId +":'"+ authorEmaild +"'},file_count="+(oldFileCount + 1)+" WHERE topic = '"+deletedInterest+"'";
						cassandraTemplate.update(ADD_FILE_TO_TOPICS);
						success = true;
						}
						catch(Exception ex)
						{
							logger.error(ex.getMessage());
						}
				}
				else
				{
					Map<Long,String> fileIds = new HashMap<Long,String>();
					fileIds.put(fileId,authorEmaild);
					
					String ADD_USER_INTEREST = "insert into topics(topic,file_count,file_ids) values(?,?,?)";
					try{
						
					Insert insertIntoTopics = YMessCommonUtility.getFormattedInsertQuery("topics", "topic,file_count,file_ids", new Object[]{deletedInterest,1,fileIds});	
					cassandraTemplate.insert(insertIntoTopics);
					
					
					/*cassandraTemplate.update(ADD_USER_INTEREST,
								new Object[]{deletedInterest,1,fileIds},
								new int[]{Types.VARCHAR,Types.BIGINT,Types.OTHER}
							);*/
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

	
	
	
	/**
	 * Facilitates User File Upload
	 * @param file
	 * @throws IOException 
	 * @throws DataAccessException 
	 */
	@Override
	public void uploadFile(File file) throws DataAccessException, IOException {
		Long lastInsertedFileId = 0L,newFileId = 0L;
		if(null == file.getEditFlag())
		{
			lastInsertedFileId = getLastFileId();
		    newFileId = lastInsertedFileId + 1;
		}
		else
			newFileId = file.getFileId();
		
		Set<String> newFileTopics = file.getTopics();
		if(!newFileTopics.isEmpty())
			newFileTopics = YMessCommonUtility.removeNullAndEmptyElements(newFileTopics);
		
		
		if(null != file.getEditFlag() && file.getEditFlag())
		{
			final String GET_FILE_TOPICS = "select topics from files where user_email_id='"+file.getAuthorEmailId()+"' and file_id="+newFileId;
			Set<String> oldTopics = cassandraTemplate.queryForObject(GET_FILE_TOPICS, Set.class);
			
			if(!oldTopics.isEmpty())
				oldTopics = YMessCommonUtility.removeNullAndEmptyElements(oldTopics);
			
			if(! oldTopics.isEmpty() && !newFileTopics.isEmpty())
			{
				Map<String,Set<String>> mergedSets = YMessCommonUtility.compareSetsAndReturnAddedAndDeletedObjects(oldTopics, newFileTopics);
				
				if(! mergedSets.isEmpty() && ! mergedSets.containsKey(YMessCommonUtility.EQUAL_SET))
				{
					//Changes have been made
					Set<String> deletedTopics = mergedSets.get(YMessCommonUtility.DELETED_ITEMS);
					Set<String> addedTopics = mergedSets.get(YMessCommonUtility.ADDED_ITEMS);
					
					if(! deletedTopics.isEmpty())
					{
						updateTopicsInFiles(deletedTopics,newFileId,true,file.getAuthorEmailId());
					}
					
					if(! addedTopics.isEmpty())
					{
						updateTopicsInFiles(addedTopics,newFileId,false,file.getAuthorEmailId());
					}
				}
			}
		}
		
		User userDetails = getUserDetailsByEmailId(file.getAuthorEmailId());
		
		Insert insertIntoFiles = YMessCommonUtility.getFormattedInsertQuery("files", 
				"file_id,user_email_id,file_type,filename,share_flag,topics,"
				+ "filedata,upload_time,file_size,file_description,"
				+ "user_first_name,user_last_name", 
				new Object[]{
				newFileId,
				file.getAuthorEmailId(),
				YMessCommonUtility.getFileExtension(file.getFileData().getOriginalFilename()),
				file.getFileData().getOriginalFilename(),
				file.getShared(),
				newFileTopics,
				ByteBuffer.wrap(file.getFileData().getBytes()),
				new Date(),
				file.getFileSize(),
				file.getFileDescription(),
				userDetails.getFirstName(),
				userDetails.getLastName()
				});
		cassandraTemplate.execute(insertIntoFiles);
		
		
		/*cassandraTemplate.update(UPLOAD_FILE,
				new Object[]{newFileId,file.getAuthorEmailId(),YMessCommonUtility.getFileExtension(file.getFileData().getOriginalFilename()),file.getFileData().getOriginalFilename(),file.getShared(),newFileTopics,file.getFileData().getBytes(),new Date(),file.getFileSize(),file.getFileDescription(),
				userDetails.getFirstName(),userDetails.getLastName()
				},
				new int[]{Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.BOOLEAN,Types.ARRAY,Types.BINARY,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR
				}
				);*/
		
		if(file.getShared()!=null && file.getShared())
		{
			for (String fileTopic : newFileTopics) 
			{
				fileTopic = fileTopic.trim().toLowerCase();
				fileTopic = YMessCommonUtility.removeExtraneousApostrophe(fileTopic);
				String CHECK_IF_TOPIC_EXISTS = "select count(1) from topics where topic='"+fileTopic+"'";
				Long topicCount = cassandraTemplate.queryForObject(CHECK_IF_TOPIC_EXISTS,Long.class);
				
				if(topicCount > 0)
				{
					final String PREVIOUS_FILE_COUNT = "select file_count from topics where topic='"+fileTopic+"'";
					Long previousFileCount = cassandraTemplate.queryForObject(PREVIOUS_FILE_COUNT,Long.class);
					
					String UPDATE_TOPIC_WITH_FILE_DETAILS = "update topics set file_ids = file_ids + {"+newFileId+":'"+file.getAuthorEmailId() +"'}, file_count = "+ (previousFileCount+1) +" where topic='"+fileTopic+"'";
					cassandraTemplate.execute(UPDATE_TOPIC_WITH_FILE_DETAILS);
				}
				else
				{
					Map<Long,String> fileIdAndAuthorEmailId = new HashMap<Long, String>();
					fileIdAndAuthorEmailId.put(newFileId,file.getAuthorEmailId());
					
					Insert INSERT_INTO_TOPICS = QueryBuilder.insertInto("topics").values(new String[]{"topic","file_ids","file_count"}, 
							new Object[]{
							fileTopic,
							fileIdAndAuthorEmailId,
							1
					});
					
					
					//String INSERT_INTO_TOPICS = "insert into topics(topic,file_ids,file_count) values('"+fileTopic+"',"+fileIdAndAuthorEmailId+","+1+")";
					cassandraTemplate.execute(INSERT_INTO_TOPICS);
				}	
				
			}
		}
		/** While Editing,If The User Unchecks the Share File Checkbox */
		else
		{
			final String UPDATE_FILE_SHARED_FLAG ="update files set share_flag=false where user_email_id='"+file.getAuthorEmailId()+"' and file_id="+newFileId;
			cassandraTemplate.execute(UPDATE_FILE_SHARED_FLAG);
			
			for (String fileTopic : newFileTopics) 
			{
				fileTopic = fileTopic.trim().toLowerCase();
				fileTopic = YMessCommonUtility.removeExtraneousApostrophe(fileTopic);
					
				final String PREVIOUS_FILE_COUNT = "select file_count from topics where topic='"+fileTopic+"'";
				Long previousFileCount = cassandraTemplate.queryForObject(PREVIOUS_FILE_COUNT,Long.class);
				
				if(previousFileCount == null || (previousFileCount !=null && previousFileCount.equals(0))){
					previousFileCount = 0L;
				}
				
				final String DELETE_FILE_FROM_TOPICS = "DELETE file_ids[" +newFileId +"] FROM topics WHERE topic='"+fileTopic+"'";
				cassandraTemplate.execute(DELETE_FILE_FROM_TOPICS);
				
				final String UPDATE_FILE_COUNT = "update topics set file_count ="+(previousFileCount - 1)+" where topic='"+fileTopic+"'";
				cassandraTemplate.execute(UPDATE_FILE_COUNT);
				
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
		List<Long> fileIds = cassandraTemplate.queryForList(LAST_FILE_ID,Long.class);
		if(! fileIds.isEmpty())
			lastInsertedFileId = Collections.max(fileIds);
		}
		catch(EmptyResultDataAccessException emptyRS)
		{
			logger.error(emptyRS.getMessage());
		}
		return lastInsertedFileId;
	}
	
	private class FileDetailsMapper implements RowMapper<File>
	{

		@Override
		public File mapRow(Row rs, int arg1) throws DriverException {
			File fileDetails = new File();
			fileDetails.setFileId(rs.getLong("file_id"));
			fileDetails.setAuthorEmailId(rs.getString("user_email_id"));
			fileDetails.setFileType(rs.getString("file_type"));
			fileDetails.setFilename(rs.getString("filename"));
			fileDetails.setShared(rs.getBool("share_flag"));
			//fileDetails.setTopics((Set<String>) rs.getObject("topics"));
			fileDetails.setUploadedTime(rs.getDate("upload_time"));
			fileDetails.setFileSize(rs.getString("file_size"));
			fileDetails.setFileDescription(rs.getString("file_description"));
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
		final String GET_SHARED_FILES = "select file_id,user_email_id,file_type,filename,share_flag,topics,upload_time,file_size,file_description from files where share_flag=true"; 
		try{
			files = cassandraTemplate.query(GET_SHARED_FILES,new FileDetailsMapperWithTopics());
		}
		catch(EmptyResultDataAccessException emptyRs)
		{
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
		}
		return files;
	}

	private class FileDownloadMapper implements RowMapper<File>
	{
		@Override
		public File mapRow(Row rs, int arg1) throws DriverException {
			File file = new File();
			file.setFilename(rs.getString("filename"));
			if(rs.getBytes("filedata") != null){
				byte[] imageData = new byte[rs.getBytes("filedata").remaining()];
				rs.getBytes("filedata").get(imageData);
				file.setFileDataDb(imageData);
			}
			return file;
		}
	}
	
	
	/**
	 * Downloads the file data
	 * @author balaji i
	 * @param fileId
	 * @param authorEmailId
	 * @return File (downloadedFileDetails)
	 */
	@Override
	public File downloadFile(String fileId, String authorEmailId) {
		final String GET_FILE_DETAILS = "select filename,filedata from files where file_id="+fileId+" and user_email_id='"+authorEmailId+"'";
		File fileDetails = cassandraTemplate.queryForObject(GET_FILE_DETAILS,new FileDownloadMapper());
		return fileDetails;
	}
	/**
	 * Gets all the Files uploaded by an User
	 * @param loggedInUserEmail
	 * @return List<File>(Files uploaded by an User)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<File> getUserFiles(String loggedInUserEmail) throws EmptyResultSetException {
		List<File> userFiles = new ArrayList<File>();
		final String GET_USER_FILES = "select file_id,user_email_id,file_type,filename,share_flag,topics,upload_time,file_size,file_description from files where user_email_id='"+loggedInUserEmail+"'"; 
		try{
			userFiles = cassandraTemplate.query(GET_USER_FILES,new FileDetailsMapperWithTopics());
		}
		catch(EmptyResultDataAccessException emptyRs)
		{
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
		}
		return userFiles;
	}
	
	private class TopicFileIdsMapper implements RowMapper<Topic>
	{
		@Override
		public Topic mapRow(Row rs, int arg1) throws DriverException {
			Topic topic = new Topic();
			topic.setFileIdsAndAuthorEmails(rs.getMap("file_ids",Long.class,String.class));
			topic.setTopicName(rs.getString("topic"));
		
			return topic;
		}
		
	}
	
	/***
	 * Gets the popular topics with fileIds
	 * @author balaji i
	 * @return Map<String, List<File>>(Popular Topic with files)
	 */
	@Override
	public Map<String,List<File>> getPopularTopicsWithFiles()
	{
		StringBuilder topics = new StringBuilder();
		String topicsStr = "";
		
		List<String> popularTopics = new ArrayList<String>();
		try {
			popularTopics = YMessCommonUtility.findPopularTopics(new java.io.File(YMessCommonUtility.INDEX_LOCATION_TOPICS));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String,List<File>> sharedFiles = new HashMap<String,List<File>>();
		for (String topic : popularTopics)
		{
			topic = YMessCommonUtility.removeExtraneousApostrophe(topic);
			topics.append("'").append(topic).append("',");
		}
		if(topics.length() > 0)
		{
			topicsStr = topics.substring(0,topics.lastIndexOf(","));
			
			String GET_POPULAR_TOPIC_DETAILS = "select topic,file_ids from topics where topic in ("+ topicsStr +")";
			List<Topic> fileAndAuthorEmails = cassandraTemplate.query(GET_POPULAR_TOPIC_DETAILS, new TopicFileIdsMapper());
		
			if( null != fileAndAuthorEmails )
			{
				for (Topic topic : fileAndAuthorEmails) {
				
				StringBuilder userEmailIds = new StringBuilder();
				StringBuilder fileIds = new StringBuilder();
				String fileIdStr = "";
				String userEmailIdStr = "";
				
				if(topic.getFileIdsAndAuthorEmails() != null)
				{	for ( Long key : topic.getFileIdsAndAuthorEmails().keySet() ) {
						fileIds.append(key).append(",");
						userEmailIds.append("'").append(topic.getFileIdsAndAuthorEmails().get(key)).append("',");
					}
				}
				if(fileIds.length() > 0)
					fileIdStr = fileIds.substring(0,fileIds.lastIndexOf(","));
				
				if(userEmailIds.length() > 0)
					userEmailIdStr = userEmailIds.substring(0,userEmailIds.lastIndexOf(","));
				
					if(userEmailIdStr != null && userEmailIdStr.length() > 0)
					{	
						final String GET_FILE_DETAILS = "select file_id,user_email_id,file_type,filename,share_flag,upload_time,file_size,file_description from files where user_email_id in ("+ userEmailIdStr +") and file_id in ("+fileIdStr+")"; 
						sharedFiles.put(topic.getTopicName(),cassandraTemplate.query(GET_FILE_DETAILS,new FileDetailsMapper()));
					}
				}
			}
		}
		return sharedFiles;
	}
	/**
	 * Deletes a File
	 * @author balaji i
	 * @param decodedFileId
	 * @param decodedAuthorEmailId
	 */
	@Override
	public void deleteFile(String decodedFileId, String decodedAuthorEmailId) {

		Long fileId = Long.parseLong(decodedFileId);
		Set<String> fileTopics = getTopics(fileId, decodedAuthorEmailId);
		
		final String DELETE_FILE = "delete from files where file_id ="+fileId+" and user_email_id ='"+decodedAuthorEmailId+"'";
		cassandraTemplate.execute(DELETE_FILE);
		
		if(fileTopics != null && !fileTopics.isEmpty())
		{
			for (String topic : fileTopics) {
				topic = YMessCommonUtility.removeExtraneousApostrophe(topic);
				final String DELETE_REFERENCES_FROM_TOPICS = "delete file_ids["+fileId+"] from topics where topic='"+topic+"'";
				cassandraTemplate.execute(DELETE_REFERENCES_FROM_TOPICS);
				
				final String DECREMENT_FILE_COUNT = "select file_count from topics where topic='"+topic+"'";
				Long fileCount = cassandraTemplate.queryForObject(DECREMENT_FILE_COUNT,Long.class);
				
				final String UPDATE_FILE_COUNT = "update topics set file_count="+(fileCount - 1)+" where topic='"+topic+"'";
				cassandraTemplate.execute(UPDATE_FILE_COUNT);
				
			}
		}
		
		
	}
	
	
	private Set<String> getTopics(Long fileId,String authorEmailId)
	{
		final String GET_TOPICS = "select topics from files where file_id="+fileId+" and user_email_id='"+authorEmailId+"'";
		@SuppressWarnings("unchecked")
		Set<String> fileTopics = cassandraTemplate.queryForObject(GET_TOPICS, Set.class);
		return fileTopics;
	}

	/**
	 * Facilitates File Sharing
	 * @author balaji i
	 * @param fileId
	 * @param authorEmailId
	 */
	@Override
	public void shareFile(String fileId, String authorEmailId) {
		Long fileIdL = Long.parseLong(fileId);
		Set<String> fileTopics = getTopics(fileIdL, authorEmailId);
		
		final String SHARE_FILE = "update files set share_flag=true where file_id="+fileIdL+" and user_email_id='"+authorEmailId+"'";
		cassandraTemplate.update(SHARE_FILE);
		
		if(! fileTopics.isEmpty())
		{
			for (String topic : fileTopics) {
				topic = YMessCommonUtility.removeExtraneousApostrophe(topic);
				final String ADD_FILE_TO_TOPICS = "update topics set file_ids = file_ids + {"+ fileIdL +": '"+ authorEmailId +"'} where topic='"+topic+"'";
				cassandraTemplate.update(ADD_FILE_TO_TOPICS);
			}
		}
		
	}
	
	private class FileDetailsMapperWithTopics implements RowMapper<File>
	{
		@Override
		public File mapRow(Row rs, int arg1) throws DriverException {
			File fileDetails = new File();
			fileDetails.setFileId(rs.getLong("file_id"));
			fileDetails.setAuthorEmailId(rs.getString("user_email_id"));
			fileDetails.setFileType(rs.getString("file_type"));
			fileDetails.setFilename(rs.getString("filename"));
			fileDetails.setShared(rs.getBool("share_flag"));
			fileDetails.setTopics(rs.getSet("topics",String.class));
			fileDetails.setUploadedTime(rs.getDate("upload_time"));
			fileDetails.setFileSize(rs.getString("file_size"));
			fileDetails.setFileDescription(rs.getString("file_description"));
			
			return fileDetails;
		}
		
	}

	/**
	 * Retrieves File Details based on FileId and AuthorEmailId
	 * @author balaji i
	 * @param fileId
	 * @param authorEmailId
	 * @return File(fileDetails)
	 */
	@Override
	public File getFileDetails(String fileId, String authorEmailId) {
		
		Long fileIdL = Long.parseLong(fileId);
		
		final String GET_FILE_DETAILS = "select file_id,user_email_id,filename,share_flag,topics,upload_time,file_type,file_size,file_description from files where file_id="+fileIdL+" and user_email_id='"+authorEmailId+"'";
		File fileDetails = cassandraTemplate.queryForObject(GET_FILE_DETAILS, new FileDetailsMapperWithTopics());
		return fileDetails;
	}

	
	/**
	 * Gets all the Files Concerned with a Topic
	 * @author balaji i
	 * @param topic
	 * @return List<File>(filesInTopic)
	 */
	@Override
	public List<File> getFilesInTopic(String topic) {
		topic = YMessCommonUtility.removeExtraneousApostrophe(topic);
		final String GET_FILES_IN_TOPIC = "select file_ids from topics where topic='"+topic+"'";
		Map<Long,String> fileIdsWithAuthorEmailIds = cassandraTemplate.queryForObject(GET_FILES_IN_TOPIC, Map.class);
		
		List<File> filesInTopic = new ArrayList<File>();
		if( null != fileIdsWithAuthorEmailIds && !fileIdsWithAuthorEmailIds.isEmpty())
		{
			for (Long fileId : fileIdsWithAuthorEmailIds.keySet()) {
				String authorEmailId = fileIdsWithAuthorEmailIds.get(fileId);
				File file = getFileDetails(String.valueOf(fileId), authorEmailId);
				filesInTopic.add(file);
			}
		}
		return filesInTopic;
	}
	
	@Override
	public File getDefaultImage()
	{
		final String GET_DEFAULT_IMAGE = "select filename,filedata from master_data where identifier=1";
		File defaultImage = cassandraTemplate.queryForObject(GET_DEFAULT_IMAGE, new FileDownloadMapper());
		return defaultImage;
	}

	/**
	 * Gets the Question Details
	 * @author RAJ
	 * @throws EmptyResultSetException 
	 */
	@Override
	public Question getQuestionDetails(String questionId) throws EmptyResultSetException {
		Question questionDetails = new Question();
		try
		{
			final String GET_DASHBOARD_QUESTIONS = "select question_id,question_title,question_desc,author_email_id,last_answer,updated_date,author_first_name,author_last_name,is_image_attached,topics from questions where question_id = "+questionId+" allow filtering";
			questionDetails = cassandraTemplate.queryForObject(GET_DASHBOARD_QUESTIONS,new QuestionMapper());
		}
		catch(EmptyResultDataAccessException emptyResultSet)
		{
			throw new EmptyResultSetException(YMessMessageConstants.EMPTY_RESULT_SET);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return questionDetails;
	}

	/**
	 * Uploads Question Image
	 * @author RAJ
	 * @param question
	 */
	@Override
	public void uploadQuestionImage(Question question) {
		
		if(question.getQuestionImage() != null)
		{
			String imageName = question.getQuestionImage().getOriginalFilename();
			try {
				byte [] originalImage = question.getQuestionImage().getBytes();
				byte[] compressedImage = null;
				
				if(null != imageName)
				{
					if(YMessCommonUtility.getFileExtension(imageName).equalsIgnoreCase("jpg") || YMessCommonUtility.getFileExtension(imageName).equalsIgnoreCase("jpeg"))
						compressedImage = YMessCommonUtility.returnCompressedImage(originalImage);
					else
						compressedImage = originalImage;
				}
				final String UPDATE_QUESTION_IMAGE = "update questions set image_name='"+imageName+"' , image_data ="+ByteBuffer.wrap(compressedImage)+",is_image_attached=true where question_id ="+question.getQuestionId();
				cassandraTemplate.update(UPDATE_QUESTION_IMAGE);

			} catch (DataAccessException e) {
				logger.error(e.getLocalizedMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error(e.getLocalizedMessage());
			}
		}
		
	}


	private class AnswerImageMapper implements RowMapper<Answer>
	{
		@Override
		public Answer mapRow(Row rs, int arg1) throws DriverException {
			Answer answer = new Answer();
			if(rs.getBytes("image_data") != null){
				byte[] imageData = new byte[rs.getBytes("image_data").remaining()];
				rs.getBytes("image_data").get(imageData);
				answer.setAnswerImageDb(imageData);
			}
			return answer;
		}
		
		
	}
	/**
	 * Fetches the Answer Image
	 * @author balaji i
	 * @param encodedQuestionId
	 * @param encodedAnswerId
	 * @return Answer(answerImageDetails)
	 */
	@Override
	public Answer getAnswerImage(String encodedQuestionId,String encodedAnswerId) {
		Long questionId = Long.parseLong(encodedQuestionId);
		Long answerId = Long.parseLong(encodedAnswerId);
		
		final String GET_ANSWER_IMAGE = "select image_data from answers where question_id="+questionId +" and answer_id="+answerId;
		Answer answerDetails = cassandraTemplate.queryForObject(GET_ANSWER_IMAGE, new AnswerImageMapper());
		
		return answerDetails;
	}
	
	
	/**
	 * Updates Question Topics in DB
	 * @author rvishwakarma
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateQuestion(Question question) {
        //Id is not allowing
		final String GET_QUESTIONS_PREVIOUS_TOPICS = "select topics from questions where question_id="+question.getQuestionId()+" and author_email_id='"+question.getAuthorEmailId()+"'";
		/** Fetching Question's previous Topics to Compare and check if anything has been updated */
		Set<String> previousTopics = (Set<String>) cassandraTemplate.queryForObject(GET_QUESTIONS_PREVIOUS_TOPICS, Set.class);
		
		Set<String> topics = new HashSet<String>();
		
		if(question.getTopics() != null && ! question.getTopics().isEmpty())
		{
			topics.addAll(question.getTopics()); 
			topics = YMessCommonUtility.removeNullAndEmptyElements(topics);
		}
		
		Date currentTime = new Date();
		User userDetails = getUserDetailsByEmailId(question.getAuthorEmailId());
		
		/** Question Along with Image */
		if(question.getIsImageAttached() != null && question.getIsImageAttached())
		{
			try {
				byte[] originalImage = question.getQuestionImage().getBytes();
				byte[] compressedImage = null;
				
				if(null !=  question.getQuestionImage().getOriginalFilename())
				{
					if(YMessCommonUtility.getFileExtension(question.getQuestionImage().getOriginalFilename()).equalsIgnoreCase("jpg") || YMessCommonUtility.getFileExtension(question.getQuestionImage().getOriginalFilename()).equalsIgnoreCase("jpeg"))
						compressedImage = YMessCommonUtility.returnCompressedImage(originalImage);
					else
						compressedImage = originalImage;
				}
				final String UPDATE_QUESTION_WITH_IMAGE = "update questions set question_title='"+question.getQuestionTitle()+"' ,"
						+ " question_desc='"+question.getQuestionDescription()+"', updated_date="+currentTime+","
								+ "keywords=["+question.getKeywords()+"],  is_image_attached=true, image_data="+ByteBuffer.wrap(compressedImage)+", "
										+ "image_name='"+question.getQuestionImage().getOriginalFilename()+"', topics={"+question.getTopics()+"}"
												+ "  where question_id ="+question.getQuestionId()+" and author_email_id='"+question.getAuthorEmailId()+"'";	
				cassandraTemplate.update(UPDATE_QUESTION_WITH_IMAGE);
						
						//cassandraTemplate.update(ADD_QUESTION_TIMELINE, new Object[]{question.getAuthorEmailId(),new Date(),ActivityConstants.POSTED_QUESTION,userDetails.getFirstName(),userDetails.getLastName(),question.getQuestionId(),question.getQuestionTitle(),question.getQuestionDescription(),true,question.getTopics(),currentTime ,YMessCommonUtility.IS_POSTED_QUESTIONS},
						//		new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.BOOLEAN,Types.ARRAY,Types.TIMESTAMP,Types.BOOLEAN}
						//);
						
			} catch (IOException e) {
				logger.error(e.getStackTrace());
			}
			
		}
		else /** Only Text Question */
		{
			final String UPDATE_QUESTION = "update questions set "
					+ "question_title='"+question.getQuestionTitle()+"',"
					+ " question_desc='"+question.getQuestionDescription()+"', "
							+ "updated_date="+currentTime+","
									+ "keywords=["+question.getKeywords()+"], "
									+ "topics={"+question.getTopics()+"} "
									+ "where question_id="+question.getQuestionId()+" and author_email_id='"+question.getAuthorEmailId()+"'";
			
			cassandraTemplate.update(UPDATE_QUESTION);
			
			//cassandraTemplate.update(ADD_QUESTION_TIMELINE, new Object[]{question.getAuthorEmailId(),new Date(),ActivityConstants.POSTED_QUESTION,userDetails.getFirstName(),userDetails.getLastName(),question.getQuestionId(),question.getQuestionTitle(),question.getQuestionDescription(),false,question.getTopics(),currentTime ,YMessCommonUtility.IS_POSTED_QUESTIONS},
			//		new int[]{Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.BIGINT,Types.VARCHAR,Types.VARCHAR,Types.BOOLEAN,Types.ARRAY,Types.TIMESTAMP,Types.BOOLEAN}
			//);
		
		}
		
		if(!topics.isEmpty())
		{
			for (String topic : topics) {
				
				topic = topic.toLowerCase();
				
				if(topic.trim().length() > 0)
				{
					topic = YMessCommonUtility.removeExtraneousApostrophe(topic);
					topic = topic.trim().toLowerCase();
				
				String CHECK_IF_TOPIC_EXISTS = "select count(1) from topics where topic='"+topic+"'";
				long topicCount = cassandraTemplate.queryForObject(CHECK_IF_TOPIC_EXISTS,Long.class);
				
				if(topicCount != 0)
				{
					long questionCount = cassandraTemplate.queryForObject("select question_count from topics where topic='"+topic+"'",Long.class);
					
					String UPDATE_TOPIC = "update topics set question_count="+(questionCount+1)+",question_ids=question_ids + {"+question.getQuestionId()+"} where topic='"+topic+"'";
					try{
						cassandraTemplate.update(UPDATE_TOPIC);
					}
					catch(Exception ex)
					{
						logger.error(ex.getStackTrace());
					}
				}
				else
				{
					String INSERT_NEW_TOPIC = "insert into topics (topic,question_ids,question_count) values ('"+topic+"',"+ "{"+ question.getQuestionId() +"},1)";
					try{
						cassandraTemplate.update(INSERT_NEW_TOPIC);
					}
					catch(Exception ex)
					{
						logger.error(ex.getStackTrace());
					}
				}
			}
				
			}
			/** New Interests Added By User in Form */
			Set<String> newTopics = question.getTopics();
			Set<String> deletedTopics = new HashSet<String>();
			Set<String> addedTopics = new HashSet<String>();
			
			
			if(previousTopics != null)
				previousTopics = YMessCommonUtility.removeNullAndEmptyElements(previousTopics);
			
			if(newTopics != null)
				newTopics = YMessCommonUtility.removeNullAndEmptyElements(newTopics);
			
			Map<String,Set<String>> mergedSets = YMessCommonUtility.compareSetsAndReturnAddedAndDeletedObjects(previousTopics,newTopics);
			
			/** Nothing has been changed in terms of topics */
			if(! mergedSets.containsKey(YMessCommonUtility.EQUAL_SET))
			{
				//Changes have been made
				deletedTopics = mergedSets.get(YMessCommonUtility.DELETED_ITEMS);
				addedTopics = mergedSets.get(YMessCommonUtility.ADDED_ITEMS);
				
				if(! deletedTopics.isEmpty())
				{
					updateUserInterestsInTopics(deletedTopics,question.getAuthorEmailId(),true);
				}
				
				if(! addedTopics.isEmpty())
				{
					updateUserInterestsInTopics(addedTopics,question.getAuthorEmailId(),false);
				}
			}
		}	
		
	}

	public CassandraTemplate getCassandraTemplate() {
		return cassandraTemplate;
	}

	public void setCassandraTemplate(CassandraTemplate cassandraTemplate) {
		this.cassandraTemplate = cassandraTemplate;
	}
	
}
