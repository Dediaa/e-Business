package com.ch.ebusiness.exception;


public class NoCartException extends Exception
{
	private static final long serialVersionUID = 1L;
	public NoCartException()
	{
		super();
	}
	public NoCartException(String message)
	{
		super(message);
	}
}