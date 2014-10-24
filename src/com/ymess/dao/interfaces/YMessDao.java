/**
 * 
 */
package com.ymess.dao.interfaces;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.pojos.Answer;
import com.ymess.pojos.File;
import com.ymess.pojos.Question;
import com.ymess.pojos.TimeLine;
import com.ymess.pojos.User;

/**
 * @author balaji i
 *
 */
public interface YMessDao {

	/**
	 * Adds a new User during registration.
	 * @author balaji i
	 * @param user
	 * @return boolean(successFlag)
	 */
	boolean addUser(User user);
	
	/**
	 * Adds a Question 
	 * @author balaji i
	 * @param question
	 */
	void addQuestion(Question question);

	/**
	 * Retrieves All the Questions posted by an User
	 * @author balaji i
	 * @param userEmailId
	 * @return questions(List<Question>)
	 * @throws EmptyResultSetException 
	 */
	List<Question> getUserQuestions(String userEmailId) throws EmptyResultSetException;

	/**
	 * Retrieves the Jobs for an User based on his likeness(category and keywords)
	 * @author balaji i
	 * @param userEmailId
	 * @return questions(List<Question>)
	 * @throws EmptyResultSetException 
	 */
	List<Question> getDashboardQuestions(String userEmailId) throws EmptyResultSetException;

	/**
	 * Adds an answer posted by a User to a Question
	 * @author balaji i
	 * @param questionId
	 * @param answer
	 * @param loggedInUserEmailId 
	 */
	void addAnswer(Answer answer);

	/**
	 * Fetches all the Answers of a Question in Descending Order (Latest First)
	 * @param loggedInUserEmailId
	 * @param decodedQuestionId
	 * @return answers(List<Answer>)
	 * @throws EmptyResultSetException 
	 */
	List<Answer> getUserQuestionResponses(String loggedInUserEmailId,
			String decodedQuestionId) throws EmptyResultSetException;

	/**
	 * Retrieves Upvoted Users for an Answer
	 * @param questionId
	 * @param answerId
	 * @return List<Users> upvotedUsers
	 * @throws EmptyResultSetException 
	 */
	List<User> getAnswerUpvoters(String questionId, String answerId) throws EmptyResultSetException;

	/**
	 * Fetches the User Details based on emailId
	 * @author rvishwakarma
	 * @param loggedInUserEmail
	 * @return User(userDetails)
	 * @throws EmptyResultSetException 
	 */
	User getUserDetails(String loggedInUserEmail) throws EmptyResultSetException;

	/**
	 * Updates User Details in DB
	 * @author rvishwakarma
	 * @param user
	 */
	void updateUserProfile(User user);

	/**
	 * Retrieves the User image based on emailId
	 * @author rvishwakarma
	 * @param loggedInUserEmail
	 * @return byte[] (userImage)
	 */
	User getUserImage(String loggedInUserEmail);

	/**
	 * Uploads the User's Image 
	 * @author balaji i
	 * @param user
	 */
	void uploadUserImage(User user);

	/**
	 * Upvotes an answer
	 * @param questionId
	 * @param answerId
	 * @param loggedInUserEmail
	 * @return Boolean(successFlag)
	 */
	Boolean upvoteAnswer(String questionId, String answerId,
			String loggedInUserEmail);

	/**
	 * Downvotes an Answer
	 * @param questionId
	 * @param answerId
	 * @param loggedInUserEmail
	 * @return Boolean(successFlag)
	 */
	Boolean downvoteAnswer(String questionId, String answerId,
			String loggedInUserEmail);

	/**
	 * loggedInUserEmail Following decodedUserEmailId
	 * @param loggedInUserEmail
	 * @param decodedUserEmailId
	 */
	void followUser(String loggedInUserEmail, String decodedUserEmailId);

	/**
	 * Gets the Following Users
	 * @param loggedInUserEmail
	 * @return List<User>(followingUsers)
	 * @throws EmptyResultSetException 
	 */
	List<User> getFollowingUsers(String loggedInUserEmail) throws EmptyResultSetException;

	/**
	 * Gets the Followers of an User
	 * @author balaji i
	 * @param loggedInUserEmail
	 * @return List<User> followers
	 * @throws EmptyResultSetException 
	 */
	List<User> getFollowers(String loggedInUserEmail) throws EmptyResultSetException;

