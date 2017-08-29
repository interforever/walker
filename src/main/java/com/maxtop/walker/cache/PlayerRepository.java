
package com.maxtop.walker.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.maxtop.walker.model.Player;
import com.maxtop.walker.service.PlayerService;

@Service
public class PlayerRepository implements InitializingBean, DisposableBean {
	
	private static final Log logger = LogFactory.getLog(PlayerRepository.class);
	
	private Map<String, Player> playerMap = new HashMap<String, Player>();
	
	private Map<String, String> telIdMap = new HashMap<String, String>();
	
	@Autowired
	private PlayerService playerService;
	
	synchronized public void refresh() {
		logger.info("Start refreshing players!");
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
	
	synchronized public Collection<Player> list() {
		return playerMap.values();
	}
	
	synchronized public Player getById(String playerid) {
		return playerMap.get(playerid);
	}
	
	synchronized public Player getById(Integer id) {
		return getById(id.toString());
	}
	
	synchronized public Player getByTel(String tel) {
		return getById(telIdMap.get(tel));
	}
	
	synchronized public void removePlayer(String playerid) {
		Player player = playerMap.remove(playerid);
		telIdMap.remove(player.getTel());
	}
	
	public void destroy() throws Exception {
		clear();
	}
	
	public void afterPropertiesSet() throws Exception {
//		refresh();
	}
	
}
