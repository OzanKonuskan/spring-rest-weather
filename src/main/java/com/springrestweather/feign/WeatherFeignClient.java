package com.springrestweather.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springrestweather.weather.WeatherResponseDTO;

@FeignClient(
name = "weatherr",
url = "${weatherstack-api-url}",	
qualifier = "weatherFeignClientttttt",
configuration = WeatherFeignClientConfiguration.class)
public interface WeatherFeignClient {
	
	@GetMapping("/current")
	WeatherResponseDTO getWeather(@RequestParam("query") String cityName);

}
