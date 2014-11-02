package com.ymess.ymail.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ymess.ymail.service.interfaces.YMailService;

@Controller
public class MailActivitiesController {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	YMailService yMailService;
	
	
}
