package com.springrestweather.report;

import java.util.List;

import com.springrestweather.exception.DataNotFoundException;

public interface ReportService {
	
	Report saveOrUpdateReport(Report report);
	
	Iterable<Report> getAllReports();
	
	List<Report> findByCriteria(String userId,String cityId,String status) throws DataNotFoundException;
}
