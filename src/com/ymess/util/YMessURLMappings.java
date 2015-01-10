/**
 * 
 */
package com.ymess.util;

/**
 * Contains all the URL Mappings
 * @author balaji i
 *
 */
public class YMessURLMappings {
	
	public static final String LOGIN_PAGE = "login.htm"; //LoginController
	public static final String LOGIN_FAILED_PAGE = "/loginfailed.htm"; //LoginController
	public static final String DASHBOARD_PAGE = "/userdashboard.htm"; //LoginController
	
	public static final String REGISTRATION_PAGE = "/registration.htm"; //RegistrationController
	
	public static final String HOME_PAGE = "home.htm"; //HomeController
	
	public static final String USER_POST_QUESTION = "/user_post_question.htm";//UserActivitiesController
	public static final String REDIRECT_SUCCESS_POSTING_QUESTION = "redirect:"+DASHBOARD_PAGE; //UserActivitiesController
	public static final String USER_QUESTIONS = "user_questions.htm"; //UserActivitiesController
	public static final String USER_POST_ANSWER = "user_post_answer.htm"; //UserActivitiesController
	public static final String USER_QUESTION_RESPONSES = "user_question_responses.htm"; //UserActivitiesController
	public static final String USER_ANSWER_UPVOTERS = "user_answer_upvoters.htm"; //UserActivitiesController
	public static final String USER_UPVOTE_ANSWER = "user_upvote_answer.htm"; //UserActivitiesController
	public static final String USER_DOWNVOTE_ANSWER = "user_downvote_answer.htm"; //UserActivitiesController
	public static final String FOLLOWING_USERS = "user_following.htm"; //UserActivitiesController
	public static final String FOLLOWERS = "user_followed_by.htm"; //UserActivitiesController
	public static final String USER_FOLLOW = "user_follow.htm"; //UserActivitiesController
	public static final String USER_UNFOLLOW =  "user_unfollow.htm"; //UserActivitiesController
	public static final String USER_ANSWER_DOWNVOTERS = "user_answer_downvoters.htm"; //UserActivitiesController
	public static final String USER_QUESTION_IMAGE = "user_question_image.htm"; //UserActivitiesController
	public static final String QUESTION_IMAGE_UPLOAD = "question_image_upload.htm";//UserActivitiesController
	public static final String QUESTION_IMAGE_UPLOAD_REDIRECTION = "redirect:"+QUESTION_IMAGE_UPLOAD; //UserActivitiesController
	public static final String FETCH_ANSWER_IMAGE = "fetch_answer_image.htm"; //UserActivitiesController
	
	
	public static final String REDIRECT_SUCCESS_USER_REGISTRATION = "redirect:"+LOGIN_PAGE+"?Success='Youve Successfully Registered at YMess'";
	
	public static final String USER_PROFILE = "user_profile.htm"; //UserProfileController
	public static final String USER_PROFILE_SUCCESS_REDIRECTION = "redirect:"+USER_PROFILE; //UserProfileController
	public static final String USER_PROFILE_IMAGE = "user_profile_image.htm"; //UserProfileController
	public static final String USER_IMAGE_UPLOAD = "user_profile_image_upload.htm"; //UserProfileController
	public static final String USER_VIEW_PROFILE = "user_view_profile.htm"; //UserProfileController
	public static final String USER_VIEW_PROFILE_IMAGE = "user_view_profile_image.htm"; //UserProfileController
	
	public static final String USER_TIMELINE = "user_timeline.htm";//UserTimeLIneController
	
	public static final String USER_VIEW_TOPIC_QUESTIONS = "view_topic_questions.htm"; //UserActivitiesController
	
	public static final String GET_TOPICS = "get_topics.htm";
	
	public static final String FILES = "files.htm"; //FileActivitiesController
	public static final String FILE_UPLOAD = "file_upload.htm"; //FileActivitiesController
	public static final String REDIRECT_SUCCESS_FILE_UPLOAD = "redirect:"+FILES+"?success='File Uploaded Successfully'"; //FileActivitiesController
	public static final String DOWNLOAD_FILE = "download_file.htm"; //FileActivitiesController
	public static final String DELETE_FILE = "delete_file.htm"; //FileActivitiesController
	public static final String SHARE_FILE = "share_file.htm"; //FileActivitiesController
	public static final String REDIRECT_SUCCESS_FILE_DELETED = "redirect:"+FILES +"?success=You've Successfully Deleted the File"; //FileActivitiesController
	public static final String REDIRECT_SUCCESS_FILE_SHARED = "redirect:"+FILES +"?success=Successfully Shared the File"; //FileActivitiesController
	
	public static final String SEARCH = "search.htm"; //SearchController
	public static final String GET_RELATED_TOPICS = "get_related_topics.htm"; //SearchController
	
	public static final String EDIT_FILE = "edit_file.htm"; //FileActivitiesController
	public static final String FILES_IN_TOPIC = "view_files_topic.htm"; //FileActivitiesController

}
