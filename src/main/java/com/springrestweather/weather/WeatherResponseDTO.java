package com.springrestweather.weather;

public class WeatherResponseDTO {
	private CurrentResponseDTO current;
	private LocationResponseDTO location;
	
	public CurrentResponseDTO getCurrent() {
		return current;
	}

	public void setCurrent(CurrentResponseDTO current) {
		this.current = current;
	}

	public LocationResponseDTO getLocation() {
		return location;
	}

	public void setLocation(LocationResponseDTO location) {
		this.location = location;
	}

}
