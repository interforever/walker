
package com.maxtop.walker.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AuthenticateFilter implements Filter {
	
	private String ignore;
	
	public void setIgnore(String ignore) {
		this.ignore = ignore;
	}
	
	public void destroy() {
		// do nothing	    
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String[] keys = ignore.split(",");
		String uri = request.getRequestURI();
		for (String key : keys) {
			if (uri.contains(key)) {
				filterChain.doFilter(req, res);
				return;
			}
		}
		if (request.getSession().getAttribute("username") == null) throw new RuntimeException("No login!");
		filterChain.doFilter(req, res);
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		// do nothing
	}
	
}
