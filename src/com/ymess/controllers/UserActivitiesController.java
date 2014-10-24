/**
 * 
 */
package com.ymess.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.pojos.Answer;
import com.ymess.pojos.File;
import com.ymess.pojos.Question;
import com.ymess.pojos.User;
import com.ymess.service.interfaces.YMessService;
import com.ymess.util.JSPMappings;
import com.ymess.util.LoggerConstants;
import com.ymess.util.MessageConstants;
import com.ymess.util.URLMappings;
import com.ymess.util.YMessCommonUtility;

/**
 * @author balaji i
 *
 */
@Controller
public class UserActivitiesController {

	private final Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Autowiring Service Components
	 */
	@Autowired
	YMessService yMessService;
	
	/**
	 * Loads the User Post Question Page 
	 * @author balaji i
	 * @param model
	 * @return String(post_question.jsp)
	 */
	@RequestMapping(value=URLMappings.USER_POST_QUESTION,method=RequestMethod.GET)
	public String userPostQuestionPage(Model model)
	{
		model.addAttribute("question", new Question());
		logger.info(LoggerConstants.USER_POST_QUESTION_PAGE);
		return JSPMappings.USER_POST_QUESTION;
	}
	
	/**
	 * Serves User Post a Question Request to process question details
	 * @param question
	 * @param result
	 * @param redirectAttributes
	 * @return String(redirect:userdashboard.htm)
	 */
	@RequestMapping(value=URLMappings.USER_POST_QUESTION,method=RequestMethod.POST)
	public String userPostQuestion(@ModelAttribute("question") @Valid Question question,BindingResult result,RedirectAttributes redirectAttributes)
	{
		if(question.getIsImageAttached() != null && question.getIsImageAttached())
		{
			if(question.getQuestionImage().getOriginalFilename().length() == 0)
			{
				result.rejectValue("questionImage","Empty.Question.questionImage","Please Upload a File or Uncheck the Add Image Option");
			}
		}
		
		if(question.getTopics() != null && question.getTopics().isEmpty())
		{
			result.rejectValue("topics","Empty.Question.topics","Please mention at least one topic against the Question");
		}
		if(result.hasErrors())
		{
			return JSPMappings.USER_POST_QUESTION;
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmailId = authentication.getName();
		try
		{
			Set<String> keywordsInQuestion =  YMessCommonUtility.findKeywordsinQuestionDescription(question.getQuestionDescription());
			
			question.setAuthorEmailId(userEmailId);
			question.setKeywords(new ArrayList<String>(keywordsInQuestion));

			/** Adding the Question Details in Database */
			yMessService.addQuestion(question);
			logger.info(LoggerConstants.USER_POSTED_QUESTION +" "+userEmailId);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		redirectAttributes.addFlashAttribute("successfullyPostedQuestion","You Posted the Question Successfully");
		return URLMappings.REDIRECT_SUCCESS_POSTING_QUESTION;	
	}
	
	/**
	 * Fetches all the Questions posted by an User
	 * @param model
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return String (questions.jsp)
	 * @throws IOException
	 */
	@RequestMapping(value=URLMappings.USER_QUESTIONS,method=RequestMethod.GET)
	public String getUserQuestions(Model model,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmailId = authentication.getName();
		
		List<Question> questions = new ArrayList<Question>();
		
		try {
			questions = yMessService.getUserQuestions(userEmailId);
			logger.info(LoggerConstants.USER_QUESTIONS +" "+ userEmailId);
		} catch (EmptyResultSetException e) {
			logger.error(e.getLocalizedMessage());
			model.addAttribute("emptyResultSet",true);
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		
		model.addAttribute("questions",questions);
		return JSPMappings.USER_QUESTIONS;
	}
	
	/**
	 * Processes and Adds an Answer of a Particular Question
	 * @param questionId
	 * @param answer
	 * @return Boolean (successFlag)
	 */
	@RequestMapping(value=URLMappings.USER_POST_ANSWER,method=RequestMethod.POST)
	@ResponseBody
	Boolean addAnswer(@ModelAttribute("answer") Answer answer)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();
		Boolean successFlag = false;
		
		try
		{
			yMessService.addAnswer(answer);
			logger.info(LoggerConstants.USER_POSTED_ANSWER+" "+loggedInUserEmailId);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		return successFlag;
	}
	
	@RequestMapping(value=URLMappings.USER_QUESTION_RESPONSES)
	String getUserQuestionResponses(@RequestParam("qId")String encodedQuestionId,Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();
		
		String decodedQuestionId = YMessCommonUtility.decodeEncodedParameter(encodedQuestionId);
		
		List<Answer> answers = new ArrayList<Answer>();
		try {
			answers = yMessService.getUserQuestionResponses(loggedInUserEmailId,decodedQuestionId);
			logger.info(LoggerConstants.USER_QUESTION_RESPONSES +" "+loggedInUserEmailId);
			
		} 
		catch(EmptyResultSetException emptyResultSet)
		{
			model.addAttribute("emptyResultSet",MessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		model.addAttribute("loggedInUser",loggedInUserEmailId);
		model.addAttribute("answers",answers);
		return JSPMappings.USER_QUESTION_RESPONSES;
	}
	
	/**
	 * Fetches all the upvoters of an Answer
	 * @param encodedAnswerId
	 * @param encodedQuestionId
	 * @param model
	 * @return List<User> upvoters
	 */
	@RequestMapping(value=URLMappings.USER_ANSWER_UPVOTERS)
	String getUpvoters(@RequestParam("aId") String encodedAnswerId,@RequestParam("qId") String encodedQuestionId,Model model)
	{
		String questionId = YMessCommonUtility.decodeEncodedParameter(encodedQuestionId);
		String answerId = YMessCommonUtility.decodeEncodedParameter(encodedAnswerId);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();
		
		List<User> upvoters = new ArrayList<User>();
		try {
			upvoters = yMessService.getAnswerUpvoters(questionId,answerId);
			logger.info(LoggerConstants.ANSWER_UPVOTERS  );
		} catch (EmptyResultSetException e) {
			model.addAttribute("emptyResultSet",MessageConstants.EMPTY_RESULT_SET);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		model.addAttribute("upvoters",upvoters);
		model.addAttribute("loggedInUser",loggedInUserEmailId);
		return JSPMappings.USER_ANSWER_UPVOTERS;
	}
	
	
	/**
	 * Fetches all the downvoters of an Answer
	 * @param encodedAnswerId
	 * @param encodedQuestionId
	 * @param model
	 * @return List<User> downvoters
	 */
	@RequestMapping(value=URLMappings.USER_ANSWER_DOWNVOTERS)
	String getDownvoters(@RequestParam("aId") String encodedAnswerId,@RequestParam("qId") String encodedQuestionId,Model model)
	{
		String questionId = YMessCommonUtility.decodeEncodedParameter(encodedQuestionId);
		String answerId = YMessCommonUtility.decodeEncodedParameter(encodedAnswerId);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();
		
		List<User> downvoters = new ArrayList<User>();
		try {
			downvoters = yMessService.getAnswerDownvoters(questionId,answerId);
			logger.info(LoggerConstants.ANSWER_UPVOTERS  );
		} catch (EmptyResultSetException e) {
			model.addAttribute("emptyResultSet",MessageConstants.EMPTY_RESULT_SET);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		model.addAttribute("downvoters",downvoters);
		model.addAttribute("loggedInUser",loggedInUserEmailId);
		return JSPMappings.USER_ANSWER_DOWNVOTERS;
	}
	
	/**
	 * Upvotes an Answer and adds the Upvoted User and the Upvoted Count
	 * @author balaji i
	 * @param questionId
	 * @param answerId
	 * @return Boolean(successFlag)
	 */
	@RequestMapping(value=URLMappings.USER_UPVOTE_ANSWER,method=RequestMethod.GET)
	@ResponseBody
	Boolean upvoteAnswer(@RequestParam("qId") String questionId,@RequestParam("aId") String answerId)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		Boolean successFlag = yMessService.upvoteAnswer(questionId,answerId,loggedInUserEmail);
		logger.info(LoggerConstants.USER_UPVOTE_ANSWER+" "+loggedInUserEmail);
		
		return successFlag;
	}
	
	/**
	 * Upvotes an Answer and adds the Upvoted User and the Upvoted Count
	 * @author balaji i
	 * @param questionId
	 * @param answerId
	 * @return Boolean(successFlag)
	 */
	@RequestMapping(value=URLMappings.USER_DOWNVOTE_ANSWER,method=RequestMethod.GET)
	@ResponseBody
	Boolean downvoteAnswer(@RequestParam("qId") String questionId,@RequestParam("aId") String answerId)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		Boolean successFlag = yMessService.downvoteAnswer(questionId,answerId,loggedInUserEmail);
		
		logger.info(LoggerConstants.USER_DOWNVOTE_ANSWER+" "+loggedInUserEmail);
		return successFlag;
	}
	
	@RequestMapping(URLMappings.USER_FOLLOW)
	@ResponseBody
	Boolean userFollow(@RequestParam("eId") String encodedUserEmailId)
	{
		String decodedUserEmailId = YMessCommonUtility.decodeEncodedParameter(encodedUserEmailId);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		yMessService.followUser(loggedInUserEmail,decodedUserEmailId);
		logger.info(loggedInUserEmail+" "+LoggerConstants.USER_FOLLOWING+" "+decodedUserEmailId);
		
		return true;
	}
	
	@RequestMapping(URLMappings.USER_UNFOLLOW)
	@ResponseBody
	Boolean userUnfollow(@RequestParam("eId") String encodedUserEmailId)
	{
		String decodedUserEmailId = YMessCommonUtility.decodeEncodedParameter(encodedUserEmailId);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		yMessService.unfollowUser(loggedInUserEmail,decodedUserEmailId);
		logger.info(loggedInUserEmail+" "+LoggerConstants.USER_UNFOLLOWING+" "+decodedUserEmailId);
		
		return true;
	}
	
	
	@RequestMapping(value=URLMappings.FOLLOWING_USERS)
	String getFollowingUsers(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		List<User> followingUsers = new ArrayList<User>();
		try{
			followingUsers = yMessService.getFollowingUsers(loggedInUserEmail);
		}
		catch(EmptyResultSetException emptyRs)
		{
			model.addAttribute("emptyResultSet",MessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
		}
		
		model.addAttribute("followingUsers",followingUsers);
		return JSPMappings.FOLLOWING_USERS;
	}
	
	@RequestMapping(value=URLMappings.FOLLOWERS)
	String getFollowers(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		List<User> followers = new ArrayList<User>();
		try{
			followers = yMessService.getFollowers(loggedInUserEmail);
		}
		catch(EmptyResultSetException emptyRs)
		{
			model.addAttribute("emptyResultSet",MessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
		}
		
		model.addAttribute("followers",followers);
		return JSPMappings.FOLLOWERS;
	}
	
	@RequestMapping(value=URLMappings.USER_QUESTION_IMAGE)
	String getUserViewImage(@RequestParam("qId") String encodedQuestionId , HttpServletRequest request,HttpServletResponse response)
	{
		String decodedQuestionId = YMessCommonUtility.decodeEncodedParameter(encodedQuestionId);
		Question questionImage = yMessService.getQuestionImage(decodedQuestionId);
		
		String imageName = questionImage.getQuestionImageName();
		byte[] image =  questionImage.getQuestionImageDB();
		   
		logger.info(LoggerConstants.USER_VIEW_PROFILE_IMAGE+" "+ decodedQuestionId);
		   try
		   {
		    String imageFormat=null;
		    //Code Added By BalajiI to Accommodate All Image Formats(namely PNG,JPEG,BMP)
		    
		    if(imageName!=null && !imageName.equals(""))
		    {
		     //To check for file format and render accordingly
		     String fileExtension = YMessCommonUtility.getFileExtension(imageName);
		     imageFormat="image/"+fileExtension;
		     response.setContentType(imageFormat);
		     response.setContentLength(image.length);
		    }
		     //Setting response headers
		      response.setHeader("Content-Disposition", "inline; filename=\"" + imageName + "\"");
		     
		      BufferedInputStream input = null;
		      BufferedOutputStream output = null;

		      try 
		      {
		          input = new BufferedInputStream(new ByteArrayInputStream(image));
		          output = new BufferedOutputStream(response.getOutputStream());
		          byte[] buffer = new byte[8192];
		          int length;
		         
		          while ((length = input.read(buffer)) > 0)
		          {
		              output.write(buffer, 0, length);
		          }
		      } 
		      catch (IOException e)
		      {
		          System.out.println("There are errors in reading/writing image stream " + e.getMessage());
		      } 
		      finally 
		      {
		       if (output != null)
		       { 
		    	   output.flush();
		           output.close();
		       }
		       if (input != null)
		           input.close();
		      }
		   }
		   catch(Exception ex)
		   {
			   logger.error(ex.getLocalizedMessage());
		   }
		return null;
	}
	
	@RequestMapping(value = URLMappings.USER_VIEW_TOPIC_QUESTIONS)
	String getQuestionsInTopic(@RequestParam("topic") String encodedTopicName,Model model)
	{
		String topicName = "";
		if(encodedTopicName != null && encodedTopicName.trim().length() > 0)
			topicName = YMessCommonUtility.decodeEncodedParameter(encodedTopicName);
		
		List<Question> questionsInTopic = new ArrayList<Question>();
		
		if(topicName.length() > 0)
		{
			try {
				questionsInTopic = yMessService.getQuestionsInTopic(topicName);
			} catch (EmptyResultSetException e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("questionsInTopic",questionsInTopic);
		return "user/view_topic_questions";
	}
}