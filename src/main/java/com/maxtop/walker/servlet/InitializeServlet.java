
package com.maxtop.walker.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.maxtop.walker.utils.SpringBeanUtils;

public class InitializeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 168993369975577449L;
	
	@Override
	public void init() throws ServletException {
		super.init();
		XmlWebApplicationContext context = (XmlWebApplicationContext) WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		SpringBeanUtils.setContext(context);
	}
	
}
