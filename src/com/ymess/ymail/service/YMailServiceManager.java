package com.ymess.ymail.service;

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
}
