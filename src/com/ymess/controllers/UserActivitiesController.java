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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.pojos.Answer;
import com.ymess.pojos.Question;
import com.ymess.pojos.User;
import com.ymess.service.interfaces.YMessService;
import com.ymess.util.YMessJSPMappings;
import com.ymess.util.YMessLoggerConstants;
import com.ymess.util.YMessMessageConstants;
import com.ymess.util.YMessURLMappings;
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
	@RequestMapping(value=YMessURLMappings.USER_POST_QUESTION,method=RequestMethod.GET)
	public String userPostQuestionPage(@RequestParam(value="qId",required=false) String questionId,Model model)
	{
		Question question = new Question();
		String questionTopics = "";
		
		if( null != questionId && questionId.length() > 0)
		{
			try {
				question = yMessService.getQuestionDetails(questionId);
				for (String  questionTopic : question.getTopics()) {
					questionTopics = questionTopics.concat(" ").concat(questionTopic).concat(",");
				}
				if(questionTopics.length() > 0 && questionTopics.contains(","))
					questionTopics = questionTopics.substring(0,questionTopics.lastIndexOf(","));
			} catch (EmptyResultSetException e) {
				model.addAttribute("emptyResultSet",YMessMessageConstants.EMPTY_RESULT_SET);
				logger.error(YMessMessageConstants.EMPTY_RESULT_SET);
			}catch(Exception ex)
			{
				logger.error(ex.getLocalizedMessage());
			}
		}
		model.addAttribute("questionTopics",questionTopics);
		model.addAttribute("question", question);
		logger.info(YMessLoggerConstants.USER_POST_QUESTION_PAGE);
		return YMessJSPMappings.USER_POST_QUESTION;
	}
	
	/**
	 * Serves User Post a Question Request to process question details
	 * @param question
	 * @param result
	 * @param redirectAttributes
	 * @return String(redirect:userdashboard.htm)
	 */
	@RequestMapping(value=YMessURLMappings.USER_POST_QUESTION,method=RequestMethod.POST)
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
			return YMessJSPMappings.USER_POST_QUESTION;
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmailId = authentication.getName();
		try
		{
			Set<String> keywordsInQuestion =  YMessCommonUtility.findKeywordsinQuestionDescription(question.getQuestionDescription());
			
			question.setAuthorEmailId(userEmailId);
			question.setKeywords(new ArrayList<String>(keywordsInQuestion));

			/** Adding the Question Details in Database */
			if(question.getQuestionId() != null)
				yMessService.updateQuestion(question);
			else
			    yMessService.addQuestion(question);
			logger.info(YMessLoggerConstants.USER_POSTED_QUESTION +" "+userEmailId);
		}
		catch(Exception ex)
		{
			//logger.error(ex.getLocalizedMessage());
			//ex.getMessage();
			ex.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("successfullyPostedQuestion","You Posted the Question Successfully");
		return YMessURLMappings.REDIRECT_SUCCESS_POSTING_QUESTION;	
	}
	
	/**
	 * Fetches all the Questions posted by an User
	 * @param model
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return String (questions.jsp)
	 * @throws IOException
	 */
	@RequestMapping(value=YMessURLMappings.USER_QUESTIONS,method=RequestMethod.GET)
	public String getUserQuestions(Model model,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userEmailId = authentication.getName();
		
		List<Question> questions = new ArrayList<Question>();
		
		try {
			questions = yMessService.getUserQuestions(userEmailId);
			logger.info(YMessLoggerConstants.USER_QUESTIONS +" "+ userEmailId);
		} catch (EmptyResultSetException e) {
			logger.error(e.getLocalizedMessage());
			model.addAttribute("emptyResultSet",true);
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		model.addAttribute("questions",questions);
		return YMessJSPMappings.USER_QUESTIONS;
	}
	
	/**
	 * Processes and Adds an Answer of a Particular Question
	 * @param questionId
	 * @param answer
	 * @return Boolean (successFlag)
	 */
	@RequestMapping(value=YMessURLMappings.USER_POST_ANSWER,method=RequestMethod.POST)
	@ResponseBody
	void addAnswer(@ModelAttribute("answer") Answer answer,BindingResult bindingResult)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();
		answer.setAuthorEmailId(loggedInUserEmailId);
		
		try
		{
			yMessService.addAnswer(answer);
			logger.info(YMessLoggerConstants.USER_POSTED_ANSWER+" "+loggedInUserEmailId);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value=YMessURLMappings.USER_QUESTION_RESPONSES)
	String getUserQuestionResponses(@RequestParam("qId")String encodedQuestionId,Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();
		
		String decodedQuestionId = YMessCommonUtility.decodeEncodedParameter(encodedQuestionId);
		
		List<Answer> answers = new ArrayList<Answer>();
		try {
			answers = yMessService.getUserQuestionResponses(loggedInUserEmailId,decodedQuestionId);
			logger.info(YMessLoggerConstants.USER_QUESTION_RESPONSES +" "+loggedInUserEmailId);
			
		} 
		catch(EmptyResultSetException emptyResultSet)
		{
			model.addAttribute("emptyResultSet",YMessMessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		model.addAttribute("loggedInUser",loggedInUserEmailId);
		model.addAttribute("answers",answers);
		return YMessJSPMappings.USER_QUESTION_RESPONSES;
	}
	
	/**
	 * Fetches all the upvoters of an Answer
	 * @param encodedAnswerId
	 * @param encodedQuestionId
	 * @param model
	 * @return List<User> upvoters
	 */
	@RequestMapping(value=YMessURLMappings.USER_ANSWER_UPVOTERS)
	String getUpvoters(@RequestParam("aId") String encodedAnswerId,@RequestParam("qId") String encodedQuestionId,Model model)
	{
		String questionId = YMessCommonUtility.decodeEncodedParameter(encodedQuestionId);
		String answerId = YMessCommonUtility.decodeEncodedParameter(encodedAnswerId);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();
		
		List<User> upvoters = new ArrayList<User>();
		try {
			upvoters = yMessService.getAnswerUpvoters(questionId,answerId);
			logger.info(YMessLoggerConstants.ANSWER_UPVOTERS  );
		} catch (EmptyResultSetException e) {
			model.addAttribute("emptyResultSet",YMessMessageConstants.EMPTY_RESULT_SET);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		model.addAttribute("upvoters",upvoters);
		model.addAttribute("loggedInUser",loggedInUserEmailId);
		return YMessJSPMappings.USER_ANSWER_UPVOTERS;
	}
	
	
	/**
	 * Fetches all the downvoters of an Answer
	 * @param encodedAnswerId
	 * @param encodedQuestionId
	 * @param model
	 * @return List<User> downvoters
	 */
	@RequestMapping(value=YMessURLMappings.USER_ANSWER_DOWNVOTERS)
	String getDownvoters(@RequestParam("aId") String encodedAnswerId,@RequestParam("qId") String encodedQuestionId,Model model)
	{
		String questionId = YMessCommonUtility.decodeEncodedParameter(encodedQuestionId);
		String answerId = YMessCommonUtility.decodeEncodedParameter(encodedAnswerId);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();
		
		List<User> downvoters = new ArrayList<User>();
		try {
			downvoters = yMessService.getAnswerDownvoters(questionId,answerId);
			logger.info(YMessLoggerConstants.ANSWER_UPVOTERS  );
		} catch (EmptyResultSetException e) {
			model.addAttribute("emptyResultSet",YMessMessageConstants.EMPTY_RESULT_SET);
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		model.addAttribute("downvoters",downvoters);
		model.addAttribute("loggedInUser",loggedInUserEmailId);
		return YMessJSPMappings.USER_ANSWER_DOWNVOTERS;
	}
	
	/**
	 * Upvotes an Answer and adds the Upvoted User and the Upvoted Count
	 * @author balaji i
	 * @param questionId
	 * @param answerId
	 * @return Boolean(successFlag)
	 */
	@RequestMapping(value=YMessURLMappings.USER_UPVOTE_ANSWER,method=RequestMethod.GET)
	@ResponseBody
	Boolean upvoteAnswer(@RequestParam("qId") String questionId,@RequestParam("aId") String answerId)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		Boolean successFlag = yMessService.upvoteAnswer(questionId,answerId,loggedInUserEmail);
		logger.info(YMessLoggerConstants.USER_UPVOTE_ANSWER+" "+loggedInUserEmail);
		
		return successFlag;
	}
	
	/**
	 * Upvotes an Answer and adds the Upvoted User and the Upvoted Count
	 * @author balaji i
	 * @param questionId
	 * @param answerId
	 * @return Boolean(successFlag)
	 */
	@RequestMapping(value=YMessURLMappings.USER_DOWNVOTE_ANSWER,method=RequestMethod.GET)
	@ResponseBody
	Boolean downvoteAnswer(@RequestParam("qId") String questionId,@RequestParam("aId") String answerId)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		Boolean successFlag = yMessService.downvoteAnswer(questionId,answerId,loggedInUserEmail);
		
		logger.info(YMessLoggerConstants.USER_DOWNVOTE_ANSWER+" "+loggedInUserEmail);
		return successFlag;
	}
	
	@RequestMapping(YMessURLMappings.USER_FOLLOW)
	@ResponseBody
	Boolean userFollow(@RequestParam("eId") String encodedUserEmailId)
	{
		String decodedUserEmailId = YMessCommonUtility.decodeEncodedParameter(encodedUserEmailId);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		yMessService.followUser(loggedInUserEmail,decodedUserEmailId);
		logger.info(loggedInUserEmail+" "+YMessLoggerConstants.USER_FOLLOWING+" "+decodedUserEmailId);
		
		return true;
	}
	
	@RequestMapping(YMessURLMappings.USER_UNFOLLOW)
	@ResponseBody
	Boolean userUnfollow(@RequestParam("eId") String encodedUserEmailId)
	{
		String decodedUserEmailId = YMessCommonUtility.decodeEncodedParameter(encodedUserEmailId);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		yMessService.unfollowUser(loggedInUserEmail,decodedUserEmailId);
		logger.info(loggedInUserEmail+" "+YMessLoggerConstants.USER_UNFOLLOWING+" "+decodedUserEmailId);
		
		return true;
	}
	
	
	@RequestMapping(value=YMessURLMappings.FOLLOWING_USERS)
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
			model.addAttribute("emptyResultSet",YMessMessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
		}
		
		model.addAttribute("followingUsers",followingUsers);
		return YMessJSPMappings.FOLLOWING_USERS;
	}
	
	@RequestMapping(value=YMessURLMappings.FOLLOWERS)
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
			model.addAttribute("emptyResultSet",YMessMessageConstants.EMPTY_RESULT_SET);
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
		}
		
		model.addAttribute("followers",followers);
		return YMessJSPMappings.FOLLOWERS;
	}
	
	@RequestMapping(value=YMessURLMappings.USER_QUESTION_IMAGE)
	String getUserViewImage(@RequestParam("qId") String encodedQuestionId , HttpServletRequest request,HttpServletResponse response)
	{
		String decodedQuestionId = YMessCommonUtility.decodeEncodedParameter(encodedQuestionId);
		Question questionImage = yMessService.getQuestionImage(decodedQuestionId);
		
		String imageName = questionImage.getQuestionImageName();
		byte[] image =  questionImage.getQuestionImageDB();
		   
		//logger.info(LoggerConstants.USER_VIEW_PROFILE_IMAGE+" "+ decodedQuestionId);
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
		      response.getOutputStream().write(image);
		      response.getOutputStream().flush();
		      response.getOutputStream().close();
		   }
		   catch(Exception ex)
		   {
			   logger.error(ex.getLocalizedMessage());
		   }
		return null;
	}
	
	@RequestMapping(value = YMessURLMappings.USER_VIEW_TOPIC_QUESTIONS)
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
	
	
	@RequestMapping(value=YMessURLMappings.QUESTION_IMAGE_UPLOAD,method=RequestMethod.POST)
	String uploadQuestionImage(@RequestParam(value="qId",required=false) String questionId,@ModelAttribute("question") Question question)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		if(question.getIsImageAttached() != null && question.getIsImageAttached())
		{	
			if(question.getQuestionImage().getOriginalFilename().length() > 0)
			{
				question.setAuthorEmailId(loggedInUserEmail);
				try
				{
					yMessService.uploadQuestionImage(question);
					logger.info(YMessLoggerConstants.QUESTION_UPLOADED_IMAGE +" "+loggedInUserEmail);
				}
				catch(Exception ex)
				{
				      logger.error(ex.getLocalizedMessage());
				}
			}
		}
		
		return YMessURLMappings.QUESTION_IMAGE_UPLOAD_REDIRECTION;
	}
	
	
	@RequestMapping(value = YMessURLMappings.FETCH_ANSWER_IMAGE)
	void fetchAnswerImage(@RequestParam("qId") String encodedQuestionId,@RequestParam("aId") String encodedAnswerId,HttpServletResponse response)
	{
		String questionId = YMessCommonUtility.decodeEncodedParameter(encodedQuestionId);
		String answerId = YMessCommonUtility.decodeEncodedParameter(encodedAnswerId);
		
		Answer answerImage = yMessService.getAnswerImage(questionId,answerId);
		
		byte[] image =  answerImage.getAnswerImageDb();
		   
		   try
		   {
			    response.setContentType("image/jpg");
			    response.setContentLength(image.length);
		        response.setHeader("Content-Disposition", "inline; ");
		        response.getOutputStream().write(image);
		        response.getOutputStream().flush();
		        response.getOutputStream().close();
		   }
		   catch(Exception ex)
		   {
			   logger.error(ex.getLocalizedMessage());
		   }
		
	}

}