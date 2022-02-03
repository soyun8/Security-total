package com.spring.main.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.spring.main.admin.dto.AdminDTO;

/**
 * @class : studycom.spring.main.admin.AdminDAO.java
 * @author : soyun8
 * @date : 2021. 11. 7.
 * @desc : 관리자와 관련된 모든 dao
 */

@Mapper
public interface AdminDAO {

	String findAdmin(String new_id);

	int joinAdmin(AdminDTO dto);

}
