package com.springrestweather.report;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, String>,JpaSpecificationExecutor<Report>{
	
}
