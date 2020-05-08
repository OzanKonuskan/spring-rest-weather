package com.springrestweather.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springrestweather.exception.ExistsException;
import com.springrestweather.exception.DataNotFoundException;
import com.springrestweather.request.CreateCityRequestDTO;
import com.springrestweather.request.UpdateCityRequestDTO;
import com.springrestweather.response.ResponseObject;

@RestController
public class CityController {
	@Autowired
	CityService cityService;
	
	ResponseObject responseObject;
	
	@RequestMapping(value = "/api/cities", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('PRIVILEGE_LIST_LOCATION')")
	public ResponseEntity<Object> getAllCities() {
		responseObject = new ResponseObject();
		responseObject.setData(cityService.getAllCities());
		return new ResponseEntity<>(responseObject, HttpStatus.OK);

	}

	@RequestMapping(value = "/api/city", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('PRIVILEGE_EDIT_LOCATION')")
	public ResponseEntity<Object> addCity(@RequestBody CreateCityRequestDTO createCityRequestDTO) {
		try {
			return new ResponseEntity<>(cityService.createCity(createCityRequestDTO), HttpStatus.OK);
		} catch (ExistsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = "/api/city/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('PRIVILEGE_EDIT_LOCATION')")
	public ResponseEntity<Object> deleteCityById(@PathVariable("id") String id) {

		try {
			cityService.deleteCity(id);
			return new ResponseEntity<>("Location Deleted.", HttpStatus.OK);
		} catch (DataNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/api/city", method = RequestMethod.PATCH)
	@PreAuthorize("hasAuthority('PRIVILEGE_EDIT_LOCATION')")
	public ResponseEntity<Object> updateCityById( @RequestBody UpdateCityRequestDTO updateCityRequestDTO) {
		try {
			return new ResponseEntity<>(cityService.updateCity(updateCityRequestDTO), HttpStatus.OK);
		} catch (ExistsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (DataNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
