package com.springrestweather.role;

import org.hibernate.annotations.Where;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PrivilegeRepository extends CrudRepository<Privilege, String> {

	Privilege findByName(String name);

	Privilege findByid(String id);

}