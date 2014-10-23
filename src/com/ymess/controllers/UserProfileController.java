package com.ymess.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.pojos.File;
import com.ymess.pojos.User;
import com.ymess.service.interfaces.YMessService;
import com.ymess.util.JSPMappings;
import com.ymess.util.LoggerConstants;
import com.ymess.util.MessageConstants;
import com.ymess.util.URLMappings;
import com.ymess.util.YMessCommonUtility;

@Controller
public class UserProfileController {
	
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	YMessService yMessService;
	
	@RequestMapping(value=URLMappings.USER_PROFILE,method=RequestMethod.GET)
	String getUserProfilePage( Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();

		User userDetails = new User();
		String userPreviousOrganizations = "";
		String userInterests = "";
		
		try{
			userDetails = yMessService.getUserDetails(loggedInUserEmail);
			
				for (String  userPreviousOrganization : userDetails.getPreviousOrganizations()) {
					userPreviousOrganizations = userPreviousOrganizations.concat(" ").concat(userPreviousOrganization).concat(",");
				}
				if(userPreviousOrganizations.length() > 0 && userPreviousOrganizations.contains(","))
					userPreviousOrganizations = userPreviousOrganizations.substring(0,userPreviousOrganizations.lastIndexOf(","));
			
				for (String  userInterest : userDetails.getInterests()) {
					userInterests = userInterests.concat(" ").concat(userInterest).concat(",");
				}
				if(userInterests.length() > 0 && userInterests.contains(","))
					userInterests = userInterests.substring(0,userInterests.lastIndexOf(","));
			logger.info(LoggerConstants.USER_PROFILE_LOADED+ " " + loggedInUserEmail);
		}
		catch(EmptyResultSetException emptyRS)
		{
			model.addAttribute("emptyResultSet",MessageConstants.EMPTY_RESULT_SET);
			logger.error(emptyRS.getLocalizedMessage());
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		model.addAttribute("userPreviousOrganizations",userPreviousOrganizations);
		model.addAttribute("userInterests",userInterests);
		
		model.addAttribute("userDetails",userDetails);
		model.addAttribute("user", new User());
		
		return JSPMappings.USER_PROFILE;
	}
	
	@RequestMapping(value=URLMappings.USER_PROFILE,method=RequestMethod.POST)
	String postUserProfileDetails(@ModelAttribute("user") @Valid User user,BindingResult result)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		result.recordSuppressedField("password");
		result.recordSuppressedField("confirmPassword");
		
		if(result.hasErrors())
		{
			return JSPMappings.USER_PROFILE;
		}
		user.setUserEmailId(loggedInUserEmail);
		
		yMessService.updateUserProfile(user);
		logger.info(LoggerConstants.UPDATED_USER_PROFILE +" " + loggedInUserEmail);
		
		return URLMappings.USER_PROFILE_SUCCESS_REDIRECTION;
	}
	
	@RequestMapping(value=URLMappings.USER_PROFILE_IMAGE)
	String getUserImage(HttpServletRequest request,HttpServletResponse response)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		User userImage = yMessService.getUserImage(loggedInUserEmail);
		String imageName = userImage.getImageName();
		byte[] image = null;
		
		if(null != imageName && imageName.length() > 0)
		{
			image =  userImage.getUserImageData();
			imageName = userImage.getImageName();
		}
		else
		{
			File fileDetails = yMessService.getDefaultImage();
			image = fileDetails.getFileDataDb();
			imageName = fileDetails.getFilename();
		}
		
		logger.info(LoggerConstants.USER_PROFILE_IMAGE+" "+ loggedInUserEmail);
		   try
		   {
		    String imageFormat=null;
		    //Code Added By BalajiI to Accommodate All Image Formats(namely PNG,JPEG,BMP)
		    
		    if(imageName != null && !imageName.equals(""))
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
	
	@RequestMapping(value=URLMappings.USER_IMAGE_UPLOAD,method=RequestMethod.POST)
	String uploadUserImage(@ModelAttribute("user") User user,BindingResult result)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		if(user.getUserImage().getOriginalFilename().length() > 0)
		{
			user.setUserEmailId(loggedInUserEmail);
			yMessService.uploadUserImage(user);
			logger.info(LoggerConstants.USER_UPLOADED_IMAGE +" "+loggedInUserEmail);
		}
		return URLMappings.USER_PROFILE_SUCCESS_REDIRECTION;
	}
	
