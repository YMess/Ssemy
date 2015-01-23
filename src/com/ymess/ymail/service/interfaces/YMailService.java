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

	/**
	 *@author RVishwakarma
	 * @param userEmailId
	 * @return List<Mail>
	 */
	List<Mail> getSentMails(String userEmailId);
    
	/**
	 * @author RVishwakarma
	 * @param userEmailId
	 * @return List<Mail>
	 */
	List<Mail> getImportantMails(String userEmailId);

	/**
	 * @author RVishwakarma
	 * @param loggedInUserEmail 
	 * @param inboxMailIds[]
	 */
	void deleteMails(Long[] inboxMailIds, String loggedInUserEmail);

	/**
	 * @author RVishwakarma
	 * @param userEmailId
	 * @return List<Mail>
	 */
	List<Mail> getTrashMails(String name);

	/**
	 * 
	 * @param decodedMailId
	 * @return
	 */
	Mail getMailDetails(String decodedMailId);

}
