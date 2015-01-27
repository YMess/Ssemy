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
	 * @return List<Mail>
	 */
	List<Mail> getInboxMails(String userEmailId);


	/**
	 * @author RVishwakarma
	 * @param userEmailId
	 * @return List<Mail>
	 */
	List<Mail> getSentMails(String userEmailId);


	List<Mail> getImportantMails(String userEmailId);


	/**
	 * 
	 * @param deleteMailIds
	 * @param userEmailId 
	 * @return boolean
	 */
	void deleteMails(Long[] deleteMailIds, String userEmailId);


	/**
	 * @author RVishwakarma
	 * @param userEmailId
	 * @return List<Mail>
	 */
	List<Mail> getTrashMails(String userEmailId);

    /**
     * 
     * @param decodedMailId
     * @return
     */
	Mail getMailDetails(String decodedMailId);


	/**
	 * @author rvishwakarma
	 * @param mail
	 * @return
	 */
	void saveMail(Mail mail);


	/**
	 * @author rvishwakarma
	 * @param userEmailId
	 * @return List<Mail>
	 */
	List<Mail> getDraftMails(String userEmailId);

	
}
