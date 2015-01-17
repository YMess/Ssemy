/**
 * 
 */
package com.ymess.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.pojos.File;
import com.ymess.service.interfaces.YMessService;
import com.ymess.util.YMessJSPMappings;
import com.ymess.util.YMessLoggerConstants;
import com.ymess.util.YMessURLMappings;
import com.ymess.util.YMessCommonUtility;

/**
 * @author balaji i
 *
 */
@Controller
public class FileActivitiesController {
	
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	YMessService yMessService;
	
	@RequestMapping(value = YMessURLMappings.FILES)
	String loadFilesPage(@ModelAttribute("fileData") File fileDetails, Model model)
	{
		if(fileDetails != null)
			model.addAttribute("file",fileDetails);
		else
			model.addAttribute("file",new File());
		
		
		String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<File> userFiles = new ArrayList<File>();
		List<File> sharedFiles = new ArrayList<File>();
		Map<String, List<File>> popularFiles = new HashMap<String, List<File>>();
		try {
			if(null != loggedInUserEmail && !loggedInUserEmail.isEmpty())
				userFiles = yMessService.getUserFiles(loggedInUserEmail);
		popularFiles = yMessService.getPopularTopicsWithFiles();
		//sharedFiles = yMessService.getAllSharedFiles();
		
		} catch (EmptyResultSetException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("userFiles",userFiles);
		model.addAttribute("sharedFiles",sharedFiles);
		model.addAttribute("popularFiles",popularFiles);
		return YMessJSPMappings.FILES;
	}
	
	/**
	 * Uploads the Zip File Data as is.
	 * @param file
	 * @param result
	 * @param model
	 * @return files.htm
	 */
	@RequestMapping(value = YMessURLMappings.FILE_UPLOAD,method = RequestMethod.POST)
	String loadFilesPage(@ModelAttribute("file") File file, BindingResult result,Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		file.setAuthorEmailId(authentication.getName());
		
		if(file.getEditFlag() != null || (file.getFileData() != null && file.getFileData().getOriginalFilename().length() > 0))
		{
			
			double megabytes = 0.0d;
			if(file.getFileData().getOriginalFilename().length() > 0)
			{
				double bytes = file.getFileData().getSize();
				double kilobytes = (bytes / 1024);
	
				if(kilobytes > 1024)
				{	
					megabytes = (kilobytes / 1024);
					file.setFileSize(Math.ceil(megabytes)+" MB");
				}
				else
				{
					file.setFileSize(Math.ceil(kilobytes)+" KB");
				}
			}
			if(megabytes > 35)
			{
				result.rejectValue("fileSize", "File.fileSize","Please Upload a file within 35 MB");
			}
		}
		else
		{
			result.rejectValue("fileSize", "File.fileSize","Please Upload a file");
		}
		
		if(file.getTopics().isEmpty())
		{
			result.rejectValue("topics", "file.topics","Please Enter at Least One Topic");
		}
		if(file.getFileDescription().isEmpty())
		{
			result.rejectValue("fileDescription", "file.fileDescription","Please Enter File Description");
		}
		
		if(result.hasErrors())
		{
			return YMessJSPMappings.FILES;
		}
		
		try {
			
			yMessService.uploadFile(file);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return YMessURLMappings.REDIRECT_SUCCESS_FILE_UPLOAD;
	}
	/**
	 * Downloads the Clicked Zip File
	 * @param fileId
	 * @param authorEmailId
	 * @param response
	 * @return file data in response
	 */
	@RequestMapping(value = YMessURLMappings.DOWNLOAD_FILE)
	String downloadFile(@RequestParam("fileId") String fileId,@RequestParam("author")String authorEmailId,HttpServletResponse response)
	{
		String encodedFileId = YMessCommonUtility.decodeEncodedParameter(fileId);
		String encodedAuthorEmailId = YMessCommonUtility.decodeEncodedParameter(authorEmailId);
		
		File fileDetails = yMessService.downloadFile(encodedFileId,encodedAuthorEmailId);
		
		byte[] zip = fileDetails.getFileDataDb();
		
		 OutputStream outputStream;
		try {
			 outputStream = response.getOutputStream();
			 response.setContentType("application/zip");
		     response.setHeader("Content-Disposition", "attachment; filename="+fileDetails.getFilename());
		     outputStream.write(zip);
		     outputStream.flush();
		     
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Facilitates File Deletion
	 * @param encodedFileId
	 * @param encodedAuthorEmailId
	 */
	@RequestMapping(value = YMessURLMappings.DELETE_FILE)
	String deleteFile(@RequestParam("fId") String encodedFileId,@RequestParam("author") String encodedAuthorEmailId)
	{
		String fileId = YMessCommonUtility.decodeEncodedParameter(encodedFileId);
		String authorEmailId = YMessCommonUtility.decodeEncodedParameter(encodedAuthorEmailId);
		
		yMessService.deleteFile(fileId,authorEmailId);
		logger.info(YMessLoggerConstants.FILE_DELETED);
		
		return YMessURLMappings.REDIRECT_SUCCESS_FILE_DELETED;
	}
	
	/**
	 * Facilitates File Sharing
	 * @param encodedFileId
	 * @param encodedAuthorEmailId
	 */
	@RequestMapping(value = YMessURLMappings.SHARE_FILE)
	String shareFile(@RequestParam("fId") String encodedFileId,@RequestParam("author") String encodedAuthorEmailId)
	{
		String fileId = YMessCommonUtility.decodeEncodedParameter(encodedFileId);
		String authorEmailId = YMessCommonUtility.decodeEncodedParameter(encodedAuthorEmailId);
		
		yMessService.shareFile(fileId,authorEmailId);
		logger.info(YMessLoggerConstants.FILE_SHARED);
		
		return YMessURLMappings.REDIRECT_SUCCESS_FILE_SHARED;
	}
	
	/**
	 * Facilitates File Editing
	 * @author balaji i
	 * @param encodedFileId
	 * @param encodedAuthorEmailId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = YMessURLMappings.EDIT_FILE)
	String editFile(@RequestParam("fId")String encodedFileId, @RequestParam("author") String encodedAuthorEmailId, RedirectAttributes redirectAttributes)
	{
		String fileId = YMessCommonUtility.decodeEncodedParameter(encodedFileId);
		String authorEmailId = YMessCommonUtility.decodeEncodedParameter(encodedAuthorEmailId);
		
		File fileDetails = new File();
		fileDetails = yMessService.getFileDetails(fileId,authorEmailId);
		
		redirectAttributes.addFlashAttribute("fileData",fileDetails);
		return "redirect:files.htm";
	}
	
	/**
	 * Retrieves Files in a Topic
	 * @author balaji i
	 * @param encodedTopic
	 * @return topic_files(filesInTopic)
	 */
	@RequestMapping(value = YMessURLMappings.FILES_IN_TOPIC)
	String getFilesInTopic(@RequestParam("t") String encodedTopic,Model model)
	{
		String topic = YMessCommonUtility.decodeEncodedParameter(encodedTopic);
		List<File> filesInTopic = yMessService.getFilesInTopic(topic);
		model.addAttribute("filesInTopic",filesInTopic);
		return YMessJSPMappings.FILES_IN_TOPIC;
	}
}
