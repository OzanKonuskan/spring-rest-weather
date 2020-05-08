package com.springrestweather.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springrestweather.exception.GetApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.springrestweather.exception.DataNotFoundException;

@RestController
public class WeatherController {
	
	@Autowired
	WeatherService weatherService;

	@RequestMapping(value = "/api/weather/{city}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('PRIVILEGE_SHOW_WEATHER')")
	public ResponseEntity<Object> getWeather(@PathVariable("city") String cityName) throws DataNotFoundException, GetApiException, JsonProcessingException {
		try {
			return new ResponseEntity<>(weatherService.GetWeather(cityName), HttpStatus.OK);
		} catch (DataNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (GetApiException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		

	}
}
