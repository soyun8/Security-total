package com.main.spring.auth;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.main.spring.domain.User;
import com.main.spring.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// username을 잘 적어줘야 함. 테이블의 이름도 input의 이름도 저거여야함
		System.out.println("===========username======"+username);
		User userEntity = userRepository.findByUsername(username);
		if (userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}

}
