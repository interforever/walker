
package com.maxtop.walker.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;

public class QuartzContextListener implements ServletContextListener {
	
	/*
	 * ≤‚ ‘¥˙¬Î–¥µ√ÀÊ±„
	 * @seejavax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		WebApplicationContext webApplicationContext = (WebApplicationContext) arg0.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		org.quartz.impl.StdScheduler startQuertz = (org.quartz.impl.StdScheduler) webApplicationContext.getBean("startQuertz");
		if (startQuertz != null) {
			startQuertz.shutdown();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		// do nothing
	}
}
