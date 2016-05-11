
package com.maxtop.walker.job;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RefreshiServerInitializer implements InitializingBean, DisposableBean {
	
	@Value("${mail.send.interval:10000}")
	private long REPEAT_INTERVAL;
	
	private Scheduler scheduler;
	
	public void afterPropertiesSet() throws Exception {
		createJob(RefreshJob.class);
	}
	
	public void destroy() throws Exception {
		scheduler.shutdown();
	}
	
	private void createJob(final Class<? extends Job> jobClass) {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			final JobDetail job = JobBuilder.newJob(jobClass).build();
			@SuppressWarnings("deprecation")
			final Trigger trigger = new SimpleTriggerImpl("mailTrigger", SimpleTrigger.REPEAT_INDEFINITELY, REPEAT_INTERVAL);
			scheduler.scheduleJob(job, trigger);
			scheduler.start();
		} catch (final SchedulerException ex) {
			throw new RuntimeException("Could not create job: " + ex.getMessage(), ex);
		}
	}
	
}
