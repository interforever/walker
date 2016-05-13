
package com.maxtop.walker.service;

import java.util.List;
import java.util.Map;

import com.maxtop.walker.model.PlayerItem;

public interface PlayerItemService {
	
	public Map<String, List<PlayerItem>> list();
	
	public List<Map<String, Object>> getSimpleList();
	
	public List<Map<String, Object>> getAllowList();
	
	public void update(String playerid, String itemid, Map<String, Object> parameters);
	
	public void setPlayerItemAllowable(String playerid, String itemid, Integer allow);
	
	public void setAllPlayerItemsAllow();
	
	public void setAllPlayerItemsDisallow();
	
}