	@RequestMapping(value=URLMappings.USER_VIEW_PROFILE)
	String viewUserProfile(@RequestParam(value = "aId") String encodedUserEmailId ,Model model)
	{
		String userEmailId = "";
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmailId = authentication.getName();
		
		if(encodedUserEmailId != null)
		{
			userEmailId =  YMessCommonUtility.decodeEncodedParameter(encodedUserEmailId);
		}
		/** To Avoid Displaying Follow button in case of Self Profile(i.e if a user with abc@gmail.com views abc@gmail.com(his own profile),he/she shouldnt be shown follow button) */
		if(loggedInUserEmailId.equals(userEmailId))
			model.addAttribute("selfProfile",true);
		
		User userDetails = new User();
		String userPreviousOrganizations = "";
		String userInterests = "";
		
		try{
			userDetails = yMessService.getUserDetails(userEmailId);
			
			Map<String,Date> followingUsers = yMessService.getFollowingUsersMap(loggedInUserEmailId);
			
			if(followingUsers != null)
			{
				if(followingUsers.containsKey(userEmailId))
				{
					model.addAttribute("alreadyFollowed",true);
				}
			}
			
			logger.info(LoggerConstants.USER_VIEW_PROFILE +" "+userEmailId);
				for (String  userPreviousOrganization : userDetails.getPreviousOrganizations()) {
					userPreviousOrganizations = userPreviousOrganizations.concat(" ").concat(userPreviousOrganization);
				}
			
				for (String  userInterest : userDetails.getInterests()) {
					userInterests = userInterests.concat(" ").concat(userInterest);
				}
			
		}
		catch(EmptyResultSetException emptyRS)
		{
			model.addAttribute("emptyResultSet",MessageConstants.EMPTY_RESULT_SET);
			logger.error(emptyRS.getLocalizedMessage());
		}
		catch(Exception ex)
		{
			logger.error(ex.getLocalizedMessage());
		}
		model.addAttribute("userPreviousOrganizations",userPreviousOrganizations);
		model.addAttribute("userInterests",userInterests);
		
		model.addAttribute("userDetails",userDetails);
		model.addAttribute("user", new User());
		
		return JSPMappings.USER_VIEW_PROFILE;
	}
	
	@RequestMapping(value=URLMappings.USER_VIEW_PROFILE_IMAGE)
	String getUserViewImage(@RequestParam("aId") String encodedUserEmailId , HttpServletRequest request,HttpServletResponse response)
	{
		String decodedUserEmailId = YMessCommonUtility.decodeEncodedParameter(encodedUserEmailId);
		User userImage = yMessService.getUserImage(decodedUserEmailId);
		
		String imageName = userImage.getImageName();
		byte[] image =  userImage.getUserImageData();
		   
		if(null != imageName && imageName.length() > 0)
		{
			image =  userImage.getUserImageData();
			imageName = userImage.getImageName();
		}
		else
		{
			File fileDetails = yMessService.getDefaultImage();
			image = fileDetails.getFileDataDb();
			imageName = fileDetails.getFilename();
		}
		
		logger.info(LoggerConstants.USER_VIEW_PROFILE_IMAGE+" "+ decodedUserEmailId);
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
	
	/**
	 * Fetches all the topic names available in DB
	 * @author balaji i
	 * @return Topics(List<String>)
	 * @throws EmptyResultSetException 
	 */
	@RequestMapping(value = URLMappings.GET_TOPICS)
	@ResponseBody
	List<String> getAllTopics()
	{
		List<String> topics = new ArrayList<String>();
		try {
			topics = yMessService.getAllTopics();
		} catch (EmptyResultSetException e) {
			logger.error(MessageConstants.EMPTY_RESULT_SET);
		}
		catch(Exception ex){
			logger.error(ex.getStackTrace());
		}
		return topics;
	}
}