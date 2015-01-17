package com.ymess.controllers;



import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ymess.exceptions.EmptyResultSetException;
import com.ymess.pojos.TimeLine;
import com.ymess.service.interfaces.YMessService;
import com.ymess.util.YMessJSPMappings;
import com.ymess.util.YMessURLMappings;

@Controller
public class UserTimeLineController {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	YMessService yMessService;
	
	@RequestMapping(value=YMessURLMappings.USER_TIMELINE, method=RequestMethod.GET)
	String viewUserTimeLine(Model model)
	{
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserEmail = authentication.getName();
		
		List<TimeLine> userTimeline = new ArrayList<TimeLine>();
		
		try {
			userTimeline = yMessService.getUserTimeLine(loggedInUserEmail);
		} catch (EmptyResultSetException e) {
			logger.error(e.getLocalizedMessage());
		}
		
		model.addAttribute("userTimeline",userTimeline);
		return YMessJSPMappings.USER_TIMELINE;
	}
	
}
