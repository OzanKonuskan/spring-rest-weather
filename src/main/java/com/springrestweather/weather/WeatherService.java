package com.springrestweather.weather;

import java.io.IOException;

import com.springrestweather.exception.GetApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.springrestweather.exception.DataNotFoundException;

public interface WeatherService {
	
	WeatherResponseDTO GetWeather(String CityName) throws DataNotFoundException,GetApiException,JsonProcessingException;
	
	WeatherResponseDTO GetApiResponse(String cityName);

}
