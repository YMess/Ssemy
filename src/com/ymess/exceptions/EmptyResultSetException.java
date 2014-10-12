/**
 * 
 */
package com.ymess.exceptions;

/** 
 * Custom Exception class to denote EmptyResultSet
 * @author balaji i
 *
 */
public class EmptyResultSetException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exceptionMessage;

	public EmptyResultSetException()
	{
	}
	
	public EmptyResultSetException(String exceptionMessage)
	{
		this.exceptionMessage = exceptionMessage;
	}
	
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
}
