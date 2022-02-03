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
// @CrossOrigin  // CORS 허용 
public class RestApiController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// 모든 사람이 접근 가능
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	@PostMapping("token")
	public String token() {
		return "token";
	}
	
	// Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
	// 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.
	
	// 유저 혹은 매니저 혹은 어드민이 접근 가능
	
	  @GetMapping("/api/v1/user") 
	  public String user(Authentication authentication) {
	  PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
	  System.out.println("principal : "+principal.getUser().getId());
	  System.out.println("principal : "+principal.getUser().getUsername());
	  System.out.println("principal : "+principal.getUser().getPassword());
	  
	  return "<h1>user</h1>"; }
	 
	
	// 매니저 혹은 어드민이 접근 가능
	@GetMapping("/api/v1/manager")
	public String manager() {
		return "매니저";
	}
	
	// 어드민이 접근 가능
	@GetMapping("/api/v1/admin")
	public  String admin() {
		return "어드민";
	}
	
	@PostMapping("join")
	public String join(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		userRepository.save(user);
		return "회원가입완료";
	}
}
	