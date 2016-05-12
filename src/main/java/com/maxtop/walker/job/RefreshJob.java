
package com.maxtop.walker.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.maxtop.walker.cache.PlayerItemRepository;
import com.maxtop.walker.cache.PlayerRepository;
import com.maxtop.walker.model.Player;
import com.maxtop.walker.model.Player.Role;
import com.maxtop.walker.service.PlayerService;
import com.maxtop.walker.utils.SpringBeanUtils;

public class RefreshJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			PlayerRepository playerRepository = (PlayerRepository) SpringBeanUtils.getBean("playerRepository");
			PlayerService playerService = (PlayerService) SpringBeanUtils.getBean("playerServiceImpl");
			for (Player player : playerService.list()) {
				Player p = playerRepository.getById(player.getPlayerid());
				p.setMoney(player.getMoney());
				p.setMoneyRank(player.getMoneyRank());
				p.setRank(player.getRank());
				p.setTudou(player.getTudou());
			}
			for (Player player : playerRepository.list()) {
				if (Role.isBuilding(Role.getByName(player.getRole()))) continue;
				player.setAudience(playerService.getPlayerAudienceCount(player.getPlayerid()));
			}
			PlayerItemRepository playerItemRepository = (PlayerItemRepository) SpringBeanUtils.getBean("playerItemRepository");
			playerItemRepository.refresh();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
