package com.watermelon.dateapp.domain.user;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
	private final LoginInfo loginInfo;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return loginInfo.getRoles()
			.stream()
			.map(role -> (GrantedAuthority)() -> role)
			.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.loginInfo.getPassword();
	}

	@Override
	public String getUsername() {
		return this.loginInfo.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return UserDetails.super.isEnabled();
	}
}
