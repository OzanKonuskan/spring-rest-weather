package com.springrestweather.city;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City,String>{
	City findByName (String name);
	//boolean existsById (String id);
}
