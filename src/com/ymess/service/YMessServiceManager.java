/**
 * 
 */
package com.ymess.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ymess.dao.interfaces.YMessDao;
import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.pojos.Answer;
import com.ymess.pojos.File;
import com.ymess.pojos.Question;
import com.ymess.pojos.TimeLine;
import com.ymess.pojos.User;
import com.ymess.service.interfaces.YMessService;

/**
 * @author balaji i
 *
 */
public class YMessServiceManager implements YMessService {

	YMessDao yMessDao;

	public YMessDao getyMessDao() {
		return yMessDao;
	}


	public void setyMessDao(YMessDao yMessDao) {
		this.yMessDao = yMessDao;
	}
	
	/**
	 * Adds a new User during registration.
	 * @author balaji i
	 * @param user
	 * @return boolean(successFlag)
	 */
	@Override
	public boolean addUser(User user) {
		return yMessDao.addUser(user);
	}

	/**
	 * Adds a Question
	 * @author balaji i
	 * @param question
	 */
	@Override
	public void addQuestion(Question question) {
		yMessDao.addQuestion(question);
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
		return yMessDao.getUserQuestions(userEmailId);
	}


	/**
	 * Retrieves the Jobs for an User based on his likeness(category and keywords)
	 * @author balaji i
	 * @param userEmailId
	 * @return questions(List<Question>)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<Question> getDashboardQuestions(String userEmailId) throws EmptyResultSetException {
		return yMessDao.getDashboardQuestions(userEmailId);
	}


	/**
	 * Adds an answer posted by a User to a Question
	 * @author balaji i
	 * @param questionId
	 * @param answer
	 */
	@Override
	public void addAnswer(String questionId, String answer,String loggedInUserEmailId) {
		yMessDao.addAnswer(questionId,answer,loggedInUserEmailId);
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
		return yMessDao.getUserQuestionResponses(loggedInUserEmailId,decodedQuestionId);
	}

	/**
	 * Retrieves Upvoted Users for an Answer
	 * @param questionId
	 * @param answerId
	 * @return List<Users> upvotedUsers
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<User> getAnswerUpvoters(String questionId, String answerId) throws EmptyResultSetException {
		return yMessDao.getAnswerUpvoters(questionId,answerId);
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
		return yMessDao.getUserDetails(loggedInUserEmail);
	}


	/**
	 * Updates User Details in DB
	 * @author rvishwakarma
	 * @param user
	 */
	@Override
	public void updateUserProfile(User user) {
		yMessDao.updateUserProfile(user);
		
	}

	/**
	 * Retrieves the User image based on emailId
	 * @author rvishwakarma
	 * @param loggedInUserEmail
	 * @return byte[] (userImage)
	 */
	@Override
	public User getUserImage(String loggedInUserEmail) {
		return yMessDao.getUserImage(loggedInUserEmail);
	}


	/**
	 * Uploads the User's Image 
	 * @author balaji i
	 * @param user
	 */
	@Override
	public void uploadUserImage(User user) {
		yMessDao.uploadUserImage(user);		
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
		return yMessDao.upvoteAnswer(questionId,answerId,loggedInUserEmail);
	}

	/**
	 * Downvotes an Answer
	 * @param questionId
	 * @param answerId
	 * @param loggedInUserEmail
	 * @return Boolean(successFlag)
	 */
	@Override
	public Boolean downvoteAnswer(String questionId, String answerId,
			String loggedInUserEmail) {
		return yMessDao.downvoteAnswer(questionId,answerId,loggedInUserEmail);
	}

	/**
	 * loggedInUserEmail Following decodedUserEmailId
	 * @param loggedInUserEmail
	 * @param decodedUserEmailId
	 */
	@Override
	public void followUser(String loggedInUserEmail, String decodedUserEmailId) {
		yMessDao.followUser(loggedInUserEmail,decodedUserEmailId);		
	}

	/**
	 * Gets the Following Users
	 * @param loggedInUserEmail
	 * @return List<User>(followingUsers)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<User> getFollowingUsers(String loggedInUserEmail) throws EmptyResultSetException {
		return yMessDao.getFollowingUsers(loggedInUserEmail);
	}


	/**
	 * Gets the Followers of an User
	 * @author balaji i
	 * @param loggedInUserEmail
	 * @return List<User> followers
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<User> getFollowers(String loggedInUserEmail) throws EmptyResultSetException {
		return yMessDao.getFollowers(loggedInUserEmail);
	}

	/**
	 * Gets the Map of Following Users
	 * @param loggedInUserEmailId
	 * @returnMap<String,Date> (followingUsers)
	 */
	@Override
	public Map<String, Date> getFollowingUsersMap(String loggedInUserEmailId) {
		return yMessDao.getFollowingUsersMap(loggedInUserEmailId);
	}

