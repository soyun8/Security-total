package com.main.spring.controller;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.spring.auth.PrincipalDetails;
import com.main.spring.domain.User;
import com.main.spring.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
// @CrossOrigin  // CORS ��� 
public class RestApiController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// ��� ����� ���� ����
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	@PostMapping("token")
	public String token() {
		return "token";
	}
	
	// Tip : JWT�� ����ϸ� UserDetailsService�� ȣ������ �ʱ� ������ @AuthenticationPrincipal ��� �Ұ���.
	// �ֳ��ϸ� @AuthenticationPrincipal�� UserDetailsService���� ���ϵ� �� ��������� �����̴�.
	
	// ���� Ȥ�� �Ŵ��� Ȥ�� ������ ���� ����
	
	  @GetMapping("/api/v1/user") 
	  public String user(Authentication authentication) {
	  PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
	  System.out.println("principal : "+principal.getUser().getId());
	  System.out.println("principal : "+principal.getUser().getUsername());
	  System.out.println("principal : "+principal.getUser().getPassword());
	  
	  return "<h1>user</h1>"; }
	 
	
	// �Ŵ��� Ȥ�� ������ ���� ����
	@GetMapping("/api/v1/manager")
	public String manager() {
		return "�Ŵ���";
	}
	
	// ������ ���� ����
	@GetMapping("/api/v1/admin")
	public  String admin() {
		return "����";
	}
	
	@PostMapping("join")
	public String join(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		userRepository.save(user);
		return "ȸ�����ԿϷ�";
	}
}
	