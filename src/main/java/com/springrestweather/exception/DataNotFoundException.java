package com.springrestweather.exception;

@SuppressWarnings("serial")
public class DataNotFoundException extends Throwable {
	public DataNotFoundException(final String entityColumn) {
		super("There is no " + entityColumn + " with");
	}
}
