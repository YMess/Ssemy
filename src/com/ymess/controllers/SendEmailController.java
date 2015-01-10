package com.ymess.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ymess.util.YMessMessageConstants;
import com.ymess.ymail.pojos.Mail;
 
@Controller
@RequestMapping("/sendEmail.htm")
public class SendEmailController {
 
    @Autowired
    private JavaMailSender mailSender;
     
    Logger logger = Logger.getLogger(getClass());
    
    @RequestMapping(method = RequestMethod.GET)
    String loadEmailPage(Model model)
    {
    	model.addAttribute("emailParameters",new Mail());
    	return "common/send_email";
    }
    
    
    @RequestMapping(method = RequestMethod.POST)
    public String doSendEmail(@ModelAttribute("emailParameters")Mail mailDetails,HttpServletRequest request) {
        
        // creates a simple e-mail object
        SimpleMailMessage email = new SimpleMailMessage();
        
        for (String toMailId : mailDetails.getMailTo()) {
        	email = new SimpleMailMessage();
        	email.setTo(toMailId);
            email.setSubject(mailDetails.getMailSubject());
            email.setText(mailDetails.getMailBody());
             
            // sends the e-mail
            mailSender.send(email);
            logger.info(YMessMessageConstants.MAIL_SENT_SUCCESSFULLY +" "+toMailId);
		}
        return "common/send_email";
    }
}
