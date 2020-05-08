package com.springrestweather.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springrestweather.exception.ExistsException;
import com.springrestweather.exception.DataNotFoundException;
import com.springrestweather.exception.NotModifiedException;
import com.springrestweather.request.CreateUserRequestDTO;
import com.springrestweather.request.UpdateUserRequestDTO;
import com.springrestweather.role.RoleRepository;
import com.springrestweather.user.AppUser;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private boolean doesUsernameExist(String username) {
		AppUser foundUser = userRepository.findByUsername(username);
		return foundUser != null;
	}

	private boolean doesEmailExist(String email) {
		AppUser foundUser = userRepository.findByEmail(email);
		return foundUser != null;
	}
	
	@Override
	public AppUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public AppUser saveOrUpdateUser(AppUser appUser) {
		return userRepository.save(appUser);
	}
	
	@Override
	public AppUser createUser(CreateUserRequestDTO createUserRequestDTO) throws ExistsException {
		if (doesEmailExist(createUserRequestDTO.getEmail())) {
			throw new ExistsException("User","Email",createUserRequestDTO.getEmail());
		}
		if (doesUsernameExist(createUserRequestDTO.getUsername())) {
			throw new ExistsException("User","User Name",createUserRequestDTO.getUsername());
		}
		AppUser appUser = new AppUser();
		appUser.setUsername(createUserRequestDTO.getUsername());
		appUser.setEmail(createUserRequestDTO.getEmail());
		appUser.setCanBeModified(true);
		appUser.getRoles().add(roleRepository.findByName("ROLE_USER"));
		appUser.setPassword(bCryptPasswordEncoder.encode(createUserRequestDTO.getPassword()));
		return saveOrUpdateUser(appUser);
	}

	@Override
	public AppUser updateUser(UpdateUserRequestDTO updateUserRequestDTO) throws DataNotFoundException, ExistsException,NotModifiedException {
		AppUser foundUser = getAppUserById(updateUserRequestDTO.getId());
		if(foundUser.getCanBeModified() == false) {
			throw new NotModifiedException("User");
		}
		if(userRepository.findByEmail(updateUserRequestDTO.getEmail())==null)
		{
			throw new ExistsException("User", "Email", updateUserRequestDTO.getEmail());
		}
		if(userRepository.findByUsername(updateUserRequestDTO.getUsername())==null)
		{
			throw new ExistsException("User", "User Name", updateUserRequestDTO.getUsername());
		}
		foundUser.setName(updateUserRequestDTO.getUsername());
		foundUser.setEmail(updateUserRequestDTO.getEmail());
		foundUser.setPassword(bCryptPasswordEncoder.encode(updateUserRequestDTO.getPassword()));
		return saveOrUpdateUser(foundUser);
	}

	@Override
	public void deleteUser(String id) throws NotModifiedException, DataNotFoundException {
		AppUser foundUser = getAppUserById(id);
		if (foundUser.getCanBeModified() == false) {
			throw new NotModifiedException("User");
		}
		userRepository.delete(foundUser);
	}

	
	@Override
	public AppUser getCurrrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		AppUser currentUser = findByUsername(currentPrincipalName);
		return currentUser;
	}
	
	@Override
	public Optional<AppUser> findAppUserById(String id) {
		return userRepository.findById(id);
	}
	
	@Override
	public AppUser getAppUserById(String id) throws DataNotFoundException {
		Optional<AppUser> foundUser = findAppUserById(id);
		if (foundUser.isPresent() == false) {
			throw new DataNotFoundException("User");
		}
		return foundUser.get();
	}

	@Override
	public Iterable<AppUser> allUsers() {
		return userRepository.findAll();
	}

	@Override
	public boolean isUserExistsById(String id) {
		return userRepository.existsById(id);
	}
}
