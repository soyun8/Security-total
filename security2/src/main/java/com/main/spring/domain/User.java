package com.main.spring.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class User {
	
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	private String role; //ROLE_USER, ROLE_ADMIN
	
	// OAuth�� ���� ������ �߰� �ʵ� 2��
	private String provider;	// ��� �α����Ѱ��� �� �� �ְ� (google �̳� kakao��)
	private String providerId;	// �޾ƿ� ���̵�
	
	@CreationTimestamp
	private Timestamp createDate;
	
	// user �ϳ��� �� ������൵ �Ǵµ� �츰 �������� �������
		public List<String> getRoleList(){
			if(this.role.length() > 0) {
				return Arrays.asList(this.role.split(","));
			}
			return new ArrayList<>();
		}

	@Builder
	public User(String username, String email, String role, String provider, String providerId,
			Timestamp createDate) {
		this.username = username;
		this.email = email;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
		this.createDate = createDate;
	}
	
	
}
