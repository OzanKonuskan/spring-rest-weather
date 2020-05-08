package com.springrestweather.role;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;


public interface RoleService {

	Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles);

	List<String> getPrivileges(Collection<Role> roles);

	List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges);

}
