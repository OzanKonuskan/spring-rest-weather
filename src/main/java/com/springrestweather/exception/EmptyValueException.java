package com.springrestweather.exception;

@SuppressWarnings("serial")
public class EmptyValueException extends Throwable {
	public EmptyValueException(final String message) {
		super("There are empty value: " + message);
	}
}
