package com.ymess.ymail.service;

import java.util.List;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.ymess.ymail.dao.interfaces.YMailDao;
import com.ymess.ymail.pojos.Mail;
import com.ymess.ymail.service.interfaces.YMailService;

public class YMailServiceManager implements YMailService {

	YMailDao yMailDao;

	public YMailDao getyMailDao() {
		return yMailDao;
	}

	public void setyMailDao(YMailDao yMailDao) {
		this.yMailDao = yMailDao;
	}

	/**
	 * Send Email to respected users
	 * @author RVishwakarma
	 * @param mail
	 */
	@Override
	public void sendMail(Mail mail) {
		yMailDao.sendMail(mail);
		
	}

	
	/**
	 * Load Inbox Email page
	 * @author RVishwakarma
	 * @param userEmailId
	 * @return List<Mail>
	 */
	@Override
	public List<Mail> getInboxMails(String userEmailId) {
		return yMailDao.getInboxMails(userEmailId);
	}

	/**
	 * Load Sent Email page
	 * @author RVishwakarma
	 * @param userEmailId
	 * @return List<Mail>
	 */
	@Override
	public List<Mail> getSentMails(String userEmailId) {
		return yMailDao.getSentMails(userEmailId);
	}

	/* Load Important Email page
	 * @author RVishwakarma
	 * @param userEmailId
	 * @return List<Mail>
	 */
	@Override
	public List<Mail> getImportantMails(String userEmailId) {
	    return yMailDao.getImportantMails(userEmailId);
	}

	/**
	 * @author RVishwakarma
	 * @param deleteMailIds[], userEmailId
	 * @return Boolean
	 */
	@Override
	public void deleteMails(Long[] deleteMailIds,String userEmailId) {
		 yMailDao.deleteMails(deleteMailIds,userEmailId);
	}

	/**Load Trash Mail Page
	 * @author RVishwakarma
	 * @param userEmailId
	 * @return List<Mail>
	 */
	@Override
	public List<Mail> getTrashMails(String userEmailId) {
		return yMailDao.getTrashMails(userEmailId);
	}

	/**
	 * 
	 */
	@Override
	public Mail getMailDetails(String decodedMailId) {
		return yMailDao.getMailDetails(decodedMailId);
	}
}
