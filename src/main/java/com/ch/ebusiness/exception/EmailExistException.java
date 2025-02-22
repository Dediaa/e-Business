package com.ch.ebusiness.exception;
public class EmailExistException extends Exception
{
	private static final long serialVersionUID = 1L;
	public EmailExistException()
	{
		super();
	}
	public EmailExistException(String message)
	{
		super(message);
	}
}