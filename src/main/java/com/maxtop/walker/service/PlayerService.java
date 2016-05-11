
package com.maxtop.walker.service;

import java.util.List;
import java.util.Map;

import com.maxtop.walker.model.Player;

public interface PlayerService {
	
	public List<Player> list();
	
	public void add(Map<String, Object> parameters);
	
	public void update(String playerid, Map<String, Object> parameters);
	
	public void delete(String playerid);
	
	public int getPlayerAudienceCount(String playerid);
	
	public List<String> getAudienceAvatars(String playerid);
	
	public String getPlayerUpdateUrl();
	
}
