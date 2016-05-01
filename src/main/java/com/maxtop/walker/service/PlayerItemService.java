
package com.maxtop.walker.service;

import java.util.List;
import java.util.Map;

import com.maxtop.walker.model.PlayerItem;

public interface PlayerItemService {
	
	public Map<String, List<PlayerItem>> list();

	public void update(String playerid, String itemid, Map<String, Object> parameters);
	
}
