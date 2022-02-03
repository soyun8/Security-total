package com.spring.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.spring.main.auth.service.Oauth2UserService;

/**
 * @class : studycom.spring.main.WebSecurityConfig.java
 * @author : soyun8
 * @date : 2021. 11. 3.
 * @desc : 애플리케이션의 보안(인증과 권한, 인가 등)을 담당하는 스프링 하위 프레임워크
 */
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 필터체인에 등록
// Controller 메소드에 직접적으로 Role을 부여할 수 있음 
// 그래서 쓰는법
// securedEnabled = true 로 쓰면 컨트롤러에서 (@Secured("ROLE_ADMIN")) 이렇게 씀
// 또는 prePostEnabled = true 는 컨트롤러에서 @PreAuthorize("haseRole('ROLE_ADMIN')")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Oauth2UserService oauth2UserService;
	
	/*
	 * class : WebSecurityConfigurerAdapter
	 * 
	 * method : configure(AuthenticationManagerBuilder auth) desc : 프로퍼티에 설정했던 보안 관련
	 * 설정을 인메모리에 설정할 수 있도록 함
	 * 
	 * method : confiugre(WebSecurity web) desc : 인증 및 인가절차 무시, 주로 정적파일(js, css,
	 * image) 경로 설정에 이용
	 */

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/resources/static/**");
//	}

	// WebSecurity web 을 주석처리 해도 이미지는 불러와졌다. 시큐리티 적용시 ignoring 이 필요하다고 했는데?
	// --> 다시 알아보기

	/*
	 * 인증 : 본인이 맞는지 확인 
	 * 인가 : 인증된 자가 요청한 이곳에 접근 가능한지 
	 * principal : 보호받는 리소스에 접근하는 대상
	 */

	/*
	 * 흐름 1. 아이디와 비번을 받아와서 필터 거침 -> 토큰 만듬 -> 객체 생성 -> service로 토큰을 던짐 -> DB에서 유저 유무
	 * 확인
	 */

	//@Bean 리턴되는 오브젝트를 IoC로 등록해줌, 이제 어디서든 쓸 수 있음
	 //제어의 역전, 개발자가 제어 하던것을 스프링 컨테이너가 대신 해줌 덕분에 우리는 new를 선언 하지 않아도 됨
	 
	@Bean
	// class : BCryptPasswordEncoder 비밀번호를 암호화 하는 메서드
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * 클라이언트가 로그인 요청 id랑pw 값 들고옴 AuthenticationFilter가 그 값을 가져감 ->
	 * Authentication(토큰) 객체 생성 인증을 위해 AuthenticationManager에게 Authentication 전달
	 * AuthenticationManager는 실제 인증 기능이 수행을 위해 AuthenticationProvider에게 다시
	 * Authentication을 전달한다.
	 * 
	 */
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//			.inMemoryAuthentication()
//			.withUser("user")
//			.password("{noop}pw")
//			.roles("USER")
//			.and()
//			.withUser("admin")
//			.password("{noop}pw")
//			.roles("ADMIN", "USER");
//	}

	// java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for
	// the id "null"
	// 위에 에러 해결법, {noop}으로 해결
	// 여기서, noop는 Spring Security에서 텍스트 그대로 비밀번호로 인식하게 해줍니다. {noop} 또는 비밀번호 암호화 둘중
	// 하나는 해줘야함
	// 이유 : PasswordEncoder 를 설정하지 않아서였다. Spring boot 는 PasswordEncoder 를 설정해 주지않으며,
	// 이부분은 개발자가 직접 등록 해야한다

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * << Spring Security >> 애플리케이션의 보안(인증과 권한, 인가 등)을 담당하는 스프링 하위 프레임워크 '인증'과 '권한'에
		 * 대한 부분을 Filter 흐름에 따라 처리
		 * 
		 * << 인증(Authorizatoin)과 인가(Authentication) >> - 인증(Authentication) : 해당 사용자가
		 * 본인이 맞는지를 확인하는 절차 - 인가(Authorization) : 인증된 사용자가 요청한 자원에 접근 가능한지를 결정하는 절차
		 * 기본적으로 인증 절차를 거친 후에 인가 절차를 진행하게 되며, 인가 과정에서 해당 리소스에 대한 접근 권한이 있는지 확인을 하게 된다.
		 * 이러한 인증과 인가를 위해 Principal을 아이디로, Credential을 비밀번호로 사용하는 Credential 기반의 인증 방식을
		 * 사용한다.
		 * 
		 * - Principal(접근 주체) : 보호받는 Resource에 접근하는 대상 - Credential(비밀번호) : Resource에
		 * 접근하는 대상의 비밀번호
		 * 
		 * 즉 인증된 사용자의 정보 'Principal'을 'Authentication'에서 관리하고 'SecurityContext'을
		 * 'SecurityContextHolder'가 관리하고 는 가 관리한다.
		 * 
		 * class : SecurityContextHolder desc : 보안 주체의 세부 정보를 포함하여 응용프래그램의 현재 보안 컨텍스트에
		 * 대한 세부 정보가 저장 SecurityContextHolder.MODE_INHERITABLETHREADLOCAL > 기본 전략, 같은
		 * Thread 내에서만 SecurityContext가 공유된다. SecurityContextHolder.MODE_THREADLOCAL >
		 * 하위 Thread가 생성되면 해당 Thread까지 SecurityContext가 공유된다.
		 * SecurityContextHolder.MODE_GLOBAL > 애플리케이션 전체에서 SecurityContexet가 공유된다.
		 * 
		 * class : SecurityContext desc : Authentication을 보관하는 역할을 하며, SecurityContext를
		 * 통해 Authentication 객체를 가져올 수 있다.
		 * 
		 * class : Authentication desc : 현재 접근하는 주체의 정보와 권한을 담는 인터페이스
		 * 
		 * class : UsernamePasswordAuthenticationToken desc : Authentication을
		 * implements한 AbstractAuthenticationToken의 하위 클래스로, User의 ID가 Principal 역할을 하고,
		 * Password가 Credential의 역할 첫 번째 생성자는 인증 전의 객체를 생성하고, 두번째 생성자는 인증이 완려된 객체를 생성
		 * 
		 * class : AuthenticationProvider desc : 실제 인증에 대한 부분을 처리 인증 전의
		 * Authentication객체를 받아서 인증이 완료된 객체를 반환하는 역할
		 * 
		 * class : AuthenticationManager desc : 인증에 대한 부분은 SpringSecurity의
		 * AuthenticatonManager를 통해서 처리하게 되는데, 실질적으로는 AuthenticationManager에 등록된
		 * AuthenticationProvider에 의해 처리된다. 인증이 성공하면 2번째 생성자를 이용해 인증이
		 * 성공한(isAuthenticated=true) 객체를 생성하여 Security Context에 저장한다. 그리고 인증 상태를 유지하기 위해
		 * 세션에 보관하며, 인증이 실패한 경우에는 AuthenticationException를 발생시킨다.
		 * 
		 * class : ProviderManager desc : AuthenticationManager를 implements 하고 실제 인증 과정에
		 * 대한 로직을 가지고 있는 AuthenticaionProvider를 List로 가지고 있으며, ProividerManager는 for문을
		 * 통해 모든 provider를 조회하면서 authenticate 처리를 한다.
		 * 
		 * class : SecurityConfig desc : ProviderManager에 직접 구현한
		 * CustomAuthenticationProvider를 등록하는 방법은 WebSecurityConfigurerAdapter를 상속해 만든
		 * SecurityConfig에서 할 수 있다. WebSecurityConfigurerAdapter의 상위 클래스에서는
		 * AuthenticationManager를 가지고 있기 때문에 직접 만든 CustomAuthenticationProvider를 등록할 수
		 * 있다.
		 * 
		 * class : UserDetails desc : 인증에 성공하여 생성된 UserDetails 객체는 Authentication객체를 구현한
		 * UsernamePasswordAuthenticationToken을 생성하기 위해 사용된다.
		 * 
		 * class : UserDetailsService desc : UserDetails 객체를 반환하는 단 하나의 메소드를 가지고 있는데,
		 * 일반적으로 이를 구현한 클래스의 내부에 UserRepository를 주입받아 DB와 연결하여 처리한다.
		 * 
		 * class : Password Encoding desc :
		 * AuthenticationManagerBuilder.userDetailsService().passwordEncoder() 를 통해 패스워드
		 * 암호화에 사용될 PasswordEncoder 구현체를 지정할 수 있다.
		 * 
		 * class : GrantedAuthority desc : 현재 사용자(principal)가 가지고 있는 권한을 의미한다.
		 * ROLE_ADMIN나 ROLE_USER와 같이 ROLE_*의 형태로 사용하며, 보통 "roles" 이라고 한다.
		 * GrantedAuthority 객체는 UserDetailsService에 의해 불러올 수 있고, 특정 자원에 대한 권한이 있는지를 검사하여
		 * 접근 허용 여부를 결정한다.
		 * 
		 */

		/*
		 * method : configure(HttpSecurity http) desc : Spring Security Filter chain에
		 * 접근하여 url 인증 및 인가 처리 시행
		 */
		http
				// join을 사용하기 위함 (post를 사용하기 위함)
				.csrf().disable()
				
				// 특정한 경로에 특정한 권한을 가진 사용자만 접근할 수 있는 메소드
				.authorizeRequests()
				// user 루트 밑으로 오는 요청은 인증이 필요하다.
				.antMatchers("/user/**").authenticated()
				// admin에 대한 요청은 전부, role이 ADMIN인 유저만이 사용가능하다.
				// 인증이 안될경우 403 에러가 뜸
				// 'ADMIN' 안됨 'ROLE_ADMIN' 이라고 해줘야 인식함.. 뭐?
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				// 나머지 경로는 다른 요청 전부 허용
				.anyRequest().permitAll()
				
				.and()
				// 위에 user와 admin 주소로 들어갈때 error 페이지가 뜨는데 이걸 login page로 오게끔 하는 방법
				.formLogin()
				// 로그인 페이지 url 지정
				.loginPage("/loginForm")
				// 인증(로그인) 성공시 이동할 페이지
				// 로그인 처리 경로, form의 action과 일치시켜주자. login주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행해줌
				// --> 안되고 있다.. (해결)
				.loginProcessingUrl("/login")
				// .defaultSuccessUrl 사용자가 특정 페이지로 이동 -> 로그인 필요 -> 로그인 -> 그 특정페이지로 이동 시켜줌
				.defaultSuccessUrl("/")
				// 로그인 요청 모두 허용
				.permitAll()
				
				// 구글로 로그인하기
				// 코드받기(인증완료)  - > 엑세스토큰(권한) -> 사용자 프로필 정보 들고옴 -> 이걸 통해 회원가입 자동 진행(이메일, 전화번호, 이름, 아이디는 디폴트로 제공) -> 
				// 또는 정보를 좀 더 받고 싶다면 회원가입 페이지를 띄워서 추가 정보를 입력하게 해줘야함 (요즘 쇼핑몰들이 하는 방식)
				.and()
				.oauth2Login()
				.loginPage("/loginForm")
				// 로그인 후처리 시작, 엑세스토큰과 프로필 정보 가져오기
				.userInfoEndpoint()
				.userService(oauth2UserService);
				
//				.and()
//				.logout()
//				 시큐리티가 웹을 처리하는 방식은 httpSession, 로그아웃시 session 지우기
//				.logoutUrl("/logout").invalidateHttpSession(true);
				
	}

}