package com.maxtop.walker.cache;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.maxtop.walker.model.Player;

@Service
public class SettingRepository {
	
	private static final Log logger = LogFactory.getLog(PlayerRepository.class);
	
	private void refresh() {
		logger.info("Start refreshing settings!");
		List<Player> players = playerService.list();
		if (CollectionUtils.isEmpty(players)) {
			logger.info("Refresh players failed!");
		} else {
			clear();
			for (Player player : players) {
				playerMap.put(player.getPlayerid(), player);
				telIdMap.put(player.getTel(), player.getPlayerid());
			}
			logger.info("Refreshing " + playerMap.size() + " players successfully!");
		}
	}
	
	private void clear() {
		playerMap.clear();
		telIdMap.clear();
	}
	
	public Collection<Player> list() {
		return playerMap.values();
	}
	
	public Player getById(String playerid) {
		return playerMap.get(playerid);
	}
	
	public Player getById(Integer id) {
		return getById(id.toString());
	}
	
	public Player getByTel(String tel) {
		return getById(telIdMap.get(tel));
	}
	
	public void destroy() throws Exception {
		clear();
	}
	
	public void afterPropertiesSet() throws Exception {
		refresh();
	}
	
}
