package com.springrestweather;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.springrestweather.user.AppUser;
import com.springrestweather.user.UserRepository;
import com.springrestweather.role.PrivilegeRepository;
import com.springrestweather.role.RoleRepository;
import com.springrestweather.city.City;
import com.springrestweather.city.CityRepository;
import com.springrestweather.role.Privilege;
import com.springrestweather.role.Role;

@Service
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	boolean alreadySetup = false;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PrivilegeRepository privilegeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CityRepository locationRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup)
			return;
		Privilege PRIVILEGE_EDIT_LOCATION = createPrivilege("PRIVILEGE_EDIT_LOCATION");
		Privilege PRIVILEGE_LIST_LOCATION = createPrivilege("PRIVILEGE_LIST_LOCATION");
		Privilege PRIVILEGE_SHOW_WEATHER = createPrivilege("PRIVILEGE_SHOW_WEATHER");
		Privilege PRIVILEGE_EDIT_USER = createPrivilege("PRIVILEGE_EDIT_USER");
		Privilege PRIVILEGE_SHOW_REPORTS = createPrivilege("PRIVILEGE_SHOW_REPORTS");

		List<Privilege> adminPrivileges = Arrays.asList(PRIVILEGE_EDIT_LOCATION,PRIVILEGE_LIST_LOCATION, PRIVILEGE_SHOW_WEATHER,
				PRIVILEGE_EDIT_USER, PRIVILEGE_SHOW_REPORTS);
		List<Privilege> userPrivileges = Arrays.asList(PRIVILEGE_LIST_LOCATION,PRIVILEGE_SHOW_WEATHER, PRIVILEGE_SHOW_REPORTS);

		createRole("ROLE_ADMIN", adminPrivileges);
		createRole("ROLE_USER", userPrivileges);

		Role adminRole = roleRepository.findByName("ROLE_ADMIN");
		Role userRole = roleRepository.findByName("ROLE_USER");
		
		AppUser root = new AppUser();
		root.setName("root");
		root.setUsername("root");
		root.setEmail("root@test.com");
		root.setPassword(passwordEncoder.encode("root"));
		root.setRoles(Set.of(adminRole));
		root.setCanBeModified(false);
		userRepository.save(root);

		AppUser user = new AppUser();
		user.setName("user");
		user.setUsername("user");
		user.setEmail("user@test.com");
		user.setPassword(passwordEncoder.encode("user"));
		user.setCanBeModified(true);
		user.setRoles(Set.of(userRole));
		userRepository.save(user);
		
		locationRepository.save(new City("Istanbul"));
		locationRepository.save(new City("New York"));
		
		alreadySetup=true;
	}
	
	@Transactional
	private Privilege createPrivilege(String name) {

		Privilege privilege = privilegeRepository.findByName(name);
		if (privilege == null) {
			privilege = new Privilege(name);
			privilegeRepository.save(privilege);
		}
		return privilege;
	}

	@Transactional
	private Role createRole(String name, Collection<Privilege> privileges) {

		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role(name);
			role.setPrivileges(privileges);
			roleRepository.save(role);
		}
		return role;
	}

}
