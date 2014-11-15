package com.ymess.ymail.service.interfaces;

import java.util.List;

import com.ymess.ymail.pojos.Mail;

public interface YMailService {

	/**
	 * @author RVishwakarma
	 * @param mail
	 * 
	 */
	void sendMail(Mail mail);

	/**
	 *@author RVishwakarma
	 * @param userEmailId
	 * @return List<Mail>
	 */
	List<Mail> getInboxMails(String userEmailId);

}
