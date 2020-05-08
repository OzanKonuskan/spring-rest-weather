package com.springrestweather.exception;

@SuppressWarnings("serial")
public class GetApiException extends Throwable{
	public GetApiException() {
		super("Result not valid.");
	}
}
