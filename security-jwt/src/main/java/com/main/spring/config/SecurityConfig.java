package com.main.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import com.main.spring.jwt.JwtAuthenticationFilter;
import com.main.spring.jwt.JwtAuthorizationFilter;
import com.main.spring.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserRepository userRepository;
	
	@Bean			// 해당 메서드의 리턴되는 오브젝트를 IOC로 등록해준다. 어디서든 쓸 수 있게됨
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	private final CorsFilter corsFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		// http.addFilterBefore(new MyFilter1(), SecurityContextPersistenceFilter.class);
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)	// jwt를 사용을 위해 우린 더이상 세션을 사용 하지 않을것임
			.and()
			.formLogin().disable()	// formLogin도 더이상 쓰지 않을거야
			.httpBasic().disable()	// http도 안 써 Token Bearer 방식 쓸거야
			.addFilter(new JwtAuthenticationFilter(authenticationManager()))
			.addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
			.addFilter(corsFilter)

			.authorizeRequests()
			.antMatchers("/api/v1/user/**").authenticated()
			.antMatchers("/api/v1/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll();
			// 로그인 페이지 꾸며서 사용할 수 있게 해줌
	}

}
