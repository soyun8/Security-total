package com.main.spring.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.spring.auth.PrincipalDetails;
import com.main.spring.domain.LoginRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("�α��� �õ���");
		ObjectMapper om = new ObjectMapper();		// JSON ������ ����� ��, ������� ����ȭ(Object -> String ���ڿ�)�ϰ� ��û���� ������ȭ(String ���ڿ� -> Object) �� �� ����ϴ� ���
		LoginRequestDto user = null;
		try {
			user = om.readValue(request.getInputStream(), LoginRequestDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
		
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(
						user.getUsername(), 
						user.getPassword());
		
		Authentication authentication = 
				authenticationManager.authenticate(authenticationToken);
		
		PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("�α����� �� �Ǹ� �� "+principalDetailis.getUser().getUsername());
		return authentication; // �α����� �� �Ǹ� ���ǿ� ������ ��
		
		}
		
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication ����� : �� �����Ϸ��");
		PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();

		// build.gradle�� implementation 'com.auth0:java-jwt:3.18.3' �߰�
		// RSA ����� �ƴϰ� Hash ��ȣ����̴�. (�̰� ���� ������)
		String jwtToken = JWT.create()
				.withSubject("cos��ū") // �ǹ̾���. �ƹ��ų� ����
				.withSubject(principalDetailis.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))	// ����ð� (10������ �س���)
				.withClaim("id", principalDetailis.getUser().getId())
				.withClaim("username", principalDetailis.getUser().getUsername())
				.sign(Algorithm.HMAC512("cos"));	// ���� ���� ������

		// ������ �κп� ��ĭ �� ����
		response.addHeader("Authorization", "Bearer "+jwtToken);
	}
}
