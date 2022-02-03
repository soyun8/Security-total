package com.spring.main.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.auth.service.PrincipalDetailsService;

/**
 * @class : studycom.spring.main.auth.SecurityController.java
 * @author : soyun8
 * @date : 2021. 11. 7
 * @desc : 시큐리티를 위한 컨트롤러 (허가, 인증을 위함)
 */

@Controller
public class SecurityController {

	@Autowired
	PrincipalDetailsService detailsService;

	// logger를 찍기 위함
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 로그인 페이지 이동
	@RequestMapping("/loginForm")
	// Model 이란 Controller 에서 생성된 데이터를 담아서 View 로 전달할 때 사용하는 객체.
	public String login(Model model) {

		return "loginForm";
	}

	// 회원가입 페이지로 이동
	@RequestMapping("/joinForm")
	public String joinForm(Model model) {
		return "member/joinForm";
	}

	@RequestMapping("/")
	public String main(Model model) {
		return "loginForm";
	}

	// 공지사항 메인 페이지 이동
	@RequestMapping("/user")
	public String noticeMain(Model model) {

		return "help/noticeMain";
	}

	// 자주묻는질문 페이지 이동
	@RequestMapping("/help/help_QAnswer")
	public String help_QAnswer(Model model) {

		return "help/help_QAnswer";
	}

	// @PreAuthorize("haseRole('ROLE_ADMIN')")
	@Secured("ROLE_ADMIN")
	@RequestMapping("/info")
	public @ResponseBody String info(Model model) {
		return "개인정보";
	}
	
	@RequestMapping("/test/login")
	public @ResponseBody String loginTest(Authentication authentication) {
		return "개인정보";
	}

}
