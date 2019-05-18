package com.orlando.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*@WebFilter( filterName="",urlPatterns="",initParams={ @WebInitParam(name="",value="") })*/
public class CharSetFilter implements Filter {
	private String charset;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		charset = filterConfig.getInitParameter("charset");
		System.out.println("system charset is "+charset);
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
			request.setCharacterEncoding(this.charset);
			response.setCharacterEncoding(this.charset);
			response.setContentType("text/html; charset="+this.charset);
			chain.doFilter(request, response);
	}
	@Override
	public void destroy() {
		
	}

}
