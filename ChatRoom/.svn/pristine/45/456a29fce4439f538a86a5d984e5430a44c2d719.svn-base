package com.orlando.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotVisitJspFilter implements Filter {
	private String[] nocheckjsp;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		nocheckjsp = filterConfig.getInitParameter("nocheckjsp").split(",");
		for (int i = 0; i < nocheckjsp.length; i++) {
			System.out.println("nocheckjsp:------>"+nocheckjsp[i]);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		String path = req.getServletPath().substring(1);  // index.jsp
		System.out.println("path:------>"+path);
		if(Arrays.asList(nocheckjsp).contains(path)){
			chain.doFilter(req, resp);
		}else{
			request.getRequestDispatcher("../index.jsp").forward(request, response);
		}
		
	}

	@Override
	public void destroy() {

	}

}
