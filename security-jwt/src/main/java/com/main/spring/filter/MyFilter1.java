package com.main.spring.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyFilter1 implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		// ����������� ��ū�� ���� ������ �ִ��� ������ �̷��� ���� �ɾ üũ��
		// ��ū�� ������ ���Ϳ��� �������� �ѱ��, �ƴϸ� �ٽ� ���ư�
		// �� ���ʹ� ��ť��Ƽ ���Ͱ� �������� ����Ǿ� �Ѵ�. ������ 
		
		/**
		 * ��ū : cos �̰� �������� �Ѵ�. id�� pw�� ���� �α����� �Ϸ�Ǹ� ��ū ���� --> ����
		 * ��û�Ҷ����� header�� Authorization�� value������ ��ū�� ������ ��
		 * �� ��ū�� �Ѿ�ö����� �׳� ���� ������ �ϸ� �� (RSA, HS256)
		 * */
		
		if(req.getMethod().equals("POST")) {
			System.out.println("����Ʈ ��û");
			String headerAuth = req.getHeader("Authorization");  // �޾ƿ���
			System.out.println(headerAuth);
			
			if(headerAuth.equals("cos")) {
				chain.doFilter(req, res);
			}else {
				PrintWriter outPrintWriter = res.getWriter();
				outPrintWriter.println("����xxx");
			}
		}
	}
}
