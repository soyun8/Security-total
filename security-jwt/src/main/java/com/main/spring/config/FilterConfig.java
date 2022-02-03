package com.main.spring.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.main.spring.filter.MyFilter1;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<MyFilter1> filter1(){
		FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<MyFilter1>(new MyFilter1());
		bean.addUrlPatterns("/*");
		bean.setOrder(0);		// ���� ���� ��ȣ�� �����߿��� ���� ���� �����
		return bean;
	}
}