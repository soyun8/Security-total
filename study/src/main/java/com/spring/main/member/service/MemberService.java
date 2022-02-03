package com.spring.main.member.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.main.member.dao.MemberDao;
import com.spring.main.member.dto.MemberDto;


/**
 * @class : studycom.spring.main.member.MemberService.java
 * @author : soyun8
 * @date : 2021. 11. 7.
 * @desc : 서비스단
 */


@Service
public class MemberService {
	
	@Autowired
	MemberDao dao;

	public HashMap<String, Object> overlay(String id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int use = dao.overlay(id);
		map.put("use", use);
		return map;
	}
	
	public HashMap<String, Object> emoverlay(String email) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int use2 = dao.emoverlay(email);
		map.put("use2", use2);
		return map;
	}

	public int join(MemberDto dto) {
		return dao.join(dto);
	}

}
