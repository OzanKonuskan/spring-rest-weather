package com.springrestweather.user;

import com.springrestweather.exception.ExistsException;

import java.util.Optional;

import com.springrestweather.exception.DataNotFoundException;
import com.springrestweather.exception.NotModifiedException;
import com.springrestweather.request.CreateUserRequestDTO;
import com.springrestweather.request.UpdateUserRequestDTO;

public interface UserService {

	AppUser findByUsername(String username);
	
	Iterable<AppUser> allUsers();
	
	AppUser saveOrUpdateUser(AppUser appUser);
	
	AppUser createUser(CreateUserRequestDTO createUserRequestDTO) throws ExistsException;
	
	AppUser updateUser(UpdateUserRequestDTO updateUserRequestDTO) throws DataNotFoundException,ExistsException,NotModifiedException;
	
	void deleteUser(String id) throws DataNotFoundException, NotModifiedException;
	
	AppUser getCurrrentUser();
	
	Optional<AppUser> findAppUserById(String id);

	AppUser getAppUserById(String id) throws DataNotFoundException;
	
	boolean isUserExistsById(String id);
}
