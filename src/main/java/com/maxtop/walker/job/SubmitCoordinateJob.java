
package com.maxtop.walker.job;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maxtop.walker.cache.PlayerRepository;
import com.maxtop.walker.model.Player;
import com.maxtop.walker.service.HttpClientService;
import com.maxtop.walker.service.PlayerService;
import com.maxtop.walker.utils.SpringBeanUtils;

public class SubmitCoordinateJob implements Job {
	
	private static final Log logger = LogFactory.getLog(SubmitCoordinateJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Start submitting coordinates!");
		try {
			PlayerRepository playerRepository = (PlayerRepository) SpringBeanUtils.getBean("playerRepository");
			HttpClientService httpClientService = (HttpClientService) SpringBeanUtils.getBean("httpClientServiceImpl");
			PlayerService playerService = (PlayerService) SpringBeanUtils.getBean("playerServiceImpl");
			for (Player player : playerRepository.list()) {
				Map<String, Object> postParameters = new HashMap<String, Object>();
				postParameters.put("playerid", player.getPlayerid());
				postParameters.put("lat", player.getLat());
				postParameters.put("lng", player.getLng());
				httpClientService.executePostService(playerService.getPlayerUpdateUrl(), postParameters);
			}
			logger.info("Submitting coordinates successfully!");
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("Submitting coordinates failed!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Submitting coordinates failed!");
		}
	}
	
}
