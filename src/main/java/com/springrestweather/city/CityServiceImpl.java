package com.springrestweather.city;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrestweather.exception.ExistsException;
import com.springrestweather.exception.DataNotFoundException;
import com.springrestweather.request.CreateCityRequestDTO;
import com.springrestweather.request.UpdateCityRequestDTO;
import com.springrestweather.user.AppUser;

@Service
public class CityServiceImpl implements CityService {
	
	@Autowired
	CityRepository cityRepository;
	
	@Override
	public Iterable<City> getAllCities() {
		return cityRepository.findAll();
	}

	@Override
	public City saveOrUpdateCity(City city) {
		return cityRepository.save(city);
	}

	@Override
	public City createCity(CreateCityRequestDTO createCityRequestDTO) throws ExistsException {
		if(cityRepository.findByName(createCityRequestDTO.getCityName())!=null)
			throw new ExistsException("City", "Name", createCityRequestDTO.getCityName());
		return saveOrUpdateCity(new City(createCityRequestDTO.getCityName()));
	}

	@Override
	public City updateCity(UpdateCityRequestDTO updateCityRequestDTO) throws DataNotFoundException, ExistsException {
		City foundLocation = getCityById(updateCityRequestDTO.getId());
		if(foundLocation==null)
			throw new DataNotFoundException("City Name");
		if(cityRepository.findByName(updateCityRequestDTO.getCityName())!=null)
			throw new ExistsException("City", "Name", updateCityRequestDTO.getCityName());
		foundLocation.setName(updateCityRequestDTO.getCityName());
		return saveOrUpdateCity(foundLocation);
	}

	@Override
	public void deleteCity(String id) throws DataNotFoundException { 
		getCityById(id);
		cityRepository.deleteById(id);
	}

	@Override
	public City getCityByName(String name) {
		return cityRepository.findByName(name);
	}

	@Override
	public Optional<City> findCityById(String id) {
		return cityRepository.findById(id);
	}

	@Override
	public City getCityById(String id) throws DataNotFoundException{
			Optional<City> foundCity = findCityById(id);
			if (foundCity.isPresent() == false) {
				throw new DataNotFoundException("City");
			}
			return foundCity.get();
	}

	@Override
	public boolean isCityExistById(String id) {
		return cityRepository.existsById(id);
	}

}
