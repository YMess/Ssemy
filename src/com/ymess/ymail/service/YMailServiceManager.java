package com.ymess.ymail.service;

import java.util.List;

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

	@Override
	public List<Mail> getImportantMails(String userEmailId) {
	    return yMailDao.getImportantMails(userEmailId);
	}
}
