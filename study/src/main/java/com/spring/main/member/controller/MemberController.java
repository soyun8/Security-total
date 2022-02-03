package com.spring.main.member.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.member.dao.MemberDao;
import com.spring.main.member.dto.MemberDto;
import com.spring.main.member.service.MemberService;

/**
 * @class : studycom.spring.main.member.MemberController.java
 * @author : soyun8
 * @date : 2021. 11. 3.
 * @desc : 일반회원 페이지를 위한 Controller
 */
@Controller
public class MemberController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MemberService service;
	
	@Autowired
	MemberDao dao;

	// BCryptPasswordEncoder : 비밀번호를 암호화 해줌
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// 회원가입을 위한 클래스
	@PostMapping("/join")
	public String join(@ModelAttribute MemberDto dto, @RequestParam HashMap<String, String> params) {
		
		logger.info("회원가입 경로" + dto);
		logger.info("입력 값" + params);
		
		dto.setRole("ROLE_USER"); // USER 강제로 입력
		
		// 비밀번호 암호화 시작
		String pw = dto.getPw();
		String encpw = bCryptPasswordEncoder.encode(pw);
		dto.setPw(encpw);
		
		service.join(dto); // 비밀번호 암호화를 시켜보자! 안하면 시큐리티 사용x
		return "redirect:/loginForm";
	}
	
	// 아이디 중복 방지를 위한 메서드
	@RequestMapping(value = "/overlay", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> overlay(Model model, @RequestParam String id) {
		logger.info("고객이 입력한 아이디 : " + id);
		return service.overlay(id);
	}

	// 이메일 중복 방지를 위한 메서드
	@RequestMapping(value = "/emoverlay", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> emoverlay(Model model, @RequestParam String email) {
		logger.info("고객이 입력한 이메일 : " + email);
		return service.emoverlay(email);
	}


	
}
