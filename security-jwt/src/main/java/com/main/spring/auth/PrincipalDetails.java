package com.main.spring.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.main.spring.domain.User;

import lombok.Data;

@Data
public class PrincipalDetails  implements UserDetails{

	private User user;

	// 일반 로그인 시 사용
		public PrincipalDetails(User user) {
			this.user = user;
		}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		user.getRoleList().forEach(r->{
			auth.add(()->r);
		});
		return auth;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
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
