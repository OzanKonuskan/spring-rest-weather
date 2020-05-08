package com.springrestweather.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springrestweather.exception.ExistsException;
import com.springrestweather.exception.DataNotFoundException;
import com.springrestweather.exception.NotModifiedException;
import com.springrestweather.request.CreateUserRequestDTO;
import com.springrestweather.request.UpdateUserRequestDTO;
import com.springrestweather.response.ResponseObject;


@RestController
public class UserController {
	@Autowired
	UserService userService;

	ResponseObject responseObject;

	@RequestMapping(value = "/api/users", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('PRIVILEGE_EDIT_USER')")
	public ResponseEntity<Object> getAllUsers() {
		responseObject = new ResponseObject();
		responseObject.setData(userService.allUsers());
		return new ResponseEntity<>(responseObject, HttpStatus.OK);
	}
	@PostMapping("/api/user")
//	@RequestMapping(value = "/api/create-user", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('PRIVILEGE_EDIT_USER')")
	public ResponseEntity<Object> addUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
		try {
			return new ResponseEntity<>(userService.createUser(createUserRequestDTO), HttpStatus.OK);
		} catch (ExistsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/api/user/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('PRIVILEGE_EDIT_USER')")
	public ResponseEntity<Object> deleteUserById(@PathVariable("id") String id) {
		try {
			userService.deleteUser(id);
			return new ResponseEntity<>("User Deleted", HttpStatus.OK);
		} catch (DataNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NotModifiedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@RequestMapping(value = "/api/user/", method = RequestMethod.PATCH)
	@PreAuthorize("hasAuthority('PRIVILEGE_EDIT_USER')")
	public ResponseEntity<Object> updateUserById(@RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
		try {
			return new ResponseEntity<>(userService.updateUser(updateUserRequestDTO), HttpStatus.OK);
		} catch (DataNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ExistsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (NotModifiedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
