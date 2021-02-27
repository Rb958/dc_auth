package com.sabc.digitalchampions.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sabc.digitalchampions.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String ref;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private boolean enable;
	@JsonIgnore
	private String password;

	private final Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(User user,Collection<? extends GrantedAuthority> authorities) {
		this.firstName = user.getFirstname();
		this.lastName = user.getLastname();
		this.ref = user.getCode();
		this.username = user.getEmail();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.authorities = authorities;
		this.enable = user.isEnabled();
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		authorities.add(authority);

		return new UserDetailsImpl(user, authorities);
	}

	public boolean getEnable(){
		return this.enable;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
