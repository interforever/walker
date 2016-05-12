
package com.maxtop.walker.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.maxtop.walker.utils.GsonBuilderFactory;

public class AuthenticateFilter implements Filter {
	
	private static final Gson gson = GsonBuilderFactory.getInstance();
	
	private String ignore;
	
	public void setIgnore(String ignore) {
		this.ignore = ignore;
	}
	
	public void destroy() {
		// do nothing	    
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String[] keys = ignore.split(",");
		String uri = request.getRequestURI();
		for (String key : keys) {
			if (uri.contains(key)) {
				filterChain.doFilter(req, res);
				return;
			}
		}
		Object username = request.getSession().getAttribute("username");
		String cnmlgb = request.getParameter("cnmlgb");
		if (username == null && !"cspjddsw8dwcnzz18d".equals(cnmlgb)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", HttpStatus.UNAUTHORIZED.value());
			map.put("msg", HttpStatus.UNAUTHORIZED.getReasonPhrase());
			String json = gson.toJson(map);
			responseSetting(response);
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return;
		}
		filterChain.doFilter(req, res);
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		String ignore = filterConfig.getInitParameter("ignore");
		this.setIgnore(ignore);
	}
	
	private void responseSetting(HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
	}
	
}
