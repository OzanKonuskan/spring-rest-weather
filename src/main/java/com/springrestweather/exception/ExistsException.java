package com.springrestweather.exception;

@SuppressWarnings("serial")
public class ExistsException extends Throwable {
	public ExistsException(final String entityName,final String entityColumn, final String entityValue) {
		super("There is an " + entityName +" with that "+entityColumn+": " + entityValue);
	}
}
