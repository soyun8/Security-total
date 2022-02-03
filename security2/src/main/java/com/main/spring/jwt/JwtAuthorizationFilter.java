package com.main.spring.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.main.spring.auth.PrincipalDetails;
import com.main.spring.domain.User;
import com.main.spring.repository.UserRepository;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	
	private UserRepository userRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("����/���� �ʿ�");
		
		String jwtHeader = request.getHeader("Authorization");
		System.out.println("jwtHeader : "+jwtHeader);
		
		// header�� �ִ��� Ȯ��
		if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}
		
		// jwt ��ū�� �����ؼ� �������� ��������� Ȯ��

		// ��ū ���� (�̰� �����̱� ������ AuthenticationManager�� �ʿ� ����)
		// ���� SecurityContext�� ���������ؼ� ������ ���鶧 �ڵ����� UserDetailsService�� �ִ� loadByUsername�� ȣ���.
		String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
		String username = JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken)
				.getClaim("username").asString();
		
		// ������ ���������� �Ǿ��ٸ�
		if(username != null) {	
			System.out.println("username ����=============");
			User user = userRepository.findByUsername(username);
			
			// ������ ��ū ������ ��. ������ �ϱ� ���ؼ��� �ƴ� ������ ��ť��Ƽ�� �������ִ� ���� ó���� ���� 
			// �Ʒ��� ���� ��ū�� ���� Authentication ��ü�� ������ ����� �װ� ���ǿ� ����!
			PrincipalDetails principalDetails = new PrincipalDetails(user);
			Authentication authentication =
					new UsernamePasswordAuthenticationToken(
							principalDetails, //���߿� ��Ʈ�ѷ����� DI�ؼ� �� �� ����ϱ� ����.
							null, // �н������ �𸣴ϱ� null ó��, ������ ���� �����ϴ°� �ƴϴϱ�!!
							principalDetails.getAuthorities());
			
			// ������ ��ť��Ƽ�� ���ǿ� �����Ͽ� Authentication ��ü ����
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		}
	}
}
