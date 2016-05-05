
package com.maxtop.walker.cache;

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

import com.maxtop.walker.model.PlayerItem;
import com.maxtop.walker.service.PlayerItemService;

@Service
public class PlayerItemRepository implements InitializingBean, DisposableBean {
	
	private static final Log logger = LogFactory.getLog(PlayerItemRepository.class);
	
	private Map<String, List<PlayerItem>> playerItemsMap = new HashMap<String, List<PlayerItem>>();
	
	@Autowired
	private PlayerItemService playerItemService;
	
	synchronized public void refresh() {
		logger.info("Start refreshing player items!");
		Map<String, List<PlayerItem>> playerItemsMap = playerItemService.list();
		if (CollectionUtils.isEmpty(playerItemsMap)) {
			logger.info("Refresh player items failed!");
		} else {
			clear();
			for (Map.Entry<String, List<PlayerItem>> entry : playerItemsMap.entrySet()) {
				this.playerItemsMap.put(entry.getKey(), entry.getValue());
			}
			logger.info("Refreshing " + playerItemsMap.size() + " players' items successfully!");
		}
	}
	
	private void clear() {
		playerItemsMap.clear();
	}
	
	synchronized public List<PlayerItem> getItemsById(String playerid) {
		return playerItemsMap.get(playerid);
	}
	
	public void destroy() throws Exception {
		clear();
	}
	
	public void afterPropertiesSet() throws Exception {
		refresh();
	}
	
}
