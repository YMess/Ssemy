package com.ymess.ymail.dao.interfaces;

import java.util.List;

import com.ymess.ymail.pojos.Mail;

public interface YMailDao {

	/**
	 * @author RVishwakarma
	 * @param mail
	 */
	void sendMail(Mail mail);

	
	/**
	 * @author RVishwakarma
	 * @param userEmailId
	 * @return
	 */
	List<Mail> getInboxMails(String userEmailId);

	


}
