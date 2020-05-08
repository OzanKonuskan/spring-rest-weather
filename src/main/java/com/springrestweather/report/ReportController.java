package com.springrestweather.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springrestweather.exception.DataNotFoundException;

@RestController
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@RequestMapping(value = "/api/reports", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('PRIVILEGE_SHOW_REPORTS')")
	public ResponseEntity<Object> getAllReports() {

		return new ResponseEntity<>(reportService.getAllReports(), HttpStatus.OK);
		
		
	}
	@RequestMapping(value = "/api/report/{userId}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('PRIVILEGE_SHOW_REPORTS')")
	public ResponseEntity<Object> getReportsByCriteria(
			@RequestParam(value = "cityId", required = false) String cityId,
			@RequestParam(value = "status", required = false) String status,
			@PathVariable("userId") String userId) {

		try {
			return new ResponseEntity<>(reportService.findByCriteria(userId, cityId, status), HttpStatus.OK);
		} catch (DataNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}
