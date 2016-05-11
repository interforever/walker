
package com.maxtop.walker.utils;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class SpringBeanUtils {
	
	private static XmlWebApplicationContext context;
	
	public static Object getBean(String beanName) {
		try {
			return context.getBean(beanName);
		} catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}
	
	public static void setContext(XmlWebApplicationContext context) {
		SpringBeanUtils.context = context;
	}
	
}
