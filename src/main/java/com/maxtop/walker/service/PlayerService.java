
package com.maxtop.walker.service;

import java.util.List;
import java.util.Map;

import com.maxtop.walker.model.Player;

public interface PlayerService {
	
	public List<Player> list();
	
	public void update(String playerid, Map<String, Object> parameters);

	public List<String> getAudienceAvatars(String playerid);
	
}
