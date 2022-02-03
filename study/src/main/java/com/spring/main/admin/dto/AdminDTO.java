package com.spring.main.admin.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @class : studycom.spring.main.admin.AdminDTO.java
 * @author : soyun8
 * @date : 2021. 11. 7.
 * @desc : 관리자 DB
 */

@Getter
@Setter
public class AdminDTO {

	/*CREATE TABLE admin ( 
	    admin_id VARCHAR(50)  PRIMARY KEY,
	    admin_pw VARCHAR(200),
	    position VARCHAR(10),
	    activation VARCHAR(5) DEFAULT 'false',
	    reg_date DATETIME DEFAULT CURRENT_TIMESTAMP,
	    role VARCHAR(20)
	);*/

	private String admin_id; 
	private String admin_pw; 
	private String position; 
	private Boolean activation; 
	private String reg_date;
	private String role;
}
