/**
 * 
 */
package com.ymess.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.pojos.File;
import com.ymess.service.interfaces.YMessService;
import com.ymess.util.JSPMappings;
import com.ymess.util.URLMappings;
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
	
	@RequestMapping(value = URLMappings.FILES)
	String loadFilesPage(Model model)
	{
		List<File> files = new ArrayList<File>();
		try {
			
			files = yMessService.getAllSharedFiles();
		} catch (EmptyResultSetException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("file",new File());
		model.addAttribute("files",files);
		return JSPMappings.FILES;
	}
	
	/**
	 * Uploads the Zip File Data as is.
	 * @param file
	 * @param result
	 * @param model
	 * @return files.htm
	 */
	@RequestMapping(value = URLMappings.FILE_UPLOAD,method = RequestMethod.POST)
	String loadFilesPage(@ModelAttribute("file") File file, BindingResult result,Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		file.setAuthorEmailId(authentication.getName());
		
		if(file.getFileData() != null && file.getFileData().getOriginalFilename().length() > 0)
		{
			double bytes = file.getFileData().getSize();
			double kilobytes = (bytes / 1024);
			double megabytes = 0.0d;

			if(kilobytes > 1024)
			{	
				megabytes = (kilobytes / 1024);
				file.setFileSize(Math.ceil(megabytes)+" MB");
			}
			else
			{
				file.setFileSize(Math.ceil(kilobytes)+" KB");
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
		
		if(result.hasErrors())
		{
			return JSPMappings.FILES;
		}
		
		try {
			
			yMessService.uploadFile(file);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return URLMappings.REDIRECT_SUCCESS_FILE_UPLOAD;
	}
	/**
	 * Downloads the Clicked Zip File
	 * @param fileId
	 * @param authorEmailId
	 * @param response
	 * @return file data in response
	 */
	@RequestMapping(value = URLMappings.DOWNLOAD_FILE)
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

}
