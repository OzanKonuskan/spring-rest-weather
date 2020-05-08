package com.springrestweather.weather;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springrestweather.city.CityService;
import com.springrestweather.exception.GetApiException;
import com.springrestweather.exception.DataNotFoundException;
import com.springrestweather.feign.WeatherFeignClient;
import com.springrestweather.report.Report;
import com.springrestweather.report.ReportService;
import com.springrestweather.user.UserService;

@Service
public class WeatherServiceImpl implements WeatherService {
	
	@Autowired
	WeatherFeignClient weatherFeignClient;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	CityService cityService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ReportService reportService;
	
	@Override
	public WeatherResponseDTO GetApiResponse(String cityName) {
		/*RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://api.weatherstack.com/current").queryParam("access_key", "447e74cb5617c9d7dd90be0aa3d4af72").queryParam("query", "New York");
		String uriString = builder.toUriString();
     	ResponseEntity<WeatherResponseDTO> response = restTemplate.getForEntity(uriString, WeatherResponseDTO.class);*/
		
		return weatherFeignClient.getWeather(cityName);
	}

	@Override
	public WeatherResponseDTO GetWeather(String cityName) throws GetApiException, DataNotFoundException, JsonProcessingException {
		Report newReport = new Report();
		newReport.setCity(cityName);
		newReport.setReportDate(LocalDateTime.now());
		newReport.setIpAddress(request.getRemoteAddr());
		
		if(userService.getCurrrentUser()==null)
		{
			newReport.setStatus("FAIL");
			reportService.saveOrUpdateReport(newReport);
			throw new DataNotFoundException("User");
		}
		newReport.setUserId(userService.getCurrrentUser().getId());
		if(cityService.getCityByName(cityName)==null )
		{
			newReport.setStatus("FAIL");
			reportService.saveOrUpdateReport(newReport);
			throw new DataNotFoundException("City Name");
		}	
		newReport.setCityId(cityService.getCityByName(cityName).getId());
		long startTime = System.currentTimeMillis();
		WeatherResponseDTO wheatherResponse=GetApiResponse(cityName);
		long responseTime = System.currentTimeMillis() - startTime;
		newReport.setResponseTime(responseTime+" millisecond");
		if(wheatherResponse.getCurrent()==null)
		{
			newReport.setStatus("FAIL");
			reportService.saveOrUpdateReport(newReport);
			throw new GetApiException();
		}
		ObjectMapper mapper = new ObjectMapper();
		newReport.setResult(mapper.writeValueAsString(wheatherResponse));
		newReport.setStatus("SUCCESS");
		reportService.saveOrUpdateReport(newReport);
		return wheatherResponse;
	}

}
