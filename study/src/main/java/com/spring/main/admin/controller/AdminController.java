package com.spring.main.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.main.admin.dto.AdminDTO;
import com.spring.main.admin.service.AdminService;

/**
 * @class : studycom.spring.main.admin.AdminController.java
 * @author : soyun8
 * @date : 2021. 11. 4.
 * @desc : 관리자와 관련된 모든 Controller
 */
@Controller
public class AdminController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AdminService service; 
	
	// 관리자 회원가입 페이지 이동
	@RequestMapping("/adminJoinForm")
	public String adminJoinForm(Model model) {
		logger.info("관리자 등록 폼 요청");
		return "admin/adminJoinForm";
	}
	
	//관리자 회원가입 요청
	@RequestMapping(value = "/adminJoin", method = RequestMethod.POST)
	public ModelAndView joinAdmin(@ModelAttribute AdminDTO dto) {
		logger.info("관리자 등록 요청 : "+dto.getAdmin_id()+"/"+dto.getAdmin_pw());
		ModelAndView mav = new ModelAndView();
		String page = "admin/adminJoinForm";//실패 : join 폼
		String msg = service.joinAdmin(dto);
		if(msg.equals("")) {//성공 : 리스트
			page = "redirect:/admin/callAdminList";
		}
		mav.addObject("msg", msg);
		mav.setViewName(page);
		return mav;
	}
	
	
	// 관리자 목록으로 이동
	@RequestMapping("/admin/adminList")
	public String admin(Model model) {
		return "admin/adminList";
	}

}