	/**
	 * Gets the Map of Following Users
	 * @param loggedInUserEmailId
	 * @returnMap<String,Date> (followingUsers)
	 */
	Map<String, Date> getFollowingUsersMap(String loggedInUserEmailId);

	/**
	 * Unfollows an User
	 * @param loggedInUserEmail
	 * @param decodedUserEmailId
	 */
	void unfollowUser(String loggedInUserEmail, String decodedUserEmailId);

	/**
	 * Gets the downvoters of an Answer
	 * @param questionId
	 * @param answerId
	 * @return List<User>(downvoters)
	 * @throws EmptyResultSetException 
	 */
	List<User> getAnswerDownvoters(String questionId, String answerId) throws EmptyResultSetException;

	/**
	 * Checks if the new registering user has entered an emailId that has already been registered with
	 * @param userEmailId
	 * @return boolean (registeredFlag)
	 */
	boolean checkIfUserEmailExists(String userEmailId);

	/**
	 * Gets the Question Image
	 * @param decodedQuestionId
	 * @return Question(Image Data)
	 */
	Question getQuestionImage(String decodedQuestionId);

	/**
	 * Gets the timeline results of a user
	 * @param loggedInUserEmail
	 * @return List<TimeLine>(loggedInUserEmail)
	 * @throws EmptyResultSetException 
	 */
	List<TimeLine> getUserTimeLine(String loggedInUserEmail) throws EmptyResultSetException;

	/**
	 * Fetches all the Questions in a Topic
	 * @param topicName
	 * @return  List<Question> (questions in a topic)
	 * @throws EmptyResultSetException 
	 */
	List<Question> getQuestionsInTopic(String topicName) throws EmptyResultSetException;

	/**
	 * Fetches all the topic names available in DB
	 * @author balaji i
	 * @return Topics(List<String>)
	 * @throws EmptyResultSetException 
	 */
	List<String> getAllTopics() throws EmptyResultSetException;

	/**
	 * Facilitates User File Upload
	 * @param file
	 * @throws IOException 
	 * @throws DataAccessException 
	 */
	void uploadFile(File file) throws DataAccessException, IOException;

	/**
	 * Gets all the files that have been shared by Users
	 * @author balaji i
	 * @return  sharedFiles(List<File>)
	 * @throws EmptyResultSetException 
	 */
	List<File> getAllSharedFiles() throws EmptyResultSetException;

	/**
	 * Downloads the file data
	 * @author balaji i
	 * @param encodedFileId
	 * @param encodedAuthorEmailId
	 * @return File (downloadedFileDetails)
	 */
	File downloadFile(String encodedFileId, String encodedAuthorEmailId);

	/**
	 * Gets all the Files uploaded by an User
	 * @param loggedInUserEmail
	 * @return List<File>(Files uploaded by an User)
	 * @throws EmptyResultSetException 
	 */
	List<File> getUserFiles(String loggedInUserEmail) throws EmptyResultSetException;

	/***
	 * Gets the popular topics with fileIds
	 * @author balaji i
	 * @return Map<String, List<File>>(Popular Topic with files)
	 */
	Map<String, List<File>> getPopularTopicsWithFiles();

	/**
	 * Deletes a File
	 * @author balaji i
	 * @param encodedFileId
	 * @param encodedAuthorEmailId
	 */
	void deleteFile(String encodedFileId, String encodedAuthorEmailId);

	/**
	 * Facilitates File Sharing
	 * @author balaji i
	 * @param fileId
	 * @param authorEmailId
	 */
	void shareFile(String fileId, String authorEmailId);

	/**
	 * Retrieves File Details based on FileId and AuthorEmailId
	 * @author balaji i
	 * @param fileId
	 * @param authorEmailId
	 * @return File(fileDetails)
	 */
	File getFileDetails(String fileId, String authorEmailId);

	/**
	 * Gets all the Files Concerned with a Topic
	 * @author balaji i
	 * @param topic
	 * @return List<File>(filesInTopic)
	 */
	List<File> getFilesInTopic(String topic);

	/**
	 * Gets the Default Image(No Image in absence of any Image)
	 * @author balaji i
	 * @return File(defaultImages)
	 */
	File getDefaultImage();

}
