package com.springrestweather.report;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.springrestweather.exception.DataNotFoundException;
import com.springrestweather.city.CityService;
import com.springrestweather.user.UserService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	ReportRepository reportRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	CityService cityService;

	@Override
	public Report saveOrUpdateReport(Report report) {
		return reportRepository.save(report);
	}

	@Override
	public Iterable<Report> getAllReports() {
		return reportRepository.findAll();
	}

	@Override
	public List<Report> findByCriteria(String userId, String cityId, String status) throws DataNotFoundException {

		if (!userService.isUserExistsById(userId)) {
			throw new DataNotFoundException("User");
		}
		if(cityId!=null && !cityService.isCityExistById(cityId))
		{
			throw new DataNotFoundException("City");
		}
		return reportRepository.findAll(new Specification<Report>() {
			@Override
			public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (userId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("userId"), userId )));
				}
				if (cityId != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cityId"), cityId)));
				}
				if (status != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status)));
				}
				query.orderBy(criteriaBuilder.desc(root.get("reportDate")));     
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}

}
