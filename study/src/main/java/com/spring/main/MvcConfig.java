package com.spring.main;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @class : studycom.spring.main.MvcConfig.java
 * @author : soyun8
 * @date : 2021. 11. 3.
 * @desc : 프레임웍 MVC 구성
 */

/*@EnableWebMvc 
 *Spring Boot 의 고유기능을 사용하기 위해서는 @EnableWebMvc 는 사용하지 않습니다.
 *@EnableWebMvc를 사용하면 Spring MVC를 사용하게 되어 직접 MVC 설정을 해주어야 한다는 의미입니다.
*/

//spring bean 등록 방법중 하나, 싱글톤 보장, 스프링컨테이너에서 빈 관리 가능
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	/*
	 * class : addResourceHandlers 정적 콘텐츠를 제공할 리소스 위치를 하나 이상 추가합니다.
	 * method :ResourceHandlerRegistry를 통해 리소스 위치와 이 리소스와 매칭될 url을 등록합니다. 
	 * method :addResourceLocations를 통해 리소스 위치 등록
	 * */
	@Override 
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/* '/js/**'로 호출하는 자원은 '/static/js/' 폴더 아래에서 찾는다. */ 
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		/* '/css/**'로 호출하는 자원은 '/static/css/' 폴더 아래에서 찾는다. */ 
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		/* '/img/**'로 호출하는 자원은 '/static/img/' 폴더 아래에서 찾는다. */ 
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/");
	}
}
