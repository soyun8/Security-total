package com.spring.main.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class Oauth2UserService extends DefaultOAuth2UserService{
	
	
	private static final Logger log = LoggerFactory.getLogger(Oauth2UserService.class);

	/* userRequest 정보 : 구글 로그인 완료 -> code를 리턴(OAuth-Client라이브러리) -> AccessToken 요청
	 * loadUser함수를 통해 구글로부터 회원 프로필 받아오기 
	 * 
	 * */
	
	
	// 구글 로그인 후 , userRequest 데이터에 대해 후처리 되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		//엑세스토큰과 프로필 정보 가져오기 (진짜로 가져오는지 로그로 찍어보기)
		log.info("userRequest : " + userRequest.getClientRegistration()); // registrationId로 어떤 OAuth로 로그인 했는지 확인 가능.
		log.info("userRequest : " + userRequest.getAccessToken().getTokenValue()); // 토큰
		log.info("userRequest : " + super.loadUser(userRequest).getAttributes()); // 프로필 정보 가져오기
		
		// 이 프로필 정보들을 db에 강제로 넣어서 회원가입 시키자!
		// loadUser함수를 통해 구글로부터 회원 프로필 받아오기 
		OAuth2User oAuth2User = super.loadUser(userRequest);
		return super.loadUser(userRequest);
	}

}
