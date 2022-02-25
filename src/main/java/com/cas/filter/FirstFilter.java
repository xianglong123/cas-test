package com.cas.filter;
 
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import org.springframework.core.annotation.Order;

@Order(1)
@WebFilter(filterName="firstFilter", urlPatterns="/*")
public class FirstFilter implements Filter {
 
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("first filter 1");
		System.out.println("before:" + response);
		chain.doFilter(request, response);
		System.out.println("after:" + response);
		System.out.println("first filter 2");
	}
 
	@Override
	public void destroy() {
	}
}