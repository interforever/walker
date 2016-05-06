
package com.maxtop.walker.dao;

import java.util.List;

import com.maxtop.walker.model.PlayerItem;

public interface PlayerItemDao {
	
	List<PlayerItem> getPlayerItems();
	
	List<PlayerItem> getPlayerItem(String playerid, Integer itemId);
	
	void addPlayerItem(String playerid, Integer itemId, Integer usedAmount);
	
	void updateUsedAmount(String playerid, Integer itemId, Integer usedAmount);
	
}
