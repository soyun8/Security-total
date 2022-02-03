package com.spring.main.member.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

/**
 * @class : studycom.spring.main.dto.MainDto.java
 * @author : soyun8
 * @date : 2021. 11. 7.
 * @desc : 유저 정보를 담기 위함. UserDetails
 */

/*
 * class : UserDetails : 사용자의 정보를 담는 인터페이스 시큐리티엔 시큐리티 session이 따로 존재함 (Security
 * ContextHolder) 로그인 요청이 오면 시큐리티가 가져가서 실행시킴 흐름 완료되면 security session 생성 -->
 * Authentication 타입의 객체 --> 이 안에는 user 정보가 있다. --> 이 user의 타입은 UserDetails 타입
 * 객체임
 */

@Getter
@Setter
public class MemberDto implements UserDetails{

		/*
		CREATE TABLE member(
		member_id VARCHAR(50) PRIMARY KEY,
		pw VARCHAR(50),
		name VARCHAR(30),
		phone VARCHAR(20),
		email VARCHAR(50),
		birth_date DATETIME DEFAULT CURRENT_TIMESTAMP,
		address VARCHAR(50),
		reg_date DATETIME DEFAULT CURRENT_TIMESTAMP,
		report_cnt decimal(7,0),
		black_cnt decimal(7,0),
		blind_cnt decimal(7,0),
		black_blind_cnt decimal(7,0),
		location VARCHAR(50),
		manner_percent decimal(3,0),
		manner_cnt decimal(7,0),
		isblack VARCHAR(5) DEFAULT 0,
		withdraw VARCHAR(5) DEFAULT 0
		);
		*/
		
		private String member_id;
		private String pw;
		private String name;
		private String phone;
		private String email;
		private String birth_date;
		private String address;
		private String reg_date;
		private String role;
		private int report_cnt;
		private int black_cnt;
		private int blind_cnt;
		private int black_blind_cnt;
		private String location;
		private double manner_percent;
		private int manner_cnt;
		private boolean isBlack;
		private boolean withdraw;
		private int manner_score;
		
		// <method>
		// getAuthorities : 계정의 권한 목록 리턴
		// 리턴타입 : Collection<? extends GrantedAuthority>

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
			auth.add(new SimpleGrantedAuthority(role));
			return auth;
		}

		// dto.getRole(); // String이므로 위에처럼 타입 변환이 필요함

		// getPassword 계정의 비밀번호를 리턴
		@Override
		public String getPassword() {
			return pw;
		}

		// getUsername : 계정의 고유한 값을 리턴 *PK 값*
		@Override
		public String getUsername() {
			return member_id;
		}

		// isAccountNonExpired : 계정의 만료 여부 리턴 ( true일시 만료x )
		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		// isAccountNonLocked : 계정의 잠김 여부 리턴 ( true일시 잠김x )

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		// isCredentialsNonExpired : 비밀번호 만료 여부 리턴 ( true일시 만료x )

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		// isEnabled : 계정의 활성화 여부 ( ture면 활성화 o )

		@Override
		public boolean isEnabled() {

			// 1년간 로그인 안한 계정 즉 휴면계정일때 사용

			return true;
		}
	
	}

