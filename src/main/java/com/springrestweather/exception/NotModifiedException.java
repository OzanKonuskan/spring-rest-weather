package com.springrestweather.exception;

@SuppressWarnings("serial")
public class NotModifiedException extends Throwable{
	public NotModifiedException(final String entityName) {
		super("This "+ entityName +"  cannot be modified");
	}
}
