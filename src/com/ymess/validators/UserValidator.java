/**
 * 
 */
package com.ymess.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ymess.pojos.User;
import com.ymess.service.interfaces.YMessService;

/**
 * @author balaji i
 *
 */
public class UserValidator implements Validator {

	@Autowired
	private YMessService yMessService;
	
	@Override
	public boolean supports(Class<?> className) {
		boolean supportsFlag = false;
		if(className.equals("User"))
		{
			supportsFlag = true;
		}
		return supportsFlag;
	}

	/**
	 * Validating User Email Id to check if it already Exists
	 */
	@Override
	public void validate(Object user, Errors errors) {

		User userDetails = (User) user;
		
		if(yMessService.checkIfUserEmailExists(userDetails.getUserEmailId()))
		{
			errors.rejectValue("userEmailId", "validation.emailExists");
		}
		
	}
}
