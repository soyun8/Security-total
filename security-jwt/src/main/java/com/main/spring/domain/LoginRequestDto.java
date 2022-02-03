package com.main.spring.domain;

import lombok.Data;

@Data
public class LoginRequestDto {
	private String username;
	private String password;
}
