package com.spring.main.auth.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.main.member.dao.MemberDao;
import com.spring.main.member.dto.MemberDto;

/**
 * @class : studycom.spring.main.auth.PrincipalDetailsService.java
 * @author : soyun8
 * @date : 2021. 11. 6.
 * @desc : DB에서 유저 정보를 직접 가져오는 인터페이스 UserDetailsService
 */

// AuthenticationProvider는 UserDetailsService에게 조회할 username을 전달하여 인증을 위한 UserDetails(DB 사용자 정보) 객체를 요청한다.
// .loginProcessingUrl("/login") 이 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
// method : loadUserByUsername : 유저 정보를 불러오는 메서드, CustomUserDetails 형으로 사용자의 정보를 가져옴

@Service
public class PrincipalDetailsService implements UserDetailsService {

	// 로그인시 비밀번호 암호화를 시키지 않아도 알아서 암호화 되어 들어감
	// 그냥 디폴트가 암호화더라

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MemberDao dao;
	@Autowired(required = false)
    private HttpServletRequest request;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		request.getParameter("mode");
		logger.info("입력한 아이디 값 : " + username);
		logger.info("선택한 모드 : " + username);
		// username은 loginForm에 있는 name = username을 뜻하는 것임 그러니까, 이름 확인 잘할것
		// 만약에 이름을 바꾸고 싶다면 HttpSecurity http 에서 .usernameParameter("바꾼 이름") 입력해줘야함
		MemberDto user = dao.findId(username);
		logger.info("user : " + user);
		return user;
	}

}