	/**
	 * Unfollows an User
	 * @param loggedInUserEmail
	 * @param decodedUserEmailId
	 */
	@Override
	public void unfollowUser(String loggedInUserEmail, String decodedUserEmailId) {
		yMessDao.unfollowUser(loggedInUserEmail,decodedUserEmailId);
	}

	/**
	 * Gets the downvoters of an Answer
	 * @param questionId
	 * @param answerId
	 * @return List<User>(downvoters)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<User> getAnswerDownvoters(String questionId, String answerId) throws EmptyResultSetException {
		return yMessDao.getAnswerDownvoters(questionId,answerId);
	}


	/**
	 * Checks if the new registering user has entered an emailId that has already been registered with
	 * @param userEmailId
	 * @return boolean (registeredFlag)
	 */
	@Override
	public boolean checkIfUserEmailExists(String userEmailId) {
		return yMessDao.checkIfUserEmailExists(userEmailId);
	}

	/**
	 * Gets the Question Image
	 * @param decodedQuestionId
	 * @return Question(Image Data)
	 */
	@Override
	public Question getQuestionImage(String decodedQuestionId) {
		return yMessDao.getQuestionImage(decodedQuestionId);
	}

	/**
	 * Gets the timeline results of a user
	 * @param loggedInUserEmail
	 * @return List<TimeLine>(loggedInUserEmail)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<TimeLine> getUserTimeLine(String loggedInUserEmail) throws EmptyResultSetException {
		return yMessDao.getUserTimeLine(loggedInUserEmail);
	}

	/**
	 * Fetches all the Questions in a Topic
	 * @param topicName
	 * @return  List<Question> (questions in a topic)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<Question> getQuestionsInTopic(String topicName) throws EmptyResultSetException {
		return yMessDao.getQuestionsInTopic(topicName);
	}

	/**
	 * Fetches all the topic names available in DB
	 * @author balaji i
	 * @return Topics(List<String>)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<String> getAllTopics() throws EmptyResultSetException {
		return yMessDao.getAllTopics();
	}


	/**
	 * Facilitates User File Upload
	 * @param file
	 * @throws IOException 
	 * @throws DataAccessException 
	 */
	@Override
	public void uploadFile(File file) throws DataAccessException, IOException {
		yMessDao.uploadFile(file);
	}

	/**
	 * Gets all the files that have been shared by Users
	 * @author balaji i
	 * @return  sharedFiles(List<File>)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<File> getAllSharedFiles() throws EmptyResultSetException {
		return yMessDao.getAllSharedFiles();
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
		return yMessDao.downloadFile(encodedFileId,encodedAuthorEmailId);
	}

	/**
	 * Gets all the Files uploaded by an User
	 * @param loggedInUserEmail
	 * @return List<File>(Files uploaded by an User)
	 * @throws EmptyResultSetException 
	 */
	@Override
	public List<File> getUserFiles(String loggedInUserEmail) throws EmptyResultSetException {
		return yMessDao.getUserFiles(loggedInUserEmail);
	}

	/***
	 * Gets the popular topics with fileIds
	 * @author balaji i
	 * @return Map<String, List<File>>(Popular Topic with files)
	 */
	@Override
	public Map<String, List<File>> getPopularTopicsWithFiles() {
		return yMessDao.getPopularTopicsWithFiles();
	}

	/**
	 * Deletes a File
	 * @author balaji i
	 * @param encodedFileId
	 * @param encodedAuthorEmailId
	 */
	@Override
	public void deleteFile(String encodedFileId, String encodedAuthorEmailId) {
		yMessDao.deleteFile(encodedFileId,encodedAuthorEmailId);
	}

	/**
	 * Facilitates File Sharing
	 * @author balaji i
	 * @param fileId
	 * @param authorEmailId
	 */
	@Override
	public void shareFile(String fileId, String authorEmailId) {
			yMessDao.shareFile(fileId,authorEmailId);
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
		return yMessDao.getFileDetails(fileId,authorEmailId);
	}

	/**
	 * Gets all the Files Concerned with a Topic
	 * @author balaji i
	 * @param topic
	 * @return List<File>(filesInTopic)
	 */
	@Override
	public List<File> getFilesInTopic(String topic) {
		return yMessDao.getFilesInTopic(topic);
	}

	/**
	 * Gets the Default Image(No Image in absence of any Image)
	 * @author balaji i
	 * @return File(defaultImages)
	 */
	@Override
	public File getDefaultImage() {
		return yMessDao.getDefaultImage();
	}
}
