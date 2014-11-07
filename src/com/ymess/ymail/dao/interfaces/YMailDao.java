package com.ymess.ymail.dao.interfaces;

import com.ymess.ymail.pojos.Mail;

public interface YMailDao {

	/**
	 * @author RVishwakarma
	 * @param mail
	 */
	void sendMail(Mail mail);

}
