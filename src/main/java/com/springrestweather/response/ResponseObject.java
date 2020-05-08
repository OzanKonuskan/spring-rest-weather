package com.springrestweather.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseObject {
	
	@JsonProperty("data")
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
