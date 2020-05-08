package com.springrestweather.weather;

public class CurrentResponseDTO {

	private int temperature;

	private String observation_time;
	
	private int wind_speed;
	
	private int wind_degree;
	
	private int humidity;


	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public String getObservation_time() {
		return observation_time;
	}

	public void setObservation_time(String observation_time) {
		this.observation_time = observation_time;
	}
	
	public int getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(int wind_speed) {
		this.wind_speed = wind_speed;
	}

	public int getWind_degree() {
		return wind_degree;
	}

	public void setWind_degree(int wind_degree) {
		this.wind_degree = wind_degree;
	}
	
	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

}
