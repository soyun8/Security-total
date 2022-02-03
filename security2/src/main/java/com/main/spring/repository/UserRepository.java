package com.main.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.spring.domain.User;

// @Repository 굳이 안 써도 된다. JpaRepository를 상속했기 때문에
public interface UserRepository extends JpaRepository<User, Integer>{

	// findBy는 어떤 규칙이다. --> select * from user where username =? 이게 호출된다. (JPA query method 라고 검색)
	public User findByUsername(String username);

}
