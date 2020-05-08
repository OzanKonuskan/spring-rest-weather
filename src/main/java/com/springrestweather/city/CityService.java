package com.springrestweather.city;

import com.springrestweather.exception.ExistsException;

import java.util.Optional;

import com.springrestweather.exception.DataNotFoundException;
import com.springrestweather.request.CreateCityRequestDTO;
import com.springrestweather.request.UpdateCityRequestDTO;

public interface CityService {
	
	Iterable<City> getAllCities();
	
	City saveOrUpdateCity(City city);
	
	City createCity(CreateCityRequestDTO createCityRequestDTO) throws ExistsException;
	
	City updateCity(UpdateCityRequestDTO updateCityRequestDTO) throws ExistsException,DataNotFoundException;
	
	void deleteCity(String id) throws DataNotFoundException;
	
	City getCityByName(String name);
	
	Optional<City> findCityById(String id);
	
	City getCityById(String id) throws DataNotFoundException;
	
	boolean isCityExistById(String id);
}
