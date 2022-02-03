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
@EnableWebSecurity // ��ť��Ƽ Ȱ��ȭ -> �⺻ ������ ����ü�ο� ���
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserRepository userRepository;
	
	@Bean			// �ش� �޼����� ���ϵǴ� ������Ʈ�� IOC�� ������ش�. ��𼭵� �� �� �ְԵ�
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	private final CorsFilter corsFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		// http.addFilterBefore(new MyFilter1(), SecurityContextPersistenceFilter.class);
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)	// jwt�� ����� ���� �츰 ���̻� ������ ��� ���� ��������
			.and()
			.formLogin().disable()	// formLogin�� ���̻� ���� �����ž�
			.httpBasic().disable()	// http�� �� �� Token Bearer ��� ���ž�
			.addFilter(new JwtAuthenticationFilter(authenticationManager()))
			.addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
			.addFilter(corsFilter)

			.authorizeRequests()
			.antMatchers("/api/v1/user/**").authenticated()
			.antMatchers("/api/v1/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll();
			// �α��� ������ �ٸ缭 ����� �� �ְ� ����
	}

}
