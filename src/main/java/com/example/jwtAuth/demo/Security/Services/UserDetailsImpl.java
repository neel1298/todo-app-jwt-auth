package com.example.jwtAuth.demo.Security.Services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.jwtAuth.demo.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jsonwebtoken.lang.Objects;


public class UserDetailsImpl  implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String username;
	private String email;
	
	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(Long id, String username, String email, String password,

			Collection<? extends GrantedAuthority> authorities) {
		
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
											.map(role->new SimpleGrantedAuthority(role.getName().name()))
											.collect(Collectors.toList());
		
		return new UserDetailsImpl(
					user.getUser_id(),
					user.getUsername(),
					user.getEmail(),
					user.getPassword(),
					authorities
				);
				
	}
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	public Long getId() {
		return id;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	public String getEmail() {
		return email;
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public UserDetailsImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return java.util.Objects.equals(id, user.id);
	}
	
	
	
}
