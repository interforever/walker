
package com.maxtop.walker.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maxtop.walker.service.HttpClientService;
import com.maxtop.walker.service.PlayerService;
import com.maxtop.walker.vo.Player;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	@Value("${player.list.url:http://114.80.120.78:9999/api/v1/player/money/rank}")
	private String PLAYER_LIST_URL;
	
	@Autowired
	private HttpClientService httpClientService;
	
	public List<Player> list() {
		List<Player> players = new ArrayList<Player>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) httpClientService.executeService(PLAYER_LIST_URL, null);
		if (!"0".equals((String) map.get("code"))) return null;
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> playerMaps = (List<Map<String, Object>>) map.get("data");
		for (Map<String, Object> playerMap : playerMaps) {
			Player player = new Player();
			player.setStatus((String) playerMap.get("status"));
			player.setMoneyRank((Integer) playerMap.get("moneyRank"));
			player.setTel((String) playerMap.get("tel"));
			player.setName((String) playerMap.get("name"));
			player.setTudou((String) playerMap.get("tudou"));
			player.setZbid((String) playerMap.get("zbid"));
			player.setPlayerid((String) playerMap.get("playerid"));
			player.setMoney((String) playerMap.get("money"));
			player.setZburl((String) playerMap.get("zburl"));
			player.setJail_time((String) playerMap.get("jail_time"));
			player.setRank((Integer) playerMap.get("rank"));
			player.setShowForUser((String) playerMap.get("showForUser"));
			player.setRole((String) playerMap.get("role"));
			player.setAvatar((String) playerMap.get("avatar"));
			player.setLat((String) playerMap.get("lat"));
			player.setRoomId((String) playerMap.get("roomId"));
			player.setLng((String) playerMap.get("lng"));
			player.setShowForPlayer((String) playerMap.get("showForPlayer"));
			player.setDescription((String) playerMap.get("description"));
			players.add(player);
		}
		return players;
	}
	
}
