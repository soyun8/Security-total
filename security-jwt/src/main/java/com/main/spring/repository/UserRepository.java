package com.main.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.spring.domain.User;

// @Repository ���� �� �ᵵ �ȴ�. JpaRepository�� ����߱� ������
public interface UserRepository extends JpaRepository<User, Integer>{

	// findBy�� � ��Ģ�̴�. --> select * from user where username =? �̰� ȣ��ȴ�. (JPA query method ��� �˻�)
	public User findByUsername(String username);

}
