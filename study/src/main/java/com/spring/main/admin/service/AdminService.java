package com.spring.main.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.main.admin.dao.AdminDAO;
import com.spring.main.admin.dto.AdminDTO;

/**
 * @class : studycom.spring.main.admin.AdminService.java
 * @author : soyun8
 * @date : 2021. 11. 7.
 * @desc : 관리자와 관련된 모든 Service
 */

@Service
public class AdminService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired AdminDAO dao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public String joinAdmin(AdminDTO dto) {
		String msg = "";

		/* 아이디 중복 검사 */
		String new_id = dto.getAdmin_id();// 받은 id
		String find_id = "";
		find_id = dao.findAdmin(new_id);// 있는지?
		logger.info(find_id);
		if (find_id != null) {// 존재한다면
			msg = find_id + " 는 이미 존재하는 아이디 입니다.";
		} else {
			
			/*비밀번호 암호화*/
			String pw_plain = dto.getAdmin_pw();//받은 pw
			logger.info("pw plain :" +pw_plain);
			String pw_hash = bCryptPasswordEncoder.encode(pw_plain);//암호화
			logger.info("pw hash :" +pw_hash);
			dto.setAdmin_pw(pw_hash);//암호키로 대체
			
			int success = dao.joinAdmin(dto);
			logger.info("등록 성공 : " + success);
		}

		return msg;
	}
}
